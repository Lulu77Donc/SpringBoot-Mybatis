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
