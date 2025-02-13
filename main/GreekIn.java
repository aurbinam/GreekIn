package main;

import java.util.ArrayList;
import java.util.Arrays;

public class GreekIn{
    public static void main(String[] args) {
        Users users = new Users();
        Network greekIn = new Graph(users);
        
        ArrayList<String> hobbies = new ArrayList<>(Arrays.asList("Football", "Basketball", "Chess", "Coding", "Dancing"));
        ArrayList<String> noHobbies = new ArrayList<>(Arrays.asList("a","b","c","d","e"));
        Person John = new Person("John", 25, 'M', "consultant", "Bayern", hobbies);
        Person Jane = new Person("Jane", 28, 'F', "engineer", "Berlin", hobbies);
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
        Node userFreida2 = greekIn.addUser(Freida2);
        
    
        try{
            greekIn.addFollow(userJack, userJill);
            greekIn.addFollow(userJohn, userJill);
            greekIn.addFollow(userJane, userJill);
            greekIn.addFollow(userJill, userJack);
            greekIn.addFollow(userJane, userJohn);
            greekIn.addFollow(userJohn, userBrock);
            greekIn.addFollow(userJill, userBrock);
            greekIn.addFollow(userJohn, userFreida);
            greekIn.addFollow(userJill, userFreida);
            greekIn.addFollow(userJane, userMax);
            greekIn.addFollow(userMax, userBrock);
            greekIn.addFollow(userMax, userFreida2);
            greekIn.addFollow(userFreida2, userJane);
            greekIn.addFollow(userJack, userJane);
            greekIn.addFollow(userJane, userJack);
            
        } catch(UserNotInNetworkException e){
            System.out.println(e.getMessage());
        }
    
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

        greekIn.printRecommendations(userJane);
        System.out.println();

        greekIn.updateUser(userMax, new Person("Max", 90, 'L', "rans", "rasmd", noHobbies));

        greekIn.printRecommendations(userJane);
    }
}
