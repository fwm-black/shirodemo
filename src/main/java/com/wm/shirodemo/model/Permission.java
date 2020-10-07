package com.wm.shirodemo.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("t_permission")
public class Permission implements Serializable {

    private int id;
    private String permission;

}
