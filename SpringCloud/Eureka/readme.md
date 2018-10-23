
# Eureka Server搭建

### 1.修改pom.xml

项目需要作为一个springboot的项目，因此需要引用parent依赖
```
<parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>2.0.4.RELEASE</version>
</parent>
```

项目还需要引用springclou的依赖，因此需要添加下面的依赖，其中scope的类型为import，用于多重继承，只在`dependencyManagement`中有作用
```
<dependencyManagement>
    <dependencies>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-dependencies</artifactId>
            <version>${spring.cloud.eureka.version}</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>
    </dependencies>
</dependencyManagement>
```

由于该项目是Server端，因此引用服务端的依赖
```
<dependencies>
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
    </dependency>
</dependencies>
```

### 2.启动服务

在启动类上添加`@EnableEurekaServer`即可
```
@SpringBootApplication
@EnableEurekaServer
public class EurekaServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(EurekaServerApplication.class, args);
    }
}
```

### 3.查看

打开浏览器 http://127.0.0.1:8080/即可查看



## Eureka配置

Eureka服务器没有后端的存储，依靠心跳在内存维持注册的服务，Eureka客户端在内存中也维护了一份服务的注册信息，不需要每次都到服务器获取服务列表。

默认情况下，Eureka服务器也是一个Client，需要最少一个服务器来配对，可以通过配置阻止client的行为，下面是一些配置

```
# 设置应用的名称
spring:
  application:
    name: eureka8761
# 设置http端口
server:
  port: 8761

eureka:
  instance:
    hostname: localhost
  client:
    # 启动时不与服务端配对
    registerWithEureka: false
    fetchRegistry: false
    serviceUrl:
      defaultZone: http://${eureka.instance.hostname}:${server.port}/eureka/
```

## 高可用

一个Eureka服务器不能保证可用性，可以使用两两配对的方式互相连接，比如，有三台用作Eureka的服务器分别为ABC，可以将A-B B-C C-A相互结对在一起。


## 安全性

1.添加依赖

```
spring-boot-starter-security
```

2.配置安全适配器
```
@EnableWebSecurity
class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().ignoringAntMatchers("/eureka/**");
        super.configure(http);
    }
}
```

# 客户端

## 客户端的搭建

1. 添加依赖

继承springboot
```
<parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>2.0.4.RELEASE</version>
</parent>
```

继承springcloud
```
<properties>
    <spring.cloud.eureka.version>Finchley.SR1</spring.cloud.eureka.version>
</properties>

<dependencyManagement>
    <dependencies>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-dependencies</artifactId>
            <version>${spring.cloud.eureka.version}</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>
    </dependencies>
</dependencyManagement>
```

客户端需要依赖的包
```
<dependencies>
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
</dependencies>
```

2. 配置
```
spring:
  application:
    name: eureka-client
eureka:
  client:
    serviceUrl:
      defaultZone: http://localhost:8761/eureka/,http://localhost:8762/eureka/
```

3.启动类，目前这个版本不加入`@EnableEurekaClient`也可以启动
```
@SpringBootApplication
@RestController
public class EurekaClientApplication {

    @RequestMapping("/")
    public String home() {
        return "Hello world";
    }

    public static void main(String[] args) {
        SpringApplication.run(EurekaClientApplication.class,args);
    }

}
```

## 鉴权

Eureka的服务端设置了安全配置后，可以通过设置用户名和密码来验证权限
```
http://user:password@localhost:8761/eureka
```

如果有更复杂的需要，可以通过来设置
```
@Bean
public DiscoveryClientOptionalArgs clientArg(ClientFilter lientFilter){
    
}
```

## 安全性

如果想使用https，可以设置如下两个配置
```
eureka.instance.[nonSecurePortEnabled]=[false]
eureka.instance.[securePortEnabled]=[true]
```


### 获取应用地址

STORES是应用的名字，可以通过这个名称获取地址

```
@Autowired
private EurekaClient discoveryClient;

public String serviceUrl() {
    InstanceInfo instance = discoveryClient.getNextServerFromEureka("STORES", false);
    return instance.getHomePageUrl();
}
```

### 心跳

默认的心跳时间是30秒，可以通过修改下面的配置：
```
eureka.instance.leaseRenewalIntervalInSeconds
```
在生产上推荐使用默认的30秒即可


### Zone

Eureka服务器可以设置不同的zone，client可以配置优先选择相同的zone

Service 1 in Zone 1
```
eureka.instance.metadataMap.zone = zone1
eureka.client.preferSameZoneEureka = true
```

Service 1 in Zone 2
```
eureka.instance.metadataMap.zone = zone2
eureka.client.preferSameZoneEureka = true
```