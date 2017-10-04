package kinoview.webservlet.controller;

import kinoview.commonjdbc.service.TaskManagerService;
import kinoview.webservlet.web.View;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalTime;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
@RequestMapping(value = "/admin")
public class AdminController{

    @Autowired
    private TaskManagerService taskManagerService;

    @RequestMapping(method = POST, path = "/startUpdate")
    public ModelAndView startUpdate() {

        taskManagerService.startUpdate();
        return new ModelAndView(View.MAIN.getName());
    }

    @RequestMapping(method = POST, path = "/setUpdateTimeHour")
    public ModelAndView setUpdateTimeHour(@RequestParam String hour) {
        LocalTime time = taskManagerService.setUpdateTimeHour(hour);
        ModelAndView view = new ModelAndView(View.MAIN.getName());
        view.addObject("time", time);

        return view;
    }
}
