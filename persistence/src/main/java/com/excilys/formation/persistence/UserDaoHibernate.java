package com.excilys.formation.persistence;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.excilys.formation.model.User;


public interface UserDaoHibernate extends JpaRepository<User, Long> {
    @Query(" select u from User u " +
            " where u.name = ?1")
    Optional<User> findUserWithName(String name);
}