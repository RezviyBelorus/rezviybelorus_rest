package kinoview.commonjdbc.service;

import kinoview.commonjdbc.dao.UserDAO;
import kinoview.commonjdbc.entity.User;
import kinoview.commonjdbc.entity.dto.UserDTO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;


/**
 * Created by alexfomin on 07.07.17.
 */
@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @Mock
    private UserDAO userDAO;

    @InjectMocks
    private UserService userService;

    @Test
    public void shouldLoginUser() throws Exception {
        //given
        User user = new User();
        user.setLogin("TestLogin");
        user.setPassword("TestPass");

        Mockito.when(userDAO.find("TestLogin")).thenReturn(user);

        //when
        UserDTO actual = userService.login("TestLogin", "TestPass");

        //then
        Assert.assertNotNull(actual);
    }

    @Test
    public void shouldSaveUser() throws Exception {
        //given
        User user = new User();
        user.setLogin("TestLogin");
        user.setPassword("pass");
        user.setfName("f_name");
        user.setlName("l_name");
        user.setEmail("email");
        user.setId(1);

        Mockito.when(userDAO.find("email")).thenReturn(null, user);
        Mockito.when(userDAO.find("TestLogin")).thenReturn(null);
        Mockito.when(userDAO.save(user)).thenReturn(true);

        //when
        UserDTO actualUserDTO = userService.save("TestLogin", "pass", "f_name",
                "l_name", "email");

        //then
        Assert.assertEquals(user.getLogin(), actualUserDTO.getLogin());
    }

    @Test
    public void shouldFindUserById() throws Exception {
        //given
        User user = new User();
        user.setId(5);
        user.setStatus(1);

        Mockito.when(userDAO.find(5)).thenReturn(user);

        //when
        User actual = userService.find(5);

        //then
        Assert.assertEquals(user.getId(), actual.getId());
    }

    @Test
    public void shouldFindEmailOrLogin() throws Exception {
        //given
        User user = new User();
        user.setEmail("123@gmail.com");
        user.setStatus(1);

        Mockito.when(userDAO.find("123@gmail.com")).thenReturn(user);

        //when
        User actual = userService.find("123@gmail.com");

        //then
        Assert.assertNotNull(actual);
    }

    @Test
    public void shouldDeleteUserByEmail() throws Exception {
        //given
        User user = new User();
        user.setEmail("123@gmail.com");
        user.setStatus(3);

        Mockito.when(userDAO.setStatus("123@gmail.com", 3)).thenReturn(true);
        Mockito.when(userDAO.find("123@gmail.com")).thenReturn(user);

        //when
        UserDTO actual = userService.delete("123@gmail.com");

        //then
        Assert.assertNotNull(actual);
    }

    @Test
    public void shouldSetStatus() throws Exception {
        //given
        User user = new User();
        user.setEmail("123@gmail.com");
        user.setStatus(2);

        Mockito.when(userDAO.setStatus("123@gmail.com", 2)).thenReturn(true);
        Mockito.when(userDAO.find("123@gmail.com")).thenReturn(user);

        //when
        UserDTO actual = userService.setStatus("123@gmail.com", "2");

        //then
        Assert.assertNotNull(actual);
    }
}