package web.service.forum;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import web.service.forum.entity.EnumRole;
import web.service.forum.entity.Role;
import web.service.forum.entity.User;
import web.service.forum.repository.RoleRepository;
import web.service.forum.repository.UserRepository;

@SpringBootApplication
public class ForumApplication {

	static Logger LOGGER = LoggerFactory.getLogger(ForumApplication.class);

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	PasswordEncoder encoder;

	public static void main(String[] args) {
		SpringApplication.run(ForumApplication.class, args);
	}

	@Value(value = "${populatedb}")
  	private boolean populatedb;

	@PostConstruct
  	private void init() throws IOException {

		if (populatedb) {
			LOGGER.info("About to populate database from raw data");
		} else {
			LOGGER.info("Populate database at startup is disabled");
			return;
		}

		// Création des roles
		Role adminRole = roleRepository.save(new Role(EnumRole.ROLE_ADMIN));
		roleRepository.save(new Role(EnumRole.ROLE_MODERATOR));
		roleRepository.save(new Role(EnumRole.ROLE_USER));
	
		// Création d'un utilisateur spécial ayant le rôle d'Administrateur,
		// seule personne habilitée à créer par la suite des entités comme Artist et Album
		User admin = new User();
		admin.setEmail("test@campus.academy");
		admin.setPassword(encoder.encode("12345678"));
		Set<Role> roles = new HashSet<>();
		roles.add(adminRole);
		admin.setRoles(roles);
		userRepository.save(admin);
	}

}
