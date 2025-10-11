package com.hubpedro.bookstoreapi.config;

import com.hubpedro.bookstoreapi.model.Role;
import com.hubpedro.bookstoreapi.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class RoleDataLoader implements CommandLineRunner {

	private final RoleRepository roleRepository;

	public RoleDataLoader(RoleRepository roleRepository) {
		this.roleRepository = roleRepository;
	}

	@Override
	public void run(String... args) {
		createRoleIfNotExists(Roles.USER);
		createRoleIfNotExists(Roles.ADMIN);
	}

	private void createRoleIfNotExists(String roleName) {
		roleRepository.findByName(roleName)
				.orElseGet(() -> roleRepository.save(new Role(roleName)));
	}
}
