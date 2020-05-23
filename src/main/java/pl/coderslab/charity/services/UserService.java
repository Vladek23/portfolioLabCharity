package pl.coderslab.charity.services;

import pl.coderslab.charity.entities.User;

public interface UserService {

    Boolean isEmailUnique (String email);

    User findByEmail(String email);

    void saveUser(User user);

}
