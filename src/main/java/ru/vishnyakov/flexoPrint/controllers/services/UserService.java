package ru.vishnyakov.flexoPrint.controllers.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.vishnyakov.flexoPrint.controllers.beens.User;
import ru.vishnyakov.flexoPrint.controllers.repositories.UsersRepository;


@Service
public class UserService implements UserDetailsService {

  @Autowired
  UsersRepository usersRepository;

 @Autowired
BCryptPasswordEncoder encoder;
    public void saveUser(User user) {
        user.setPassword(this.encoder.encode(user.getPassword()));
        this.usersRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User result = this.usersRepository.findByUsername(username);
        if(result == null) {
            throw new UsernameNotFoundException("Пользователь не найден");
        }
        return result;
    }


}
