package com.bike_server.service;

import com.bike_server.dao.OrderMapper;
import com.bike_server.pojo.Order;
import com.bike_server.utility.StringUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class OrderService implements OrderMapper {
    @Autowired
    private OrderMapper orderMapper;

    /**
     * 创建订单: 在数据中插入一条新订单数据,
     *  新建订单数据,刚刚创建的订单只有 订单号,订单状态,订单开始时间(即创建时间)
     * @param order_id 订单号
     * @param start_time 订单创建时间
     * @return state, 返回执行状态,如果新建订单数据创建成功,返回true,创建失败(如订单已存在)返回 false
     *  如果出现其他异常,如 传入非法参数,则返回 null
     */
    public Boolean createOrder(String order_id, Date start_time){
        Boolean state = null;
        Order newOrder = new Order();
        int affectedRows;
        try {
            newOrder.setOrder_id(order_id);
            newOrder.setOrder_status(Order.BeIng);
            newOrder.setStart_time(start_time);
            affectedRows = orderMapper.addOrder(newOrder);

            state = affectedRows > 0;
        }
        catch (Exception e){
            System.err.println(newOrder+"订单新建失败!");
        }
        return state;
    }

    /**
     * 结束骑行订单, 修改订单信息
     * @param order_id 需要修改的订单
     * @param amount 本次订单所需金额
     * @param end_time 订单结束时间
     * @return 如果修改成功返回 true, 修改失败(如订单不村存在) 返回 false, 内部异常返回 null
     */
    public Boolean stopOrder(String order_id, double amount, Date end_time){
        Boolean state = null;
        Order order = null;
        int affectedRows;
        try {
            order = orderMapper.getOrderByOrder_id(order_id);
            if (order == null) return false;  // 避免空指针

            order.setAmount(amount);
            order.setOrder_status(Order.WaitPay);
            order.setEnd_time(end_time);
            affectedRows = orderMapper.updateOrder(order);

            state = affectedRows > 0;
        }
        catch (Exception e){
            System.err.println(order+"订单停止失败!");
        }
        return state;
    }

    /**
     * 当用户支付成功后调用,将订单修改为 完成状态
     * @param order_id
     * @param trade_no
     * @param trade_type
     * @return
     */
    public Boolean finishOrder(String order_id, String trade_no, String trade_type){
        Boolean state = null;
        Order order = null;
        int affectedRows;
        try {
            order = orderMapper.getOrderByOrder_id(order_id);
            if (order == null) return false;  // 避免空指针

            order.setTrade_no(trade_no);
            order.setTrade_type(trade_type);
            order.setOrder_status(Order.Finished);
            affectedRows = orderMapper.updateOrder(order);

            state = affectedRows > 0;
        }
        catch (Exception e){
            System.err.println(order+"修改订单为完成时失败!");
        }
        return state;
    }


    public Order getUnFinishOrderByUid(String uid){
        String template = "`order_id` like '#{uid}-%-%' and (`order_status`='#{order_status}' or `order_status`='#{order_status}')";
        template = template.replace("#{uid}", uid);
        template = StringUtility.replaceFirst(template, "#{order_status}", Order.BeIng);
        template = StringUtility.replaceFirst(template, "#{order_status}", Order.WaitPay);
        String condition = template;
        List<Order> list = null;
        Order order = null;
        try{
            list = orderMapper.getOrderByCondition(condition);
            if (!list.isEmpty()) {
                order = list.get(0);
            }
        }
        catch (Exception e){
            System.out.println("根据用户id获取未完成订单失败!uid: "+uid);
            e.printStackTrace();
        }
        return order;
    }

    @Override
    public List<Order> getAllOrder() {
        return orderMapper.getAllOrder();
    }

    @Override
    public List<Order> getOrderByTrade_type(String trade_type) {
        return orderMapper.getOrderByTrade_type(trade_type);
    }

    @Override
    public List<Order> getOrderByOrder_status(String order_status) {
        return orderMapper.getOrderByOrder_status(order_status);
    }

    @Override
    public List<Order> getOrderByCondition(String condition) {
        List<Order> list = null;
        try {
            list = orderMapper.getOrderByCondition(condition);
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public Order getOrderByOrder_id(String order_id) {
        return orderMapper.getOrderByOrder_id(order_id);
    }

    @Override
    public Order getOrderByTrade_no(String trade_no) {
        return orderMapper.getOrderByTrade_no(trade_no);
    }

    @Override
    public int addOrder(Order order) {
        return orderMapper.addOrder(order);
    }

    @Override
    public int deleteOrder(String order_id) {
        int rows = 0;
        try {
            rows = orderMapper.deleteOrder(order_id);
        }
        catch (Exception e){
            System.err.println(order_id+"订单删除出现异常!");
            e.printStackTrace();
        }
        return rows;
    }


    @Override
    public int updateOrder(Order order) {
        return orderMapper.updateOrder(order);
    }
}
