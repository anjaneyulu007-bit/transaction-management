package com.bank.transactionmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.bank.transactionmanagement.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
	User findByUsername(String username);
}
