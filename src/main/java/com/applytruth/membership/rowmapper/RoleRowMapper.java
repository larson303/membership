package com.applytruth.membership.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.applytruth.membership.domain.Role;

public class RoleRowMapper implements RowMapper<Role>{

  @Override
  public Role mapRow(ResultSet resultSet, int rowNum) throws SQLException {
    
    return Role.builder()
      .id(resultSet.getLong("id"))
      .roleName(resultSet.getString("roleName"))
      .permissions(resultSet.getString("permissions"))
      .build();
    
  }
  
}
