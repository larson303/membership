package com.applytruth.membership.resource;

import static java.time.LocalDateTime.now;
import static java.util.Map.of;
import static org.springframework.http.HttpStatus.CREATED;
/* 
 * Could do the /users endpoint here
 * However, by adding the @RequestMapping("/api/v1/user") annotation, 
 * we can update API payload for existing endpoints 
 * with minimal changes to to other layers such as the UI
 * - e.g. we want to alter fields that are returned to the UI without
 * breaking the UI.  Such as feature toggles, or blue-green testing. 
 * by pointing to @RequestMapping("/api/v2/user") Allowing UI and API
 * changes to be made independently of each other.
 */

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.applytruth.membership.domain.HttpResponse;
import com.applytruth.membership.domain.User;
import com.applytruth.membership.dto.UserDTO;
import com.applytruth.membership.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserResource {

  private final UserService userService;

  @PostMapping("/register")
  public ResponseEntity<HttpResponse> saveUser(@RequestBody @Valid User user) {
    UserDTO userDto = userService.createUser(user);
    String id = userDto.getId().toString();
    return ResponseEntity.created(getUri(id)).body(
        HttpResponse.builder()
          .timeStamp(now().toString())
          .data(of("user", userDto))
          .message("User created successfully")
          .httpStatus(CREATED)
          .httpStatusCode(CREATED.value())  // 201
          .developerMessage("User created successfully.")
          .build());
  }

  private URI getUri(String userId) {
    return URI.create(ServletUriComponentsBuilder.fromCurrentContextPath()
      .path("/user/get/<userId>").toUriString());
  }
  
}
