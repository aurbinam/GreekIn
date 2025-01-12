//The Follow class represents a link between two users in the network

public class Follow {
    private Node follower;
    private Node followed;

    public Node getFollower() {
        return this.follower;
    }

    public Node getFollowed() {
        return this.followed;
    }

    public Follow(Node follower, Node followed) {
        this.follower = follower;
        this.followed = followed;
    }

}