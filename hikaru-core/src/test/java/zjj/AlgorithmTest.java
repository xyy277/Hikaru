package zjj;

import org.junit.Test;
import savvy.wit.framework.core.algorithm.model.StringRetrieve;
import savvy.wit.framework.core.base.service.Log;
import savvy.wit.framework.core.base.util.DateUtil;
import savvy.wit.framework.core.base.util.StringUtil;
import savvy.wit.framework.core.pattern.factory.LogFactory;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : test for StringRetrieve
 * File name : AlgorithmTest
 * Author : zhoujiajun
 * Date : 2019/1/6 17:57
 * Version : 1.0
 * Description : 
 ******************************/
public class AlgorithmTest {

    private static final Log log = LogFactory.getLog();


    @Test
    public void testAlgorithm() {
        while (true) {
            String paramString = initParamString();
            log.log(  "首个仅出现一次的字符是：" +      StringRetrieve.getBoOnlyOnceCode(paramString));
            zjj.Test.test(paramString);
            log.log(() -> Thread.sleep(1000 * 5));
        }
    }

    /**
     * 生成一个满足条件的字符串
     */
    private static String initParamString() {
        int length = DateUtil.random(~(1<<31) / 1000) / 1000 / 10; // 字符串长度随机,尽可能短一点降低重复率
        String paramString = StringUtil.createCode( length <= 5 ? 5 : length); // 生成a-zA-Z0-9的字符串
//        paramString = "Wdcp0OmAGln6DzIqPINHHOq6UaNQMQlKnfVCJz0j17A26NT0bSZ7KMUAh67Jh1JWRGWLoXKmEpujzHladnOxGu3B7HjoVdk0OTRgY9UNsf885gtNyaZ6wo7N8wMn1YBAEcPcvMS9NIk7r5lJdz0ZcTcCtS3jDGcPFz3fnvGyeBhker7lpUxanl4aLoQw3OG06LWeoCt02FA1D0OdRfXms9ef4oxf530migzNRhWnZl4f5iaWnWekk8UdsgIBMWZKMHtawciy9OIj7VTA510EdfwRUZr35MAJyYwqhdkzQaztpiBWeD4XSNsY0Mpnkr63l74SYfKoaaGMthiETdx60IcDgle3Hcl8XAwxN4oez3JaLrepEDuPiu6";
        log.log("随机字符串：" + paramString);
        log.log("随机字符串长度：" + paramString.length());
        return paramString;
    }

}
