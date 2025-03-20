# SpringBoot-Mybatisplus
SpringBoot和MyBatisPlus整合开发

## 关于SpringBoot
springboot感觉没有什么好说的，唯一给我的感觉是震惊地好用，非常简洁，原先我们在做基础的spring开发，包括springmvc，我们要设置一堆的配置类，还有pom包下一堆的配置，十分的繁琐，jdbc链接数据库的配置，我们还有一个个设置数据库的属性。
还有mybatis还要自己设置bean返回对象，等等这一系列操作，还要创建Tomcat服务器运行。真的十分繁琐。。。 
但是有了springboot，瞬间感觉开发变得简单了，配置类不用你写了，mybatis扫描映射的动态代理只用在dao层写上@Mapper就可以了，测试已经整合junit了，启动类甚至给你内置了Tomcat。
瞬间感觉spring不香了。

## MybatisPlus
### mp使用
MybatisPlus（简称mp）属于是国人开发的api，其功能之强大不亚于springboot，大大简化了sql的开发，很多时候不用自己写sql语句了，只需要继承BaseMapper类即可
```
@Mapper
public interface UserDao extends BaseMapper<User> {
}
```
这里<>泛型写自己的实体类
还有我们基本推荐用boot项目来写，这里不要忘记在创建项目的时候加上mysql driver，mybatis的依赖。这里是没有mp的，我们还需要在pom文件下写上对应的依赖。还有数据源也不要忘记，如果有异常可以参考我的CSDN的笔记
```
<!--mybatisplus依赖-->
        <dependency>
            <groupId>com.baomidou</groupId>
            <artifactId>mybatis-plus-spring-boot3-starter</artifactId>
            <version>3.5.9</version>
        </dependency>
        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>druid</artifactId>
            <version>1.2.23</version>
        </dependency>
```
### mp实现分页查询
我们正常写sql语言，要想实现分类查询，无非就是select * from xxx limit （x,x），这里写上页数和每页多少数据，就可以实现分页查询
mp中也有这个功能，不过我觉得就没那么方便
首先要用mp封装的selectPage(page,queryWrapper)方法，里面参数第一个是mp的一个对象，其接口是IPage，queryWrapper我们暂时不管，为null。还要把这个IPage实体类给new出来，参数就是第几页，每页多少数据
接着光有这个还不行，我们还得写一个配置类，定义一个拦截器，加上page的拦截器才可以实现分页功能
```
@Configuration
public class MpConfig {

    @Bean
    public MybatisPlusInterceptor mpInterceptor(){
        //定义mp拦截器
        MybatisPlusInterceptor mybatisPlusInterceptor =
                new MybatisPlusInterceptor();
        //添加具体拦截器
        mybatisPlusInterceptor.addInnerInterceptor(new PaginationInnerInterceptor());
        return mybatisPlusInterceptor;
    }
}
```
注意一点，如果你的mybatis版本过高可能没有这个Interceptor类，我们还要降低版本，这里我降到3.5.4.1才可以

### 如何使我们打印出的信息变得简介
我们在启动springboot和mybatisplus的时候，往往出现很多INFO信息，这些信息对我们来说没有用，那么如何去除呢？
首先在resources中创建一个xml文件，这里我叫logback.xml，然后除第一行以外其他都删掉，配成这样：
```
<?xml version="1.0" encoding="UTF-8"?>
<configuration>
</configuration>
```
就可以看到没有INFO那些多余的信息了，变得简洁，那么如何更近一步，把mybatisplus和springboot也隐藏呢
在yml配置文件中加上下面配置就可以了：
```
mybatis-plus:
  global-config:
    banner: false
```
```
server:
  port: 80
spring:
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/mybatisplus_db
    username: root
    password: 123456
    type: com.alibaba.druid.pool.DruidDataSource
  main:
    banner-mode: off
```
spring的也加上

### mp条件查询
先来讲一个简单的例子，我们在使用basemapper给我们的方法--selectList()的时候，里面要传入wrapper，这个wrapper就是自定义对象
用来放我们的查询条件的
```
void testGetAll() {
        //按条件查询
        QueryWrapper wrapper = new QueryWrapper<>();
        wrapper.lt("age",18);
        List<User> userList = userDao.selectList(wrapper);
        System.out.println(userList);
    }
```
这里我在测试类里放了一个测试方法，首先new这个wrapper对象，然后设置查询条件，lt指的是小于，第一个参数是列名
这样设置有个问题，这个age可能会出现错误的情况，因此还有一种方法，用lambda格式来查询
```
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.lambda().lt(User::getAge,18);
        List<User> userList = userDao.selectList(wrapper);
        System.out.println(userList);
```
在查询前面加一个lambda，这里要在wrapper对象前加上泛型，后面age用方法引用
还有更简单的方法
```
        LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<User>();
        lqw.lt(User::getAge,18);
        List<User> userList = userDao.selectList(lqw);
        System.out.println(userList);
```

### 查询投影
众所周知，我们使用selectList()方法可以查询到所有制，有没有什么办法可以只显示个别字段呢？
有的兄弟有的
```
        LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<User>();
        lqw.select(User::getId,User::getName,User::getAge);
        List<User> userList = userDao.selectList(lqw);
        System.out.println(userList);
```
这样我们就可以只显示对应的字段，但只限于lambda格式
普通的这样写：
```
        QueryWrapper queryWrapper = new QueryWrapper<>();
        queryWrapper.select("id","name","age");
        List<User> userList = userDao.selectList(queryWrapper);
        System.out.println(userList);
```
