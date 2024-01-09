package com.message.api.service;

import com.message.api.ApplicationRuntimeException;
import com.message.api.entity.MessageEntity;
import com.message.api.entity.TagEnum;
import com.message.api.repository.MessageRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;

    public MessageServiceImpl(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }


    @Override
    public List<MessageEntity> getAll() {
        return  messageRepository.findAll();
    }

    @Override
    public List<MessageEntity> getMessagesByUserIdAndTag(UUID userId,TagEnum tag) {
        return  (messageRepository.findMessagesEntitiesByUserUuidAndTag(userId.toString(),tag.getValue()));
    }

    @Override
    public MessageEntity createMessage(MessageEntity message) {
        return messageRepository.save(message);
    }

    @Override
    public MessageEntity updateMessage(UUID messageUuid,MessageEntity message) throws RuntimeException{
        Throwable firstException = null;
        Optional<MessageEntity> updateMessage=null;
        try {
            updateMessage =  messageRepository.findById(messageUuid);
        } catch (RuntimeException e) {
            firstException = e;
        } finally {
            if (firstException != null) {
                //.addSuppressed(firstException);
                throw new ApplicationRuntimeException(firstException.getMessage(), "Message not exist with id: " + messageUuid);

            }
            MessageEntity updateMessageEntity = updateMessage.get();
            updateMessageEntity.setDescription(message.getDescription()!=null ?message.getDescription():updateMessageEntity.getDescription());
            updateMessageEntity.setUser(message.getUser()!=null ?message.getUser():updateMessageEntity.getUser());
            updateMessageEntity.setTags(message.getTags()!=null ?message.getTags():updateMessageEntity.getTags());

            return createMessage(updateMessageEntity);
        }
//        Optional<MessageEntity> updateMessage =  messageRepository.findById(messageUuid);
//        if(updateMessage.isEmpty()){
//            throw new ApplicationRuntimeException("1", "Message not exist with id: " + messageUuid);
//        }
//        MessageEntity updateMessageEntity = updateMessage.get();
//        updateMessageEntity.setDescription(message.getDescription()!=null ?message.getDescription():updateMessageEntity.getDescription());
//        updateMessageEntity.setUser(message.getUser()!=null ?message.getUser():updateMessageEntity.getUser());
//        updateMessageEntity.setTags(message.getTags()!=null ?message.getTags():updateMessageEntity.getTags());
//
//        return createMessage(updateMessageEntity);
    }

    @Override
    public void deleteMessage(UUID messageUuid) {
        Optional<MessageEntity> messageToDelete = messageRepository.findById(messageUuid);
        if(messageToDelete.isEmpty()){
            throw new ApplicationRuntimeException("1", "Message not exist with id: " + messageUuid);
        }
        MessageEntity messageToDeleteEntity = messageToDelete.get();
        messageRepository.deleteById(messageToDeleteEntity.getMessageUuid());
    }

    private void createError(String... message){}
    private void validateMessageExist(UUID messageUuid){
        MessageEntity updateMessage = messageRepository.findById(messageUuid)
                .orElseThrow(() -> new RuntimeException("Message not exist with id: " + messageUuid));
    }
}
