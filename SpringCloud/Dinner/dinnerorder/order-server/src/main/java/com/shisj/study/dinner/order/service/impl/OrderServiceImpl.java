package com.shisj.study.dinner.order.service.impl;

import com.shisj.study.dinner.order.dataobject.OrderDetail;
import com.shisj.study.dinner.order.dataobject.OrderMaster;
import com.shisj.study.dinner.order.mapper.OrderMapper;
import com.shisj.study.dinner.order.service.OrderService;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Override
    public void saveOrderMaster(OrderMaster orderMaster) {
        orderMapper.saveOrderMaster(orderMaster);
    }

    @Override
    public void saveOrderDetail(OrderDetail orderDetail) {
        orderMapper.saveOrderDetail(orderDetail);
    }

    @Override
    public void updateOrderMaster(OrderMaster orderMaster) {
        orderMapper.updateOrderMaster(orderMaster);
    }
}
