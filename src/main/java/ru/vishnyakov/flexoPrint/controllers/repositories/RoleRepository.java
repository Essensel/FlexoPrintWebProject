package ru.vishnyakov.flexoPrint.controllers.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.vishnyakov.flexoPrint.controllers.beens.Role;

public interface RoleRepository extends CrudRepository<Role,Long> {
}
