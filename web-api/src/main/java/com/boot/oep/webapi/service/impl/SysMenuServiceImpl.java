package com.boot.oep.webapi.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.boot.oep.mapper.SysMenuMapper;
import com.boot.oep.model.SysMenu;
import com.boot.oep.webapi.service.SysMenuService;
import org.springframework.stereotype.Service;

/**
 * @author ccjr
 * @date 2021/3/28 10:03 下午
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {
}
