package Testing;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import main.*;
public class PerformanceTesting {
    public static void main(String[] args) {
        // Start measuring time
        long startTime = System.currentTimeMillis();

        Users users = new Users();
        Network greekIn = new Graph(users);

        // Create hardcoded users
        ArrayList<String> hobbies = new ArrayList<>(Arrays.asList("Football", "Basketball", "Chess", "Coding", "Dancing"));
        ArrayList<String> noHobbies = new ArrayList<>(Arrays.asList("a","b","c","d","e"));
        Person John = new Person("John", 25, 'M', "consultant", "Bayern", hobbies);
        Person Jane = new Person("Jane", 28, 'F', "Engineer", "Berlin", hobbies);
        Person Jack = new Person("Jack", 30, 'M', "doctor", "Munich", hobbies);
        Person Jill = new Person("Jill", 22, 'F', "student", "Hamburg", hobbies);
        Person Brock = new Person("Brock", 27, 'T', "engineer", "Berlin", hobbies);
        Person Freida = new Person("Freida", 55, 'F', "whatever", "wherever", hobbies);
        Person Max = new Person("Max", 55, 'M', "whatever", "wherever", hobbies);
        Person Freida2 = new Person("Freida", 55, 'F', "whatever", "wherever", hobbies);

        Node userJohn = greekIn.addUser(John);
        Node userJane = greekIn.addUser(Jane);
        Node userJack = greekIn.addUser(Jack);
        Node userJill = greekIn.addUser(Jill);
        Node userBrock = greekIn.addUser(Brock);
        Node userFreida = greekIn.addUser(Freida);
        Node userMax = greekIn.addUser(Max);
        
        // Follow relationships
        try {
            greekIn.addFollow(userJack, userJill);
            greekIn.addFollow(userJohn, userJill);
            greekIn.addFollow(userJane, userJill);
            greekIn.addFollow(userJill, userJack);
            greekIn.addFollow(userJane, userJohn);
            greekIn.addFollow(userJohn, userBrock);
            greekIn.addFollow(userJill, userBrock);
            greekIn.addFollow(userJohn, userFreida);
            greekIn.addFollow(userJill, userFreida);
        } catch (UserNotInNetworkException e) {
            System.out.println(e.getMessage());
        }

        // Remove follows
        greekIn.removeFollow(userJohn, userBrock);
        greekIn.removeFollow(userJohn, userFreida);
    
        System.out.println();
        greekIn.displayUser(userJane);
        System.out.println();
        greekIn.displayFollowers(userJane);
        System.out.println();
        greekIn.displayFollowing(userJane);
        System.out.println();
        greekIn.displayFriends(userJane);
        System.out.println();

        try{
            greekIn.removeUser(userJack);
        } catch(UserNotInNetworkException e){
            System.out.println(e.getMessage());
        }
        System.out.println();

        greekIn.displayFollowers(userJane);
        System.out.println();
        greekIn.displayFollowing(userJane);
        System.out.println();
        greekIn.displayFriends(userJane);

        // greekIn.printRecommendations(userJane);
        System.out.println();

        greekIn.updateUser(userMax, new Person("Max", 90, 'L', "rans", "rasmd", noHobbies));

        // greekIn.printRecommendations(userJane);

        // Generate random users
        
        createRandomUsers((Graph) greekIn, 2000);  // Create 100 random users
        long randomStartTime = System.currentTimeMillis();  // Start timing random user creation

        // Find recommendations for Jane
        greekIn.recommendUsers(greekIn.getUsers().get(7));

        long randomEndTime = System.currentTimeMillis();  // End timing random user creation
        System.out.println("Time taken to find recommendations for User ID: " + userJane.getNodeId() + " Name: " + userJane.getName() + " for " + users.countUsers() + " number of users: " + (randomEndTime - randomStartTime) + " milliseconds");

        // Optionally, display total user count
        System.out.println("Total users in the network: " + users.countUsers());

        // End measuring total execution time
        long endTime = System.currentTimeMillis();
        System.out.println("Total execution time: " + (endTime - startTime) + " milliseconds");

        greekIn.displayUser(greekIn.getUsers().get(7));
        greekIn.printRecommendations(greekIn.getUsers().get(7));
        
    }

    // Method to create random users
    public static void createRandomUsers(Graph graph, int numberOfUsers) {
        // Base pools for random generation
        String[] baseNames = {"Alex", "Jordan", "Taylor", "Morgan", "Casey", "Riley", "Jamie", "Cameron", "Quinn", "Drew"};
        String[] occupations = {"Engineer", "Doctor", "Teacher", "Consultant", "Student", "Artist", "Developer", "Manager", "Pilot", "Designer"};
        String[] locations = {"New York", "Los Angeles", "Berlin", "London", "Tokyo", "Paris", "Rome", "Sydney", "Madrid", "Amsterdam"};
        ArrayList<String> hobbiesPool = new ArrayList<>(Arrays.asList("Reading", "Cooking", "Gaming", "Traveling", "Coding", "Dancing", "Photography", "Running", "Yoga", "Fishing"));

        Random random = new Random();

        for (int i = 0; i < numberOfUsers; i++) {
            // Generate unique names by appending a number to base names
            String name = baseNames[random.nextInt(baseNames.length)];

            // Generate random attributes
            int age = random.nextInt(50) + 18; // Random age between 18 and 67
            char gender = random.nextBoolean() ? 'M' : 'F'; // Random gender
            String occupation = occupations[random.nextInt(occupations.length)];
            String location = locations[random.nextInt(locations.length)];

            // Assign 5 random hobbies
            int hobbiesCount = 5;
            ArrayList<String> hobbies = new ArrayList<>(5);
            for (int j = 0; j < hobbiesCount; j++) {
                hobbies.add(hobbiesPool.get(random.nextInt(hobbiesPool.size())));
            }

            // Create and add the user to the graph
            Person person = new Person(name, age, gender, occupation, location, hobbies);
            Node user = graph.addUser(person);

            // Make the user follow random existing users
            int followsCount = random.nextInt(100) + 1; // Each user follows 1 to 50 other users
            for (int j = 0; j < followsCount; j++) {
                Node randomUser = graph.getUsers().get(random.nextInt(graph.getUsers().size()));
                if (randomUser != null && !randomUser.equals(user)) {
                    try {
                        graph.addFollow(user, randomUser);

                    } catch (UserNotInNetworkException e) {
                        System.out.println(e.getMessage());
                    }
                }
            }
        }
    }
}