package kinoview.commonjdbc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalTime;

@Component
public class TaskManagerService {
    @Autowired
    private FilmService filmService;

    private TaskManager taskManager;

    @PostConstruct
    public void init() {
        taskManager = new TaskManager(filmService);

        //todo: temporary disabled, but working well
//        new Thread(taskManager).start();
    }

    public void startUpdate() {
        taskManager.setAdminWantsToRunUpdate(true);
    }

    public LocalTime setUpdateTimeHour(String hour) {
        taskManager.setStartUpdateHour(hour);
        String startUpdateHour = taskManager.getStartUpdateHour();
        String startUpdateMinute = taskManager.getStartUpdateMinute();
        return LocalTime.parse(startUpdateHour + ":" + startUpdateMinute);
    }
}
