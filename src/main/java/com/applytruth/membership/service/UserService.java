package com.applytruth.membership.service;

import com.applytruth.membership.domain.User;
import com.applytruth.membership.dto.UserDTO;

public interface UserService {
  
  UserDTO createUser(User user);
  
}
