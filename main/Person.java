package main;

import java.util.ArrayList;

public class Person {
    private String name;
    private int age;
    private char gender;
    private String occupation;
    private String location;
    private ArrayList<String> hobbies;
     
    public Person(String name, int age, char gender, String occupation, String location, ArrayList<String> hobbies) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.occupation = occupation;
        this.location = location;
        this.hobbies = hobbies;
    }

    //Getters and Setters
    public Person getPerson(){
        return this;
    }
    
    public String getName() {
        return name;
    } 
    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }

    public char getGender() {   
        return gender;
    }   
    public void setGender(char gender) {
        this.gender = gender;
    }

    public String getOccupation() {
        return occupation;
    }
    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getLocation() {   
        return location;    
    }
    public void setLocation(String location) {
        this.location = location;
    }

    public ArrayList<String> getHobbies() {
        return hobbies;
    }
    
    public void printHobbies(){
        for(String s : hobbies){
            System.out.print(" '" + s + "', ");
        }
    }
    public void setHobbies(ArrayList<String> hobbies) {
        this.hobbies = hobbies;
    }
    
    public String toString() {
        return "Name = '" + name + "', Age = " + age + ", Gender = '" + gender + "', Occupation = '" + occupation +
               "', Location = '" + location + "', Hobbies = ";
    }

}
