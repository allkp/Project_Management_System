package com.allkp.project_management_system.controller;

import com.allkp.project_management_system.model.Comment;
import com.allkp.project_management_system.model.User;
import com.allkp.project_management_system.request.CommentRequest;
import com.allkp.project_management_system.response.MessageResponse;
import com.allkp.project_management_system.service.CommentService;
import com.allkp.project_management_system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<Comment> createComment(
            @RequestBody CommentRequest request,
            @RequestHeader("Authorization") String token
            ) throws Exception {
        User user = userService.findUserProfileByJwt(token);
        Comment createdComment = commentService.createComment(
                request.getIssueId(),
                user.getId(),
                request.getContent()
        );
        return new ResponseEntity<>(createdComment, HttpStatus.CREATED);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<MessageResponse> deleteComment(
            @PathVariable Long commentId,
            @RequestHeader("Authorization") String token
    ) throws Exception {
        User user = userService.findUserProfileByJwt(token);
        commentService.deleteComment(commentId, user.getId());
        MessageResponse response = new MessageResponse();
        response.setMessage("Comment deleted successfully......");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{issueId}")
    public ResponseEntity<List<Comment>> getCommentsByIssueId(@PathVariable Long issueId) {
        List<Comment> comments = commentService.findCommentByIssueId(issueId);
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

}
