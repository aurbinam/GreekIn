package Testing;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import main.*;

public class GraphTest {
    private Graph graph;
    private Users users;
    private Node nodeJohn;
    private Node nodeJane;
    private Person personJohn;
    private Person personJane;

    @Before
    public void setUp() {
        users = new Users();
        graph = new Graph(users);
        personJohn = new Person("John", 25, 'M', "consultant", "Bayern", new ArrayList<>(Arrays.asList("Football")));
        personJane = new Person("Jane", 28, 'F', "engineer", "Berlin", new ArrayList<>(Arrays.asList("Basketball")));
        nodeJohn = graph.addUser(personJohn);
        nodeJane = graph.addUser(personJane);
    }

    @Test
    public void testAddUser() {
        assertNotNull(nodeJohn);
        assertEquals(personJohn, nodeJohn.getUserFromNode());
    }

    @Test
    public void testAddFollow() throws UserNotInNetworkException, UserAlreadyFollowingException {
        graph.addFollow(nodeJohn, nodeJane);
        assertTrue(nodeJohn.getFollowing().contains(nodeJane));
        assertTrue(nodeJane.getFollowers().contains(nodeJohn));
    }

    @Test(expected = UserNotInNetworkException.class)
    public void testAddFollowUserNotInNetwork() throws UserNotInNetworkException {
        Node nodeNotInNetwork = new Node(new Person("NotInNetwork", 30, 'M', "job", "location", new ArrayList<>(Arrays.asList("hobby1"))));
        graph.addFollow(nodeJohn, nodeNotInNetwork); // This should throw an exception
    }

    @Test
    public void testRemoveFollow() throws UserNotInNetworkException, UserAlreadyFollowingException, FollowDoesntExistException {
        graph.addFollow(nodeJohn, nodeJane);
        graph.removeFollow(nodeJohn, nodeJane);
        assertFalse(nodeJohn.getFollowing().contains(nodeJane));
        assertFalse(nodeJane.getFollowers().contains(nodeJohn));
    }

    @Test
    public void testIsFollowing() throws UserNotInNetworkException, UserAlreadyFollowingException {
        graph.addFollow(nodeJohn, nodeJane);
        assertTrue(graph.isFollowing(nodeJohn, nodeJane));
        assertFalse(graph.isFollowing(nodeJane, nodeJohn));
    }

    @Test
    public void testAreFriends() throws UserNotInNetworkException, UserAlreadyFollowingException {
        graph.addFollow(nodeJohn, nodeJane);
        graph.addFollow(nodeJane, nodeJohn);
        assertTrue(graph.areFriends(nodeJohn, nodeJane));
        assertFalse(graph.areFriends(nodeJohn, new Node(new Person("NotFriend", 30, 'M', "job", "location", new ArrayList<>(Arrays.asList("hobby1"))))));
    }

    @Test
    public void testGetPotentialFollows() throws UserNotInNetworkException, UserAlreadyFollowingException {
        Node nodeJack = graph.addUser(new Person("Jack", 30, 'M', "doctor", "Munich", new ArrayList<>(Arrays.asList("Chess"))));
        graph.addFollow(nodeJohn, nodeJane);
        graph.addFollow(nodeJane, nodeJack);
        ArrayList<Node> potentialFollows = graph.getPotentialFollows(nodeJohn);
        assertTrue(potentialFollows.contains(nodeJack));
    }

    @Test
    public void testCalculateScale() {
        int scale = graph.calculateScale(nodeJohn, nodeJane);
        assertTrue(scale > 0);
    }

    @Test
    public void testRecommendNode() {
        ArrayList<String> recommendations = graph.recommendUsers(nodeJohn);
        assertFalse(recommendations.isEmpty());
    }

    @Test
    public void testDisplayUser() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        graph.displayUser(nodeJohn);

        assertTrue(outContent.toString().contains("John profile information:"));

        System.setOut(originalOut);
    }

    @Test
    public void testPrintAllUsers() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        graph.printAllUsers();

        assertTrue(outContent.toString().contains("John profile information:"));
        assertTrue(outContent.toString().contains("Jane profile information:"));

        System.setOut(originalOut);
    }

    @Test
    public void testDisplayFollowers() throws UserNotInNetworkException, UserAlreadyFollowingException {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        graph.addFollow(nodeJane, nodeJohn);
        graph.displayFollowers(nodeJohn);

        assertTrue(outContent.toString().contains("List of followers for John:"));
        assertTrue(outContent.toString().contains("Jane"));

        System.setOut(originalOut);
    }

    @Test
    public void testDisplayFollowing() throws UserNotInNetworkException, UserAlreadyFollowingException {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        graph.addFollow(nodeJohn, nodeJane);
        graph.displayFollowing(nodeJohn);

        assertTrue(outContent.toString().contains("John follows:"));
        assertTrue(outContent.toString().contains("Jane"));

        System.setOut(originalOut);
    }

    @Test
    public void testGetFriends() throws UserNotInNetworkException, UserAlreadyFollowingException {
        graph.addFollow(nodeJohn, nodeJane);
        graph.addFollow(nodeJane, nodeJohn);
        ArrayList<String> friends = graph.getFriends(nodeJohn);
        assertTrue(friends.contains("Jane"));
    }

    @Test
    public void testDisplayFriends() throws UserNotInNetworkException, UserAlreadyFollowingException {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        graph.addFollow(nodeJohn, nodeJane);
        graph.addFollow(nodeJane, nodeJohn);
        graph.displayFriends(nodeJohn);

        assertTrue(outContent.toString().contains("John is friends with:"));
        assertTrue(outContent.toString().contains("Jane"));

        System.setOut(originalOut);
    }

    @Test(expected = UserNotInNetworkException.class)
    public void testRemoveUserNotInNetwork() throws UserNotInNetworkException {
        Node nodeNotInNetwork = new Node(new Person("NotInNetwork", 30, 'M', "job", "location", new ArrayList<>(Arrays.asList("hobby1"))));
        graph.removeUser(nodeNotInNetwork); // This should throw an exception
    }

    @Test
    public void testRemoveUser() throws UserNotInNetworkException{
        graph.removeUser(nodeJohn);
        assertFalse(graph.getUsers().contains(nodeJohn));
        assertFalse(nodeJane.getFollowing().contains(nodeJohn));
        assertFalse(nodeJane.getFollowers().contains(nodeJohn));
    }

    @Test
    public void testUpdateUser() {
        Person updatedPerson = new Person("John Updated", 26, 'M', "consultant", "Bayern", new ArrayList<>(Arrays.asList("Football")));
        graph.updateUser(nodeJohn, updatedPerson);
        assertEquals(updatedPerson, nodeJohn.getUserFromNode());
    }
}