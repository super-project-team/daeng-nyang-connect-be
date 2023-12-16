package com.git.backend.daeng_nyang_connect.mypet.board.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.EncryptedPutObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.git.backend.daeng_nyang_connect.exception.FileUploadFailedException;
import com.git.backend.daeng_nyang_connect.mypet.board.entity.MyPet;
import com.git.backend.daeng_nyang_connect.mypet.board.entity.MyPetImage;
import com.git.backend.daeng_nyang_connect.mypet.board.repository.MyPetImageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
@EnableCaching
public class MyPetImgUpload {

    private final AmazonS3Client amazonS3Client;
    private final MyPetImageRepository myPetImageRepository;

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    public String buildFileName(String title,String originFileName){
        String randomString = UUID.randomUUID().toString().substring(0, 8);
        return "myPet" + "/" + title  + "/" + title+randomString;
    }

    public List<String> uploadMyPetImgs(MyPet myPet, String title, List<MultipartFile> multipartFileList){
        List<String> filenameList = new ArrayList<>();
        if(multipartFileList!=null){
            multipartFileList.forEach(file -> {
                try{
                    String fileName = uploadMyPetImg(myPet, title, file);
                    filenameList.add(fileName);
                } catch (FileUploadFailedException e) {
                    throw new RuntimeException(e);
                }
            });
        }else{
            return null;
        }
        return filenameList;
    }

    public String uploadMyPetImg(MyPet myPet, String title, MultipartFile multipartFile) throws FileUploadFailedException{

        String fileName = buildFileName(title, Objects.requireNonNull(multipartFile.getOriginalFilename()));

        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(multipartFile.getContentType());
        objectMetadata.setContentLength(multipartFile.getSize());
        objectMetadata.setContentDisposition("inline");

        try(InputStream inputStream = multipartFile.getInputStream()){
            amazonS3Client.putObject(new EncryptedPutObjectRequest(bucketName, fileName, inputStream,objectMetadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
        }catch (Exception e){
            log.error("Amazon S3 파일 업로드 실패: {}", e.getMessage(), e);
            throw new FileUploadFailedException("파일 업로드에 실패했습니다");
        }
        MyPetImage myPetImage = MyPetImage.builder()
                .myPet(myPet)
                .url(amazonS3Client.getUrl(bucketName, fileName).toString())
                .build();

        if(multipartFile.isEmpty()){
            return null;
        }
        myPetImageRepository.save(myPetImage);

        return amazonS3Client.getUrl(bucketName, fileName).toString();
    }

    public String uploadModifyMyPetImg(MyPet myPet, String title, MultipartFile multipartFile) throws FileUploadFailedException {
        // 기존 이미지 삭제
        deleteMyPetImg(myPet.getMyPetBoardId());

        // 새 이미지 추가
        String fileName = buildFileName(title, Objects.requireNonNull(multipartFile.getOriginalFilename()));
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(multipartFile.getContentType());
        objectMetadata.setContentLength(multipartFile.getSize());
        objectMetadata.setContentDisposition("inline");

        try (InputStream inputStream = multipartFile.getInputStream()) {
            amazonS3Client.putObject(new EncryptedPutObjectRequest(bucketName, fileName, inputStream, objectMetadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
        } catch (Exception e) {
            log.error("Amazon S3 파일 업로드 실패: {}", e.getMessage(), e);
            throw new FileUploadFailedException("파일 업로드에 실패했습니다");
        }

        // 데이터베이스에 새 이미지 정보 저장
        MyPetImage myPetImage = MyPetImage.builder()
                .myPet(myPet)
                .url(amazonS3Client.getUrl(bucketName, fileName).toString())
                .build();
        myPetImageRepository.save(myPetImage);

        return amazonS3Client.getUrl(bucketName, fileName).toString();
    }

    // 이미지 삭제 메서드
    public void deleteMyPetImg(Long myPetId) {
        // 기존 이미지 정보 조회
        List<MyPetImage> myPetImages = myPetImageRepository.findByMyPet_MyPetBoardId(myPetId);

        // 기존 이미지 삭제
        myPetImages.forEach(image -> {
            // Amazon S3에서 이미지 삭제
            deleteImageFromS3(image.getUrl());

            // DB에서 이미지 정보 삭제
            myPetImageRepository.delete(image);
        });
    }
    // Amazon S3에서 이미지 삭제하는 메서드
    private void deleteImageFromS3(String imageUrl) {
        try {
            String fileName = extractFileNameFromUrl(imageUrl);
            amazonS3Client.deleteObject(bucketName, fileName);
        } catch (Exception e) {
            log.error("Amazon S3 파일 삭제 실패: {}", e.getMessage(), e);
            // 예외 처리: 파일 삭제 실패 시 로그를 남기거나 다른 방식으로 처리할 수 있습니다.
        }
    }

    // 이미지 URL에서 파일명 추출
    private String extractFileNameFromUrl(String imageUrl) {
        try {
            URI uri = new URI(imageUrl);
            String path = uri.getPath();
            return path.substring(path.lastIndexOf('/') + 1);
        } catch (URISyntaxException e) {
            log.error("이미지 URL에서 파일명 추출 실패: {}", e.getMessage(), e);
            throw new RuntimeException("이미지 URL에서 파일명 추출 실패");
        }
    }
    @Transactional
    public List<MyPetImage> getMyPetImg(MyPet myPet){
        List<MyPetImage> myPetImages = myPetImageRepository.findByMyPet(myPet)
                .orElseThrow();
        return myPetImages;
    }
}
