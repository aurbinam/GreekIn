public class GreekIn{
    public static void main(String[] args) {
        Users users = new Users();
        Network greekIn = new Graph(users);
        String[] hobbies = {"Football","Basketball","Chess","Coding","Dancing"};
        String[] noHobbies = {"a","b","c","d","e"};
        Person John = new Person("John", 25, 'M', "consultant", "Bayern", hobbies);
        Person Jane = new Person("Jane", 28, 'F', "engineer", "Berlin", hobbies);
        Person Jack = new Person("Jack", 30, 'M', "doctor", "Munich", hobbies);
        Person Jill = new Person("Jill", 22, 'F', "student", "Hamburg", hobbies);
        Person Brock = new Person("Brock", 27, 'T', "engineer", "Berlin", hobbies);
        Person Freida = new Person("Freida", 55, 'F', "whatever", "wherever", hobbies);
        Person Max = new Person("Max", 55, 'M', "whatever", "wherever", hobbies);
        Person Freida2 = new Person("Freida", 55, 'F', "whatever", "wherever", hobbies);
    
        Node nodeJohn = greekIn.addUser(John);
        Node nodeJane = greekIn.addUser(Jane);
        Node nodeJack = greekIn.addUser(Jack);
        Node nodeJill = greekIn.addUser(Jill);
        Node nodeBrock = greekIn.addUser(Brock);
        Node nodeFreida = greekIn.addUser(Freida);
        Node nodeMax = greekIn.addUser(Max);
        Node nodeFreida2 = greekIn.addUser(Freida2);
    
        try{
            greekIn.addFollow(nodeJack, nodeJill);
            greekIn.addFollow(nodeJohn, nodeJill);
            greekIn.addFollow(nodeJane, nodeJill);
            greekIn.addFollow(nodeJill, nodeJack);
            greekIn.addFollow(nodeJane, nodeJohn);
            greekIn.addFollow(nodeJohn, nodeBrock);
            greekIn.addFollow(nodeJill, nodeBrock);
            greekIn.addFollow(nodeJohn, nodeFreida);
            greekIn.addFollow(nodeJill, nodeFreida);
            greekIn.addFollow(nodeJill, nodeFreida);
            greekIn.addFollow(nodeJohn, nodeFreida);
            
        } catch(UserNotInNetworkException e){
            System.out.println(e.getMessage());
        }
    
        // System.out.println("removing");
        greekIn.removeFollow(nodeJohn, nodeBrock);
        greekIn.removeFollow(nodeJohn, nodeFreida);
    
        // nodeJohn.printFollowing();
        System.out.println();
        // nodeBrock.printFollowers();
    
        
        // System.out.println(greekIn.compareMutuals(nodeJane, nodeBrock));
        // nodeJane.printFollowing();  
        // greekIn.printUserFollowing(Jack);
        // if(greekIn.areFriends(Jack, Jane)){
        //     System.out.println("John and Jane are friends");
        // } else {
        //     System.out.println("John and Jane are not friends");
        // }
    
        
        // try{
        //     System.out.println("John is followed by");
        //     greekIn.printUserFollowers(nodeJohn);
        //     System.out.println("John follows");
        //     greekIn.printUserFollowing(nodeJohn);
    
        //     System.out.println("Jane Follows");
        //     greekIn.printUserFollowing(nodeJane);
        // } catch(UserNotInNetworkException e){
        //     System.out.println(e.getMessage());
        // }
    
    
        // System.out.println("Janes recommended freiends are");
        // ArrayList<Node> recommendedFriends = greekIn.getRecommendedFriends(nodeJane);
        // for(Node user : recommendedFriends){
        //     System.out.println(user.getName());
        // }
        // nodeBrock.printFollowers();
        // System.out.println("sdaf");
        // nodeJill.printFollowing();
    
        // int scale = greekIn.calculateScale(nodeJane, nodeBrock);
        // System.out.println("Janes recommendation scale with Brock is " + scale);
    
        // greekIn.printRecommendations(nodeJane);
    
        // greekIn.recommendNode(nodeJane);
        // System.out.println("");

        // users.displayUsers();

        greekIn.displayUser(nodeJill);
        System.out.println();
        greekIn.displayFollowers(nodeJill);
        System.out.println();
        greekIn.displayFollowing(nodeJill);
        System.out.println();
        greekIn.displayFriends(nodeJill);
        System.out.println();

        greekIn.removeUser(nodeJack);
        System.out.println();

        greekIn.displayFollowers(nodeJill);
        System.out.println();
        greekIn.displayFollowing(nodeJill);
        System.out.println();
        greekIn.displayFriends(nodeJill);

        greekIn.printRecommendations(nodeJill);
        System.out.println();

        greekIn.updateUser(nodeMax, new Person("Max", 90, 'L', "rans", "rasmd", noHobbies));

        greekIn.printRecommendations(nodeJill);
    }
}
