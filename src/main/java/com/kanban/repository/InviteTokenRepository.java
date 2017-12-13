package com.kanban.repository;

import com.kanban.model.Board;
import com.kanban.model.InviteToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

/**
 * Created by david on 10-Dec-17.
 */
public interface InviteTokenRepository extends JpaRepository<InviteToken, Long> {
    InviteToken findByTokenAndIsAcceptedFalse(String token);
    Long countByBoard_IdAndIsAcceptedFalse(Long boardId);
    List<InviteToken> findByBoard_IdAndExpireDateGreaterThan(Long boardId, Date expireDate);
}
