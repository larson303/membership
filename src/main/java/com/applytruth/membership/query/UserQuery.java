package com.applytruth.membership.query;

import com.applytruth.membership.domain.User;

public class UserQuery {
  
  public static final String INSERT_USER_QUERY = "INSERT INTO users (first_name, last_name, email, password, group_name) VALUES (:firstName, :lastName, :email, :password, :groupName)";
  public static final String UPDATE_USER_QUERY = "INSERT INTO users (first_name, last_name, email, password, group_name, address, phone, title, bio, images_url, enabled, is_not_locked, is_using_mfa, created_ts, updated_ts) VALUES (:firstName, :lastName, :email, :password, :groupName, :address, :phone, :title, :bio, :imagesUrl, :enabled, :isNotLocked, :isUsingMFA, :createdTS, :updatedTS)";
  
  public static final String INSERT_ACCOUNT_VERIFICATION_URL_QUERY = "INSERT INTO account_verifications (user_id, verification_url) VALUES (:userId, :url)";
  
  public static final String SELECT_USER_BY_ID_QUERY = "SELECT * FROM users WHERE user_id = :id";
  public static final String SELECT_USER_BY_NAME_QUERY = "SELECT * FROM users WHERE first_name = :firstName AND last_name = :lastName";
  public static final String SELECT_USER_BY_EMAIL_QUERY = "SELECT * FROM users WHERE email = :email";
  
  public static final String COUNT_USER_EMAIL_QUERY = "SELECT COUNT(*) FROM users WHERE email = :email";
  
}
