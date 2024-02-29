package com.applytruth.membership.domain;


import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

import java.time.LocalDateTime;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class User {

  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  private Long id;

  @NotEmpty(message = "FIrst name is required")
  private String firstName;

  @NotEmpty(message = "Last name is required")
  private String lastName;

  @NotEmpty(message = "Valid Email is required")
  @Email(message = "Email is invalid")
  private String email;

  @NotEmpty(message = "Password is required")
  private String password;

  private String groupName;
  private String address;
  private String phone;
  private String title;
  private String bio;
  private String imagesUrl;
  private boolean enabled;
  private boolean isNotLocked;
  private boolean isUsingMFA;
  private LocalDateTime createdTS;
  private LocalDateTime updatedTS;
}
