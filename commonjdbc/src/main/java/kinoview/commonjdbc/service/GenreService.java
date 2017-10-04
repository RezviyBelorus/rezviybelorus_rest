package kinoview.commonjdbc.service;

import kinoview.commonjdbc.dao.GenreDAO;
import kinoview.commonjdbc.entity.Genre;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by alexfomin on 06.07.17.
 */
@Service
public class GenreService {
    @Autowired
    private GenreDAO genreDAO;

    @Transactional
    public Genre addGenre(String genreName) {
        Genre genre = genreDAO.find(genreName);

        if(genre==null) {
            genre = new Genre();
            genre.setGenreName(genreName);
            genreDAO.save(genre);
            return genreDAO.find(genreName);
        }
        return null;
    }

    @Transactional
    public boolean delete(String genreName) {
        boolean isDeleted = genreDAO.delete(genreName);
        if(isDeleted) {
            return true;
        }
        return false;
    }

    public Genre find(String genreName) {
        return genreDAO.find(genreName);
    }

    public Genre find(int genreId){
        return genreDAO.find(genreId);
    }
}
