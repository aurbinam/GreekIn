import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Users extends Person{
    private Map<Integer, Person> userMap;
    private int userID=0;
    
    public Users(String name, int age, char gender, String occupation, String placeOfStudy, String[] hobbies) {
        super(name, age, gender, occupation, placeOfStudy, hobbies);
        this.userMap = new HashMap<>();       
    }

    public void addUser(Person person) {
        if(userMap.containsKey(userID)) {
            throw new IllegalArgumentException("User ID already exists");
        }
        userMap.put(userID, person);
        userID++;
    }

    public void removeUser(int userID) {
        if(!userMap.containsKey(userID)) {
            throw new IllegalArgumentException("User does not exist");
        }
        userMap.remove(userID);
        userID--;
    }

    public Person getUser(int userID) {
        if(!userMap.containsKey(userID)) {
            throw new IllegalArgumentException("User does not exist");
        }
        return userMap.get(userID);
    }

    public int getUserID(){
        return this.userID;
    }

    public void updateUser(int userID, Person person) {
        if(!userMap.containsKey(userID)) {
            throw new IllegalArgumentException("User does not exist");
        }
        userMap.put(userID, person);
    }

    public void displayUsers() {
        if(userMap.isEmpty()) {
            System.out.println("No users to display");
        }else {
            for(Map.Entry<Integer, Person> entry : userMap.entrySet()) {
                System.out.println(entry.getValue().toString());
            }
        }
    }


        public int countUsers() {
            return userMap.size();
        }
}
