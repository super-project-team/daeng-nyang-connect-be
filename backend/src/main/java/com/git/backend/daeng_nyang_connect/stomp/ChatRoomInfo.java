package com.git.backend.daeng_nyang_connect.stomp;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatRoomInfo {
    private Long chatRoomId;
    List<ChatRoomUserListDTO> userList = new ArrayList<>();
    private String animalImage;
    private String animalName;
    private String animalAge;
    private String breed;

    public ChatRoomInfo(ChatRoom chatRoom) {
        this.chatRoomId = chatRoom.getChatRoomId();
        for(ChatRoomUser chatRoomUser:chatRoom.getUserList()) {
            ChatRoomUserListDTO userList = new ChatRoomUserListDTO(chatRoomUser);
            this.userList.add(userList);
        }
        this.animalAge = chatRoom.getAnimal().getAge();
        this.animalImage = chatRoom.getAnimal().getImages().get(0).getUrl();
        this.animalName = chatRoom.getAnimal().getAnimalName();
        this.breed = chatRoom.getAnimal().getBreed();
    }
}
