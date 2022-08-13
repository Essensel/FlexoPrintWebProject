package ru.vishnyakov.flexoPrint.controllers.repositories;


import org.springframework.data.repository.CrudRepository;
import ru.vishnyakov.flexoPrint.controllers.beens.User;

public interface UsersRepository extends CrudRepository<User,Long> {
    public User findByUsername(String username);
}
