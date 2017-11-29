package com.kanban.model;

import com.kanban.security.model.User;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Board {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @ManyToOne
    private User owner;

    @ManyToMany
    @JoinTable(name = "board_participants", joinColumns = @JoinColumn(name = "board_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "participants_id", referencedColumnName = "id"))
    private Set<User> participants;

    @OneToMany
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
