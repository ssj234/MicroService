
# product提供的接口

## 前端

### 获取商品列表：

/product/list
输入：{}
输出：

```
    {
    "code": 0,
    "msg": "成功",
    "data": [
    {
    "id": "1800001",
    "name": "黄山烧饼",
    "price": 20.3,
    "icon": "icon"
    },
    {
    "id": "1800003",
    "name": "淮南牛肉汤",
    "price": 15,
    "icon": "icon"
    }
    ]
    }
```

## 为Order提供的服务


### 查询商品详情：

/product/info
输入： {items：["1","2",...]}
输出：


### 减少商品库存：
/product/decrease
输入： {items：[{id:"",count:2},{}]}

# order的接口

## 前端：

## 提交订单：

/order/create
输入:[{id:"111",quantity:20}]
输出:
```
```

# 数据库设计

商品表：
drop table dinner_product;
create table dinner_product(
  `id` varchar(10) not null primary key,
  `name` varchar(20)  not null,
  `count` int default 0,
  `price` decimal(15,2) not null default 0.00,
  `icon`  varchar(30),
  `status` int comment '0-下架 1-上架'
 ) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=1;

delete from dinner_product;
insert into dinner_product values('1800001','黄山烧饼',200,20.30,'icon','1');
insert into dinner_product values('1800002','兰州烧饼',100,10.00,'icon','0');
insert into dinner_product values('1800003','淮南牛肉汤',32,15.00,'icon','1');

drop table dinner_order_master;
create table dinner_order_master(
  `id` varchar(10) not null primary key,
  `pricesum` decimal(15,2)  not null,
  `createtime` TIMESTAMP  NOT NULL DEFAULT CURRENT_TIMESTAMP ,
  `status`  int default '00'
 )ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=1;

 create table dinner_order_detail(
   `order_id` varchar(10) not null,
   `product_id` varchar(10)  not null,
   `product_name` varchar(32) default 0,
   `product_price` decimal(15,2) not null default 0.00,
   `product_icon`  varchar(30)
  )ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=1;

