package com.hubpedro.bookstoreapi.repository;


import com.hubpedro.bookstoreapi.model.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, String> {
	// Você pode adicionar métodos customizados se precisar, mas por enquanto JpaRepository já dá findById, save, findAll...
}
