package pl.coderslab.charity.services;

import org.springframework.security.core.GrantedAuthority;
import pl.coderslab.charity.entities.User;

import java.util.Collection;

public class CurrentUser extends User {

    private final pl.coderslab.charity.entities.User user;

    public CurrentUser(String username,
                       String password,
                       Collection<? extends GrantedAuthority> authorities,
                       pl.coderslab.charity.entities.User user) {
//        super(username, password, authorities);
        this.user = user;
    }

    public pl.coderslab.charity.entities.User getUser() {
        return user;
    }
}
