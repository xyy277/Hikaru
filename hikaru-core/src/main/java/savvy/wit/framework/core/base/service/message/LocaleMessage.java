package savvy.wit.framework.core.base.service.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import savvy.wit.framework.core.base.util.Strings;

import java.util.Locale;
import java.util.Map;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : 
 * File name : LocalMessage
 * Author : zhoujiajun
 * Date : 2019/7/26 10:52
 * Version : 1.0
 * Description : 
 ******************************/
@Component
@Lazy
public class LocaleMessage {

    @Value("${language}")
    private String language;

    private String result;

    private Locale locale;

    @Autowired
    private MessageSource messageSource;

    public MessageSource getMessageSource() {
        return messageSource;
    }

    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public LocaleMessage() {

    }

    public String getMessage(String key) {
        if (this.locale == null) {
            if (language != null)
                this.locale = new Locale(language);
            else
            this.locale = LocaleContextHolder.getLocale();
        }
        return messageSource.getMessage(key, null, this.locale);
    }

    public String[] getMessage(String... keys) {
        String[] values = new String[keys.length];
        for (int i = 0; i < keys.length; i++) {
            values[i] = getMessage(keys[i]);
        }
        return values;
    }

    /**
     * abc - 你好
     * ${abc}efg => 你好efg
     * @param source
     * @return
     */
    public String getPackingMessage(String source) {
        this.result = source;
        Map<String, String> param = Strings.getParam(source, this::getMessage);
        param.forEach((s, s2) -> {
            String key = "${" + s + "}";
            if (this.result.indexOf(key) != -1) {
                this.result = this.result.replace(key, s2);
            }
        });
        source = this.result;
        this.result = null;
        return source;
    }

}
