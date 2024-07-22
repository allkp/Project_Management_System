package com.allkp.project_management_system.serviceImpl;

import com.allkp.project_management_system.model.Chat;
import com.allkp.project_management_system.model.Message;
import com.allkp.project_management_system.model.User;
import com.allkp.project_management_system.repository.MessageRepository;
import com.allkp.project_management_system.repository.UserRepository;
import com.allkp.project_management_system.service.MessageService;
import com.allkp.project_management_system.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProjectService projectService;

    @Override
    public Message sendMessage(Long senderId, Long projectId, String content) throws Exception {
        User user = userRepository.findById(senderId)
                .orElseThrow(() -> new Exception("User Not Found with this id : " + senderId));

        Chat chat = projectService.getProjectById(projectId).getChat();

        Message message = new Message();
        message.setContent(content);
        message.setSender(user);
        message.setCreatedAt(LocalDateTime.now());
        message.setChat(chat);

        Message savedMessage = messageRepository.save(message);

        chat.getMessages().add(savedMessage);

        return savedMessage;
    }

    @Override
    public List<Message> getMessageByProjectId(Long projectId) throws Exception {
        Chat chat = projectService.getChatByProjectId(projectId);

        return messageRepository.findByChatIdOrderByCreatedAtAsc(chat.getId());
    }
}
