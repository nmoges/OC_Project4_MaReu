package com.openclassrooms.mareu.model;

/**
 * Model object representing an Employee
 */
public class Employee {

    private String name;
    private String email;
    private final int id;

    /**
     * Constructor
     * @param name : String
     * @param email : String
     * @param id : int
     */
    public Employee(String name, String email, int id){
        this.name = name;
        this.email = email;
        this.id = id;
    }

    public String getName(){
        return this.name;
    }

    public String getEmail() {
        return this.email;
    }

    public int getId() {
        return this.id;
    }
}
