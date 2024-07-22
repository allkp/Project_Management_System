package com.allkp.project_management_system.service;

import com.allkp.project_management_system.model.Comment;

import java.util.List;

public interface CommentService {

    Comment createComment(Long issueId, Long UserId, String content) throws Exception;

    void deleteComment(Long commentId, Long UserId) throws Exception;

    List<Comment> findCommentByIssueId(Long issueId);

}
