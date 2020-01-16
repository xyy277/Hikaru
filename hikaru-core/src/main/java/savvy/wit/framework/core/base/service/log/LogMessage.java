package savvy.wit.framework.core.base.service.log;

import java.io.Serializable;
import java.util.Arrays;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : 
 * File name : LogMessage
 * Author : zhoujiajun
 * Date : 2019/12/30 12:45
 * Version : 1.0
 * Description : 
 ******************************/
public class LogMessage implements Serializable {

    private static final long serialVersionUID = -459867260744413972L;

    private String type;

    private String timestamp;

    private String prefix;

    private Object[] param;

    private Object result;

    private String suffix;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public Object[] getParam() {
        return param;
    }

    public void setParam(Object[] param) {
        this.param = param;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    @Override
    public String toString() {
        return "LogMessage{" +
                "type='" + type + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", prefix='" + prefix + '\'' +
                ", param=" + Arrays.toString(param) +
                ", result=" + result +
                ", suffix='" + suffix + '\'' +
                '}';
    }
}
