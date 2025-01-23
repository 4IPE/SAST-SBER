package ru.SberTex.SastManager.service;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.SberTex.SastDto.model.UserUpdateDto;
import ru.SberTex.SastManager.exception.EmailSendException;
import ru.SberTex.SastManager.exception.NotFoundException;
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
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Пользователь не найден: " + username);
        }
        return user;
    }

    @Override
    public User getUserByUsername(String username) {
        return Optional.ofNullable(userRepository.findByUsername(username))
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь " + username + " не найден"));
    }

    @Override
    public User getUserByUsernameOrEmail(String usernameOrEmail) {
        return Optional.ofNullable(userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail))
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь " + usernameOrEmail + " не найден"));
    }

//    @Override
//    public UserDetailsService userDetailsService() {
//        return this::getUserByUsername;
//    }

    @Override
    public User getUserWithId(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("Пользователь не найден"));
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public void updateUserProfile(UserUpdateDto userUpdateDto, HttpServletRequest request) {
        User user = getUserWithCookie(request);

        user.setEmail(userUpdateDto.getEmail());
        user.setPassword(passwordEncoder.encode(userUpdateDto.getPassword()));
        userRepository.save(user);
    }

    @Override
    public boolean checkUser(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public User getUserWithCookie(HttpServletRequest request) {
        var token = jwtTokenProvider.resolveToken(request);
        if (!jwtTokenProvider.validateToken(token)) {
            throw new RuntimeException("Invalid token");
        }
        String username = jwtTokenProvider.getUsername(token);
        log.info("Вход пользователя {}", username);
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
    public void requestForEditPassword(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("Пользователь не был найден"));
        var token = jwtTokenProvider.createTokenForChangePassword(user.getUsername());
        String url = "http://localhost:3000/user/request?token=" + token;
        try {
            emailService.sendEmail(user.getEmail(), url);
        } catch (MessagingException e) {
            throw new EmailSendException("Ошибка в отправке сообщения на почту!");
        }
    }

    @Override
    public void editPassword(String token, String password) {
        jwtTokenProvider.validateToken(token);
        User user = Optional.ofNullable(userRepository.findByUsername(jwtTokenProvider.getUsername(token)))
                .orElseThrow(() -> new NotFoundException("Пользователь не был найден"));
        user.setPassword(password);
        userRepository.save(user);
    }

}
