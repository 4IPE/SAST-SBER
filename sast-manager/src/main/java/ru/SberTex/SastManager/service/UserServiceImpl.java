package ru.SberTex.SastManager.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import ru.SberTex.SastManager.model.User;
import ru.SberTex.SastManager.repository.UserRepository;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;


    @Override
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public UserDetailsService userDetailsService() {
        return this::getUserByUsername;
    }

    @Transactional
    @Override
    public User save(User user) {

        return userRepository.save(user);
    }

}
