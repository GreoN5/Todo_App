package com.example.todoapp_georgi_dimitrov_1901682009.Model;

public class ToDoModel {
    private int _id;
    private String _name;
    private int _isDone; // 0 for false - 1 for true

    public int getId() {
        return this._id;
    }

    public void setId(int id) {
        this._id = id;
    }

    public String getName() {
        return this._name;
    }

    public void setName(String name) {
        this._name = name;
    }

    public int getIsDone() {
        return this._isDone;
    }

    public void setIsDone(int done) {
        this._isDone = done;
    }
}
