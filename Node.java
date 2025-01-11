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

    public void addFollowing(Node followedUser) throws UserAlreadyFollowingException{
        if(!following.contains(followedUser)){
            Follow f = new Follow(this, followedUser);
            this.follow.add(f);
            this.following.add(followedUser);
            followedUser.followers.add(this);
        }
        else{
            throw new UserAlreadyFollowingException(this.getName() + " is already following " +  followedUser.getName());
        } 
    }

    public void removeFollow(Node followedUser) throws FollowDoesntExistException{
        boolean found = false;
        for(Follow f : follow){
            // System.out.println(f.getFollowed().getName());
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

    public void removeUser(Node user){
        user.follow.clear();
        user.followers.clear();
        user.following.clear();
    }
}
