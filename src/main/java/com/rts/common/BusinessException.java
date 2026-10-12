package com.rts.common;

/** 사용자에게 그대로 보여줄 수 있는 업무 오류 */
public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public BusinessException(String message) {
        super(message);
    }
}
