package com.blog.JavaSpringBoot.dto.response;

import lombok.Getter;
import lombok.Setter;

public class ResponseBase<Any> {

	@Getter
	@Setter
	private boolean status = false;
	
	@Getter
	@Setter
	private String code = "500";
	
	@Getter
	@Setter
	private String message = "internal server error";

	@Getter
	@Setter
	private Any data;
}
	