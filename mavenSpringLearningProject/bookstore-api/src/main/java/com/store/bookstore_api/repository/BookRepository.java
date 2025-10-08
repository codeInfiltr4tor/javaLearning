package com.store.bookstore_api.repository;

import com.store.bookstore_api.model.book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BookRepository extends JpaRepository<book, Long> {

}
