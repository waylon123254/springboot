package com.duck.serviceImpl;

import com.duck.pojo.RoleMenu;
import com.duck.mapper.RoleMenuMapper;
import com.duck.service.RoleMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 角色菜单中间表 服务实现类
 * </p>
 *
 * @author Duck
 * @since 2022-03-23
 */
@Service
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper, RoleMenu> implements RoleMenuService {

}
