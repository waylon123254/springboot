package com.duck.service;

import com.duck.pojo.Role;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 角色表 服务类
 * </p>
 *
 * @author Duck
 * @since 2022-03-23
 */
public interface RoleService extends IService<Role> {
    List<Role> listRolesByUserId(Long userId);
}
