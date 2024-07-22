package com.allkp.project_management_system.controller;

import com.allkp.project_management_system.dto.IssueDTO;
import com.allkp.project_management_system.model.Issue;
import com.allkp.project_management_system.model.User;
import com.allkp.project_management_system.request.IssueRequest;
import com.allkp.project_management_system.response.AuthResponse;
import com.allkp.project_management_system.response.MessageResponse;
import com.allkp.project_management_system.service.IssueService;
import com.allkp.project_management_system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/issues")
public class IssueController {

    @Autowired
    private IssueService issueService;

    @Autowired
    private UserService userService;

    @GetMapping("/{issueId}")
    public ResponseEntity<Issue> getIssueById(@PathVariable("issueId") Long issueId) throws Exception {
        return ResponseEntity.ok(issueService.getIssueById(issueId));
    }

    @GetMapping("/project/{projectId}")
    public ResponseEntity<List<Issue>> getIssueByProjectId(@PathVariable("projectId") Long projectId) throws Exception {
        return ResponseEntity.ok(issueService.getIssueByProjectId(projectId));
    }

    @PostMapping
    public ResponseEntity<IssueDTO> createIssue(
                @RequestBody IssueRequest issue,
                @RequestHeader("Authorization") String token
            ) throws Exception {
        User tokenUser = userService.findUserProfileByJwt(token);
        User user = userService.findUserById(tokenUser.getId());

        if(user != null){
            Issue createdIssue = issueService.createIssue(issue, tokenUser);
            IssueDTO issueDTO = new IssueDTO();
            issueDTO.setDescription(createdIssue.getDescription());
            issueDTO.setDueDate(createdIssue.getDueDate());
            issueDTO.setPriority(createdIssue.getPriority());
            issueDTO.setId(createdIssue.getId());
            issueDTO.setPriority(createdIssue.getPriority());
            issueDTO.setProjectID(createdIssue.getProjectID());
            issueDTO.setStatus(createdIssue.getStatus());
            issueDTO.setTitle(createdIssue.getTitle());
            issueDTO.setTags(createdIssue.getTags());
            issueDTO.setAssignee(createdIssue.getAssignee());

            return ResponseEntity.ok(issueDTO);
        }else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

    }

    @PostMapping("/{issueId}")
    public ResponseEntity<MessageResponse> deleteIssue(
            @PathVariable("issueId") Long issueId,
            @RequestHeader("Authorization") String token
    ) throws Exception {
        User user = userService.findUserProfileByJwt(token);
        issueService.deleteIssue(issueId, user.getId());

        MessageResponse response = new MessageResponse();
        response.setMessage("Successfully Issue deleted");

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{issueId}/assignee/{userId}")
    public ResponseEntity<Issue> addUserToIssue(
            @PathVariable Long issueId,
            @PathVariable Long userId
    ) throws Exception {
        Issue issue = issueService.addUsertoIssue(issueId, userId);
        return ResponseEntity.ok(issue);
    }

    @PutMapping("/{issueId}/status/{status}")
    public ResponseEntity<Issue> updateIssueStatus(
            @PathVariable Long issueId,
            @PathVariable String status
    ) throws Exception {
        Issue issue = issueService.updateStatus(issueId, status);
        return ResponseEntity.ok(issue);
    }

}
