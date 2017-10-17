package kinoview.webservlet.controller;

import org.springframework.context.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.UrlBasedViewResolver;

//@Configuration
//@EnableWebMvc
//@EnableAspectJAutoProxy
//@ComponentScan(basePackages = "kinoview.webservlet")
//@ImportResource(locations = "common-jdbc.xml")
public class JavaConfig {
    @Bean
    public UrlBasedViewResolver viewResolver() {
        UrlBasedViewResolver resolver = new UrlBasedViewResolver();
        resolver.setViewClass(JstlView.class);
        resolver.setPrefix("/WEB-INF/view/");
        resolver.setSuffix(".jsp");
        return resolver;
    }
}
