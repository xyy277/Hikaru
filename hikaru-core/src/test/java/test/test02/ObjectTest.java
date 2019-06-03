package test.test02;

import java.util.Scanner;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : 
 * File name : ObjectTest
 * Author : zhoujiajun
 * Date : 2019/3/6 10:14
 * Version : 1.0
 * Description : 
 ******************************/
public class ObjectTest {

    public static void main(String[] args) throws Exception {
        int year = 0;
        Scanner scanner = new Scanner(System.in);
        for(;;) {
            year = scanner.nextInt();
            System.out.println(year + "年是" + ((0 == year % 100?0 == year % 400:0 == (year & (2^2-1)))?"闰年":"平年"));
            System.out.println(0 == year % 100 ? 0 == year % 400 : 0 == (year & (2^2-1)));
        }
    }
}
