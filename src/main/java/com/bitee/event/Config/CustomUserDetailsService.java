package com.bitee.event.Config;

import com.bitee.event.User.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Objects;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    private com.bitee.event.User.User userDetail;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
         userDetail = userRepository.findByUserEmail(email);

        if(Objects.isNull(userDetail)){
          throw new UsernameNotFoundException("User not found");
        }else return new User(userDetail.getEmail(),userDetail.getPassword(),new ArrayList<>());

    }

    public com.bitee.event.User.User getUserDetail() {return userDetail;}
}
