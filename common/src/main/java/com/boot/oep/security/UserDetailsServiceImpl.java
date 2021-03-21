package com.boot.oep.security;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.boot.oep.model.SysUser;
import com.boot.oep.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

/**
 * @author ccjr
 * @date 2021/3/20 10:38 上午
 */
@Slf4j
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final SysUserService sysUserService;

    public UserDetailsServiceImpl(SysUserService sysUserService) {
        this.sysUserService = sysUserService;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        SysUser sysUser = sysUserService.getOne(new QueryWrapper<SysUser>().eq("username", s));
        if(sysUser == null){
            throw new UsernameNotFoundException("用户名或密码不正确");
        }
        Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority(UserRoleEnum.getRoleNameById(sysUser.getUserType())));
        LoginUserDetails loginUserDetails = new LoginUserDetails();
        BeanUtil.copyProperties(sysUser, loginUserDetails);
        loginUserDetails.setAuthorities(authorities);
        return loginUserDetails;
    }
}
