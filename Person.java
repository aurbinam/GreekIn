public class Person {
    private String name;
    private int age;
    private char gender;
    private String occupation;
    private String placeOfStudy;
    private String[] hobbies;

    public Person(String name, int age, char gender, String occupation, String placeOfStudy, String[] hobbies) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.occupation = occupation;
        this.placeOfStudy = placeOfStudy;
        this.hobbies = hobbies;
    }

    public Person getPerson(){
        return this;
    }
    
    //getters and setters
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

    public String getPlaceOfStudy() {   
        return placeOfStudy;    
    }
    public void setPlaceOfStudy(String placeOfStudy) {
        this.placeOfStudy = placeOfStudy;
    }

    public String[] getHobbies() {
        return hobbies;
    }
    
    public void printHobbies(){
        for(String s : hobbies){
            System.out.print(" '" + s + "', ");
        }
    }
    public void setHobbies(String[] hobbies) {
        this.hobbies = hobbies;
    }
    
    public String toString() {
        return "Name = '" + name + "', Age = " + age + ", Gender = '" + gender + "', Occupation = '" + occupation +
               "', PlaceOfStudy = '" + placeOfStudy + "', Hobbies = ";
    }

}
