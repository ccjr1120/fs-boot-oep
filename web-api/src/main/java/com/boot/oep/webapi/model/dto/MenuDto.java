package com.boot.oep.webapi.model.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.List;

/**
 * @author ccjr
 * @date 2021/3/30 9:55 下午
 */
@Data
public class MenuDto {


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
    private List<String> roles;

    /**
     * 父级菜单Id
     */
    private String parentId;

}
