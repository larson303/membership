package com.applytruth.membership.repository.implementation;

import static com.applytruth.membership.query.RoleQuery.INSERT_ROLE_TO_USER_QUERY;
import static com.applytruth.membership.query.RoleQuery.SELECT_ROLE_BY_NAME_QUERY;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.applytruth.membership.domain.Role;
import com.applytruth.membership.exception.ApiException;
import com.applytruth.membership.repository.RoleRepository;
import com.applytruth.membership.rowmapper.RoleRowMapper;

import static com.applytruth.membership.enumeration.RoleType.ROLE_USER;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Repository
@RequiredArgsConstructor  
@Slf4j
public class RoleRepositoryImpl implements RoleRepository<Role> {

  private final NamedParameterJdbcTemplate jdbcTemplate;
  String msgRoleMissing = "Role does not exist in the database: ";

  @Override
  public void addRoleToUser(Long userId, String roleName) {
    log.info("Adding role {} to user id: {}", roleName, userId);    
    try {
        // Get the role id from the database
        Role role = jdbcTemplate.queryForObject(SELECT_ROLE_BY_NAME_QUERY, Map.of("roleName", roleName), new RoleRowMapper());
        jdbcTemplate.update(INSERT_ROLE_TO_USER_QUERY, Map.of("userId", userId, "roleId", Objects.requireNonNull(role).getId()));
    
      // If any of the above steps fail, throw an exception with proper message
    } catch (EmptyResultDataAccessException e) {
        log.error(msgRoleMissing);
        throw new ApiException(msgRoleMissing + ROLE_USER.name());
    } catch (Exception e) {
        log.error("Failed to add role to user. Please try again.");
        throw new ApiException("Failed to add role to user. Please try again.");
    }
  }

  @Override
  public Role create(com.applytruth.membership.domain.Role data) {
    // Auto-generated method stub
    return null;
  }

  @Override
  public Boolean delete(Long id) {
    // Auto-generated method stub
    return false;
  }

  @Override
  public Role get(Long id) {
    // Auto-generated method stub
    return null;
  }

  @Override
  public Collection list(int page, int pageSize) {
    //  Auto-generated method stub
    ArrayList<Role> roles = new ArrayList<Role>();
    return roles;
  }

  @Override
  public Role update(Role data) {
    Role role = null;
    role = Role.builder()
      .id(data.getId())
      .roleName(data.getRoleName())
      .permissions(data.getPermissions())
      .build();
    return role;
  }

  @Override
  public Role getRoleByRoleId(Long roleId) {
    try {
      return jdbcTemplate.queryForObject(SELECT_ROLE_BY_NAME_QUERY, Map.of("role_id", roleId), new RoleRowMapper());
    } catch (EmptyResultDataAccessException e) {
      log.error(msgRoleMissing);
      throw new ApiException("Role does not exist in the database. Please use a different role.");
    } catch (Exception e) {
      log.error("Failed to get role by role id. Please try again.");
      throw new ApiException("Failed to get role by role id. Please try again.");
    }
    
  }

  @Override
  public Role getRoleByRoleName(String roleName) {
    //  Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getRoleByEmail'");
  }

  @Override
  public void updateUserRole(Long userId, String roleName) {
    //  Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'updateUserRole'");
  }

  @Override
  public Role getRoleByUserId(Long userId) {
    //  Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getRoleByUserId'");
  }

  @Override
  public Role getRoleByUserEmail(String email) {
    //  Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getRoleByUserEmail'");
  }
  
  
}
