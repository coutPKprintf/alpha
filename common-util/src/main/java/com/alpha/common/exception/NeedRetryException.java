package com.alpha.common.exception;

import com.alpha.common.BaseException;

/**
 * Created by cw on 15-11-20.
 */
public class NeedRetryException extends BaseException {
    private String message;

    public NeedRetryException(IErrorCode iErrorCode) {
        super(iErrorCode);
        this.message = iErrorCode.getMessage();
    }

    @Override
    public String getMessage() {
        return message;
    }
}
