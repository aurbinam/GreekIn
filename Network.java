import java.util.ArrayList;

public interface Network{
    public Node addUser(Person person);
    public void addFollow(Node follower, Node followed) throws UserNotInNetworkException;
    public void removeFollow(Node follower, Node followed);
    public void printUserFollowing(Node user) throws UserNotInNetworkException;
    public void printUserFollowers(Node user) throws UserNotInNetworkException;
    public boolean isFollowing(Node follower, Node followed);
    public boolean areFriends(Node user1, Node user2);
    public ArrayList<Node> getRecommendedFriends(Node user);
    public ArrayList<String> getMutuals(Node user1, Node user2);
    public int calculateScale(Node user1, Node user2);
    public ArrayList<String> recommendNode(Node user1);
    public void printRecommendations(Node user1);
    public int compareMutuals(Node user1, Node user2);


    public void displayUser(Node user);
    public void displayFollowers(Node user);
    public void displayFollowing(Node user);
    public void displayFriends(Node user);
    public void removeUser(Node user);
    public void updateUser(Node user, Person person);
}