package com.boot.oep.webapi.controller.admin;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.boot.oep.model.SysMenu;
import com.boot.oep.model.SysUser;
import com.boot.oep.result.ApiResponse;
import com.boot.oep.service.SysUserService;
import com.boot.oep.webapi.model.dto.MenuQueryDto;
import com.boot.oep.webapi.model.dto.UserDto;
import com.boot.oep.webapi.model.dto.UserQueryDto;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @author zrf
 * @date 2021/4/2
 */
@RestController
@RequestMapping("/admin/user")
public class UserController {

    @Resource
    private SysUserService sysUserService;

    @PostMapping("/list")
    public ApiResponse<IPage<SysUser>> listMenu(@RequestBody @Valid UserQueryDto dto){
        IPage<SysUser> page = new Page<>(dto.getCurrent(), dto.getPageSize());
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        if (dto.getQueryStr() != null){
            queryWrapper.like("name", dto.getQueryStr())
                    .or().like("username", dto.getQueryStr());
        }
        return ApiResponse.ok(sysUserService.page(page, queryWrapper));
    }

    @PostMapping("/add")
    public ApiResponse<String> addMenu(@RequestBody @Valid UserDto dto){
        SysUser sysUser = sysUserService.getOne(new QueryWrapper<SysUser>().eq("username", dto.getUsername()));
        if (sysUser == null){
            sysUser = new SysUser();
            BeanUtils.copyProperties(dto, sysUser);
            sysUserService.save(sysUser);
            return ApiResponse.ok();
        }else{
            return ApiResponse.fail("该账号已存在");
        }
    }

    @PostMapping("/del")
    public ApiResponse<String> removeOne(@RequestBody @Valid UserDto userDto){
        sysUserService.removeById(userDto.getId());
        return ApiResponse.ok();
    }

}
