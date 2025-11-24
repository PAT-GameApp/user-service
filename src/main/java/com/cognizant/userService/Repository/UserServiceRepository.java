package com.cognizant.userService.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.cognizant.userService.entity.UserServiceEntity;

@Repository
public interface UserServiceRepository extends JpaRepository<UserServiceEntity, Long> {

}
