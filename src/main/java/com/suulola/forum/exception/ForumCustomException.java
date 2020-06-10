package com.suulola.forum.exception;

public class ForumCustomException extends RuntimeException {
    public ForumCustomException(String errorMessage) {
        super(errorMessage);
        System.out.println("Error occurred");
        System.out.println(errorMessage);
    }
}
