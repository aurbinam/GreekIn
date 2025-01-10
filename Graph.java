import java.util.ArrayList;

public class Graph implements Network{
    private ArrayList<Node> users;
    
    public Graph(){
        this.users = new ArrayList<Node>();
    }

    public Node addUser(String name){
        Node user = new Node(name);
        this.users.add(user);
        return user;
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
        Node John = greekIn.addUser("John");
        Node Jane = greekIn.addUser("Jane");
        Node Jack = greekIn.addUser("Jack");
        Node Jill = greekIn.addUser("Jill");
        Node Brock = greekIn.addUser("Brock");
        Node Jodhn = new Node("Jodhn");

        try{
            greekIn.addFollow(John, Jane);
            greekIn.addFollow(John, Jack);
            greekIn.addFollow(Jane, Jack);
            greekIn.addFollow(Jack, Jill);
            greekIn.addFollow(Jane, John);
            greekIn.addFollow(Jill, Brock);
            greekIn.addFollow(Jack, John);
            greekIn.addFollow(John, Brock);
        } catch(UserNotInNetworkException e){
            System.out.println(e.getMessage());
        }
        

        greekIn.removeFollow(Jack, Jill);

        // greekIn.printUserFollowing(Jack);
        // if(greekIn.areFriends(Jack, Jane)){
        //     System.out.println("John and Jane are friends");
        // } else {
        //     System.out.println("John and Jane are not friends");
        // }

        
        try{
            System.out.println("John is followed by");
            greekIn.printUserFollowers(John);
            System.out.println("John follows");
            greekIn.printUserFollowing(John);

            System.out.println("Jane Follows");
            greekIn.printUserFollowing(Jane);
        } catch(UserNotInNetworkException e){
            System.out.println(e.getMessage());
        }


        System.out.println("Janes recommended freiends are");
        ArrayList<Node> recommendedFriends = greekIn.getRecommendedFriends(Jane);
        for(Node user : recommendedFriends){
            System.out.println(user);
        }
    }
}