package com.example.CodeFellowship.config;

import com.example.CodeFellowship.models.ApplicationUser;
import com.example.CodeFellowship.repositories.ApplicationUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private ApplicationUserRepository applicationUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ApplicationUser applicationUser = applicationUserRepository.findByUsername(username);
        ApplicationUser user=applicationUserRepository.findByUsername(username);
        if (applicationUser == null){
            throw new UsernameNotFoundException("user not found");
        }
        return applicationUser;
    }
}
