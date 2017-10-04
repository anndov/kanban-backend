package com.kanban.repositories;

import com.kanban.domain.Bookmark;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

/**
 * Created by david on 11-Sep-17.
 */
public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
    Collection<Bookmark> findByAccountUsername(String username);
}
