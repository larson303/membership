package com.applytruth.membership.repository;

import java.util.Collection;

import com.applytruth.membership.domain.User;

public interface UserRepository <T extends User>{

  /* Basic CRUD */
  T create (T data);
  Collection<T> list(int page, int pageSize);
  T get(Long id);
  T update(T data);
  Boolean delete(Long id);

  // public T findByUsername(String username);
  // public T findByEmail(String email);
  // public T findByUsernameAndPassword(String username, String password);
  // public T findByEmailAndPassword(String email, String password);
  // public T findByUsernameOrEmail(String username, String email);

}
