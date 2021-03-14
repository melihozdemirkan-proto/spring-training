package com.proto.app.repository;

import com.proto.app.entity.Movie;
import com.proto.app.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;


@Repository
public interface MovieRepository extends CrudRepository<Movie, String> {

    List<Movie> findByDirector(String director);

    List<Movie> findByName(String name);

    @Query("from Movie where (:director is null or director = :director) and (:name is null or name = :name)")
    List<Movie> findByDirectorAndName(String director, String name);

}

