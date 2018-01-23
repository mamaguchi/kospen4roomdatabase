package com.example.intel.kospenmove02;

public class Person {

    private int _ID;
    private String _name;

    // Constructor
    public Person(String name) {
        this._name = name;
    }

    // Setter
    public void set_id(int _ID) {
        this._ID = _ID;
    }


    public void set_name(String _name) {
        this._name = _name;
    }


    // Getter
    public int get_id() {
        return _ID;
    }


    public String get_name() {
        return _name;
    }

}
