package kinoview.commonjdbc.service;

import kinoview.commonjdbc.dao.UserDAO;
import kinoview.commonjdbc.entity.User;
import kinoview.commonjdbc.entity.dto.UserDTO;
import kinoview.commonjdbc.exception.IllegalRequestException;
import kinoview.commonjdbc.util.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDateTime;


/**
 * Created by alexfomin on 04.07.17.
 */
@Service
public class UserService {
    @Autowired
    private UserDAO userDAO;

    public UserDTO login(String emailOrLogin, String password) {
        User user;
        try {
            user = userDAO.find(emailOrLogin);
        } catch (Exception e){
            return null;
        }
        boolean isLogined = user.getPassword().equals(password);
        if (isLogined) {
            return new UserDTO(user);
        }
        return null;
    }

    @Transactional
    public UserDTO save(String login, String password, String f_name, String l_name, String email) {
        User user = userDAO.find(email);
        User user1 = userDAO.find(login);

        if (user == null && user1 == null) {
            User userToSave = new User();
            userToSave.setLogin(login);
            userToSave.setPassword(password);
            userToSave.setfName(f_name);
            userToSave.setlName(l_name);
            userToSave.setLogin(login);
            userToSave.setEmail(email);
            userToSave.setStatus(1);
            userToSave.setCreateDate(LocalDateTime.now());
            userDAO.save(userToSave);

            user = userDAO.find(email);

            return new UserDTO(user);
        }
        return null;
    }

    public User find(int id) {
        User user = userDAO.find(id);
        if (user.getStatus() < UserStatus.INACTIVE.getValue()) {
            return user;
        }
        return null;
    }

    public User find(String emailOrLogin) {
        User user = userDAO.find(emailOrLogin);
        if (user.getStatus() < UserStatus.INACTIVE.getValue()) {
            return user;
        }
        return null;
    }


    @Transactional
    public UserDTO delete(String emailOrLogin) {
        boolean isDeleted = userDAO.setStatus(emailOrLogin, UserStatus.DELETED.getValue());
        if (isDeleted) {
            return new UserDTO(userDAO.find(emailOrLogin));
        }
        throw new IllegalRequestException("");
    }

    @Transactional
    public UserDTO setStatus(String emailOrLogin, String status) {
        userDAO.setStatus(emailOrLogin, Validator.validateInt(status));
        User user = userDAO.find(emailOrLogin);
        if (user.getStatus() == Validator.validateInt(status)) {
            return new UserDTO(user);
        } else return null;
    }
}
