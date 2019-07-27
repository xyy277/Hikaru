package savvy.wit.framework.core.base.exception.runtime;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : 
 * File name : CreateExcelException
 * Author : zhoujiajun
 * Date : 2019/7/27 9:35
 * Version : 1.0
 * Description : 
 ******************************/
public class CreateExcelException extends BaseRuntimeException {

    public CreateExcelException() {

    }

    /**
     * Constructs a new runtime exception with the specified detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public CreateExcelException(String message) {
        super(message);
    }
}
