package com.ziwang.user.service;

import com.ziwang.user.dao.UserRepository;
import com.ziwang.user.pojo.User;
import entity.Result;
import entity.StatusCode;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import util.IdWorker;
import util.JwtUtil;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
public class UserService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private UserRepository repository;

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private BCryptPasswordEncoder encoder;

    // 发送短信验证码
    public void sendSms(String mobile) {
        User isExist = repository.findUserByMobile(mobile);

        // 6位随机验证码
        String code = RandomStringUtils.randomNumeric(6);

        //放入缓存中
        redisTemplate.opsForValue().set("code_" + mobile, code, 5, TimeUnit.MINUTES);

        //发送给用户
        Map<String, String> map = new HashMap<>();
        map.put("mobile", mobile);
        map.put("code", code);
        rabbitTemplate.convertAndSend("sms", map);

        //测试显示
        System.out.println(code);
    }

    // 注册
    public Map<String, Object> register(String code, User user) {
        User isExist = repository.findUserByMobile(user.getMobile());
        if (isExist != null){
            throw new RuntimeException("该手机号已注册");
        }

        String checkCode = redisTemplate.opsForValue().get("code_" + user.getMobile());

        if (StringUtils.isEmpty(checkCode)){
            throw new RuntimeException("验证码已过期");
        }

        if (!code.equals(checkCode)) {
            throw new RuntimeException("验证码输入不正确");
        }

        user.setId(idWorker.nextId() + "");
        user.setAvatar("https://i.loli.net/2019/06/09/5cfca41fb3aa192172.png");
        user.setFollowcount(0);//关注数
        user.setFanscount(0);//粉丝数
        user.setRegdate(new Date());//注册日期
        user.setUpdatedate(new Date());//更新日期
        user.setLastdate(new Date());//最后登陆日期

        //密码加盐
        user.setPassword(encoder.encode(user.getPassword()));

        repository.save(user);

        return createJWT(user);

    }

    // 通过账户登录
    public Map<String, Object> loginByAccount(Map<String,String> user) {

        Optional<User> optional = repository.findUserByUsername(user.get("username"));

        User loginUser = optional.orElseThrow(() -> new RuntimeException("用户名不存在"));

        if (encoder.matches(user.get("password"),loginUser.getPassword())){
            return createJWT(loginUser);
        }else {
            throw new RuntimeException("用户名或密码错误");
        }
    }

    // 通过短信验证码登录
    public Map<String,Object> loginBySms(String code, User user) {
        String mobile = user.getMobile();
        String checkCode = redisTemplate.opsForValue().get("code_" + mobile);
        if (StringUtils.isEmpty(checkCode)){
            throw new RuntimeException("验证码已过期");
        }
        if (!code.equals(checkCode)){
            throw new RuntimeException("验证码输入不正确");
        }else{
            User loginUser = repository.findUserByMobile(mobile);
            if (loginUser == null){
                throw new RuntimeException("用户不存在，请注册！");
            }

           return createJWT(loginUser);
        }
    }

    /**
     * 根据Token查询用户信息
     * @param token Token
     * @return 用户信息userInfo
     */
    public User userInfo(String token) {
        Claims claims = jwtUtil.parseJWT(token);
        String id = claims.getId();
        return repository.findById(id).get();
    }

    public Result doLogin(String token) {

        if (token == null){
            return new Result(false,StatusCode.LOGINERROR,"未登录，请先登录");
        }

        try{
            Claims claims = jwtUtil.parseJWT(token);
        }catch (ExpiredJwtException e){
            return new Result(false, StatusCode.EXPIREDERROR,"登录信息过期,请重新登录");
        }catch (MalformedJwtException | SignatureException e){
            return new Result(false,StatusCode.LOGINERROR,"未登录，请先登录");
        }

        return new Result(true, StatusCode.OK,"已登录");
    }

    public User user(String id) {
        return repository.findById(id).get();
    }


    /**
     * 登录成功创建Token
     * @param user 用户信息
     * @return 登录信息
     */
    private Map<String,Object> createJWT(User user){
        String token = jwtUtil.createJWT(user.getId(),user.getUsername(),"user");

        Map<String,Object> map = new HashMap<>();

        map.put("token",token);
        map.put("name",user.getUsername());
        map.put("avatar",user.getAvatar());
        //map.put("role","user");

        return map;
    }

    public void updateUser(User user) {
        repository.save(user);
    }
}
