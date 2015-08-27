package com.mobile.ram.simpletodo.model;

import java.io.Serializable;

/**
 * Created by Ram on 8/24/15.
 * This is TODO item model call
 */
public class TodoItem implements Serializable {
    private static final long serialVersionUID = 2L;
    public int id;
    public String item;


    public TodoItem(int id, String item) {
        super();
        this.item = item;
        this.id = id;
    }

    public TodoItem(String item) {
        super();
        this.item = item;


    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
