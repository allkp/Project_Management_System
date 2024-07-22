package com.allkp.project_management_system.request;

import lombok.Data;

@Data
public class CommentRequest {

    private Long issueId;

    private String content;
}
