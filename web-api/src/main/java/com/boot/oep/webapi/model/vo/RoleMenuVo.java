package com.boot.oep.webapi.model.vo;

import lombok.Data;

import java.util.List;

/**
 * @author zrf
 * @date 2021/4/8
 */
@Data
public class RoleMenuVo {

    private String key;
    private String path;
    private String name;
    private Integer sort;
    private List<RoleMenuVo> children;

}
