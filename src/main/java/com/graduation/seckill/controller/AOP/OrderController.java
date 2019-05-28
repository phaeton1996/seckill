package com.graduation.seckill.controller.AOP;

import com.graduation.seckill.entity.Addr;
import com.graduation.seckill.entity.Order;
import com.graduation.seckill.enums.CodeMsg;
import com.graduation.seckill.enums.RedisPrefix;
import com.graduation.seckill.exception.RedirectException;
import com.graduation.seckill.rabbitmq.MQSender;
import com.graduation.seckill.redis.RedisService;
import com.graduation.seckill.service.AddrService;
import com.graduation.seckill.service.GoodsService;
import com.graduation.seckill.service.OrderService;
import com.graduation.seckill.utils.CookieUtil;
import com.graduation.seckill.utils.QRCodeUtil;
import com.graduation.seckill.vo.OrderVo;
import com.graduation.seckill.vo.Result;
import com.graduation.seckill.vo.SeckillVo;
import com.graduation.seckill.vo.UserVo;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Random;

import static com.graduation.seckill.enums.CodeMsg.*;

@Controller
@RequestMapping("/order")
public class OrderController implements InitializingBean {

    @Autowired
    private MQSender mqSender;

    @Autowired
    private AddrService addrService;

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private RedisService redisService;

    private static String USER_COOKIE_NAME = "token";

    /**
     * 获取下单时的地址信息
     */
    @RequestMapping("/information")
    @ResponseBody
    public Result<List<Addr>> getById(Integer userId) {
        if (userId != null) {
            List<Addr> list = addrService.getById(userId);
            return Result.returnWithData(list);
        }
        return null;
    }

    /**
     * 处理秒杀操作
     */
    @RequestMapping(value = "/submit", method = RequestMethod.POST)
    @ResponseBody
    public Result<CodeMsg> order(OrderVo order) {
        // 从redis中预减库存
        long stock = redisService.decr(RedisPrefix.GOODS_STOCK_PREFIX,Integer.toString(order.getGoodsId()));
        if (stock <= 0) {
            return Result.returnWithCodeMsg(SEKILLGOODS_SOLD_OUT);
        }
        // 查询是否重复秒杀
        OrderVo orderVo = orderService.getByUserIDAndGoodsID(order.getUserId(), order.getGoodsId());
        if (orderVo != null) {
            return Result.returnWithCodeMsg(SEKILLGOODS_REPEATED);
        }
        // 添加进消息队列
        mqSender.sendMiaoshaMessage(new SeckillVo(order.getUserId(), order.getAddrId(), order.getGoodsId()));
        return Result.returnWithCodeMsg(SEKILLGOODS_IN_MQ);
    }

    /**
     * 处理秒杀操作 -- 测试接口 -- 优化版本
     */
    @RequestMapping(value = "/testsubmit", method = RequestMethod.GET)
    @ResponseBody
    public Result<CodeMsg> order() {
        OrderVo order = new OrderVo();
        int param = new Random().nextInt();
        order.setUserId(param);
        // 测试1004号商品
        order.setGoodsId(1004);
        order.setAddrId(param);
        // 从redis中预减库存
        long stock = goodsService.decr(order.getGoodsId());
        if (stock < 0) {
            return Result.returnWithCodeMsg(SEKILLGOODS_SOLD_OUT);
        }
        // 查询是否重复秒杀
//        OrderVo orderVo = orderService.getByUserIDAndGoodsID(order.getUserId(), order.getGoodsId());
//        if (orderVo != null) {
//            return Result.returnWithCodeMsg(SEKILLGOODS_REPEATED);
//        }

        // 添加进消息队列
        mqSender.sendMiaoshaMessage(new SeckillVo(order.getUserId(), order.getAddrId(), order.getGoodsId()));
        return Result.returnWithCodeMsg(SEKILLGOODS_IN_MQ);
    }


    /**
     * 处理秒杀操作 -- 测试接口 -- 优化版本
     */
    @RequestMapping(value = "/_testsubmit", method = RequestMethod.GET)
    @ResponseBody
    public Result<CodeMsg> _order(){
        OrderVo order = new OrderVo();
        int param = new Random().nextInt();
        order.setUserId(param);
        // 测试1004号商品
        order.setGoodsId(1004);
        order.setAddrId(param);
        orderService._doseckill(order);
        return null;
    }


    /**
     * 返回秒杀结果
     */
    @RequestMapping(value = "/result", method = RequestMethod.POST)
    @ResponseBody
    public <T> Result<T> getOrderResult(OrderVo orderVo) {
        System.out.println(orderVo);
        Result res = Result.returnWithCodeMsg(SEKILLGOODS_NOT_OVER);
        OrderVo query = orderService.getByUserIDAndGoodsID(orderVo.getUserId(), orderVo.getGoodsId());
        if (query == null) {
            res.setData(0);
        }
        res.setData(query.getOrderId());
        return (Result<T>) res;
    }

    @RequestMapping(value = "/detail")
    public String getOrderDeatil(String orderId, ModelMap map) {
        if (orderId != null && !orderId.equals("")) {
            Order order = orderService.getById(orderId);
            if (order == null) {
                throw new RedirectException(CodeMsg.SEKILLGOODS_NOT_EXIST);
            }
            String src = QRCodeUtil.creatRrCode("http://192.168.0.114:8080/seckill/order/p_pay?orderId="+orderId,200,200);
            map.addAttribute("order", order);
            map.addAttribute("src", src);
            return "order_detail";
        } else {
            throw new RedirectException(CodeMsg.SEKILLGOODS_ID_ILLEGAL);
        }
    }

    /**
     * 继承了InitializingBean接口，在系统初始化之后回调的方法
     * 这个方法主要是更新商品和商品数量对应的缓存到Redis数据库
     *
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        goodsService.updateGoodsCache();
    }

    @RequestMapping("/list")
    public String getByUserId(HttpServletRequest request, ModelMap map){
        String value = CookieUtil.getCookieValue(request, USER_COOKIE_NAME);
        UserVo userVo = redisService.get(RedisPrefix.USER_PREFIX, value, UserVo.class);
        System.out.println(userVo.getId());
        List<Order> orders = orderService.getByUserId(userVo.getId());
        map.addAttribute("orderList",orders);
        return "orderlist";
    }


    @RequestMapping("/p_pay")
    public String p_pay(ModelMap modelMap,String orderId){
        Order order = orderService.getById(orderId);
        modelMap.addAttribute("order", order);
        return "p_pay";
    }

    @RequestMapping("/pay")
    @ResponseBody
    public Boolean pay(ModelMap modelMap,String orderId){
        int res = orderService.pay(orderId);
        return res == 1;
    }
}
