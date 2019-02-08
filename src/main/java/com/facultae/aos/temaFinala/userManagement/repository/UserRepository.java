package com.facultae.aos.temaFinala.userManagement.repository;

import com.facultae.aos.temaFinala.userManagement.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User,Integer> {
}
