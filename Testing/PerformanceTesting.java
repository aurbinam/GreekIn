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

        // Generate random users
        
        createRandomUsers((Graph) greekIn, 100);  // Create 100 random users
        long randomStartTime = System.currentTimeMillis();

        greekIn.recommendUsers(greekIn.getUsers().get(7));

        long randomEndTime = System.currentTimeMillis();
        System.out.println("Time taken to find recommendations for User ID: " + greekIn.getUsers().get(7).getNodeId() + " Name: " + greekIn.getUsers().get(7).getName() + " for " + users.countUsers() + " number of users: " + (randomEndTime - randomStartTime) + " milliseconds");

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
            int age = random.nextInt(70) + 18; // Random age between 18 and 87
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
            int followsCount = random.nextInt(100) + 1; // Each user follows 1 to 100 other users
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