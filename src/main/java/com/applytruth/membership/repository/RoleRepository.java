package com.applytruth.membership.repository;

import java.util.Collection;

import com.applytruth.membership.domain.Role;

public interface RoleRepository<T extends Role>{
    /* Basic CRUD */
  T create (T data);
  Collection<T> list(int page, int pageSize);
  T get(Long id);
  T update(T data);
  Boolean delete(Long id);

  /* More Complex Operations */
  void addRoleToUser(Long userId, String roleName);
  Role getRoleByUserId(Long userId);
  Role getRoleByUserEmail(String email);
  Role getRoleByRoleName(String roleName);
  Role getRoleByRoleId(Long userId);
  void updateUserRole(Long userId, String roleName);
}
