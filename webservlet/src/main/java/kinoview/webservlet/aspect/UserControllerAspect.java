package kinoview.webservlet.aspect;

import kinoview.commonjdbc.entity.User;
import kinoview.commonjdbc.entity.dto.UserDTO;
import kinoview.webservlet.web.View;
import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Aspect
@Component
public class UserControllerAspect {
    Logger logger = Logger.getLogger(UserControllerAspect.class);

    @Pointcut("execution(* kinoview.webservlet.controller.UserController.login(..)) && args(request,response,emailOrLogin,password,..)")
    public void logenIn(HttpServletRequest request, HttpServletResponse response,
                        String emailOrLogin,String password) {
    }

    @AfterReturning(pointcut = "logenIn(request,response,emailOrLogin,password)", returning = "retVal")
    public void afterLogenIn(Object retVal, HttpServletRequest request, HttpServletResponse response,
                             String emailOrLogin,String password) {
        ModelAndView view = (ModelAndView) retVal;
        if (view.getViewName().equals(View.MAIN.getName())) {
            UserDTO user = (UserDTO) view.getModelMap().get("user");
            logger.info("Successful login: " + user.getLogin());
        } else {
            logger.info("Login failed by: " + emailOrLogin);
        }
    }
}
