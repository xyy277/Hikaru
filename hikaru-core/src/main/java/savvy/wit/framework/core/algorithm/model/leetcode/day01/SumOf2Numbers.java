package savvy.wit.framework.core.algorithm.model.leetcode.day01;

import java.util.HashMap;
import java.util.Map;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : 两数之和
 * File name : SumOf2Numbers
 * Author : zhoujiajun
 * Date : 2019/11/14 17:21
 * Version : 1.0
 * Description :
 * 测试用例：[-3,4,3,9] 0  ||   [2,3,7,11] 9  ||    [3,2,3] 6    ||
 * 题干：
 * 给出两个 非空 的链表用来表示两个非负的整数。其中，它们各自的位数是按照 逆序 的方式存储的，并且它们的每个节点只能存储 一位 数字。
 * 如果，我们将这两个数相加起来，则会返回一个新的链表来表示它们的和。
 * 您可以假设除了数字 0 之外，这两个数都不会以 0 开头。
 *
 * 难度：简单
 * 通用解法，hash
 ******************************/
public class SumOf2Numbers {

    /*
     * 这种方式不太好理解
     */
    public static int[] twoSum(int[] nums, int target) {
        int max = 2047;
        int[] map = new int[max + 1];
        for(int i = 0; i< nums.length; i++){
            int index = (target - nums[i]) & max;
            int temp = map[index];
            if(temp != 0) {
                return new int[]{temp - 1, i};
            }
            int cursor = nums[i] & max;
            map[cursor] = i + 1;
        }
        throw new IllegalArgumentException("NO two Sum solution");
    }

    /*
     * 比较好理解的一种方式
     */
    public static int[] twoSuHashMa(int[] nums, int target) {
        Map<Integer,Integer> map = new HashMap<>();
        for (int i = 0; i <nums.length ; i++) {
            int temp = target-nums[i];
            if(map.containsKey(temp)){// n
                return new int[]{map.get(temp),i};
            }
            map.put(nums[i],i);
        }
        throw new IllegalArgumentException("NO two Sum solution");

    }

    public static void main(String[] args) {
        int[] r = twoSum(new int[]{1,1,2,3,5,-3,4,3,9}, 0);
        for (int i : r) {
            System.out.print(i + " \t ");
        }
    }
}
