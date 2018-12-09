package com.yuan.yiyao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class YiyaoApplicationTests {

    @Test
    public void contextLoads() {


    }

    public static void main(String args[]){
        double a = 71;
        double b = 103;
        double d = (double)71/103;
        System.out.println(d);
    }

}
