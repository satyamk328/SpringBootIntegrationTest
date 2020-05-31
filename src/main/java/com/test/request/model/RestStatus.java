package com.test.request.model;


import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
/**
 * This is a wrapper class containing response to be send with appropriate message, code and description
 * @author satyam.kumar
 *
 * @param <T>
 */
@Getter
@Setter
@NoArgsConstructor
public class RestStatus <T>{

    private String code;
    private String message;
    
    public RestStatus(final HttpStatus status, final String statusMessage) {
        this.code = Integer.toString(status.value());
        this.message = statusMessage;
    }

}
