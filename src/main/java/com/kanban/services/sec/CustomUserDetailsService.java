package com.kanban.services.sec;

import com.kanban.domain.sec.CustomUserDetails;
import com.kanban.domain.sec.User;
import com.kanban.domain.sec.UserRole;
import com.kanban.repositories.sec.UserRepository;
import com.kanban.repositories.sec.UserRolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;

@Service("customUserDetailsService")
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserRolesRepository userRolesRepository;

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository, UserRolesRepository userRolesRepository) {
        this.userRepository = userRepository;
        this.userRolesRepository = userRolesRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUserName(username);
        if (null == user) {
            throw new UsernameNotFoundException("No user present with username: " + username);
        } else {
            List<String> userRoles = userRolesRepository.findRoleByUserName(username);
            return new CustomUserDetails(user, userRoles);
        }
    }

    public boolean userExists(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUserName(username);
        if (null == user) {
            return false;
        } else {
            return true;
        }
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public UserRole saveUserRole(UserRole userRole) {
        return userRolesRepository.save(userRole);
    }

    public void createUser(String username, String email, String password, List<String> roles, String firstName, String lastName) {
        User user = new User();
        user.setUserName(username);
        user.setEnabled(1);
        if (email != null)
            user.setEmail(email);
        if (firstName != null)
            user.setFirstName(firstName);
        if (lastName != null)
            user.setLastName(lastName);

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(password);
        user.setPassword(encodedPassword);

        final User savedUser = save(user);

        roles.forEach(s -> {
            UserRole ur = new UserRole();
            ur.setUserid(savedUser.getUserid());
            ur.setRole(s);
            saveUserRole(ur);
        });
    }
}
