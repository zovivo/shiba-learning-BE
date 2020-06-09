package com.shibalearning.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

@NoRepositoryBean
public interface BaseRepository <T, ID extends Serializable> extends JpaRepository<T,ID> {
    T findById(long id);
    Page<T> findAll(Pageable p);
}
