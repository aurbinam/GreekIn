package Testing;
import org.junit.Before;
import org.junit.Test;

import main.*;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class UsersTest {
    private Users users;
    private Node userNode1;
    private Node userNode2;
    private Person person1;
    private Person person2;

    @Before
    public void setUp() {
        users = new Users();
        person1 = new Person("John", 25, 'M', "consultant", "Bayern", new String[]{"Football"});
        person2 = new Person("Jane", 28, 'F', "engineer", "Berlin", new String[]{"Basketball"});
        userNode1 = new Node(person1);
        userNode2 = new Node(person2);
    }

    @Test
    public void testAddUser() {
        users.addUser(userNode1, person1);
        assertEquals(person1, users.getUser(userNode1));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddUserDuplicate() {
        users.addUser(userNode1, person1);
        users.addUser(userNode1, person1); // This should throw an exception
    }

    @Test
    public void testRemoveUser() {
        users.addUser(userNode1, person1);
        users.removeUser(userNode1);
        assertFalse(users.getUserMap().containsKey(userNode1));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRemoveUserNotExist() {
        users.removeUser(userNode1); // This should throw an exception
    }

    @Test
    public void testGetUser() {
        users.addUser(userNode1, person1);
        assertEquals(person1, users.getUser(userNode1));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetUserNotExist() {
        users.getUser(userNode1); // This should throw an exception
    }

    @Test
    public void testUpdateUser() {
        users.addUser(userNode1, person1);
        users.updateUser(userNode1, person2);
        assertEquals(person2, users.getUser(userNode1));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUpdateUserNotExist() {
        users.updateUser(userNode1, person2); // This should throw an exception
    }

    @Test
    public void testCountUsers() {
        assertEquals(0, users.countUsers());
        users.addUser(userNode1, person1);
        assertEquals(1, users.countUsers());
    }
}
