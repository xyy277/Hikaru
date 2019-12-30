package zjj;

import org.junit.Before;
import org.junit.Test;
import savvy.wit.framework.core.base.service.log.Log;
import savvy.wit.framework.core.construction.string.NewString;
import savvy.wit.framework.core.pattern.factory.Daos;
import savvy.wit.framework.core.pattern.factory.LogFactory;
import savvy.wit.framework.core.structure.physical.loopStructure.circle.Circle;
import savvy.wit.framework.core.structure.physical.loopStructure.circle.Loop;
import savvy.wit.framework.core.structure.physical.shape.Curve;

import java.util.Optional;
import java.util.regex.Pattern;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : Optional使用
 * File name : OptionalTest
 * Author : zhoujiajun
 * Date : 2018/10/9 12:09
 * Version : 1.0
 * Description : 
 ******************************/
public class OptionalTest {

    private Log log = LogFactory.getLog();

    private Loop loop;
    private Circle circle;
    private Curve curve;
    @Before
    public void before() {
        loop = Optional.ofNullable(loop)
                .orElse(new Loop());
        circle = Optional.ofNullable(circle)
                .orElse(new Circle());
        circle.setCurve(new Curve());
        loop.setCircle(circle);
        curve = Optional.ofNullable(loop)
                .map(loop -> loop.getCircle())
                .map(circle -> circle.getCurve())
                .orElse(null);
//        loop = new Loop();
//        loop.setCircle(circle);
    }

    @Test
    public void test() {
        log.log(curve);
        log.log(circle);
        log.log(loop);
        NewString string = new NewString();
        string.add("a",'C',"B", "\t", Daos.get());
        log.log(string);
        String s = "";
        Pattern pattern = Pattern.compile("abc");
        log.log(pattern.matcher(string.toString()));
        log.log(string.count("c"));
        log.log(string.contains("6"));
        string.contains("a");

        log.log(1 != (4 & 5));
    }
}
