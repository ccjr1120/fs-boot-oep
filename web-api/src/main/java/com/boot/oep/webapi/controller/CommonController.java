package com.boot.oep.webapi.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.UUID;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.boot.oep.model.SysMenu;
import com.boot.oep.model.SysUser;
import com.boot.oep.result.ApiResponse;
import com.boot.oep.security.LoginUserDetails;
import com.boot.oep.security.SecurityUserUtils;
import com.boot.oep.security.UserRoleEnum;
import com.boot.oep.service.SysUserService;
import com.boot.oep.webapi.model.vo.RoleMenuVo;
import com.boot.oep.webapi.service.SysMenuService;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * @author zrf
 * @date 2021/4/8
 */
@RestController
@RequestMapping("/common")
public class CommonController extends BaseController{

    @Resource
    private SysMenuService sysMenuService;
    @Resource
    private SysUserService sysUserService;
    @Resource
    private PasswordEncoder passwordEncoder;


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

    @PostMapping("/baseUserInfo")
    public ApiResponse<SysUser> getBaseUserInfo(){
        SysUser sysUser = sysUserService.getById(getCurId());
        sysUser.setPassword(null);
        return ApiResponse.ok(sysUser);
    }

    @PostMapping("/updateUserInfo")
    public ApiResponse<SysUser> getBaseUserInfo(@RequestBody SysUser sysUser){
        SysUser sysUserDb = sysUserService.getById(getCurId());
        sysUserDb.setUsername(sysUser.getUsername());
        sysUserDb.setName(sysUser.getName());
        sysUserService.updateById(sysUserDb);
        return ApiResponse.ok(sysUserDb);
    }

    @PostMapping("/updatePwd")
    public ApiResponse<SysUser> updatePwd(@RequestParam("oldPwd") String oldPwd, @RequestParam("newPwd") String newPwd){
        SysUser sysUser = sysUserService.getById(getCurId());
        if (passwordEncoder.matches(oldPwd, sysUser.getPassword())){
            sysUser.setPassword(passwordEncoder.encode(newPwd));
            sysUserService.updateById(sysUser);
            return ApiResponse.ok();
        }
        return ApiResponse.fail("更新失败，密码不匹配");
    }

    @PostMapping("/upload")
    public ApiResponse<String> upload(@RequestParam(value = "file") MultipartFile multipartFile) throws IOException {
        File directory = new File("");
        String courseFile = directory.getCanonicalPath();
        String path = courseFile + File.separator + "img" + File.separator;
        if (!FileUtil.exist(path)) {
            FileUtil.mkdir(path);
        }
        String suffix = Objects.requireNonNull(multipartFile.getOriginalFilename()).substring(multipartFile.getOriginalFilename().lastIndexOf(".") + 1);
        String fileName = UUID.randomUUID().toString().replace("-", "") + "." + suffix;
        File file = FileUtil.writeBytes(multipartFile.getBytes(), path + fileName);
        String fileUrl = "";
        if (file.length() > 0) {
            fileUrl = "/img/"+ fileName ;
        }
        SysUser sysUser = new SysUser();
        sysUser.setId(getCurId());
        sysUser.setAvatarUrl(fileUrl);
        sysUserService.updateById(sysUser);
        return ApiResponse.ok(fileUrl);
    }

    @PostMapping("/remove")
    public ApiResponse<String> remove(@RequestParam(value = "file") String fileName) throws IOException {
        File directory = new File("");
        String courseFile = directory.getCanonicalPath();
        if(FileSystemUtils.deleteRecursively(new File(courseFile + fileName))){
            return ApiResponse.ok();
        }
        return ApiResponse.fail("服务器上不存在该文件");
    }


}
