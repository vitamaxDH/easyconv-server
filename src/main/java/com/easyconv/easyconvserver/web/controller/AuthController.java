package com.easyconv.easyconvserver.web.controller;

import com.easyconv.easyconvserver.web.auth.UserDetailsImpl;
import com.easyconv.easyconvserver.web.model.ERole;
import com.easyconv.easyconvserver.web.model.Role;
import com.easyconv.easyconvserver.web.model.User;
import com.easyconv.easyconvserver.web.payload.request.LoginRequest;
import com.easyconv.easyconvserver.web.payload.request.SignupRequest;
import com.easyconv.easyconvserver.web.payload.response.JwtResponse;
import com.easyconv.easyconvserver.web.payload.response.MessageResponse;
import com.easyconv.easyconvserver.web.repository.RoleRepository;
import com.easyconv.easyconvserver.web.securiuty.jwt.JwtUtils;
import com.easyconv.easyconvserver.web.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@RestController
public class AuthController extends BaseController{

    private final UserService userService;
    private final RoleRepository roleRepository;
    private final AuthenticationManager authenticationManager;

    private final JwtUtils jwtUtils;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> register(@Valid @RequestBody SignupRequest signUpRequest){
        if (userService.existsUser(signUpRequest)){
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: User already exists!!"));
        }

        // 아닐 경우 새로 생성
        User user = userService.mapToEntity(signUpRequest);

        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roleSet = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roleSet.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roleSet.add(adminRole);

                        break;
                    default:
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roleSet.add(userRole);
                }
            });
        }

        user.setRoles(roleSet);
        userService.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

}
