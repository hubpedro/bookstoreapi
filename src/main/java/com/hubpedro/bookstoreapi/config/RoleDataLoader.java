package com.hubpedro.bookstoreapi.config;

import com.hubpedro.bookstoreapi.model.Role;
import com.hubpedro.bookstoreapi.model.User;
import com.hubpedro.bookstoreapi.repository.RoleRepository;
import com.hubpedro.bookstoreapi.repository.UserRepositoryJPA;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class RoleDataLoader implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final UserRepositoryJPA userRepositoryJPA;

    public RoleDataLoader(RoleRepository roleRepository, UserRepositoryJPA userRepositoryJPA) {
        this.roleRepository = roleRepository;
        this.userRepositoryJPA = userRepositoryJPA;
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

    @Bean
    CommandLineRunner initDatabase(UserRepositoryJPA userRepositoryJPA, PasswordEncoder passwordEncoder) {
        return args -> {
            // Verifica se o admin já existe
            if (userRepositoryJPA.findByName("admin").isEmpty()) {
                User admin = User.create("admin", "admin@gmail.com", passwordEncoder.encode("admin123"));
                admin.getRoles().add(new Role(Roles.ADMIN));

                userRepositoryJPA.save(admin);
                System.out.println("✅ Usuário administrador criado com sucesso!");
            } else {
                System.out.println("ℹ️ Usuário administrador já existe, ignorando criação.");
            }
        };
    }
}
