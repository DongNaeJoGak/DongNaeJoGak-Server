package com.example.DongNaeJoGak.global.apiPayload.exception;

import com.example.DongNaeJoGak.global.apiPayload.code.BaseErrorCode;

public class IdeaException extends GeneralException{

    public IdeaException(BaseErrorCode baseErrorCode) {
        super(baseErrorCode);
    }
}
