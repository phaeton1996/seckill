package com.graduation.seckill.controller;

import com.graduation.seckill.entity.Order;
import com.graduation.seckill.service.OrderService;
import com.graduation.seckill.utils.QRCodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/qrcode")
public class QRCodeController {

    @Autowired
    OrderService orderService;

    @RequestMapping("/test")
    public String testGen(ModelMap modelMap){
        String binary = QRCodeUtil.creatRrCode("http://119.29.138.243/examples/test.mp4", 200,200);
        modelMap.addAttribute("src",binary);
        return "qrtest";
    }

    @RequestMapping("/w_pay")
    public String w_pay(ModelMap modelMap,String orderId){
        Order order = orderService.getById(orderId);
        String binary = QRCodeUtil.creatRrCode(
                "http://191.168.0.114:8080/seckill/order/p_pay?orderId="+orderId,200,200);
        modelMap.addAttribute("src", binary);
        modelMap.addAttribute("order", order);
        return "p_pay";
    }

    @RequestMapping("/p_pay")
    public String p_pay(ModelMap modelMap,String orderId){
        Order order = orderService.getById(orderId);
        modelMap.addAttribute("order", order);
        return "p_pay";
    }
}
