package ru.vishnyakov.flexoPrint.controllers.beens;

import org.springframework.security.core.GrantedAuthority;
import ru.vishnyakov.flexoPrint.controllers.beens.User;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "role")
public class Role implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;
    private String name;
    @Transient
    @ManyToMany(mappedBy = "roles")
    Set<User> users = new HashSet<>();
    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Set<User> getUsers() {
        return users;
    }


    @Override
    public String getAuthority() {
        return this.getName();
    }
}
