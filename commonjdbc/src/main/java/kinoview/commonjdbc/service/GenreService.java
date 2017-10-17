package kinoview.commonjdbc.service;

import kinoview.commonjdbc.dao.GenreDAO;
import kinoview.commonjdbc.entity.Genre;
import kinoview.commonjdbc.entity.dto.GenreDTO;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by alexfomin on 06.07.17.
 */
@Service
public class GenreService {
    @Autowired
    private GenreDAO genreDAO;

    @Transactional
    public GenreDTO addGenre(String genreName) {
        Genre genre = genreDAO.find(genreName);

        if(genre==null) {
            genre = new Genre();
            genre.setGenreName(genreName);
            genreDAO.save(genre);
            return new GenreDTO(genreDAO.find(genreName));
        }
        return null;
    }

    @Transactional
    public boolean delete(String genreName) {
        Genre genre = genreDAO.find(genreName);
        boolean isDeleted = genreDAO.delete(genre);
        if(isDeleted) {
            return true;
        }
        return false;
    }

    public GenreDTO find(String genreName) {

        return new GenreDTO(genreDAO.find(genreName));
    }

    public GenreDTO find(int genreId){
        return new GenreDTO(genreDAO.find(genreId));
    }

    public Set<GenreDTO> getAllGenres() {
        Set<Genre> allGenres = genreDAO.findAllGenres();
        Set<GenreDTO> allGenresDTO = new HashSet<>();
        allGenres.forEach(genre -> allGenresDTO.add(new GenreDTO(genre)));
        return allGenresDTO;
    }
}
