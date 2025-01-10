import java.util.ArrayList;

public class Node {
    private String name;
    private ArrayList<Follow> follow;
    private ArrayList<Node> followers;
    private ArrayList<Node> following;
    
    public String getName() {
        return this.name;
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
        return this.name;
    }

    public void printFollowing() {
        for (Node u : this.following) {
            System.out.println(u.getName());
        }
    }

    public void printFollowers() {
        for (Node u : this.followers) {
            System.out.println(u.getName());
        }
    }
    
    public Node(String name) {
        this.name = name;
        this.follow = new ArrayList<Follow>();
        this.followers = new ArrayList<Node>();
        this.following = new ArrayList<Node>();
    }

    public void addFollowing(Node followedUser) {
        this.follow.add(new Follow(this, followedUser));
        this.following.add(followedUser);
    }

    public void addFollowers(Node follower) {
        this.followers.add(follower);
    }

    public void removeFollow(Node followedUser) {
        for (int i = 0; i < this.following.size(); i++) {
            if (this.follow.get(i).getFollowed() == followedUser) {
                this.follow.remove(i);
                this.following.remove(followedUser);
                return;
            }
        }
    }
}
