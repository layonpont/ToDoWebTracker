package com.tiy;

import javax.persistence.*;

/**
 * Created by Sulton on 5/17/2016.
 */
@Entity
@Table(name = "todos")
public class ToDo {
    @Id
    @GeneratedValue
    int id;

    @Column(nullable = false)
    String text;



    @Column(nullable = false)
    String toDoType;

    @Column(nullable = false)
    boolean isDone;



    public ToDo() {
    }

    public ToDo(String text, String toDoType, boolean isDone) {
        this.text = text;

        this.toDoType = toDoType;
        this.isDone = isDone;

    }
}
