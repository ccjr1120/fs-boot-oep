package com.boot.oep.model;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.boot.oep.model.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.time.LocalDateTime;

/**
* @author ccjr
* @date 2021年03月21 09:50 
*/
@Data
@EqualsAndHashCode(callSuper = true)
public class SysUser extends BaseEntity {

	@TableId(type = IdType.ASSIGN_UUID)
	private String id;

	/**
	 * 登录名
	 */
	private String username;

	/**
	 * 姓名
	 */
	private String name;

	/**
	 * 用户密码
	 */
	private String password;

	/**
	 * 由于用户角色不多，选择在代码的枚举类中体现,不单独建表。1：学生，2:老师, 3:管理员
	 */
	private Integer roleId;

	/**
	 * 账号锁定状态
	 */
	private Boolean lockFlag;

	/**
	 * 头像地址
	 */
	private String avatarUrl;

}
