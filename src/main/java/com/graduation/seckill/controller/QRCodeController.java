package com.graduation.seckill.controller;

import com.graduation.seckill.utils.QRCodeUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/qrcode")
public class QRCodeController {

    @RequestMapping("/test")
    public String testGen(ModelMap modelMap){
        String binary = QRCodeUtil.creatRrCode("http://119.29.138.243/examples/test.mp4", 200,200);
        modelMap.addAttribute("src",binary);
        return "qrtest";
    }
}
