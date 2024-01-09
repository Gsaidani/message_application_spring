package com.message.api;


import org.joda.time.LocalDateTime;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice()
public class ControllerAdvisor extends ResponseEntityExceptionHandler {
    ResponseEntity<Object> handleApplicationRuntimeException(ApplicationRuntimeException e) {
//        logger.error(e.getApplicationError(), e);
//        logger.warn("error: " + e.getClass().getName());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(((ApplicationRuntimeException) e).getApplicationError());
    }

    @ExceptionHandler(ApplicationRuntimeException.class)
    ResponseEntity<Object> handleException(Exception e) {
        logger.error("Exception: ", e);
        //Throwable firstException = e;

        if (e instanceof ApplicationRuntimeException be) {
//            be.addSuppressed(e);
//            System.out.println("suppressed : "+be.getSuppressed());
            return handleApplicationRuntimeException(be);
//        } else if (logger.isWarnEnabled()) {
//            logger.warn("Unknown exception type: " + e.getClass().getName());
//
//            HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
//            return handleExceptionInternal(e, "Unknown exception type", null, status, null);
            //AuthenticationException
        }else  if (e instanceof AuthenticationException) {
            OAuth2Error error = ((OAuth2AuthenticationException) e).getError();
            Map<String, Object> body = new LinkedHashMap<>();
            body.put("timestamp", LocalDateTime.now());
            body.put("message", "delegating Authentication Access Denied");
            body.put("error", error.getErrorCode());
            return new ResponseEntity<>(body, HttpStatus.FORBIDDEN);
        }else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
