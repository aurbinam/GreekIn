import java.util.ArrayList;
import java.util.List;

public class Graph implements Network{
    private ArrayList<Node> users;
    
    public Graph(){
        this.users = new ArrayList<Node>();
    }

    public Node addUser(Users user){
        Node node = new Node(user);
        this.users.add(node);
        return node;
    }

    public void addFollow(Node follower, Node followed) throws UserNotInNetworkException{
        if(!users.contains(followed) || !users.contains(follower)){
            throw new UserNotInNetworkException("User is not in the network");
        }
        follower.addFollowing(followed);
    }

    public void removeFollow(Node follower, Node followed){
        follower.removeFollow(followed);
    }

    public void printUserFollowing(Node user) throws UserNotInNetworkException{
        if (user.getFollowing().isEmpty()) {
            throw new UserNotInNetworkException("User is not in the network");
        }
        user.printFollowing();
    }

    public void printUserFollowers(Node user) throws UserNotInNetworkException{
        if (user.getFollowers().isEmpty()) {
            throw new UserNotInNetworkException("User is not in the network");
        }
        user.printFollowers();
    }

    public boolean isFollowing(Node follower, Node followed){
        for(Follow f : follower.getFollows()){
            if(f.getFollowed() == followed){
                return true;
            }
        }
        return false;
    }

    public boolean areFriends(Node user1, Node user2){
        for(Follow f : user1.getFollows()){
            if(f.getFollowed() == user2){
                for(Follow f2 : user2.getFollows()){
                    if(f2.getFollowed() == user1){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public ArrayList<Node> getRecommendedFriends(Node user1){
        ArrayList<Node> recommendedFriends = new ArrayList<Node>();
        for(Node user2 : this.users){
            if(user2 != user1 && areFriends(user1, user2)){ //do we do it based on mutuals or followings?
                for(Follow f : user2.getFollows()){
                    if(!isFollowing(user1, f.getFollowed()) && user1 != f.getFollowed()){
                        recommendedFriends.add(f.getFollowed());
                    }
                }
            }
        }
        return recommendedFriends;
    }

    public int getAgeGroup(int age){
        if(age<=18) return 1;
        else if(age<=30) return 2;
        else if(age<=45) return 3;
        else if(age<=60) return 4;
        else return 5;
    }
    
    public int compareAge(Node user1, Node user2){
        int age1 = getAgeGroup(user1.getUserFromNode().getAge());
        int age2 = getAgeGroup(user2.getUserFromNode().getAge());
        if(age1 == age2) return 20;
        else if(age1 < age2 && age1+1 == age2) return 10;
        else if(age1 > age2 && age1-1 == age2) return 10;
        else return 0;
    }

    public int compareHobbies(Node user1, Node user2){
        List<String> l1 = user1.getUserFromNode().getHobbies();
        List<String> l2 = user2.getUserFromNode().getHobbies();
        int percent=0;
        for(int i=0; i<l1.size();i++){
            for(int j=0; j<l2.size();j++){
                if(l1.get(i)==l2.get(j)) percent += 5;
            }
        }
        return percent;
    }

    public int compareGender(Node user1, Node user2){
        if(user1.getUserFromNode().getGender()==user2.getUserFromNode().getGender()) return 10;
        else return 5;
    }

    public ArrayList<String> getMutuals(Node user1, Node user2){
        ArrayList<Node> m = getRecommendedFriends(user1);
        ArrayList<String> mutuals = new ArrayList<>();
        for(Node n : m){
            if(n==user2){
                for(Node n1 : user2.getFollowers()){
                    if(isFollowing(user1, n1)) mutuals.add(n1.getName());
                }
            }
        }
        System.out.println(mutuals);
        return mutuals;
    }

    public int compareMutuals(Node user1, Node user2){
        int percent = 0;
        ArrayList<String> mutuals = getMutuals(user1, user2);
        for(int i=0; i<mutuals.size();i++){
            percent += 5;
        }
        return percent;
    }

    public int calculateScale(Node user1, Node user2){
            int age = compareAge(user1, user2);
            int hobbies = compareHobbies(user1, user2);
            int gender = compareGender(user1, user2);
            int mutuals = compareMutuals(user1, user2);
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
            greekIn.addFollow(nodeJack, nodeJill);
        } catch(UserNotInNetworkException e){
            System.out.println(e.getMessage());
        }

        nodeJack.printFollowing();  
        // greekIn.printUserFollowing(Jack);
        // if(greekIn.areFriends(Jack, Jane)){
        //     System.out.println("John and Jane are friends");
        // } else {
        //     System.out.println("John and Jane are not friends");
        // }

        
        // try{
        //     System.out.println("John is followed by");
        //     greekIn.printUserFollowers(nodeJohn);
        //     System.out.println("John follows");
        //     greekIn.printUserFollowing(nodeJohn);

        //     System.out.println("Jane Follows");
        //     greekIn.printUserFollowing(nodeJane);
        // } catch(UserNotInNetworkException e){
        //     System.out.println(e.getMessage());
        // }


        // System.out.println("Janes recommended freiends are");
        // ArrayList<Node> recommendedFriends = greekIn.getRecommendedFriends(nodeJane);
        // for(Node user : recommendedFriends){
        //     System.out.println(user.getName());
        // }
        nodeBrock.printFollowers();
        System.out.println("sdaf");
        nodeJill.printFollowing();

        int scale = greekIn.calculateScale(nodeJane, nodeBrock);
        System.out.println("Janes recommendation scale with Brock is " + scale);
        
    }
}