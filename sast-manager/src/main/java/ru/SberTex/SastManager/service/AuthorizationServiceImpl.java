package ru.SberTex.SastManager.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

@Service
@RequiredArgsConstructor
@Slf4j
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

        User user = User.builder()
                .username(request.username())
                .password(passwordEncoder.encode(request.password()))
                .role(roleService.getRoleWithName(RoleName.ROLE_USER))
                .build();

        userService.save(user);

        var jwt = jwtService.createToken(user.getUsername());
        return new JwtAuthenticationResponse(jwt, user.getId());
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

        User user = userService.findByUsername(request.username());

        var jwt = jwtService.createToken(request.username());

        return new JwtAuthenticationResponse(jwt, user.getId());
    }

}
