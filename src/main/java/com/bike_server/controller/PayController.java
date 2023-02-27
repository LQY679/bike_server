package com.bike_server.controller;

import com.bike_server.pojo.Order;
import com.bike_server.pojo.Result;
import com.bike_server.pojo.Trade;
import com.bike_server.service.AliPayService;
import com.bike_server.service.OrderService;
import com.bike_server.utility.JsonUtility;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


// 全部测试通过!
@Controller
public class PayController {

    @Autowired
    AliPayService aliPayService;
    @Autowired
    OrderService orderService;

    JsonUtility ju = new JsonUtility();

    @RequestMapping("/goPay")
    public void goPay(@RequestParam String order_id, @RequestParam String amount, HttpServletResponse resp){
        try {
            String payPage = aliPayService.getPayPage("http://localhost:8081/finishPay",
                    order_id,
                    "小罗骑行-骑行订单费用",
                    Double.parseDouble(amount));
            resp.setContentType("text/html;charset=utf-8");
            resp.getWriter().write(payPage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*  暂时无办法解决!!!  */
//    @RequestMapping("/goPay")
//    public void goPay(@RequestBody Order order, HttpServletResponse resp){
//        try {
//            String payPage = aliPayService.getPayPage("http://localhost:8081/finishPay",
//                    order.getOrder_id(),
//                    "小罗骑行-骑行订单费用",
//                    order.getAmount());
//            resp.setContentType("text/html;charset=utf-8");
//            resp.getWriter().write(payPage);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    @RequestMapping("/finishPay")
    public void finishPay(HttpServletRequest req, HttpServletResponse resp) throws UnsupportedEncodingException {
        //获取支付宝GET过来反馈信息
        Map<String,String[]> requestParams = req.getParameterMap();
        Map<String,String> params = new HashMap<>();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用
            valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }
        /*  安全验证,暂时用不到  */
//        boolean signVerified = false; //调用SDK验证签名
//        try {
//            signVerified = AlipaySignature.rsaCheckV1(
//                    params,
//                    AlipayConfig.alipay_public_key,
//                    AlipayConfig.charset,
//                    AlipayConfig.sign_type);
//        } catch (AlipayApiException e) {
//            e.printStackTrace();
//        }
        // 提取所需参数
        String out_trade_no = new String(req.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");
        String trade_no = new String(req.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");
//      修改订单状态为 完成状态
        Boolean state = orderService.finishOrder(out_trade_no, trade_no, Order.AliPay);
        try {
            if (!state) {
                resp.getWriter().write("订单状态更新失败!如果已经付款请进行订单申诉(3秒后自动回到首页)");
                Thread.sleep(3000);
            }
            resp.sendRedirect("/");
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

    @PostMapping("/reviewAliPayOrder")
    @ResponseBody
    public String reviewAliPayOrder(@RequestParam String trade_no, @RequestParam String order_id){
        Result result = new Result();
        Trade trade = aliPayService.tradeQuery(trade_no);
        if (trade == null || ! trade.getOut_trade_no().equals(order_id)){
            result.setCode(2000);
            result.setSub("fail");
            result.setMsg("申诉失败!该交易不存在!");
        }else if ("TRADE_SUCCESS".equals(trade.getTrade_status()) || "TRADE_FINISHED".equals( trade.getTrade_status())){
            orderService.finishOrder(order_id, trade_no, Order.AliPay);
            result.setMsg("申诉订单成功!请刷新重新订单状态");
        }
        else {
            result.setCode(4000);
            result.setSub("error");
            result.setMsg("出现未知错误,请检查服务后重试!");
        }
        ObjectMapper mapper = ju.mapperFactory();
        return ju.jsonParser_ByObject(mapper, result);
    }

}
