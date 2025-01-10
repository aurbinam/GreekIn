import java.util.ArrayList;

public interface Network{
    public Node addUser(Users user);
    public void addFollow(Node follower, Node followed) throws UserNotInNetworkException;
    public void removeFollow(Node follower, Node followed);
    public void printUserFollowing(Node user) throws UserNotInNetworkException;
    public void printUserFollowers(Node user) throws UserNotInNetworkException;
    public boolean isFollowing(Node follower, Node followed);
    public boolean areFriends(Node user1, Node user2);
    public ArrayList<Node> getRecommendedFriends(Node user);
    public ArrayList<Users> getMutuals(Node user1, Node user2);
}