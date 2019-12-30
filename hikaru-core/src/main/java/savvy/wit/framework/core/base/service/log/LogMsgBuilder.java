package savvy.wit.framework.core.base.service.log;

import savvy.wit.framework.core.base.enums.LogType;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : 
 * File name : LogMsgBuilder
 * Author : zhoujiajun
 * Date : 2019/12/30 12:46
 * Version : 1.0
 * Description : 
 ******************************/
public class LogMsgBuilder {

    private static LogMessage message;

    public LogMsgBuilder() {
    }

    public static LogMsgBuilder create() {
        message = new LogMessage();
        return LazyInit.INITIALIZATION;
    }

    public LogMsgBuilder type(LogType type) {
        message.setType(type.name());
        return this;
    }

    public LogMsgBuilder time(String timestamp) {
        message.setTimestamp(timestamp);
        return this;
    }

    public LogMsgBuilder prefix(String... prefixes) {
        StringBuilder prefix = new StringBuilder();
        for (String p : prefixes) {
            prefix.append("[" + p + "]");
        }
        message.setPrefix(prefix.toString());
        return this;
    }

    public LogMsgBuilder param(Object... params) {
        if (params.length > 0)
            message.setParam(params);
        else
            message.setParam(null);
        return this;
    }

    public LogMsgBuilder result(Object result) {
        message.setResult(result);
        return this;
    }


    public LogMsgBuilder suffix(String... suffixes) {
        StringBuilder suffix = new StringBuilder();
        for (String s : suffixes) {
            suffix.append("[" + s + "]");
        }
        message.setSuffix(suffix.toString());
        return this;
    }

    public LogMessage build() {
        return message;
    }

    private static class LazyInit {
        private static LogMsgBuilder INITIALIZATION = new LogMsgBuilder();
    }


}
