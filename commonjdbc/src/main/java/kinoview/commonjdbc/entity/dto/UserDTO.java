package kinoview.commonjdbc.entity.dto;

import kinoview.commonjdbc.entity.User;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Created by alexfomin on 03.07.17.
 */
public class UserDTO implements Serializable{
    private int id;
    private String login;

    private String fName;
    private String lName;

    private String email;
    private int status;
    private LocalDateTime create_date;

    public UserDTO(User user) {
        this.id = user.getId();
        this.login = user.getLogin();
        this.fName = user.getfName();
        this.lName = user.getlName();
        this.email = user.getEmail();
        this.status = user.getStatus();
        this.create_date = user.getCreateDate();
    }

    public int getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getfName() {
        return fName;
    }

    public String getlName() {
        return lName;
    }

    public String getEmail() {
        return email;
    }

    public int getStatus() {
        return status;
    }

    public LocalDateTime getCreate_date() {
        return create_date;
    }
}
