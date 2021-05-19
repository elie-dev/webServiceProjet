package web.service.forum;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;

import web.service.forum.entity.EnumRole;
import web.service.forum.entity.Role;
import web.service.forum.entity.User;
import web.service.forum.repository.RoleRepository;
import web.service.forum.repository.UserRepository;

@SpringBootApplication
public class ForumApplication {

	public static void main(String[] args) {
		SpringApplication.run(ForumApplication.class, args);
	}

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Lazy
	@Autowired
  	PasswordEncoder encoder;

	@PostConstruct
  	private void init() throws IOException {
		  // Création des roles
		  Role adminRole = roleRepository.save(new Role(EnumRole.ROLE_ADMIN));
		  roleRepository.save(new Role(EnumRole.ROLE_MODERATOR));
		  roleRepository.save(new Role(EnumRole.ROLE_USER));
	  
		  // Création d'un utilisateur spécial ayant le rôle d'Administrateur,
		  // seule personne habilitée à créer par la suite des entités comme Artist et Album
		  User admin = new User();
		  admin.setEmail("test@campus.academy");
		  admin.setPassword("12345678");
		  Set<Role> roles = new HashSet<>();
		  roles.add(adminRole);
		  admin.setRoles(roles);
		  userRepository.save(admin);
	}

}
