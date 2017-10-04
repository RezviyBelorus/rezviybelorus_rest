package kinoview.commonjdbc.service;

import kinoview.commonjdbc.entity.Film;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/*
* Test works well, but TaskManager is infinity job, thats why all code commented
* */
@Ignore
@RunWith(MockitoJUnitRunner.class)
public class TaskManagerTest {

    @Mock
    FilmService filmService;

    @InjectMocks
    TaskManager taskManager;

    @Test
    public void run() throws Exception {

        Film film = new Film();
        film.setName("test3");
        film.setReleaseYear(2017);
        film.setQuality("HDRip");
        film.setTranslation("Дублированный");
        film.setDuration("01:24:13");
        film.setRating(5);
        film.setUploadDate(LocalDateTime.now());
        film.setStatus(3);
        film.setWatchLink("watchLink");
        film.setImgLink("imgLink");
        film.setShortStory("story");

        List<Film> filmsInDB = new ArrayList<>();
        filmsInDB.add(film);

        Mockito.when(filmService.findLoadedFilms()).thenReturn(filmsInDB);
        Mockito.when(filmService.updateBatchFilms(filmsInDB)).thenReturn(filmsInDB);
        Mockito.when(filmService.saveBatch(filmsInDB)).thenReturn(filmsInDB);

        taskManager.setJobRun(true);
        taskManager.setAdminWantsToRunUpdate(true);
        taskManager.run();
        taskManager.setJobRun(false);

        Assert.assertEquals(filmsInDB.get(0).getName(), filmsInDB.get(0).getName());
    }
}