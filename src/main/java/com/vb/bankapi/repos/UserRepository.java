package com.vb.bankapi.repos;



import org.springframework.data.jpa.repository.JpaRepository;


import com.vb.bankapi.entities.UserDetails;

public interface UserRepository extends JpaRepository<UserDetails, Integer> {
	
	UserDetails findByusername(String username);
	UserDetails findByIdAndPin(int id,int pin);
}
