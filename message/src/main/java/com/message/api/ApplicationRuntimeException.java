package com.message.api;

import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class ApplicationRuntimeException extends RuntimeException {

    public static class ApplicationError {

        final String error;

        final String message;

        public ApplicationError(String error, String message) {
            this.error = error;
            this.message = message;

        }

        public String getError() {
            return error;
        }

        public String getMessage() {
            return message;
        }
    }

    private final ApplicationError applicationError;


    public ApplicationRuntimeException(String error, String message) {
        applicationError = new ApplicationError(error, message);
    }

    public ApplicationError getApplicationError(){
        return applicationError;
    }

}
