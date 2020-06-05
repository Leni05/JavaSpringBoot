package com.blog.JavaSpringBoot.service;

public interface LoggingService {
    public Boolean createLooging(String type, String method, String code, String message, String description, String time);
}