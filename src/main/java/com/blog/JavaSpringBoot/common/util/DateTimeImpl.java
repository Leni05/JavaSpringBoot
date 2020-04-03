package com.blog.JavaSpringBoot.common.util;


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