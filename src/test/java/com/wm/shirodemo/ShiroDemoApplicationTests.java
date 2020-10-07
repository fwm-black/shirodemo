package com.wm.shirodemo;

import org.apache.shiro.crypto.hash.Md5Hash;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ShiroDemoApplicationTests {

    @Test
    void contextLoads() {

        Md5Hash md5Hash = new Md5Hash("6666","mySlat",22);
        System.out.println(md5Hash.toHex());

    }

}
