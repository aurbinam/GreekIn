import java.util.ArrayList;

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

    public static Node getNodeFromUser(Users user){
        Node node = new Node(user);
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

    public ArrayList<Users> getMutuals(Node user1, Node user2){
        ArrayList<Node> m = getRecommendedFriends(user1);
        ArrayList<Users> mutuals = new ArrayList<>();
        for(Node n : m){
            if(n==user2){
                for(Node n1 : user2.getFollowers()){
                    if(isFollowing(n1, user1)) mutuals.add(n1.getUserFromNode());
                }
            }
        }
        return mutuals;

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

        
    }
}