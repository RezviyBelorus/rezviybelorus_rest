package kinoview.webservlet.aspect;

import org.apache.log4j.Logger;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TaskManagerAspect {
    private static Logger logger = Logger.getLogger(TaskManagerAspect.class);

    @Pointcut("execution(* kinoview.commonjdbc.service.FilmService.updateBatchFilms(..))")
    public void taskManager() {
    }

    @Before("taskManager()")
    public void startUpdate() {
        logger.info("TaskManager started update");
    }
}
