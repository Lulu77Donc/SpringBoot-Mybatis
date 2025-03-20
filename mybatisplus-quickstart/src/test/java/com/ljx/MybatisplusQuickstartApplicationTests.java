package com.ljx;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ljx.dao.UserDao;
import com.ljx.domain.User;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest

class MybatisplusQuickstartApplicationTests {

    @Autowired
    private UserDao userDao;

    @Test
    void testSave(){
        User user = new User();
        user.setPassword("kai");
        user.setName("kai");
        user.setAge(32);
        user.setTel("111");
        user.setId(5L);
        userDao.insert(user);
    }

    @Test
    void testDelete(){
        userDao.deleteById(5L);
    }

    @Test
    void testUpdate(){
        User user = new User();
        user.setName("Tom666");
        user.setId(1L);
        user.setPassword("Tom888");
        userDao.updateById(user);
    }

    @Test
    void testGetById(){
        User user = userDao.selectById(2L);
        System.out.println(user);
    }

    @Test
    void testGetAll() {
        List<User> userList = userDao.selectList(null);
        System.out.println(userList);
    }

    @Test
    void testGetByPage(){
        IPage page = new Page(1,2);//第一页，每页2个
        userDao.selectPage(page,null);
        System.out.println("当前页码值："+page.getCurrent());//当前第几页
        System.out.println("一共多少页："+page.getPages());
        System.out.println("每页显示数："+page.getSize());
        System.out.println("一共多少条数据："+page.getTotal());
        System.out.println("数据："+page.getRecords());
    }

}
