package com.ziwang.resource.client;

import com.ziwang.resource.client.impl.UserClientImpl;
import entity.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "ziwang-user",fallback = UserClientImpl.class)
public interface UserClient {

    @GetMapping("/user/{id}")
    Result findUserById(@PathVariable String id);

}
