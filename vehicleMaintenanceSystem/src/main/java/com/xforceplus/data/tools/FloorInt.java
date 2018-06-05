package com.xforceplus.data.tools;

import org.junit.Test;

/**
 * Created by admin on 2017/10/13.
 */
public class FloorInt {
    public static int floorInt(int one,int two){
        int a=one%two;
        if(a>0){
            return one/two+1;
        }else {
            return one/two;
        }
    }
    @Test
    public void contextLoads() {
        int a=21;
        int b=5;
        System.out.println(floorInt(19,5));
    };
}
