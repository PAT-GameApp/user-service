package com.cognizant.userService.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.cognizant.userService.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
