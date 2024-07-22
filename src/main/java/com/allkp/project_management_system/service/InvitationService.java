package com.allkp.project_management_system.service;

import com.allkp.project_management_system.model.Invitation;
import jakarta.mail.MessagingException;

public interface InvitationService {

    public void sendInvitation(String Email, Long projectId) throws MessagingException;

    public Invitation acceptInvitation(String token, Long userId) throws Exception;

    public String getTokenByUserMail(String userEmail);

    void deleteToken(String token);

}
