package Testing;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import main.*;

public class NodeTest {
    private Node nodeJohn;
    private Node nodeJane;
    private Person personJohn;
    private Person personJane;

    @Before
    public void setUp() {
        personJohn = new Person("John", 25, 'M', "consultant", "Bayern", new String[]{"Football"});
        personJane = new Person("Jane", 28, 'F', "engineer", "Berlin", new String[]{"Basketball"});
        nodeJohn = new Node(personJohn);
        nodeJane = new Node(personJane);
    }

    @Test
    public void testGetNodeId() {
        nodeJohn.setNodeId(1);
        assertEquals(1, nodeJohn.getNodeId());
    }

    @Test
    public void testGetName() {
        assertEquals("John", nodeJohn.getName());
    }


    @Test
    public void testAddFollowing() throws UserAlreadyFollowingException {
        nodeJohn.addFollowing(nodeJane);
        assertTrue(nodeJohn.getFollowing().contains(nodeJane));
        assertTrue(nodeJane.getFollowers().contains(nodeJohn));
    }

    @Test(expected = UserAlreadyFollowingException.class)
    public void testAddFollowingAlreadyFollowing() throws UserAlreadyFollowingException {
        nodeJohn.addFollowing(nodeJane);
        nodeJohn.addFollowing(nodeJane); // This should throw an exception
    }

    @Test
    public void testRemoveFollow() throws FollowDoesntExistException, UserAlreadyFollowingException {
        nodeJohn.addFollowing(nodeJane);
        nodeJohn.removeFollow(nodeJane);
        assertFalse(nodeJohn.getFollowing().contains(nodeJane));
        assertFalse(nodeJane.getFollowers().contains(nodeJohn));
    }

    @Test(expected = FollowDoesntExistException.class)
    public void testRemoveFollowNotFollowing() throws FollowDoesntExistException {
        nodeJohn.removeFollow(nodeJane); // This should throw an exception
    }

    @Test
    public void testRemoveUser() {
        try {
            nodeJohn.addFollowing(nodeJane);
        } catch (UserAlreadyFollowingException e) {
            e.printStackTrace();
            fail("UserAlreadyFollowingException was thrown");
        }
        nodeJohn.removeUser(nodeJohn);
        assertTrue(nodeJohn.getFollows().isEmpty());
        assertTrue(nodeJohn.getFollowers().isEmpty());
        assertTrue(nodeJohn.getFollowing().isEmpty());
    }
}