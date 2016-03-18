package com.icube.investframe;

import org.springframework.data.jpa.repository.JpaRepository;

import com.icube.investframe.entity.User;

public interface UserRepository extends JpaRepository<User, Long>{
	
}
