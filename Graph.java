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
        followed.addFollowers(follower);
    }

    public void removeFollow(Node follower, Node followed){
        follower.removeFollow(followed);
    }

    public Node getUser(String name){
        for(Node user : this.users){
            if(user.getName() == name){
                return user;
            }
        }
        return null;
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

    public ArrayList<Node> removeDuplicates(ArrayList<Node> users){
        for(int i = 0; i < users.size(); i++){
            for(int j = i + 1; j < users.size(); j++){
                if(users.get(i) == users.get(j)){
                    users.remove(j);
                }
            }
        }
        return users;
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
        return removeDuplicates(recommendedFriends);
    }


    public static void main(String[] args) {
        Network greekIn = new Graph();
        Users John = new Users("John", 25, true, "consultant", "Bayern", new ArrayList<String>());
        Users Jane = new Users("Jane", 28, true, "engineer", "Berlin", new ArrayList<String>());
        Users Jack = new Users("Jack", 30, true, "doctor", "Munich", new ArrayList<String>());
        Users Jill = new Users("Jill", 22, true, "student", "Hamburg", new ArrayList<String>());
        Users Brock = new Users("Brock", 27, true, "teacher", "Cologne", new ArrayList<String>());

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
    }
}