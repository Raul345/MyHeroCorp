package com.herocorp.ui.activities.DSEapp.models;

/**
 * Created by rsawh on 30-Sep-16.
 */
public class State {
    String id;
    String state;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String toString() {
        return state;
    }

    public State(String id, String state) {

        this.id = id;
        this.state = state;
    }

    public State(){}
}