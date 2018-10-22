# spring-boot 笔记

## 1. 搭建HelloWorld工程


### 1.4版本的项目搭建

**pom.xml**

使用maven工程构建spring-boot，需要引入的模块

```
	<parent><!-- 当前项目继承自这个项目-->
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-parent</artifactId>
		<version>1.4.1.RELEASE</version>
		<relativePath/> <!-- lookup parent from repository -->
	</parent>

	<properties>
		<project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
		<project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
		<java.version>1.8</java.version>
	</properties>

	<dependencies>
		<dependency> <!-- web功能 -->
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-web</artifactId>
		</dependency>

		<dependency> <!-- test功能 -->
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-test</artifactId>
			<scope>test</scope>
		</dependency>
	</dependencies>

	<build>
		<plugins>
			<plugin> <!-- maven插件 可执行mvn spring-boot:task相关任务 -->
				<groupId>org.springframework.boot</groupId>
				<artifactId>spring-boot-maven-plugin</artifactId>
			</plugin>
		</plugins>
	</build>
```
**java启动入口**
启动入口需要`@SpringBootApplication`注解进行标注，在main方法中，使用`SpringApplication`的run方法启动应用
```java
@SpringBootApplication
public class StudySpringbootHelloApplication {

	public static void main(String[] args) {

		SpringApplication.run(StudySpringbootHelloApplication.class, args);
	}
}
```

**配置文件**

resources下的`application.properties`或`application.yml`，推荐使用yml格式

有了上面3个地方，就可以启动项目了，但是没有任何逻辑。启动的方式有三种：

* 使用IDEA的main方法启动
* 使用mvn命令：`mvn spring-boot:run` 启动应用
* 使用mvn打包为jar包后，使用`java -jar xxx.jar`启动应用

## 2.属性配置

**配置系统参数**

spring-boot内部有许多系统参数可以配置，例如web服务的端口和contextPath
```
server:
  port: 8082
  context-path: /study
```

**配置自定义参数**

自定义参数可以定义一些变量的值，或者定义对象的一组值，例如

```
# 单个配置
cupSize: B
age: 18
content: "cupSize:${cupSize},age:${age}"

# 分组配置
girl:
  cupSize: B
  age: 19
  content: "cupSize=${girl.cupSize},age=${girl.age}"
```

有了这些配置，就可以在java程序中引用了，单个配置引用的方式如下，也可以在命令行修改参数`java -jar xxx.jar --server.port=8888`，通过`SpringApplication.setAddCommandLineProperties(false)`可以禁止这种行为
```
@Value("${cupSize}")
private String cupSize;

@Value("${age}")
private int age;

@Value("${content}")
private String content;
```
分组配置引用的方式：指定prefix即可，会向内部的属性注入指定的值
```
@ConfigurationProperties(prefix = "girl")
public class GirlProperties {
    private String cupSize;
    private int age;
    private String content;
    //  setter/getter
}
```

**不同环境配置**

在实际的开发中，开发/测试/生产环境的参数是不一样的，spring-boot提供了快速的切换环境配置的功能。在resources下创建三个文件，dev表示开发环境，prod表示生产环境
```
application.yml
application-dev.yml
application-prod.yml
```

在`application.yml`中，使用下面的配置可以指定当前的配置文件
```
spring:
  profiles:
    active: dev
```
在启动命令行，可以通过`java -jar xxx.jar --spring.profiles.active=dev`也可以指定需要的配置


## 3.Controller的使用

主要有两种Cotroller，都可以处理http请求

* @Controller  需要配合模板使用，但是如今的开发都是前后端分离的，使用的不多
* @RestController RESTful协议的请求，可以制定收入参数和方法

**@RestController 设置请求路径**

在class上使用`@RequestMapping("/hello")`，可以为内部的方法添加统一的前缀路径，在方法上使用`@RequestMapping(value = {"/hello","/hi"},method=RequestMethod.GET)`指定了请求路径，请求方法，对于方法上的路径，需要与class路径相加组成最终的路径。

**@RestController 设置请求参数**

参数的获取可以两种方式，一种将参数放在url中，一种使用传统的url的query中,一种放在http的body中。

***@PathVariable url路径中***
例如，请求产品id的信息，可以使用`/prod/100`,启动100是产品id，对于这种请求，可以使用`@PathVariable`,分为两个步骤，首先指定路径的格式，之后使用@PathVariable对参数中的id和java的uid进行映射
```
@RequestMapping(value = {"/say/{id}"},method=RequestMethod.GET)
public String hi(@PathVariable("id") Integer uid){
    // String.format( " my name is : %s,cupSize is %s,age is %d!" , name,cupSize,age)
    return "id is " + uid;
}
```

***@RequestParam url参数中***
例如，请求产品id的信息，还可以使用`/prod?id=100`,对于这类请求，可以使用`@RequestParam`，需要指定参数的key，是否必输，非必输时默认值，当然也需要映射url参数与java变量的映射关系。

```
@RequestMapping(value = {"/say0"},method=RequestMethod.GET)
public String hii(@RequestParam(value = "id",required = false,defaultValue = "0") Integer uid){
    // String.format( " my name is : %s,cupSize is %s,age is %d!" , name,cupSize,age)
        return "id is " + uid;
}
```

***@RequestBody***
在提交数据时，我们一般会使用post请求并将数据放入http的body中，获取body的参数时，我们可以使用`@RequestBody`，其会映射到一个map中，通过map可以获取id的值。

```
@PostMapping(value = {"/say1"})
public String hii2(@RequestBody Map<String,Object> reqMap){
    int uid = Integer.valueOf(reqMap.get("id").toString());
    return "[post]id is " + uid;
}
```

最后，为了便于各种方法的简便使用，提供了对应的简化注解
```
@GetMapping
@PostMapping
@PutMapping
@DeleteMapping
```

## 4. 模块化构建

SpringBoot也可以使用模块化进行开发，使用maven的方式即可
```
study-springboot-app
	-\ study-springboot-app-model
	-\ study-springboot-app-persistence
	-\ study-springboot-app-web
```

最外层study-springboot-app的pom.xml中，将该项目配置为使用SptingBoot的相关依赖，做为`spring-boot-starter-parent`的子项目，并引入需要的SpringBoot模块，并使用下面的配置设置子模块
```
<modules>
	<module>study-springboot-app-web</module>
	<module>study-springboot-app-persistence</module>
	<module>study-springboot-app-model</module>
</modules>
<packaging>pom</packaging>
```
在三个子项目中， web需要引用其他两个项目，需要引入依赖：
```
<dependencies>
        <dependency>
            <artifactId>study-springboot-app-model</artifactId>
            <groupId>com.shisj.study</groupId>
            <version>0.0.1-SNAPSHOT</version>
        </dependency>

        <dependency>
            <artifactId>study-springboot-app-persistence</artifactId>
            <groupId>com.shisj.study</groupId>
            <version>0.0.1-SNAPSHOT</version>
        </dependency>
    </dependencies>
```
需要注意的是使用mvn打包时，需要将插件配置移动到web下面的pom.xml中，因为web是我们的主工程
```
<build>
    <plugins>
        <plugin>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-maven-plugin</artifactId>
            <configuration>
                <mainClass>com.shisj.study.springboot.hello.App</mainClass>
            </configuration>
        </plugin>
    </plugins>
</build>
```

## 5. 统一异常处理

在发生异常时，我们一般需要转向到统一的错误页面，或者返回错误json数据。在SpringBoot在，可以使用：

*  `@ControllerAdvice`指定处理异常的类
*  `@ExceptionHandler`指定了处理异常的方法

```
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 当网页端一个请求发送到后台时，后台的控制视图层通过@RequestMapping映射相应的视图方法，
     * 如果在视图方法上用注解@ResponseBody标识后，
     * 方法执行完后返回的内容会返回到请求页面的body上，直接显示在网页上。
     * @param req
     * @param exception
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Map jsonErrorHandler(Exception exception){
        Map ret = new HashMap();
        ret.put("msg",exception.getMessage());
        ret.put("code","99999");
        return  ret;
    }
}
```

## 6. 集成Redis

集成redis时需要引入下面的依赖：
```
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-data-redis</artifactId>
</dependency>
```
redis的配置如下：
```
spring:
  redis:
    database: 0
    host: 123.88.30.88
    port: 6388
    timeout: 60
    password: 88888888
    pool:
      max-active: 8
      max-wait: -1
      max-idle: 8
      min-idle: 0
```

redis的使用

```
@Autowired
private StringRedisTemplate stringRedisTemplate;
// 获取key的值
stringRedisTemplate.opsForValue().get("aaa");    
```


## 7. 整合Mybatis

首先需要引入mybatis的依赖，包括数据源使用druid，mysql驱动和mybatis针对springboot的包
```
<!-- 整合Mybatis -->
<dependency>
	<groupId>com.alibaba</groupId>
	<artifactId>druid</artifactId>
	<version>1.0.20</version>
</dependency>
<dependency>
	<groupId>mysql</groupId>
	<artifactId>mysql-connector-java</artifactId>
	<version>5.1.21</version>
</dependency>

<dependency>
	<groupId>org.mybatis.spring.boot</groupId>
	<artifactId>mybatis-spring-boot-starter</artifactId>
	<version>1.1.1</version>
</dependency>
```
引入依赖后，需要指定配置: 指定数据源配置和mybatis的配置
```
spring:
  datasource:
    url: jdbc:mysql://qdm16XX64XX8.my3w.com:3306/XXXXXX
    username: qdm1664XX8
    password: xxxXXXXXX
    driver-class-name: com.mysql.jdbc.Driver
    type: com.alibaba.druid.pool.DruidDataSource
    druid:
      initial-size: 1
      min-idle: 1
      max-active: 20
      test-on-borrow: true
      stat-view-servlet.allow: true
mybatis:
  config-locations: classpath:mybatis-config.xml
  mapper-locations: classpath:mapper/*.xml
```

在mybatis-config.xml中，设置了mybatis的相关配置，比引用了其他的mapper.xml，xml文件中，namespace是指定的mapper类，mapper类是一个接口，内部的方法与sql语句的id相对应。
最后，在启动的时候，加入`@MapperScan`并指定包名，使之可以自动扫描。
```
@SpringBootApplication
@MapperScan("com.shisj.study.springboot.hello.mybatis.mapper")
public class App {
    public  static void main(String args[]){
        SpringApplication.run(App.class,args);
    }
}
```

## 8.日志管理


**设置级别**

默认是Info级别，如果需要debug级别，可以在application.yml中配置`debug： true`启动debug日志。

设置包名或Logger名的日志级别,root是Logger的名字，com.didspace是包名
```
logging.level.root=WARN
logging.level.com.didispace=Debug
```

**文件**

`logging.path`设置目录，会在该目录下创建spring.log文件;  
`logging.file`可以是绝对路径，也可以是相对路径
只需要指定一个配置即可

**使用logback**

将`logback.xml`放在src/main/resources目录下，如果不在该目录下，可以使用下面的配置指定路径
```
# 在src/main/resources/log下
logging.config=classpath:log/logback.xml
```


## 9.表单校验

在controller处理数据时，可以对数据进行校验，例如传入id和name，我们要求id必须大于18,name不能为空，那么可以这样处理：

* 将原先的@PathVariable和@RequestParam修改为对象，让参数包装为对象User后传入
* 使用@Valid表明需要校验User，处理结果需要自己来处理，信息在BindingResult中;使用@Validated时，传入之前的user应该符合校验规则，如果不符合，会抛出异常，由统一的异常处理机制来处理。

下面的例子是两个传入方式
```
@GetMapping("/t1")
public User valid(@Valid User user, BindingResult bindingResult) throws Exception {
    if(bindingResult.hasErrors()){

        throw new Exception(bindingResult.getFieldError().getField()+" "+bindingResult.getFieldError().getDefaultMessage());
    }
    System.out.print("add user" + user.toString());
    return user;
}

@GetMapping("/t2")
public User valid2(@Validated User user) throws Exception {
    System.out.print("add user" + user.toString());
    return user;
}
```

User对象的id和name属性配置如下：
```
@Min(value= 18,message = "too small,values is {value}!")
private int id;

@NotBlank(message = "cannot be null!")
private String name;
```

提供的校验规则有如下几种：
```
@Null	限制只能为null
@NotNull	限制必须不为null
@AssertFalse	限制必须为false
@AssertTrue	限制必须为true
@DecimalMax(value)	限制必须为一个不大于指定值的数字
@DecimalMin(value)	限制必须为一个不小于指定值的数字
@Digits(integer,fraction)	限制必须为一个小数，且整数部分的位数不能超过integer，小数部分的位数不能超过fraction
@Future	限制必须是一个将来的日期
@Max(value)	限制必须为一个不大于指定值的数字
@Min(value)	限制必须为一个不小于指定值的数字
@Past	限制必须是一个过去的日期
@Pattern(value)	限制必须符合指定的正则表达式
@Size(max,min)	限制字符长度必须在min到max之间
@Past	验证注解的元素值（日期类型）比当前时间早
@NotEmpty	验证注解的元素值不为null且不为空（字符串长度不为0、集合大小不为0）
@NotBlank	验证注解的元素值不为空（不为null、去除首位空格后长度为0），不同于@NotEmpty，@NotBlank只应用于字符串且在比较时会去除字符串的空格
@Email	验证注解的元素值是Email，也可以通过正则表达式和flag指定自定义的email格式
```

**自定义验证器**

使用的时候查资料吧


## 10.Aop

在发送外部接口时，需要记录流水，发送前insert，返回后update记录结果。这样可以将业务逻辑作为一个切面。

1. 引入依赖
```
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-aop</artifactId>
</dependency>
```

2.定义切面

使用`@Aspect`声明类，`@Before`和`@After`指定要拦截类的方法
```

@Aspect
@Component
public class HttpAspect {

    private final  static Logger logger = LoggerFactory.getLogger(HttpAspect.class);

    @Pointcut("execution(public * com.shisj.study.springboot.hello.controller.AspectController.*(..))")
    public void log(){
    }

    @Before("log())")
    public void log1(JoinPoint joinpoint){
        ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        logger.info("insert record");
        if(attrs != null){
            HttpServletRequest request = attrs.getRequest();
            logger.info("url={}",request.getRequestURL());
            logger.info("method={}",request.getMethod());
            logger.info("ip={}",request.getRemoteAddr());
        }
        // 获取类和方法
        logger.info("class_method={}",joinpoint.getSignature().getDeclaringTypeName()+"."+joinpoint.getSignature().getName());
        // 获取参数
        logger.info("args={}",joinpoint.getArgs());
    }

    @After("log())")
    public void log2(){
        logger.info("update record");
    }
	@AfterReturning(pointcut = "log()",returning = "object")
    public void doAfterReturning(Object object){
        logger.info("response={}",object);
    }
}
```


## 11.多数据源