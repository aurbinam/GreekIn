import java.util.List;
import java.util.*;
public class Person {
    private String name;
    private int age;
    private boolean gender;
    private String occupation;
    private String placeOfStudy;
    private List<String> hobbies;
    private Set<String> followers;
    private Set<String> following;

    public Person(String name, int age, boolean gender, String occupation, String placeOfStudy, List<String> hobbies, List<String> followers, List<String> following) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.occupation = occupation;
        this.placeOfStudy = placeOfStudy;
        this.hobbies = hobbies;
        this.followers = new HashSet<>(followers); 
        this.following = new HashSet<>(following);
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

    public Set<String> getFollowers() {
        return followers;
    }
    public void setFollowers(Set<String> followers) {
        this.followers = followers;
    }

    public Set<String> getFollowing() {    
        return following;
    }
    public void setFollowing(Set<String> following) {
        this.following = following;
    }

    public String toString() {
        return "Person{name='" + name + "', age=" + age + ", gender='" + gender + "', occupation='" + occupation +
               "', placeOfStudy='" + placeOfStudy + "', hobbies=" + hobbies + ", followers=" + followers +
               ", following=" + following + "}";
    }
}
