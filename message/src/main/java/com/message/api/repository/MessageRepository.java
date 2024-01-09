package com.message.api.repository;

import com.message.api.entity.MessageEntity;
import com.message.api.entity.TagEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MessageRepository extends JpaRepository<MessageEntity, UUID> {
    @Query(value = "SELECT" +
            "  message.message_uuid, message.message_description, message.user_uuid, message_tag.tag_uuid" +
            "  FROM message" +
            "  INNER JOIN message_tag" +
            "    ON message.message_uuid = message_tag.message_uuid" +
            "  JOIN tag" +
            "    ON tag.tag_uuid = message_tag.tag_uuid" +
            "  WHERE tag.tag_name = :tag" +
            "    AND message.user_uuid = :userUuid", nativeQuery = true)
    List<MessageEntity> findMessagesEntitiesByUserUuidAndTag(@Param("userUuid") String userUuid, @Param("tag") String tag);

}

