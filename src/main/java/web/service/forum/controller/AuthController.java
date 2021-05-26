package web.service.forum.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import web.service.forum.entity.EnumRole;
import web.service.forum.entity.Role;
import web.service.forum.entity.User;
import web.service.forum.payload.request.LoginRequest;
import web.service.forum.payload.request.SignupRequest;
import web.service.forum.payload.response.JwtResponse;
import web.service.forum.payload.response.MessageResponse;
import web.service.forum.repository.RoleRepository;
import web.service.forum.repository.UserRepository;
import web.service.forum.security.jwt.JwtUtils;
import web.service.forum.security.service.UserDetailsImpl;
import web.service.forum.security.service.UserDetailsServiceImpl;


@RestController
@RequestMapping("auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtils.generateJwtToken(authentication);

            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
                    .collect(Collectors.toList());

            return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(), roles));
        } catch (AuthenticationException e) {
            return ResponseEntity.ok(new MessageResponse(ApiMessage.ERROR_LOGIN_FAILED, "Identifiants invalides"));
        }
    }

    @PostMapping("register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity.ok()
                    .body(new MessageResponse(ApiMessage.ERROR_REGISTER_EMAIL_TAKEN, "L'adresse email est déjà prise"));
        }

        User user = new User();
        user.setEmail(signUpRequest.getEmail());
        user.setPassword(encoder.encode(signUpRequest.getPassword()));
        Set<Role> roles = new HashSet<Role>();
        Optional<Role> optional = roleRepository.findByName(EnumRole.ROLE_USER);
        roles.add(optional.get());
        user.setRoles(roles);
        userRepository.save(user);
        return ResponseEntity.ok(new MessageResponse(ApiMessage.REGISTER_OK, "Utilisateur inscrit avec succès !"));
    }

    @PostMapping("register/moderator")
    public ResponseEntity<MessageResponse> admin(@Valid @RequestBody SignupRequest signUpRequest) {
        if (UserDetailsServiceImpl.isAdmin()) {
            if (userRepository.existsByEmail(signUpRequest.getEmail())) {
                return ResponseEntity.ok()
                        .body(new MessageResponse(ApiMessage.ERROR_REGISTER_EMAIL_TAKEN, "L'adresse email est déjà prise"));
            }
            User user = new User();
            user.setEmail(signUpRequest.getEmail());
            user.setPassword(encoder.encode(signUpRequest.getPassword()));
            Set<Role> roles = new HashSet<Role>();
            roles.add(roleRepository.findByName(EnumRole.ROLE_MODERATOR).get());
            user.setRoles(roles);
            userRepository.save(user);
            return ResponseEntity.ok(new MessageResponse(ApiMessage.REGISTER_OK, "Modérateur inscrit avec succès !"));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

}
