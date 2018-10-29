package com.shisj.study.dinner.order.service;

import com.shisj.study.dinner.order.dataobject.OrderDetail;
import com.shisj.study.dinner.order.dataobject.OrderMaster;

public interface OrderService {
    public void saveOrderMaster(OrderMaster orderMaster);
    public void saveOrderDetail(OrderDetail orderDetail);

    public void updateOrderMaster(OrderMaster orderMaster);
}
