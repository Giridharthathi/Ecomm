package com.ns.task.service;

import com.ns.task.config.UserDetailInfo;
import com.ns.task.entity.UserEntity;
import com.ns.task.repository.RegistrationRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;
@Component
@AllArgsConstructor
public class UserService implements UserDetailsService {

    private final RegistrationRepository registrationRepository;
    @Override
    public UserDetails loadUserByUsername(String userMail) throws UsernameNotFoundException {
        Optional<UserEntity> userDetail = registrationRepository.findByEmail(userMail);
        return userDetail.map(UserDetailInfo::new)
                .orElseThrow(()->new UsernameNotFoundException("User not Found"));
    }
}