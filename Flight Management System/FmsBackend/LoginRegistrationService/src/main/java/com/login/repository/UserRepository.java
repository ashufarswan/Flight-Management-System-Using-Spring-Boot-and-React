package com.login.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.login.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

	User findByName(String username);

	User findByNameOrEmail(String name,String email);

}
