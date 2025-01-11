import java.util.HashMap;
import java.util.Map;

public class Users{
    private Node userId;
    private Map<Node, Person> userMap;

    Users(){
        this.userMap = new HashMap<>();
    }

    public void addUser (Node userId, Person person) {
        this.userId = userId;
        if(userMap.containsKey(userId)) {
            throw new IllegalArgumentException("User ID already exists");
        }
        userMap.put(userId, person);
    }

    public void removeUser(Node userId) {
        if(!userMap.containsKey(userId)) {
            throw new IllegalArgumentException("User does not exist");
        }
        userMap.remove(userId);
    }

    public Person getUser(Node userId) {
        if(!userMap.containsKey(userId)) {
            throw new IllegalArgumentException("User does not exist");
        }
        return userMap.get(userId);
    }

    public Node getuserId(){
        return this.userId;
    }

    public void updateUser(Node userId, Person person) {
        if(!userMap.containsKey(userId)) {
            throw new IllegalArgumentException("User does not exist");
        }
        userMap.put(userId, person);
    }

    public void displayUsers() {
        if(userMap.isEmpty()) {
            System.out.println("No users to display");
        }else {
            for(Map.Entry<Node, Person> entry : userMap.entrySet()) {
                System.out.print("User ID: " + entry.getKey().getNodeId() + " ");
                System.out.print(entry.getValue().toString());
                entry.getValue().printHobbies();
                System.out.println();
            }
        }
    }

    public void printUser(int findId){
        if(userMap.isEmpty()) {
            System.out.println("No users to display");
        }else {
            for(Map.Entry<Node, Person> entry : userMap.entrySet()) {
                if(entry.getKey().getNodeId()==findId){
                    System.out.print("User ID: " + entry.getKey().getNodeId() + " ");
                    System.out.print(entry.getValue().toString());
                    entry.getValue().printHobbies();
                    System.out.println();
                }
            }
        }
    }
    

    public int countUsers() {
        return userMap.size();
    }

    public void setUserId(Node userId) {
        this.userId = userId;
    }
}
