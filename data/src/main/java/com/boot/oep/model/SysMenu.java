package com.boot.oep.model;


import com.boot.oep.model.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.time.LocalDateTime;

/**
* @author ccjr
* @date 2021年03月28 10:02 
*/
@Data
@EqualsAndHashCode(callSuper = true)
public class SysMenu extends BaseEntity {

	private String id;

	/**
	 * 菜单名称
	 */
	private String name;

	/**
	 * 路由地址(前端)
	 */
	private String path;

	/**
	 * 权限列表
	 */
	private String roles;

	/**
	 * 父级菜单Id
	 */
	private String parentId;


}
