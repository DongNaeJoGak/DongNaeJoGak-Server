package com.example.DongNaeJoGak.global.apiPayload.exception;

import com.example.DongNaeJoGak.global.apiPayload.code.BaseErrorCode;

public class MemberException extends GeneralException {

    public MemberException(BaseErrorCode baseErrorCode) { super(baseErrorCode); }
}
