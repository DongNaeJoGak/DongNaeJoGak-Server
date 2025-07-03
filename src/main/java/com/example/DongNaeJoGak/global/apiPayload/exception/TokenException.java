package com.example.DongNaeJoGak.global.apiPayload.exception;

import com.example.DongNaeJoGak.global.apiPayload.code.BaseErrorCode;

public class TokenException extends GeneralException {

    public TokenException(BaseErrorCode baseErrorCode) { super(baseErrorCode);}
}
