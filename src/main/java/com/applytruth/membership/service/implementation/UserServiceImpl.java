package com.applytruth.membership.service.implementation;

import org.springframework.stereotype.Service;

import com.applytruth.membership.domain.User;
import com.applytruth.membership.dto.UserDTO;
import com.applytruth.membership.dto.UserDTOMapper;
import com.applytruth.membership.repository.UserRepository;
import com.applytruth.membership.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

  private final UserRepository<User> userRepository;

  @Override
  public UserDTO createUser(User user) {
    log.info("Creating user: {}", user);
    return UserDTOMapper.fromUser(userRepository.create(user));
    
  }
 
}
