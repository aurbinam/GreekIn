import java.util.ArrayList;
import java.util.List;

public class RecommendationScale extends Graph {
    public static int getAgeGroup(int age){
        if(age<=18) return 1;
        else if(age<=30) return 2;
        else if(age<=45) return 3;
        else if(age<=60) return 4;
        else return 5;
    }
    
    public static int compareAge(Users user1, Users user2){
        int age1 = getAgeGroup(user1.getAge());
        int age2 = getAgeGroup(user2.getAge());
        if(age1 == age2) return 20;
        else if(age1 < age2 && age1+1 == age2) return 10;
        else if(age1 > age2 && age1-1 == age2) return 10;
        else return 0;
    }

    public static int compareHobbies(Users user1, Users user2){
        List<String> l1 = user1.getHobbies();
        List<String> l2 = user2.getHobbies();
        int percent=0;
        for(int i=0; i<l1.size();i++){
            for(int j=0; j<l2.size();j++){
                if(l1.get(i)==l2.get(j)) percent += 5;
            }
        }
        return percent;
    }

    public static int compareGender(Users user1, Users user2){
        if(user1.getGender()==user2.getGender()) return 10;
        else return 5;
    }

    public static int compareMutuals(Users user1, Users user2, Network network){
        Node node1 = getNodeFromUser(user1);
        Node node2 = getNodeFromUser(user2);
        int percent = 0;
        ArrayList<Users> mutuals = network.getMutuals(node1, node2);
        for(int i=0; i<mutuals.size();i++){
            percent += 5;
        }
        return percent;
    }

    public static int calculateScale(Users user1, Users user2, Network network){
            int age = compareAge(user1, user2);
            int hobbies = compareHobbies(user1, user2);
            int gender = compareGender(user1, user2);
            int mutuals = compareMutuals(user1, user2, network);
            return age+hobbies+gender+mutuals;
        }
    
        public static void main(String[] args) {
            Network greekIn = new Graph();
            Users John = new Users("John", 25, 'M', "consultant", "Bayern", new ArrayList<String>());
            Users Jane = new Users("Jane", 28, 'F', "engineer", "Berlin", new ArrayList<String>());
            Users Jack = new Users("Jack", 30, 'M', "doctor", "Munich", new ArrayList<String>());
            Users Jill = new Users("Jill", 22, 'F', "student", "Hamburg", new ArrayList<String>());
            Users Brock = new Users("Brock", 27, 'T', "teacher", "Cologne", new ArrayList<String>());
    
            Node nodeJohn = greekIn.addUser(John);
            Node nodeJane = greekIn.addUser(Jane);
            Node nodeJack = greekIn.addUser(Jack);
            Node nodeJill = greekIn.addUser(Jill);
            Node nodeBrock = greekIn.addUser(Brock);
    
            try{
                greekIn.addFollow(nodeJohn, nodeJane);
                greekIn.addFollow(nodeJohn, nodeJack);
                greekIn.addFollow(nodeJane, nodeJack);
                greekIn.addFollow(nodeJack, nodeJill);
                greekIn.addFollow(nodeJane, nodeJohn);
                greekIn.addFollow(nodeJill, nodeBrock);
                greekIn.addFollow(nodeJack, nodeJohn);
                greekIn.addFollow(nodeJohn, nodeBrock);
                greekIn.addFollow(nodeJack, nodeBrock);
            } catch(UserNotInNetworkException e){
                System.out.println(e.getMessage());
            }
            
    
            greekIn.removeFollow(nodeJack, nodeJill);
    
            // greekIn.printUserFollowing(Jack);
            // if(greekIn.areFriends(Jack, Jane)){
            //     System.out.println("John and Jane are friends");
            // } else {
            //     System.out.println("John and Jane are not friends");
            // }
    
            
            try{
                System.out.println("John is followed by");
                greekIn.printUserFollowers(nodeJohn);
                System.out.println("John follows");
                greekIn.printUserFollowing(nodeJohn);
    
                System.out.println("Jane Follows");
                greekIn.printUserFollowing(nodeJane);
            } catch(UserNotInNetworkException e){
                System.out.println(e.getMessage());
            }
    
    
            System.out.println("Janes recommended freiends are");
            ArrayList<Node> recommendedFriends = greekIn.getRecommendedFriends(nodeJane);
            for(Node user : recommendedFriends){
                System.out.println(user.getName());
            }
    
            int scale = calculateScale(Jane, Brock, greekIn);
            System.out.println("Janes recommendation scale with Brock is " + scale);
    }
}
