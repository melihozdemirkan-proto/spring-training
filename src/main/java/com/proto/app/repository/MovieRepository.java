package com.proto.app.repository;

import com.proto.app.entity.Movie;
import com.proto.app.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class MovieRepository {
    @PersistenceContext
    private final EntityManager entityManager;

    public List<Movie> findAll() {
        String query = "from Movie";
        TypedQuery<Movie> typedQuery = entityManager.createQuery(query, Movie.class);
        return typedQuery.getResultList();
    }

    public Optional<Movie> findById(String id) {
        String query = "from Movie where id = :id";
        TypedQuery<Movie> typedQuery = entityManager.createQuery(query, Movie.class)
                .setParameter("id", id);

        return Optional.ofNullable(typedQuery.getSingleResult());
    }

    public List<Movie> findByDirectorAndName(String director, String name) {
        if(director==null && name==null){
            return findAll();
        }
        String query = null;
        TypedQuery<Movie> typedQuery;
        if(director==null){
            query = "from Movie where name = :name";
            typedQuery = entityManager.createQuery(query, Movie.class)
                    .setParameter("name", name);

        }else if(name ==null){
            query = "from Movie where director = :director";
            typedQuery = entityManager.createQuery(query, Movie.class)
                    .setParameter("director", director);
        }else{
            query = "from Movie where director = :director and name = :name";
            typedQuery = entityManager.createQuery(query, Movie.class)
                    .setParameter("director", director).setParameter("name", name);

        }


        return typedQuery.getResultList();
    }

    @Transactional
    public void deleteById(String id) {
        String query = "delete from Movie where id = :id";
        Query deleteQuery = entityManager.createQuery(query)
                .setParameter("id", id);
        deleteQuery.executeUpdate();
    }

    @Transactional
    public void save(Movie movie) {
        //entityManager.merge(movie);
        entityManager.persist(movie);
    }
}

