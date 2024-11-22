package ru.SberTex.SastManager.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.SberTex.SastDto.model.JwtAuthenticationResponse;
import ru.SberTex.SastDto.model.UserSingInDto;
import ru.SberTex.SastDto.model.UserSingUpDto;
import ru.SberTex.SastManager.enumeration.RoleName;
import ru.SberTex.SastManager.model.User;
import ru.SberTex.SastManager.security.jwt.JwtTokenProvider;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthorizationServiceImpl implements AuthorizationService {
    private final UserService userService;
    private final JwtTokenProvider jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final RoleService roleService;

    /**
     * Регистрация пользователя
     *
     * @param request данные пользователя
     * @return токен
     */
    @Override
    @Transactional
    public JwtAuthenticationResponse singUp(UserSingUpDto request) {

        User user = new User();
        user.setUsername(request.username());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setRoles(Set.of(roleService.getRoleWithName(RoleName.ROLE_USER)));

        userService.save(user);

        var jwt = jwtService.createToken(user.getUsername());
        return new JwtAuthenticationResponse(jwt);
    }

    /**
     * Аутентификация пользователя
     *
     * @param request данные пользователя
     * @return токен
     */
    @Override
    @Transactional
    public JwtAuthenticationResponse singIn(UserSingInDto request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.username(),
                request.password()
        ));

        var user = userService
                .userDetailsService()
                .loadUserByUsername(request.username());

        var jwt = jwtService.createToken(user.getUsername());
        return new JwtAuthenticationResponse(jwt);
    }
}

