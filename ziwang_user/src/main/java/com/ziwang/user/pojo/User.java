package com.ziwang.user.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.NumberFormat;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * 实体类
 *
 * @author Administrator
 */

@Entity
@Table(name = "tb_user")
@Data
public class User implements Serializable {

    @Id
    private String id;//ID


    private String username;//
    private String mobile;//手机号码
    private String password;//密码
    private String nickname;//昵称

    private Integer sex;//性别

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date birthday;//出生年月日

    private String avatar;//头像图片地址
    private String email;//E-Mail

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date regdate;//注册日期

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updatedate;//修改日期

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date lastdate;//最后登陆日期

    private Integer fanscount;//粉丝数
    private Integer followcount;//关注数
    private String address;//
    private String job;//
    private String isvip;//


}
