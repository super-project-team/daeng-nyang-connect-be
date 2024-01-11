package com.git.backend.daeng_nyang_connect.stomp;

import com.git.backend.daeng_nyang_connect.animal.entity.Animal;
import com.git.backend.daeng_nyang_connect.user.entity.User;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatRoomResponseDTO {
    private Long chatRoomId;
    List<ChatRoomUserListDTO> userList = new ArrayList<>();
//    private String animalImage;
//    private String animalName;
//    private String animalAge;
//    private String breed;

    public ChatRoomResponseDTO(ChatRoom chatRoom) {
        this.chatRoomId = chatRoom.getChatRoomId();

        for(ChatRoomUser chatRoomUser:chatRoom.getUserList()) {
            ChatRoomUserListDTO userList = new ChatRoomUserListDTO(chatRoomUser);
            this.userList.add(userList);
        }
    }
}
