package com.avaliacaoinfuse.config.hangler;

import java.util.List;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiErrorMessage {
	
    public ApiErrorMessage(String message,HttpStatus status) {
		this.message = message;
		this.status = status;
	}
	private HttpStatus status;
    private String message;
    private List<String> errors;

}
