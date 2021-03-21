package com.boot.oep.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.boot.oep.mapper.SysUserMapper;
import com.boot.oep.model.SysUser;
import com.boot.oep.service.SysUserService;
import org.springframework.stereotype.Service;

/**
 * @author ccjr
 * @date 2021/3/21 10:12 上午
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {
}
