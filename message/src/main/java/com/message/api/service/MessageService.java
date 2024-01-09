package com.message.api.service;

import com.message.api.entity.MessageEntity;
import com.message.api.entity.TagEnum;

import java.util.List;
import java.util.UUID;

public interface MessageService {
    List<MessageEntity> getAll();
    List<MessageEntity> getMessagesByUserIdAndTag(UUID userUuid, TagEnum tag);
    MessageEntity createMessage(MessageEntity message);
    MessageEntity updateMessage(UUID messageUuid, MessageEntity message);
    void deleteMessage(UUID messageUuid);
}
