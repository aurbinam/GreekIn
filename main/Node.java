package main;


import java.util.ArrayList;

public class Node {
    private int nodeId;
    private Person user;
    private ArrayList<Follow> follow;
    private ArrayList<Node> followers;
    private ArrayList<Node> following;
    
    public int getNodeId() {
        return this.nodeId;
    }

    public void setNodeId(int nodeId) {
        this.nodeId = nodeId;
    }

    public String getName() {
        return user.getName();
    }

    public Person getUserFromNode(){
        return this.user;
    }

    public void setPerson(Person user){
        this.user = user;
    }

    public ArrayList<Follow> getFollows() {
        return this.follow;
    }

    public ArrayList<Node> getFollowers() {
        return this.followers;
    }

    public ArrayList<Node> getFollowing() {
        return this.following;
    }

    public String toString() {
        return  user.toString();
    }

    public void printFollowing() {
        for (Node u : this.following) {
            System.out.print(", " + u.getName());
        }
    }

    public void printFollowers() {
        for (Node u : this.followers) {
            System.out.print(", " + u.getName());
        }
    }
    
    public Node(Person user) {
        this.user = user;
        this.follow = new ArrayList<Follow>();
        this.followers = new ArrayList<Node>();
        this.following = new ArrayList<Node>();
    }

    //Creates a new follow between current user and followed user. adds to the current user's following the followed user, and also adds the current user to the followed user's list of followers
    public void addFollowing(Node followedUser) throws UserAlreadyFollowingException{
        if(!following.contains(followedUser)){
            Follow f = new Follow(this, followedUser);
            this.follow.add(f);
            this.following.add(followedUser);
            followedUser.followers.add(this);
        }
        else{
            //commented out for testing purposes
            // throw new UserAlreadyFollowingException(this.getName() + " is already following " +  followedUser.getName());
        } 
    }

    //Current user unfollows someone, they are also removed from their followers list, and the follow is removed from the network
    public void removeFollow(Node followedUser) throws FollowDoesntExistException{
        boolean found = false;
        for(Follow f : follow){
            if(f.getFollower().equals(this) && f.getFollowed().equals(followedUser)){
                this.follow.remove(f);
                this.following.remove(followedUser);
                followedUser.followers.remove(this);
                found = true;
                break;
            }
        }
        if (!found) {
            throw new FollowDoesntExistException(this.getName() + " is not following " + followedUser.getName());
        }
    }

    //Removes user information stored in the node
    public void removeUser(Node user){
        user.follow.clear();
        user.followers.clear();
        user.following.clear();
    }

}
