package com.wm.shirodemo.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("t_role")
public class Role implements Serializable {

    private int id;
    private String role;

}
