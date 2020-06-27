package com.suulola.forum.exception;

public class SubredditNotFoundException extends RuntimeException {
    public SubredditNotFoundException(String errorMessage) {
        super(errorMessage);
        System.out.println("Error occurred");
        System.out.println(errorMessage);
    }
}
