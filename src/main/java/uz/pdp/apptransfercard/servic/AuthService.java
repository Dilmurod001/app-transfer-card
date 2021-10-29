package uz.pdp.apptransfercard.servic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class AuthService implements UserDetailsService {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        List<User> userList = new ArrayList<>(Arrays.asList(
                new User("Avazbek",passwordEncoder.encode("123"),new ArrayList<>()),
                new User("Jasurbek",passwordEncoder.encode("1234"),new ArrayList<>()),
                new User("Jafarjon",passwordEncoder.encode("1236"),new ArrayList<>())
        ));

        for (User user : userList) {
            if (s.equals(user.getUsername())){
                return user;
            }
        }

        throw new UsernameNotFoundException("User not found");
    }


}
