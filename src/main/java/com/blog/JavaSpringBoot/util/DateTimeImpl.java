package com.blog.JavaSpringBoot.util;


import java.util.Date;

import org.springframework.stereotype.Service;

/**
 * DateTimeImpl
 */
@Service
public class DateTimeImpl implements DateTime{
    @Override
    public Date getCurrentDate() {
        return new Date();
    }

}