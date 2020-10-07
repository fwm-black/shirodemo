package com.wm.shirodemo.service;

import java.util.Map;

public interface ShiroService {

    Map<String,Object> createToken(Integer userId);

}
