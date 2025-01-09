import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Users extends Person{
    private Map<Integer, Person> userMap;
    
    public Users(String name, int age, boolean gender, String occupation, String placeOfStudy, List<String> hobbies, List<String> followers, List<String> following) {
        super(name, age, gender, occupation, placeOfStudy, hobbies, followers, following);
        this.userMap = new HashMap<>();       
    }

    public void addUser(int userID, Person person) {
        if(userMap.containsKey(userID)) {
            throw new IllegalArgumentException("User ID already exists");
        }
        userMap.put(userID, person);
    }

    public void removeUser(int userID) {
        if(!userMap.containsKey(userID)) {
            throw new IllegalArgumentException("User does not exist");
        }
        userMap.remove(userID);
    }

    public Person getUser(int userID) {
        if(!userMap.containsKey(userID)) {
            throw new IllegalArgumentException("User does not exist");
        }
        return userMap.get(userID);
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
