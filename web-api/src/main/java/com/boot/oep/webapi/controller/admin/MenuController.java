package com.boot.oep.webapi.controller.admin;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.boot.oep.model.SysMenu;
import com.boot.oep.result.ApiResponse;
import com.boot.oep.webapi.model.dto.MenuQueryDto;
import com.boot.oep.webapi.service.SysMenuService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author ccjr
 * @date 2021/3/28 9:53 下午
 */
@RestController
@RequestMapping("/admin/menu")
public class MenuController {

    private final SysMenuService sysMenuService;

    public MenuController(SysMenuService sysMenuService) {
        this.sysMenuService = sysMenuService;
    }

    @PostMapping("/list")
    public ApiResponse<IPage<SysMenu>> listMenu(@RequestBody @Valid MenuQueryDto dto){
        IPage<SysMenu> page = new Page<>(dto.getCurrent(), dto.getPageSize());
        QueryWrapper<SysMenu> queryWrapper = new QueryWrapper<>();
        if (StrUtil.isNotBlank(dto.getQueryStr())){
            queryWrapper.like("name", dto.getQueryStr())
                    .or().like("path", dto.getQueryStr());
        }
        return ApiResponse.ok(sysMenuService.page(page, queryWrapper));
    }

}
