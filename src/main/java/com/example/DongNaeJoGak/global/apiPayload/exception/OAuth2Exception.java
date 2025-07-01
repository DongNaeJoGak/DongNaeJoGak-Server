package com.example.DongNaeJoGak.global.apiPayload.exception;

import com.example.DongNaeJoGak.global.apiPayload.code.BaseErrorCode;

public class OAuth2Exception extends GeneralException {

    public OAuth2Exception(BaseErrorCode baseErrorCode) { super(baseErrorCode);}

}