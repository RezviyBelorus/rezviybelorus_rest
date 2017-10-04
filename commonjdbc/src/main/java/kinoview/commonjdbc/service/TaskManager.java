package kinoview.commonjdbc.service;

import kinoview.commonjdbc.entity.Film;
import kinoview.commonjdbc.exception.IllegalRequestException;
import kinoview.commonjdbc.util.LocalProperties;
import kinoview.commonjdbc.util.Validator;
import org.apache.log4j.Logger;
import org.jsoup.nodes.Document;
import kinoview.commonjdbc.util.SiteConnector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

//@Component
public class TaskManager implements Runnable {
    LocalProperties properties = new LocalProperties();

    private static final String THREAD_SEPARATOR = "taskmanager.thread.separator";

    private static final String START_UPDATE_HOUR = "taskmanager.startupdatehour";

    private static final String START_UPDATE_MINUTE = "taskmanager.startupdateminute";

    private static final Logger logger = Logger.getLogger(TaskManager.class);

    private int threadSeparator;
    private String startUpdateHour;
    private String startUpdateMinute;

    private FilmService filmService;

    private boolean isAdminWantsToRunUpdate = true;
    private boolean isJobRun = true;

    public TaskManager(FilmService filmService) {
        threadSeparator = Validator.validateInt(properties.get(THREAD_SEPARATOR));
        startUpdateHour = properties.get(START_UPDATE_HOUR);
        startUpdateMinute = properties.get(START_UPDATE_MINUTE);
        this.filmService = filmService;
    }

    @Override
    public void run() {
        while (isJobRun) {
            LocalTime currentTime = LocalTime.now();

            LocalTime updateTime = LocalTime.parse(startUpdateHour + ":" + startUpdateMinute);
            boolean isTimeToUpdate = isTimeToUpdate(currentTime, updateTime);
            if (isAdminWantsToRunUpdate || isTimeToUpdate) {

                List<Film> resultFilmsList = getFilms();

                filmService.updateBatchFilms(resultFilmsList);

                filmService.saveBatch(resultFilmsList);
                
                isAdminWantsToRunUpdate = false;
            }
        }
    }

    private boolean isTimeToUpdate(LocalTime currentTime, LocalTime updateTime) {
        return updateTime.getHour() == currentTime.getHour() && updateTime.getMinute() == currentTime.getMinute();
    }

    private List<Film> getFilms() {
        List<Future<List<Film>>> resultFilmsList = new ArrayList<>();
        int threadsQuantity = getThreadsQuantity(findLastPage());
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(threadsQuantity);

        int begin;
        int end = 0;
        for (int i = 0; i < threadsQuantity; i++) {
            begin = end + 1;
            end = end + threadSeparator;
            Future<List<Film>> submit = executor.submit(new Task(begin, end + 1));
            resultFilmsList.add(submit);
        }

        List<Film> films = new ArrayList<>();

        for (Future<List<Film>> listFuture : resultFilmsList) {
            try {
                films.addAll(listFuture.get());
            } catch (InterruptedException | ExecutionException e) {
                logger.error("Task manager error");
                throw new IllegalRequestException("");
            }
        }
        return films;
    }

    private int findLastPage() {
        SiteConnector siteConnector;

        int begin;
        int end = 1;

        while (true) {
            siteConnector = new SiteConnector(end);
            Document page = siteConnector.getPage();
            if (page == null) {
                begin = end / 2;
                for (int i = begin; i < end; i++) {
                    siteConnector = new SiteConnector(i);
                    Document currentPage = siteConnector.getPage();
                    if (currentPage == null) {
                        return i - 1;
                    }
                }
            }
            end *= 2;
        }
    }

    private int getThreadsQuantity(int lastPage) {
        int threadsNumber = lastPage / threadSeparator;
        return threadsNumber == 0 ? 1 : threadsNumber;
    }

    public boolean isAdminWantsToRunUpdate() {
        return isAdminWantsToRunUpdate;
    }

    public void setAdminWantsToRunUpdate(boolean adminWantsToRunUpdate) {
        isAdminWantsToRunUpdate = adminWantsToRunUpdate;
    }

    public int getTHREAD_SEPARATOR() {
        return threadSeparator;
    }

    public static String getStartUpdateHour() {
        return START_UPDATE_HOUR;
    }

    public void setStartUpdateHour(String startUpdateHour) {
        this.startUpdateHour = startUpdateHour;
    }

    public String getStartUpdateMinute() {
        return startUpdateMinute;
    }

    public void setStartUpdateMinute(String startUpdateMinute) {
        this.startUpdateMinute = startUpdateMinute;
    }

    public boolean isJobRun() {
        return isJobRun;
    }

    public void setJobRun(boolean jobRun) {
        isJobRun = jobRun;
    }

}


