package com.team1.DailyBox.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {
    EMOJI_NOT_FOUND(400, "ToDo를 찾지 못했습니다."),
    COUNT_OVERFLOW(400, "ToDo 횟수가 목표치를 초과했습니다."),
    COUNT_ALREADY_DONE(400, "ToDo 횟수가 이미 완료되었습니다."),
    ;

    private final int code;
    private final String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
