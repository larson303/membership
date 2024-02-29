package com.applytruth.membership.domain;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class Role {
  
    // @Id
    // @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
  
    private String roleName;
  
    private String permissions;
  
}
