package com.ljx;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ljx.dao.UserDao;
import com.ljx.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
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
       /* QueryWrapper<User> qm = new QueryWrapper<>();
        qm.select("count(*) as nums,tel");//查询不同电话对应数量
        qm.groupBy("tel");
        List<Map<String, Object>> maps = userDao.selectMaps(qm);
        System.out.println(maps);*/

        //---------------------------------------------------------

        /*LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<User>();
        //等同于=
        lqw.eq(User::getName,"jerry").eq(User::getPassword,"jerry");
        User loginUser = userDao.selectOne(lqw);//如果只查一个数据用selectone即可
        System.out.println(loginUser);*/

        /*LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<User>();
        //范围查询 lt（不带等号）   le（带等号） gt ge eq between
        lqw.between(User::getAge,10,30);
        List<User> userList = userDao.selectList(lqw);
        System.out.println(userList);*/

        LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<User>();
        //模糊匹配 like
        lqw.like(User::getName,"j");//likeRight指的是j% %在右边。likeLeft同理
        List<User> userList = userDao.selectList(lqw);
        System.out.println(userList);


    }

    @Test
    void testDelete(){
        List<Long> list = new ArrayList<>();
        list.add(1L);
        list.add(2L);
        userDao.deleteBatchIds(list);
    }

    @Test
    void testSelect(){
        List<Long> list = new ArrayList<>();
        list.add(1L);
        list.add(2L);
        List<User> userList = userDao.selectBatchIds(list);
        System.out.println(userList);
    }

    @Test
    void testUpdate(){
        /*User user = new User();
        user.setId(2L);
        user.setName("Jock666");
        user.setVersion(1);
        userDao.updateById(user);*/

        //乐观锁，修改前将version比对
        //先通过要修改的数据id将当前数据查询出来
        User user = userDao.selectById(2L);//version = 3
        User user2 = userDao.selectById(2L);//version = 3

        user2.setName("Jock888");
        userDao.updateById(user2);//version = 4

        user.setName("Jock888");
        userDao.updateById(user);//version = 3

    }

}
