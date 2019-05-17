package jschool.service.impl;

import jschool.dao.UserDAO;
//import jschool.model.User;
import jschool.model.Role;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

/**
 * User details service, used in security, loads granted roles
 * */
@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {
    private static final Logger log = Logger.getLogger(UserDetailsServiceImpl.class);

    private final UserDAO userRepository;

    @Autowired
    public UserDetailsServiceImpl(UserDAO userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Loads User By email
     * @param username email
     * @return UserDetails
     * @throws UsernameNotFoundException
     */
    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        jschool.model.User user = userRepository.findByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found.");
        }
        log.info("loadUserByUsername() : {}" + username);
        Set<Role> roles = user.getRoles();
        Set<GrantedAuthority> grantList = new HashSet<>();
        if (roles != null) {
            for (Role role : roles) {
                grantList.add(new SimpleGrantedAuthority(role.getRole()));
            }
        }
        return new User(user.getEmail(),user.getPassword(),grantList);
    }
}
