package com.message.api.controller;

import com.message.api.ApplicationRuntimeException;
import com.message.api.entity.MessageEntity;
import com.message.api.entity.TagEnum;
import com.message.api.service.MessageService;
import com.message.api.validation.constraints.TagValidation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@RestController
@RequestMapping(path = "message", produces = { MediaType.APPLICATION_JSON_VALUE })
@Validated
public class MessageController {
    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

//    @GetMapping
//    //@PreAuthorize("hasAuthority('SCOPE_ADMIN')")
//    public Map<String,Object> dataTest(Authentication authentication){
//
////                if( !authentication.getAuthorities().contains("SCOPE_ADMIN")){
////                    return Map.of(
////                            "message", "probleme d'authentification/authorisation",
////                            "authorities", authentication.getAuthorities()
////                    );
////                }
//
//        return Map.of(
//                "message", "Data test",
//                "username", authentication.getName(),
//                "authorities", authentication.getAuthorities()
//        );
//    }

    @GetMapping()
    public @ResponseBody List<MessageEntity> getAll() {

        return this.messageService.getAll();

    }

    @GetMapping("/{user-uuid}/{tag-name}")
    public  @ResponseBody List<MessageEntity> getAllByUserAndTagName(@PathVariable("user-uuid") UUID userUuid, @PathVariable("tag-name") @NotBlank @TagValidation @Pattern(regexp = "^Tag[123]$") String tagName)  {

        return this.messageService.getMessagesByUserIdAndTag(userUuid,TagEnum.fromValue(tagName));
    }

    @PostMapping()
    public @ResponseBody MessageEntity addMessage(@RequestBody @Valid MessageEntity message) {

        return this.messageService.createMessage(message);
    }

    @PutMapping("/{message-uuid}")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public @ResponseBody MessageEntity updateMessage(@PathVariable("message-uuid") UUID messageUuid, @RequestBody MessageEntity message) {

        try {
            return this.messageService.updateMessage(messageUuid, message);
        } catch (Exception e) {
            throw new ApplicationRuntimeException("Probleme d'authentification/authorisation: ", e.getMessage());

        }
    }


    @DeleteMapping("/{message-uuid}")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public void deleteMessage(@PathVariable("message-uuid") UUID messageUuid, BindingResult result) {
        try {
            this.messageService.deleteMessage(messageUuid);
        } catch (Exception e) {
            throw new ApplicationRuntimeException("Probleme d'authentification/authorisation: ", e.getMessage());

        }
    }



}
