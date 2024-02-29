package com.applytruth.membership.query;

public class RoleQuery {
  public static final String INSERT_ROLE_TO_USER_QUERY = "INSERT INTO user_role (user_id, role_id) VALUES (:userId, :roleId)";
  public static final String SELECT_ROLE_BY_NAME_QUERY = "SELECT * FROM roles WHERE role_name = :roleName";
  public static final String SELECT_ROLE_BY_ID_QUERY = "SELECT * FROM roles WHERE id = :id";
}
