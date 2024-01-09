package com.message.api.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;
import java.util.UUID;

    @Entity
    @Table(name = "message")
    public class MessageEntity {

        public UUID getMessageUuid() {
            return messageUuid;
        }

        public void setMessageUuid(UUID messageUuid) {
            this.messageUuid = messageUuid;
        }

        public String getDescription() {
            return description;
        }

        public MessageEntity(UUID messageUuid, String description, List<TagEntity> tags, UserEntity user) {
            this.messageUuid = messageUuid;
            this.description = description;
            this.tags = tags;
            this.user = user;
        }

        public MessageEntity() {
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public List<TagEntity> getTags() {
            return tags;
        }

        public void setTags(List<TagEntity> tags) {
            this.tags = tags;
        }

        public UserEntity getUser() {
            return user;
        }

        public void setUser(UserEntity user) {
            this.user = user;
        }

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        @Column(name = "message_uuid")
        private UUID messageUuid;

        @Column(name = "message_description")
        private String description;

        @JsonIgnore
        @ManyToMany(fetch = FetchType.LAZY)
        @JoinTable(
                name = "message_tag",
                joinColumns = @JoinColumn(name = "message_uuid"),
                inverseJoinColumns = @JoinColumn(name = "tag_uuid"))
        @NotEmpty
        private List<TagEntity> tags;
        @JsonIgnore
        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "user_uuid")
        private UserEntity user;

    }
