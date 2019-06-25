package com.ziwang.resource.client.impl;

import com.ziwang.resource.client.UserClient;
import entity.Result;
import entity.StatusCode;
import org.springframework.stereotype.Component;

@Component
public class UserClientImpl implements UserClient {

    @Override
    public Result findUserById(String id) {
        return new Result(false, StatusCode.ERROR,"服务器宕机！熔断器启动！");
    }
}
