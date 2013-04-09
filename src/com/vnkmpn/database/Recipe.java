package com.vnkmpn.database;

public class Recipe {
	//private variables
    int _id;
    String _name;
    String _ingredient;
 
    // Empty constructor
    public Recipe(){
 
    }
    // constructor
    public Recipe(int id, String name, String ingredient){
        this._id = id;
        this._name = name;
        this._ingredient = ingredient;
    }
 
    // constructor
    public Recipe(String name, String ingredient){
        this._name = name;
        this._ingredient = ingredient;
    }
    // getting ID
    public int getID(){
        return this._id;
    }
 
    // setting id
    public void setID(int id){
        this._id = id;
    }
 
    // getting name
    public String getName(){
        return this._name;
    }
 
    // setting name
    public void setName(String name){
        this._name = name;
    }
 
    // getting phone number
    public String getIngredient(){
        return this._ingredient;
    }
 
    // setting phone number
    public void setIngredient(String ingredient){
        this._ingredient = ingredient;
    }
    
    @Override
    public String toString() {
        return this._name;
    }
}
