# SpringBoot-Mybatisplus
SpringBoot和MyBatisPlus整合开发
## 关于SpringBoot
springboot感觉没有什么好说的，唯一给我的感觉是震惊地好用，非常简洁，原先我们在做基础的spring开发，包括springmvc，我们要设置一堆的配置类，还有pom包下一堆的配置，十分的繁琐，jdbc链接数据库的配置，我们还有一个个设置数据库的属性。还有mybatis还要自己设置bean返回对象，等等这一系列操作，还要创建Tomcat服务器运行。真的十分繁琐。。。
但是有了springboot，瞬间感觉开发变得简单了，配置类不用你写了，mybatis扫描映射的动态代理只用在dao层写上@Mapper就可以了，测试已经整合junit了，启动类甚至给你内置了Tomcat。瞬间感觉spring不香了。
