package com.vnkmpn.database;

public class Recipe {
	//private variables
    int _id;
    String _name;
    String _ingredients;
    String _ovenTemp;
    String _cookTime;
    String _from;
    String _directions;
    String _serves;
    String _image_url;
 
    // Empty constructor
    public Recipe(){
 
    }
    // constructor
    public Recipe(int id, String name, String ingredients){
        this._id = id;
        this._name = name;
        this._ingredients = ingredients;
    }
 
    // constructor
    public Recipe(String name, String ingredients){
        this._name = name;
        this._ingredients = ingredients;
    }
    
 // constructor
    public Recipe(int id, String name, String ingredients, String ovenTemp, String cookTime, String from, String directions){
        this._id = id;
    	this._name = name;
        this._ingredients = ingredients;
        this._ovenTemp = ovenTemp;
        this._cookTime = cookTime;
        this._from = from;
        this._directions = directions;
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
    
    public void setIngredients(String ingredients){
        this._ingredients = ingredients;
    }
    
    public void setDirections(String directions){
        this._directions = directions;
    }
    
    public void setFrom(String from){
        this._from = from;
    }
    
    public void setOvenTemp(String ovenTemp){
        this._ovenTemp = ovenTemp;
    }
    
    public void setCookTime(String cookTime){
        this._cookTime = cookTime;
    }
    
    public void setServes(String serves) {
    	this._serves = serves;
    }
    
    public void setImageURL(String image_url) {
    	this._image_url = image_url;
    }
 
    // getting phone number
    public String getIngredients(){
        return this._ingredients;
    }
    
    public String getDirections(){
        return this._directions;
    }
    
    public String getFrom(){
        return this._from;
    }
    
    public String getOvenTemp(){
        return this._ovenTemp;
    }
    
    public String getCookTime(){
        return this._cookTime;
    }
    
    public String getServes(){
        return this._serves;
    }
    
    public String getImageURL(){
        return this._image_url;
    }
    
    @Override
    public String toString() {
        return this._name;
    }
}
