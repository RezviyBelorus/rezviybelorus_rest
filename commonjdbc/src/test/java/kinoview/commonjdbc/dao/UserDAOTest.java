package kinoview.commonjdbc.dao;

import kinoview.commonjdbc.entity.User;
import org.junit.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Commit;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Column;
import java.time.LocalDateTime;

import static org.junit.Assert.assertTrue;

/**
 * Created by alexfomin on 30.06.17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:common-jdbc.xml")
@Transactional
@Rollback
public class UserDAOTest {

    @Autowired
    private UserDAO dao;

    @Test
    @Commit
    public void shouldSaveUser() throws Exception {
       User user = new User();

        user.setLogin("tester77");
        user.setPassword("testPass");
        user.setfName("alex");
        user.setlName("kurik");
        user.setEmail("123@cor.com");
        user.setStatus(1);
        user.setCreateDate(LocalDateTime.now());
        boolean save = dao.save(user);

        assertTrue(save);
    }

    @Test
    public void shouldDeleteById() throws Exception {
        boolean result = dao.delete(55);
        assertTrue(result);
    }

    @Test
    public void deleteByEmail() throws Exception {
        boolean result = dao.delete("123@gmail.com");
        assertTrue(result);
    }

    @Test
    public void shouldFindById() throws Exception {
        User actual = dao.find(55);

        Assert.assertEquals(55, actual.getId());
    }

    @Test
    public void shouldFindByEmailOrLogin() {
        //test will work if table users contains current ID
        User actual = dao.find("test");

        Assert.assertEquals("test", actual.getLogin());
    }

    @Test
    public void shouldSetStatus() throws Exception {
        boolean result = dao.setStatus("test", 2);
        assertTrue(result);
    }


}
