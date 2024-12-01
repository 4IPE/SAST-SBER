package ru.SberTex.SastManager.service;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.SberTex.SastDto.model.UserOutDto;
import ru.SberTex.SastManager.model.User;
import ru.SberTex.SastManager.repository.UserRepository;
import ru.SberTex.SastManager.security.jwt.JwtTokenProvider;

import java.util.Optional;


@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Пользователь не найден");
        }
        return user;
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public User getUserByUsername(String username) {
        return Optional.ofNullable(userRepository.findByUsername(username))
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }

    @Override
    public UserDetailsService userDetailsService() {
        return this::getUserByUsername;
    }

    @Override
    public User getUserWithCookie(HttpServletRequest request) {
        var token = jwtTokenProvider.resolveToken(request);
        if (!jwtTokenProvider.validateToken(token)) {
            throw new RuntimeException("Invalid token");
        }
        String username = jwtTokenProvider.getUsername(token);
        log.info("Вход пользователя с именем: {}", username);
        return getUserByUsername(username);
    }

    @Override
    public ResponseEntity<String> validCookies(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("token".equals(cookie.getName())) {
                    return ResponseEntity.ok("Token is present");
                }
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token is not present");
    }

    @Override
    public void updateUserProfile(UserOutDto userDto, HttpServletRequest request) {
        User user = getUserWithCookie(request);
        if (userDto.getEmail() != null) user.setEmail(userDto.getEmail());
        userRepository.save(user);
    }

    @Override
    public User getUserWithId(Long id){
        return userRepository.findById(id).orElseThrow(()->new RuntimeException("Юзер не найден") );
    }
}
