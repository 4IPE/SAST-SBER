package ru.SberTex.SastManager.service;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
    @Transactional
    @Override
    public void signUp(UserSingUpDto request, HttpServletResponse response) {
        if (userService.checkUser(request.username())) {
            throw new IllegalArgumentException("Пользователь с таким именем уже существует");
        }

        User user = new User();
        user.setUsername(request.username());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setRole(roleService.getRoleWithName(RoleName.ROLE_USER));
        userService.save(user);

        var jwt = jwtService.createToken(user.getUsername());
        response.addCookie(createJwtCookie(jwt));
    }

    @Override
    public void signIn(UserSingInDto request, HttpServletResponse response) {
        var user = userService.getUserByUsername(request.username());

        // Проверка пароля
        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new BadCredentialsException("Неверный пароль");
        }

        var jwt = jwtService.createToken(user.getUsername());
        response.addCookie(createJwtCookie(jwt));
    }

    private Cookie createJwtCookie(String jwt) {
        Cookie cookie = new Cookie("token", jwt);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(3600);
        return cookie;
    }

}
