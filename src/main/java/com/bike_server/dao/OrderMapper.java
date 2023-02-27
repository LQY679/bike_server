package com.bike_server.dao;

import com.bike_server.pojo.Order;
import com.sun.org.apache.xpath.internal.operations.Or;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;
import java.util.List;

@Mapper
public interface OrderMapper {
    List<Order> getAllOrder();

    List<Order> getOrderByTrade_type(String trade_type);

    List<Order> getOrderByOrder_status(String order_status);


    List<Order> getOrderByCondition(String condition);

    Order getOrderByOrder_id(String order_id);

    Order getOrderByTrade_no(String trade_no);

    int addOrder(Order order);

    int deleteOrder(String order_id);

    int updateOrder(Order order);
}
