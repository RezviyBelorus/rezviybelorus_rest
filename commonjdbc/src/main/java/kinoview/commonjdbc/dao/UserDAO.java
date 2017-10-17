package kinoview.commonjdbc.dao;

import kinoview.commonjdbc.entity.User;
import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.*;

/**
 * Created by alexfomin on 30.06.17.
 */

@Repository
public class UserDAO extends AbstractDAO {
    private Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    private JdbcTemplate template;

    @Autowired
    SessionFactory sessionFactory;

    private String SAVE_USER_QUERY = "INSERT INTO users (login, password, f_name, l_name, email, status, create_date)" +
            " VALUES (?,?,?,?,?,?,?)";

    private String DELETE_BY_ID_QUERY = "DELETE FROM users WHERE user_id = ?";
    private String DELETE_BY_EMAIL_QUERY = "DELETE FROM users WHERE email = ?";

    private String SELECT_BY_ID_QUERY = "SELECT user_id, login, password, f_name, l_name, email, status, create_date" +
            " FROM users WHERE user_id = ?";

    private String SELECT_BY_EMAIL_OR_LOGIN_QUERY = "SELECT user_id, login, password, f_name, l_name, email, status, create_date " +
            "FROM users WHERE email = ? OR login = ?";

    private String UPDATE_STATUS_BY_EMAIL_OR_LOGIN_QUERY = "UPDATE users SET status = ? WHERE email = ? OR login = ?";

    public boolean save(User user) {
        sessionFactory.getCurrentSession().save(user);
        return true;
    }

    public boolean delete(int id) {
        User user = sessionFactory.getCurrentSession().get(User.class, id);
        sessionFactory.getCurrentSession().delete(user);
        return true;
    }

    public boolean delete(String email) {
        Query query = sessionFactory.getCurrentSession().createQuery("from User where email=:email");
        query.setParameter("email", email);
        User user = (User) query.uniqueResult();
        sessionFactory.getCurrentSession().delete(user);
        return true;
    }

    public User find(int id) {
        User user;
        try {
            user = template.queryForObject(SELECT_BY_ID_QUERY, new RowMapper<User>() {
                @Override
                public User mapRow(ResultSet rs, int i) throws SQLException {
                    User user = new User();
                    user.setId(rs.getInt(1));
                    user.setLogin(rs.getString(2));
                    user.setPassword(rs.getString(3));
                    user.setfName(rs.getString(4));
                    user.setlName(rs.getString(5));
                    user.setEmail(rs.getString(6));
                    user.setStatus(rs.getInt(7));
                    user.setCreateDate(rs.getTimestamp(8).toLocalDateTime());
                    return user;
                }
            }, id);
        } catch (Exception e) {
            return null;
        }
        return user;
    }

    public User find(String emailOrLogin) {
        User user;
        try {
            user = template.queryForObject(SELECT_BY_EMAIL_OR_LOGIN_QUERY, new RowMapper<User>() {
                @Override
                public User mapRow(ResultSet rs, int i) throws SQLException {
                    User user = new User();
                    user.setId(rs.getInt(1));
                    user.setLogin(rs.getString(2));
                    user.setPassword(rs.getString(3));
                    user.setfName(rs.getString(4));
                    user.setlName(rs.getString(5));
                    user.setEmail(rs.getString(6));
                    user.setStatus(rs.getInt(7));
                    user.setCreateDate(rs.getTimestamp(8).toLocalDateTime());
                    return user;
                }
            }, emailOrLogin, emailOrLogin);
        } catch (Exception e) {
            return null;
        }

        return user;
    }

    public boolean setStatus(String emailOrLogin, int status) {
        template.update(UPDATE_STATUS_BY_EMAIL_OR_LOGIN_QUERY, status, emailOrLogin, emailOrLogin);
        return true;
    }
}
