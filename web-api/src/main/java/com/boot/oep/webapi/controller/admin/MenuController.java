package com.boot.oep.webapi.controller.admin;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.boot.oep.model.SysMenu;
import com.boot.oep.result.ApiResponse;
import com.boot.oep.webapi.controller.BaseController;
import com.boot.oep.webapi.model.dto.MenuDto;
import com.boot.oep.webapi.model.dto.MenuQueryDto;
import com.boot.oep.webapi.model.vo.SysMenuVo;
import com.boot.oep.webapi.service.SysMenuService;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * @author ccjr
 * @date 2021/3/28 9:53 下午
 */
@RestController
@RequestMapping("/admin/menu")
public class MenuController extends BaseController {

    private final SysMenuService sysMenuService;
    private static final String ROOT_KEY = "/";

    public MenuController(SysMenuService sysMenuService) {
        this.sysMenuService = sysMenuService;
    }

    @PostMapping("/list")
    public ApiResponse<IPage<SysMenuVo>> listMenu(@RequestBody @Valid MenuQueryDto dto){
        QueryWrapper<SysMenu> queryWrapper = new QueryWrapper<>();
        if (StrUtil.isNotBlank(dto.getQueryStr())){
            queryWrapper.like("name", dto.getQueryStr())
                    .or().like("path", dto.getQueryStr());
        }
        List<SysMenu> sysMenuList = sysMenuService.list(queryWrapper);
        List<SysMenuVo> sysMenuVoList = new ArrayList<>();
        for (SysMenu record : sysMenuList) {
            SysMenuVo sysMenuVo = new SysMenuVo();
            if (ROOT_KEY.equals(record.getParentId())){
                BeanUtils.copyProperties(record, sysMenuVo);
                sysMenuVoList.add(sysMenuVo);
            }else{
                boolean f = false;
                for (SysMenuVo menuVo : sysMenuVoList) {
                    if (menuVo.getId().equals(record.getParentId())){
                        f = true;
                        if (menuVo.getChildren() == null){
                            menuVo.setChildren(new ArrayList<>());
                        }
                        menuVo.getChildren().add(record);
                        break;
                    }
                }
                if (!f){
                    SysMenu sysMenu = sysMenuService.getById(record.getParentId());
                    BeanUtils.copyProperties(sysMenu, sysMenuVo);
                    if (sysMenuVo.getChildren() == null){
                        sysMenuVo.setChildren(new ArrayList<>());
                    }
                    sysMenuVo.getChildren().add(record);
                    sysMenuVoList.add(sysMenuVo);
                }
            }
        }
        sysMenuVoList = sysMenuVoList.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(SysMenu :: getId))), ArrayList::new));
        Page<SysMenuVo> sysMenuVoPage = new Page<>();
        sysMenuVoPage.setCurrent(dto.getCurrent());
        sysMenuVoPage.setSize(dto.getPageSize());
        sysMenuVoPage.setTotal(sysMenuVoList.size());
        sysMenuVoList.sort((x, y)->x.getSort().compareTo(y.getSort()));
        int start = (dto.getCurrent() - 1) * dto.getPageSize();
        int end = (dto.getCurrent() - 1) * dto.getPageSize() + dto.getPageSize();
        if (start >= sysMenuVoList.size()){
            return ApiResponse.ok(new Page<>());
        }
        if (end >= sysMenuVoList.size()){
            end = sysMenuVoList.size();
        }
        sysMenuVoPage.setRecords(sysMenuVoList.subList(start, end));
        return ApiResponse.ok(sysMenuVoPage);
    }

    @PostMapping("/listFirstMenu")
    public ApiResponse<List<SysMenu>> listFirstMenu(){
        QueryWrapper<SysMenu> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("parent_id", "/");
        List<SysMenu> sysMenuList = sysMenuService.list(queryWrapper);
        return ApiResponse.ok(sysMenuList);
    }

    @PostMapping("/add")
    public ApiResponse<String> addMenu(@RequestBody MenuDto menuDTO){
        SysMenu sysMenu = new SysMenu();
        menuDTO.setPath("/app" + menuDTO.getPath());
        BeanUtil.copyProperties(menuDTO, sysMenu);
        SysMenu sysMenuDb = sysMenuService.getOne(
                new LambdaQueryWrapper<SysMenu>().eq(SysMenu::getName, sysMenu.getName())
                .or().eq(SysMenu::getPath, sysMenu.getPath()));
        if (sysMenuDb != null){
            return ApiResponse.fail("菜单或路径已存在");
        }
        sysMenu.setRoles(JSON.toJSONString(menuDTO.getRoles()));
        sysMenuService.save(sysMenu);
        return ApiResponse.ok();
    }

    @PostMapping("/update")
    public ApiResponse<String> updateOne(@RequestBody MenuDto menuDTO){
        SysMenu sysMenu = sysMenuService.getById(menuDTO.getId());
        if (sysMenu == null){
            return ApiResponse.fail("该菜单不存在");
        }
        BeanUtil.copyProperties(menuDTO, sysMenu);
        sysMenuService.updateById(sysMenu);
        return ApiResponse.ok();
    }

    @PostMapping("/del")
    public ApiResponse<String> delOne(@RequestBody SysMenu sysMenu){
        sysMenuService.remove(new QueryWrapper<SysMenu>().eq("id", sysMenu.getId())
        .or().eq("parent_id", sysMenu.getId()));
        return ApiResponse.ok();
    }

}
