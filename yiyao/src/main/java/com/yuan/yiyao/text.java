package com.yuan.yiyao;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 测试controller
 */
@Controller
public class text {

    @RequestMapping("/text")
    public String text(){
        return "drag";
    }

}
