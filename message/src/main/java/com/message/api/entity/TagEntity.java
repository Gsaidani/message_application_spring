package com.message.api.entity;

import com.message.api.validation.constraints.TagValidation;
import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "tag")
public class TagEntity {
    public TagEntity() {
    }

    public TagEntity(UUID tagUuid, TagEnum tagName, List<MessageEntity> messages) {
        this.tagUuid = tagUuid;
        this.tagName = tagName;
        this.messages = messages;
    }

    public UUID getTagUuid() {
        return tagUuid;
    }

    public void setTagUuid(UUID tagUuid) {
        this.tagUuid = tagUuid;
    }

    public TagEnum getTagName() {
        return tagName;
    }

    public void setTagName(TagEnum tagName) {
        this.tagName = tagName;
    }

    public List<MessageEntity> getMessages() {
        return messages;
    }

    public void setMessages(List<MessageEntity> messages) {
        this.messages = messages;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "tag_uuid")
    private UUID tagUuid;

    @Column(name = "tag_name")
    @Enumerated(EnumType.STRING)
    //@TagValidation()
    private TagEnum tagName;

    @ManyToMany(mappedBy = "tags",fetch = FetchType.LAZY, cascade = {CascadeType.MERGE})
    private List<MessageEntity> messages;
}
