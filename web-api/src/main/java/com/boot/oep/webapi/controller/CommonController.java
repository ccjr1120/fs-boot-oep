package com.boot.oep.webapi.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.boot.oep.model.SysMenu;
import com.boot.oep.result.ApiResponse;
import com.boot.oep.security.SecurityUserUtils;
import com.boot.oep.webapi.model.vo.RoleMenuVo;
import com.boot.oep.webapi.service.SysMenuService;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zrf
 * @date 2021/4/8
 */
@RestController
@RequestMapping("/common")
public class CommonController extends BaseController{

    @Resource
    private SysMenuService sysMenuService;


    @PostMapping("/curMenus")
    public ApiResponse<List<RoleMenuVo>> getCurMenuList(){
        Integer roleId = SecurityUserUtils.getCurUser().getRoleId();
        List<SysMenu> sysMenuList = sysMenuService.list(new QueryWrapper<SysMenu>()
                .like("roles", roleId));
        List<RoleMenuVo> roleMenuVos= new ArrayList<>();
        for (SysMenu sysMenu : sysMenuList) {
            if (ROOT_KEY.equals(sysMenu.getParentId())){
                RoleMenuVo roleMenuVo = new RoleMenuVo();
                BeanUtils.copyProperties(sysMenu, roleMenuVo);
                roleMenuVo.setKey(sysMenu.getId());
                roleMenuVos.add(roleMenuVo);
            }
        }
        int l = roleMenuVos.size();
        for (SysMenu sysMenu : sysMenuList) {
            if (!ROOT_KEY.equals(sysMenu.getParentId())){
                for (RoleMenuVo roleMenuVo : roleMenuVos) {
                    if (roleMenuVo.getKey().equals(sysMenu.getParentId())){
                        RoleMenuVo roleMenuVo1 = new RoleMenuVo();
                        BeanUtils.copyProperties(sysMenu, roleMenuVo1);
                        roleMenuVo1.setKey(sysMenu.getId());
                        List<RoleMenuVo> childList = roleMenuVo.getChildren();
                        if (childList == null){
                            childList = new ArrayList<>();
                        }
                        childList.add(roleMenuVo1);
                        roleMenuVo.setChildren(childList);
                    }
                }
            }
        }
        roleMenuVos.sort((x, y)->x.getSort().compareTo(y.getSort()));
        return ApiResponse.ok(roleMenuVos);
    }

}
