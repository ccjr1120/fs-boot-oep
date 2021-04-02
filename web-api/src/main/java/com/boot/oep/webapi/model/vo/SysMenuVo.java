package com.boot.oep.webapi.model.vo;

import com.boot.oep.model.SysMenu;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author ccjr
 * @date 2021/4/2 8:21 下午
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class SysMenuVo extends SysMenu {

    private List<SysMenu> children;

}
