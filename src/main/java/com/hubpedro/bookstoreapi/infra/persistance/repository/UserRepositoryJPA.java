package com.hubpedro.bookstoreapi.infra.persistance.repository;


import com.hubpedro.bookstoreapi.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepositoryJPA extends JpaRepository<User, Long> {
}
