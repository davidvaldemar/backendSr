package com.cuscatlan.backendsr.mobile.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.cuscatlan.backendsr.mobile.entities.Users;

@Repository
public interface UserRepository extends CrudRepository<Users, Long> {

	public Optional<Users> findByUsernameAndStatus(String username, Boolean status);
	
}
