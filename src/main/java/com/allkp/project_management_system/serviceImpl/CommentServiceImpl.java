package com.allkp.project_management_system.serviceImpl;

import com.allkp.project_management_system.model.Comment;
import com.allkp.project_management_system.model.Issue;
import com.allkp.project_management_system.model.User;
import com.allkp.project_management_system.repository.CommentRepository;
import com.allkp.project_management_system.repository.IssueRepository;
import com.allkp.project_management_system.repository.UserRepository;
import com.allkp.project_management_system.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private IssueRepository issueRepository;

    @Override
    public Comment createComment(Long issueId, Long UserId, String content) throws Exception {
        Optional<Issue> issueOptional = issueRepository.findById(issueId);
        Optional<User> userOptional = userRepository.findById(UserId);

        if(issueOptional.isEmpty()){
            throw new Exception("issue not found with id : " + issueId);
        }

        if(userOptional.isEmpty()){
            throw new Exception("user not found with id : " + UserId);
        }

        Issue issue = issueOptional.get();
        User user = userOptional.get();

        Comment comment = new Comment();

        comment.setIssue(issue);
        comment.setUser(user);
        comment.setCreateDateTime(LocalDateTime.now());
        comment.setContent(content);

        Comment savedComment = commentRepository.save(comment);

        issue.getComments().add(savedComment);

        return savedComment;
    }

    @Override
    public void deleteComment(Long commentId, Long UserId) throws Exception {
        Optional<Comment> commentOptional = commentRepository.findById(commentId);
        Optional<User> userOptional = userRepository.findById(UserId);

        if(commentOptional.isEmpty()){
            throw new Exception("comment not found with id : " + commentId);
        }

        if(userOptional.isEmpty()){
            throw new Exception("user not found with id : " + UserId);
        }

        Comment comment = commentOptional.get();
        User user = userOptional.get();

        if(comment.getUser().equals(user)){
            commentRepository.delete(comment);
        }else{
            throw new Exception("User does not have permission to delete this comment!");
        }

    }

    @Override
    public List<Comment> findCommentByIssueId(Long issueId) {
        return commentRepository.findByIssueId(issueId);
    }
}
