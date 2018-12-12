package com.yuan.yiyao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.swing.filechooser.FileFilter;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest
public class YiyaoApplicationTests {

    @Test
    public void contextLoads() {


    }

    public static void main(String args[]){
        //得到当前文件的工作目录
        ClassLoader classLoader = YiyaoApplicationTests.class.getClassLoader();
//        classLoader.

    }

}


class JavaFilter extends FileFilter{


    @Override
    public boolean accept(File f) {

        return false;
    }

    @Override
    public String getDescription() {
        return null;
    }
}

