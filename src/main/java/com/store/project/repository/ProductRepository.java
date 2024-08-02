package com.store.project.repository;

import com.store.project.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<User, Long> {

}
