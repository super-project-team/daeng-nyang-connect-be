package com.git.backend.daeng_nyang_connect.websocket;

import lombok.Data;

@Data
public class ChatDto {
    private Integer channelId; //각 구독 채널을 구분할 수 있는 식별자
    private Integer writerId;
    private String chat;
}
