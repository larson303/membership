package com.applytruth.membership.repository.implementation;

import static com.applytruth.membership.enumeration.VerificationType.ACCOUNT;
import static com.applytruth.membership.query.UserQuery.COUNT_USER_EMAIL_QUERY;
import static com.applytruth.membership.query.UserQuery.INSERT_ACCOUNT_VERIFICATION_URL_QUERY;
import static com.applytruth.membership.query.UserQuery.INSERT_USER_QUERY;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.applytruth.membership.domain.Role;
import com.applytruth.membership.domain.User;
import com.applytruth.membership.enumeration.RoleType;
import com.applytruth.membership.exception.ApiException;
import com.applytruth.membership.repository.RoleRepository;
import com.applytruth.membership.repository.UserRepository;

// import com.applytruth.membership.service.EmailService;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Repository
@RequiredArgsConstructor  
@Slf4j
// Use named parameter JDBC template to interact with the database rather than 
public class UserRepositoryImpl implements UserRepository<User> {

  private final NamedParameterJdbcTemplate jdbcTemplate;
  private final RoleRepository<Role> roleRepository;
  private final BCryptPasswordEncoder passwordEncoder;



  // private final EmailService emailService;
 
  @Override
  public User create(User user) {
    // Check if the email is unique
    if (getEmailCount(user.getEmail().trim().toLowerCase()) > 0) {
      throw new ApiException("Email already exists in the database. Please use a different email.");
    }
    // Save new user to the database
    try {
      KeyHolder keyHolder = new GeneratedKeyHolder();
      SqlParameterSource parameters = getSqlParameterSource(user);
      jdbcTemplate.update(INSERT_USER_QUERY, parameters, keyHolder);
      
      
      // Add role to the user
      user.setId(Objects.requireNonNull(keyHolder.getKey()).longValue());
      roleRepository.addRoleToUser(user.getId(), RoleType.ROLE_USER.name());

      // Send a verification email with a verification url
      String verificationUrl = getVerificationUrl(UUID.randomUUID().toString(), ACCOUNT.getType());
    
      // Save url in the verification table
      jdbcTemplate.update(INSERT_ACCOUNT_VERIFICATION_URL_QUERY, Map.of("userId", user.getId(), "url", verificationUrl));
      // Send a confirmation email with a verification url
      // emailService.sendVerificationUrl(user.getFirstName(), user.getEmail(), verificationUrl, ACCOUNT);
      user.setEnabled(false);
      user.setNotLocked(true);
      // Return the newly created user 
      return user;
    
      // If any of the above steps fail, throw an exception with proper message
    } catch(Exception exception){
        log.info("UserRepository:create:Failed to save user to the database." + exception.getMessage());
        throw new ApiException("UserRepository:create:Failed to create user to the database: " + exception.getMessage());
    }
  }

  @Override
  public User get(Long id) {
    throw new UnsupportedOperationException("Unimplemented method 'get'");
  }

  @Override
  public User update(User data) {
    throw new UnsupportedOperationException("Unimplemented method 'update'");
  }

  @Override
  public Boolean delete(Long id) {
    throw new UnsupportedOperationException("Unimplemented method 'delete'");
  }

  @Override
  public Collection<User> list(int page, int pageSize) {

    throw new UnsupportedOperationException("Unimplemented method 'list'");
  }

  private Integer getEmailCount(String email) {
    return jdbcTemplate.queryForObject(COUNT_USER_EMAIL_QUERY, Map.of("email", email), Integer.class);
  }

  private SqlParameterSource getSqlParameterSource(User user) {
    return new MapSqlParameterSource()
      .addValue("firstName", user.getFirstName())
      .addValue("lastName", user.getLastName())
      .addValue("email", user.getEmail())
      .addValue("groupName", user.getGroupName())
      .addValue("password", passwordEncoder.encode(user.getPassword()));
  }

  private String getVerificationUrl(String key, String type) {
    return ServletUriComponentsBuilder.fromCurrentContextPath()
      .path("/user/verify" + "/" + type + "/" + key).toUriString();
  }
}
