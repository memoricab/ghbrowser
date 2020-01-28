package com.repostats.ghbackend.exception;

import com.repostats.ghbackend.util.ExceptionMessages;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

    @Getter
    private String resourceName;

    @Getter
    private String fieldName;

    @Getter
    private Object fieldValue;

    public ResourceNotFoundException(String resourceName, String fieldName, Object fieldValue) {
        super(String.format(ExceptionMessages.RESOURCE_NOT_FOUND_EXCEPTION, resourceName, fieldName, fieldValue));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }
}
