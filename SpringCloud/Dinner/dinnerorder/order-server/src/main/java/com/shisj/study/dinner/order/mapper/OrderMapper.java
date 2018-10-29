package com.shisj.study.dinner.order.mapper;

import com.shisj.study.dinner.order.dataobject.OrderDetail;
import com.shisj.study.dinner.order.dataobject.OrderMaster;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderMapper {
    public void saveOrderMaster(OrderMaster orderMaster);
    public void saveOrderDetail(OrderDetail orderDetail);
    public void updateOrderMaster(OrderMaster orderMaster);
}
