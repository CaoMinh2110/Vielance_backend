package com.example.clothing_stores.Exception;

import com.example.clothing_stores.Enums.ErrorCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AppException extends RuntimeException {
    ErrorCode errorCode;

    public AppException (ErrorCode errorCode){
        super();
        this.errorCode = errorCode;
    }
}
