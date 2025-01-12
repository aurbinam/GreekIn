//Interface for the network for better use

public interface Network{
    public Node addUser(Person person);
    public void addFollow(Node follower, Node followed) throws UserNotInNetworkException;
    public void removeFollow(Node follower, Node followed);
    public void printUserFollowing(Node user) throws UserNotInNetworkException;
    public void printUserFollowers(Node user) throws UserNotInNetworkException;
    public void printRecommendations(Node user1);
    public void printAllUsers();
    public void displayUser(Node user);
    public void displayFollowers(Node user);
    public void displayFollowing(Node user);
    public void displayFriends(Node user);
    public void removeUser(Node user);
    public void updateUser(Node user, Person person);
}