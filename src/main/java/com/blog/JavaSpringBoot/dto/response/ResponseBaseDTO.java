package com.blog.JavaSpringBoot.dto.response;

import java.util.LinkedHashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ResponseBaseDTO<T> {
    private boolean status;
    private String code;
    private String message;
    private T data;

    public static ResponseBaseDTO error(String code, String message) {
        return new ResponseBaseDTO<>(false, code, message, null);
    }

    public static ResponseBaseDTO ok() {
        return new ResponseBaseDTO<>(true, "200", "success", null);
    }

    public static <I> ResponseBaseDTO<I> ok(I body) {
        return new ResponseBaseDTO<I>(true, "200", "success", body);
    }

    public static ResponseBaseDTO created() {
        return new ResponseBaseDTO<>(true, "201", "created", null);
    }

    public static ResponseBaseDTO created(String uri) {
        ResponseBaseDTO<Map> baseResponse = new ResponseBaseDTO<>();
        baseResponse.setStatus(true);
        baseResponse.setCode("201");
        baseResponse.setMessage("created");
        Map<String, String> map = new LinkedHashMap<>();
        map.put("uri", uri);
        baseResponse.setData(map);
        return baseResponse;
    }
}