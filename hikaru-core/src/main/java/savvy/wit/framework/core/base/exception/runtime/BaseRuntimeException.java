package savvy.wit.framework.core.base.exception.runtime;

import org.springframework.http.HttpStatus;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : 
 * File name : BaseRuntimeException
 * Author : zhoujiajun
 * Date : 2019/7/27 9:39
 * Version : 1.0
 * Description : 
 ******************************/
public class BaseRuntimeException extends RuntimeException {

    protected String code;

    protected String message;

    protected HttpStatus status;

    protected Object data;

    /**
     * Constructs a new runtime exception with {@code null} as its
     * detail message.  The cause is not initialized, and may subsequently be
     * initialized by a call to {@link #initCause}.
     */
    public BaseRuntimeException() {
    }

    /**
     * Constructs a new runtime exception with the specified detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public BaseRuntimeException(String message) {
        super(message);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
