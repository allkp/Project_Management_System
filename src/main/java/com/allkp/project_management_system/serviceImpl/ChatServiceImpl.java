package com.allkp.project_management_system.serviceImpl;

import com.allkp.project_management_system.model.Chat;
import com.allkp.project_management_system.repository.ChatRepository;
import com.allkp.project_management_system.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatServiceImpl implements ChatService {

    @Autowired
    private ChatRepository chatRepository;

    @Override
    public Chat createChat(Chat chat) {
        return chatRepository.save(chat);
    }
}
