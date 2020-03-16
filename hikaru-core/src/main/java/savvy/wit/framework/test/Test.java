package savvy.wit.framework.test;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : Test
 * File name : Test
 * Author : zhoujj
 * Date : 2020/3/5 16:42
 * Version : 1.0
 * Description : 
 ******************************/
public class Test {



    private B b;

    private C c;

    private void ok(A a) {
        if (a instanceof B) {
            b = (B) a;
        } else if (a instanceof C) {
            c = (C) a;
        } else {
            System.out.println("noting");
        }
        a.test();
    }


    public static void main(String[] args) {
        Test test = new Test();
        B b = () -> {
            System.out.println("BBBB");
        };
        test.ok(b);
    }
}
