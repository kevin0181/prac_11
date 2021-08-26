package securityexample1.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import securityexample1.demo.detail.UserDT;
import securityexample1.demo.model.UserModel;
import securityexample1.demo.repository.UserRepository;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepositroy;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void save(UserModel userModel) {

        userModel.setPassword(passwordEncoder.encode(userModel.getPassword()));
//        userModel.setRole("ROLE_USER");
        userRepositroy.save(userModel);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserModel userModel = new UserModel();
        userModel = userRepositroy.findByEmail(username);

        if (userModel == null) {
            throw new UsernameNotFoundException(username);
        }


        return new UserDT(userModel);
    }

    public UserModel findMember(String name) {
        UserModel userModel = userRepositroy.findByEmail(name);
        return userModel;
    }
}
