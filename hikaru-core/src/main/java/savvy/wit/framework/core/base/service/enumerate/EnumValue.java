package savvy.wit.framework.core.base.service.enumerate;

import java.lang.annotation.*;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : EnumValue
 * File name : EnumValue
 * Author : zhoujiajun
 * Date : 2019/5/29 10:09
 * Version : 1.0
 * Description : 标注扫描泛型类对泛型接口EnumValueContract的实现
 ******************************/
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface EnumValue {
}
