package com.onerivet.repositry;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.onerivet.model.entity.Users;

@Repository
public interface UsersRepository extends JpaRepository<Users, Integer> {
	
	Optional<Users> findByUserName(String username);

}
