package com.ljx;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ljx.dao.UserDao;
import com.ljx.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

@SpringBootTest
class MybatisplusDqlApplicationTests {

    @Autowired
    private UserDao userDao;

    @Test
    void testGetAll() {
        //方法一：按条件查询
        /*QueryWrapper wrapper = new QueryWrapper<>();
        wrapper.lt("age",18);
        List<User> userList = userDao.selectList(wrapper);
        System.out.println(userList);*/

        //方式二：lambda格式查询
        /*QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.lambda().lt(User::getAge,18);
        List<User> userList = userDao.selectList(wrapper);
        System.out.println(userList);*/

        //方式三：同上
        /*LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<User>();
        lqw.lt(User::getAge,18);
        List<User> userList = userDao.selectList(lqw);
        System.out.println(userList);*/

        //查询映射
        /*LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<User>();
        lqw.select(User::getId,User::getName,User::getAge);*/

        /*QueryWrapper queryWrapper = new QueryWrapper<>();
        queryWrapper.select("id","name","age");
        List<User> userList = userDao.selectList(queryWrapper);
        System.out.println(userList);*/
        //查询结果包含吗实体类未定义的属性
        QueryWrapper<User> qm = new QueryWrapper<>();
        qm.select("count(*) as nums,tel");//查询不同电话对应数量
        qm.groupBy("tel");
        List<Map<String, Object>> maps = userDao.selectMaps(qm);
        System.out.println(maps);
    }

}
