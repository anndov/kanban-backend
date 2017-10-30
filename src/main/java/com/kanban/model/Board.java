package com.kanban.model;

import com.kanban.security.model.User;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.Set;

@Entity
public class Board extends AbstractEntity {

    private String name;

    @ManyToOne
    private User owner;

    @OneToMany
    private Set<User> participants;

    @OneToMany(fetch = FetchType.EAGER)
    private Set<BoardColumn> boardColumns;

    public Board() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<BoardColumn> getBoardColumns() {
        return boardColumns;
    }

    public void setBoardColumns(Set<BoardColumn> boardColumns) {
        this.boardColumns = boardColumns;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Set<User> getParticipants() {
        return participants;
    }

    public void setParticipants(Set<User> participants) {
        this.participants = participants;
    }
}
