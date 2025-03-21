package com.ljx.domain;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.Version;
import lombok.Data;

@Data
public class User {
    private Long id;
    private String name;
    private String password;
    private Integer age;
    private String tel;

    @TableLogic//逻辑删除
    private Integer deleted;

    @Version //乐观锁
    private Integer version;
}
