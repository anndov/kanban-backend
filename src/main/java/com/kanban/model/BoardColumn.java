package com.kanban.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class BoardColumn extends AbstractEntity {

    private String name;

    @ManyToOne
    private Board board;

    private Integer columnOrder;

    public BoardColumn(String name, Integer columnOrder) {
        this.name = name;
        this.columnOrder = columnOrder;
    }

    public BoardColumn() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public Integer getColumnOrder() {
        return columnOrder;
    }

    public void setColumnOrder(Integer columnOrder) {
        this.columnOrder = columnOrder;
    }
}
