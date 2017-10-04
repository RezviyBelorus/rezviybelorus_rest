package webservlet.controller;/*
package controller;

import User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import UserService;
import ModelAndView;
import View;
import UserDTO;

import javax.servlet.http.HttpServletRequest;

import static org.junit.Assert.*;

*/
/**
 * Created by alexfomin on 13.07.17.
 *//*

@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {

    @Mock
    UserService userService;

    @InjectMocks
    UserController userController;

    @Test
    public void shouldSignUpUser() throws Exception {
        //given
        UserDTO userDTO = new UserDTO(new User());

        Mockito.when(userService.save("login", "password", "fName", "lName",
                "email")).thenReturn(userDTO);

        //when
        ModelAndView actual = userController.signUp("login", "password", "fName", "lName",
                "email");

        //then
        assertEquals(View.MAIN, actual.getView());
    }

    @Test
    public void shouldLoginUser() throws Exception {
        //given
        UserDTO userDTO = new UserDTO(new User());


        Mockito.when(userService.login("login", "password")).thenReturn(userDTO);

        //todo:
        //when
        ModelAndView actual = userController.login("login", "password");


        //then
        assertEquals(View.USER, actual.getView());
    }

    @Test
    public void shouldFindById() throws Exception {
        //given
        User user = new User();

        Mockito.when(userService.find(1)).thenReturn(user);

        //when
        ModelAndView actual = userController.find(1);

        //then
        assertEquals(View.USER, actual.getView());
    }

    @Test
    public void shouldFindByEmailOrLogin() throws Exception {
        //given
        User user = new User();

        Mockito.when(userService.find("emailOrLogin")).thenReturn(user);

        //when
        ModelAndView actual = userController.find("emailOrLogin");

        //then
        assertEquals(View.USER, actual.getView());
    }

    @Test
    public void shouldDeleteUser() throws Exception {
        //given
        UserDTO userDTO = new UserDTO(new User());

        Mockito.when(userService.delete("emailOrLogin")).thenReturn(userDTO);

        //when
        ModelAndView actual = userController.delete("emailOrLogin");

        //then
        assertEquals(View.USER, actual.getView());
    }

    @Test
    public void shouldSetUserStatus() throws Exception {
        //given
        UserDTO userDTO = new UserDTO(new User());

        Mockito.when(userService.setStatus("emailOrLogin", "1")).thenReturn(userDTO);

        //when
        ModelAndView actual = userController.setStatus("emailOrLogin", "1");

        //then
        assertEquals(View.USER, actual.getView());
    }
}*/
