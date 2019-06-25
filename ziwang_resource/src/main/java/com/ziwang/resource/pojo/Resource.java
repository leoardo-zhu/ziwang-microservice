package com.ziwang.resource.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;
import java.util.LinkedHashMap;

/**
 * 实体类
 * @author Administrator
 *
 */
@Entity
@Table(name="tb_resource")
@Data
public class Resource implements Serializable{

	@Id
	private String id;//


	private String title;
	private String name;//
	private String type;//
	private Integer category;//
	private String description;//

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date uploaddate;//

	private String state;//
	private String ishot;//
	private String ispublic;//
	private Integer downloads;//
	private String tags;//
	private Integer cost;//

	private String uid;

	@Transient
	private Object user;
}
