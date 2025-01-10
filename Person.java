import java.util.List;

public class Person {
    private String name;
    private int age;
    private boolean gender;
    private String occupation;
    private String placeOfStudy;
    private List<String> hobbies;

    public Person(String name, int age, boolean gender, String occupation, String placeOfStudy, List<String> hobbies) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.occupation = occupation;
        this.placeOfStudy = placeOfStudy;
        this.hobbies = hobbies;
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

    public boolean getGender() {   
        return gender;
    }   
    public void setGender(boolean gender) {
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

    public List<String> getHobbies() {
        return hobbies;
    }
    public void setHobbies(List<String> hobbies) {
        this.hobbies = hobbies;
    }

    public String toString() {
        return "Person{name='" + name + "', age=" + age + ", gender='" + gender + "', occupation='" + occupation +
               "', placeOfStudy='" + placeOfStudy + "', hobbies=" + hobbies;
    }
}
