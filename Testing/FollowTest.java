package Testing;
import org.junit.Before;
import org.junit.Test;

import main.*;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;

public class FollowTest {
    private Node follower;
    private Node followed;
    private Follow follow;

    @Before
    public void setUp() {
        follower = new Node(new Person("Follower", 30, 'M', "bio", "location", new ArrayList<>(Arrays.asList("hobby1"))));
        followed = new Node(new Person("Followed", 25, 'F', "bio", "location", new ArrayList<>(Arrays.asList("hobby2"))));
        follow = new Follow(follower, followed);
    }

    @Test
    public void testGetFollower() {
        assertEquals(follower, follow.getFollower());
    }

    @Test
    public void testGetFollowed() {
        assertEquals(followed, follow.getFollowed());
    }
}