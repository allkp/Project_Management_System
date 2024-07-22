package com.allkp.project_management_system.request;

import lombok.Data;

@Data
public class MessageRequest {

    private Long senderId;

    private String content;

    private Long projectId;

}
