package com.ljx.controller;

import com.ljx.domain.Enterprise;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/books")
public class BookController {

    @Value("${lesson}")
    private String lesson;
    @Value("${server.port}")
    private Integer port;

    @Value("${enterprise.subject[0]}")
    private String subject_00;

    @Autowired
    private Environment environment;
    @Autowired
    private Enterprise enterprise;


    @GetMapping("/{id}")
    public String getById(@PathVariable Integer id){
        System.out.println("id=" + id);
        //第一种读取属性方式
        System.out.println(lesson);
        System.out.println(port);
        System.out.println(subject_00);
        System.out.println("---------");
        //第二种读取属性方式，全读
        System.out.println(environment.getProperty("lesson"));
        System.out.println(environment.getProperty("server.port"));
        System.out.println(environment.getProperty("enterprise.subject[1]"));
        System.out.println("-------");
        //第三种读取属性方式，自定义对象封装指定数据
        System.out.println(enterprise);
        return "hello,springboot!";
    }
}
