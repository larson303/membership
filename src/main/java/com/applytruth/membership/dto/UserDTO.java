package com.applytruth.membership.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class UserDTO {

  private Long id;
  private String firstName;
  private String lastName;
  private String email;

  // private String password;

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
