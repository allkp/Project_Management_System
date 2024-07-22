package com.allkp.project_management_system.controller;

import com.allkp.project_management_system.model.Chat;
import com.allkp.project_management_system.model.Message;
import com.allkp.project_management_system.model.User;
import com.allkp.project_management_system.request.MessageRequest;
import com.allkp.project_management_system.service.MessageService;
import com.allkp.project_management_system.service.ProjectService;
import com.allkp.project_management_system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @Autowired
    private UserService userService;

    @Autowired
    private ProjectService projectService;


    @PostMapping("/send")
    public ResponseEntity<Message> sendMessage(@RequestBody MessageRequest request) throws Exception{

        User user = userService.findUserById(request.getSenderId());

        Chat chats = projectService.getProjectById(request.getProjectId()).getChat();

        if(chats == null){
            throw new Exception("Chats not found....");
        }

        Message sentMessage = messageService.sendMessage(
                request.getSenderId(),
                request.getProjectId(),
                request.getContent()
        );

        return ResponseEntity.ok(sentMessage);
    }

    @GetMapping("/chat/{projectId}")
    public ResponseEntity<List<Message>> getMessagesByChatId(
            @PathVariable Long projectId
    ) throws Exception{
        List<Message> messages = messageService.getMessageByProjectId(projectId);

        return ResponseEntity.ok(messages);
    }

}
