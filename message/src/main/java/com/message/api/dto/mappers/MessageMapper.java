//package com.message.api.dto.mappers;
//
//import com.message.api.dto.MessageDTO;
//import com.message.api.dto.TagDTO;
//import com.message.api.dto.UserDTO;
//import com.message.api.entity.MessageEntity;
//import com.message.api.entity.TagEntity;
//import com.message.api.entity.UserEntity;
//import org.hibernate.engine.spi.ManagedEntity;
//import org.mapstruct.Mapper;
//import org.mapstruct.Mapping;
//
//import java.util.Collection;
//import java.util.List;
//import java.util.UUID;
//
//@Mapper
//public abstract class MessageMapper {
//    @Mapping(target="description", source = "entity.description")
//    public MessageDTO toMessageDTO(MessageEntity entity) {
//        MessageDTO messageDTO = new MessageDTO();
//        messageDTO.setUuid(entity.getMessageUuid().toString());
//        //messageDTO.setDescription(entity.getDescription());
//        messageDTO.setTags(toTagDTO(entity.getTags()));
//        messageDTO.setUser(toUserDTO(entity.getUser()));
//        return messageDTO;
//    }
//
//    public MessageEntity toMessage(MessageDTO dto) {
//        MessageEntity message = new MessageEntity();
//        message.setMessageUuid(UUID.fromString(dto.getUuid()));
//        message.setDescription(dto.getDescription());
//        message.setTags(toTag(dto.getTags()));
//        message.setUser(toUser(dto.getUser()));
//        return message;
//    }
//
//    public abstract TagDTO toTagDTO(TagEntity entity);
//    public abstract List<TagDTO> toTagDTO(
//            Collection<TagEntity> tags);
//
//    public abstract TagEntity toTag(TagDTO dto);
//    public abstract List<TagEntity> toTag(
//            Collection<TagDTO> tagsDTO);
//
//    public abstract UserDTO toUserDTO(UserEntity entity);
//
//    public abstract UserEntity toUser(UserDTO dto);
//}
