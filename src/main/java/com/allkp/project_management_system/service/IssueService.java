package com.allkp.project_management_system.service;

import com.allkp.project_management_system.model.Issue;
import com.allkp.project_management_system.model.User;
import com.allkp.project_management_system.request.IssueRequest;

import java.util.List;
import java.util.Optional;

public interface IssueService {

    Issue getIssueById(Long issueId) throws Exception;

    List<Issue> getIssueByProjectId(Long projectId) throws Exception;

    Issue createIssue(IssueRequest issue, User user) throws Exception;

    void deleteIssue(Long issueId, Long userId) throws Exception;

    Issue addUsertoIssue(Long issueId, Long userId) throws Exception;

    Issue updateStatus(Long issueId, String status) throws Exception;

}
