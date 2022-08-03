package com.user.userDetailsApi;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface userRepository extends JpaRepository<userDetails,Integer> {

	Optional<userDetails> findByEmail(String custEmail);
	
}

