package com.hubpedro.bookstoreapi.config;

import com.hubpedro.bookstoreapi.model.Permission;
import com.hubpedro.bookstoreapi.model.Role;
import com.hubpedro.bookstoreapi.repository.PermissionRepository;
import com.hubpedro.bookstoreapi.repository.RoleRepository;
import com.hubpedro.bookstoreapi.security.PermissionEnum;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class RolePermissionDataLoader implements CommandLineRunner {

	private final PermissionRepository permissionRepository;
	private final RoleRepository roleRepository;

	public RolePermissionDataLoader(PermissionRepository permissionRepository, RoleRepository roleRepository) {
		this.permissionRepository = permissionRepository;
		this.roleRepository = roleRepository;
	}

	@Override
	public void run(String... args) throws Exception {

		for (PermissionEnum permEnum : PermissionEnum.values()) {
			permissionRepository.findById(permEnum.getName()).orElseGet(() -> permissionRepository.save(new Permission(permEnum.getName(), permEnum.getDescription())));
		}
		Role userRole = roleRepository.findById(Roles.USER).orElseGet(() -> {
			Role r = new Role(Roles.USER);
			r.setPermissions(Stream.of(PermissionEnum.BOOK_READ).map(p -> permissionRepository.getReferenceById(p.getName())).collect(Collectors.toSet()));
			return roleRepository.save(r);
		});
		Role adminRole = roleRepository.findById(Roles.ADMIN).orElseGet(() -> {
			Role r = new Role(Roles.ADMIN);
			r.setPermissions(Stream.of(PermissionEnum.BOOK_READ, PermissionEnum.BOOK_WRITE, PermissionEnum.USER_CREATE).map(p -> permissionRepository.getReferenceById(p.getName())).collect(Collectors.toSet()));
			return roleRepository.save(r);
		});

	}
}
