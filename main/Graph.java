package main;


import java.util.Scanner;
import java.util.ArrayList;


public class Graph implements Network{
    private ArrayList<Node> users;
    private Users userData;
    //Number is used to assign automatically a unique userId to each user added
    private int number=0;
    
    public Graph(Users userData){
        this.users = new ArrayList<Node>();
        this.userData = userData;
    }

    public ArrayList<Node> getUsers(){
        return this.users;
    }

    //Adds a user to the network, and creates a node where they are stored
    public Node addUser(Person person){
        Node node = new Node(person);
        this.users.add(node);
        node.setNodeId(number++);
        userData.addUser(node, person);
        return node;
    }


    //Adds / removes a follow between two users, where the follower follows the followed
    public void addFollow(Node follower, Node followed) throws UserNotInNetworkException{
        if(!users.contains(followed) || !users.contains(follower)){
            throw new UserNotInNetworkException("User is not in the network");
        }
        try{
            follower.addFollowing(followed);
        }
        catch(UserAlreadyFollowingException e){
            System.out.println(e.getMessage());
        }
    }

    public void removeFollow(Node follower, Node followed){
        try{
            follower.removeFollow(followed);
        }
        catch(FollowDoesntExistException e){
            System.out.println(e.getMessage());
        }
    }

    //Displays user's following / followers list
    public void printUserFollowing(Node user) throws UserNotInNetworkException{
        if (user.getFollowing().isEmpty()) {
            throw new UserNotInNetworkException("User is not in the network");
        }
        user.printFollowing();
    }

    public void printUserFollowers(Node user) throws UserNotInNetworkException{
        if (user.getFollowers().isEmpty()) {
            throw new UserNotInNetworkException("User is not in the network");
        }
        user.printFollowers();
    }

    //Checks if a user is following another
    public boolean isFollowing(Node follower, Node followed){
        if(follower.getFollowing().contains(followed)) return true;
        else return false;
    }

    //Checks if two users are following each other
    public boolean areFriends(Node user1, Node user2){
        if(user1.getFollowing().contains(user2) && user2.getFollowing().contains(user1)) return true;
        return false;
    }

    //Gets all of the users that a given user does not follow but are followed by users in their following
    public ArrayList<Node> getPotentialFollows(Node user1){
        ArrayList<Node> recommendedFriends = new ArrayList<Node>();
        for(Node user2 : this.users){
            if(user2 != user1 && isFollowing(user1, user2)){
                for(Node n : user2.getFollowing()){
                    if(!n.equals(user1) && !isFollowing(user1, n) && !recommendedFriends.contains(n)){
                        recommendedFriends.add(n);
                    }
                }
            }
        }
        return recommendedFriends;
    }

    //Methods used for calculating the percentage of likelihood for a user to follow another user
    public int getAgeGroup(int age){
        if(age<=18) return 1;
        else if(age<=30) return 2;
        else if(age<=45) return 3;
        else if(age<=60) return 4;
        else return 5;
    }
    
    public int compareAge(Node user1, Node user2){
        int age1 = getAgeGroup(user1.getUserFromNode().getAge());
        int age2 = getAgeGroup(user2.getUserFromNode().getAge());
        if(age1 == age2) return 10;
        else if(age1 < age2 && age1+1 == age2) return 5;
        else if(age1 > age2 && age1-1 == age2) return 5;
        else return 0;
    }

    public int compareHobbies(Node user1, Node user2){
        ArrayList<String> l1 = user1.getUserFromNode().getHobbies();
        ArrayList<String> l2 = user2.getUserFromNode().getHobbies();
        int percent=0;
        for(int i=0; i<l1.size();i++){
            if(l2.contains(l1.get(i))) percent+=4;
        }
        return percent;
    }

    public int compareGender(Node user1, Node user2){
        if(user1.getUserFromNode().getGender()==user2.getUserFromNode().getGender()) return 10;
        else return 5;
    }

    public int compareOccupations(Node user1, Node user2){
        if(user1.getUserFromNode().getOccupation()==user2.getUserFromNode().getOccupation()) return 5;
        else return 0;
    }

    public int compareLocation(Node user1, Node user2){
        if(user1.getUserFromNode().getLocation()==user2.getUserFromNode().getLocation()) return 5;
        else return 0;
    }

    //Gets user1 and user2 which is not followed by user1 and finds every mutual connection they have
    public ArrayList<String> getMutuals(Node user1, Node user2){
        ArrayList<Node> m = getPotentialFollows(user1);
        ArrayList<String> mutuals = new ArrayList<>();

        for(Node n : m){
            if(n==user2){
                for(Node n1 : user2.getFollowers()){
                    if(isFollowing(user1, n1) && !mutuals.contains(n1.getName())){
                        mutuals.add(n1.getName());
                    }
                    
                }
            }
        }
        return mutuals;
    }

    public int compareMutuals(Node user1, Node user2){
        int percent = 0;
        ArrayList<String> mutuals = getMutuals(user1, user2);
        for(int i=0; i<mutuals.size();i++){
            if(percent<=50){  
                percent += 5;
            }
            else break;
        }
        return percent;
    }

    public int calculateScale(Node user1, Node user2){
            int age = compareAge(user1, user2);
            int hobbies = compareHobbies(user1, user2);
            int gender = compareGender(user1, user2);
            int study = compareLocation(user1, user2);
            int occupation = compareOccupations(user1, user2);
            int mutuals = compareMutuals(user1, user2);
            return age+hobbies+gender+study+occupation+mutuals;
        }

    //Gets all of the users in the network which are not followed by a given user
    public ArrayList<Node> canBeRecommended(Node user1){
        ArrayList<Node> arr = new ArrayList<>();
        for(Node n : users){
            if(!isFollowing(user1, n) && n!=user1){
                arr.add(n);
            }
        }
        return arr;
    }

    //Stores every relationship percentage in an int array for sorting purpouses
    public int[] recommendedUsersToScale(Node user1, ArrayList<Node> recommendedUsers){
        int[] scale = new int[recommendedUsers.size()];
        for(int i=0; i<recommendedUsers.size();i++){
            scale[i] = calculateScale(user1, recommendedUsers.get(i));
        }
        return scale;
    }

    public void reverseInsertionSort( int [] theArray , int n )
	{
		for ( int i = 1; i < n; i++ )
		{
			int temp = theArray[ i ];
			int loc = i;

			while ( ( loc > 0 ) && ( theArray[ loc-1 ] < temp ) )
			{
				theArray[ loc ] = theArray[ loc-1 ];
				loc = loc - 1;
			}
			theArray[ loc ] = temp;
		}
	}
    
    //Sorts the scale and matches every number to the recommendation number given for the selected user
    public ArrayList<String> recommendUsers(Node user1){
        ArrayList<String> recommendations = new ArrayList<>();
        ArrayList<Node> canBeRecommended = canBeRecommended(user1);
        int[] scale = recommendedUsersToScale(user1, canBeRecommended);
        reverseInsertionSort(scale,scale.length);
        for(int i=0; i<scale.length;i++){
            for(int j=0; j<canBeRecommended.size();j++){
                String userName = canBeRecommended.get(j).getName();
                String recommendation = "User ID: " + canBeRecommended.get(j).getNodeId() + ", Name: " + canBeRecommended.get(j).getName() + ", Recommendation Scale: " + String.valueOf(scale[i]);
                if(!recommendations.contains(userName) && scale[i]==calculateScale(user1, canBeRecommended.get(j)) && !recommendations.contains(recommendation)){
                    recommendations.add(recommendation);
                }
            }
        }
        return recommendations;
    }

    //Displays all of the recommendations sorted in descending order by their recommendation scale with the user
    public void printRecommendations(Node user1){
        Scanner scan = new Scanner(System.in);
        System.out.println("\n" + user1.getName() + "'s recommended users are:");
        ArrayList<String> recommendations = recommendUsers(user1);
        for(int i=0; i < 2;i++){
            System.out.println(recommendations.get(i));
        }
        System.out.println("Do you want to see more? (Y for yes)");
        char userInput = scan.next().charAt(0);
        if(userInput=='y' || userInput=='Y'){
            for(int i=2;i < recommendations.size(); i++){
                System.out.println(recommendations.get(i));
            }
        }
    }

    //Displays all information about a user
    public void displayUser(Node user){
        System.out.println(user.getName() + " profile information: ");
        userData.printUser(user.getNodeId());
    }

    //Displays all users in the network
    public void printAllUsers(){
        for(Node user : users){
            displayUser(user);
        }
    }

    //Displays all users the given user is followed by
    public void displayFollowers(Node user){
        System.out.print("List of followers for " + user.getName() + ":");
        user.printFollowers();
        System.out.println();
    }

    //Displays all users the given user follows
    public void displayFollowing(Node user){
        System.out.print(user.getName() + " follows:");
        user.printFollowing();
        System.out.println();
    }

    //Gets all of the users which mutually follow each other with the given user
    public ArrayList<String> getFriends(Node user){
        ArrayList<Node> following = user.getFollowing();
        ArrayList<String> friends = new ArrayList<>();
        for(Node n : following){
            if(areFriends(user, n)){
                friends.add(n.getName());
            }
        }
       return friends;
    }

    //Prints all of the friends of a given user
    public void displayFriends(Node user){
        ArrayList<String> friends = getFriends(user);
        if (!friends.isEmpty()) {
            System.out.print(user.getName() + " is friends with: ");
            for(String s : friends){
                System.out.println(s);
            }
        }
        else System.out.println(user.getName() + " has no friends :(");
    }

    //Removes a user from the network and map, deleting them fully
    public void removeUser(Node user) throws UserNotInNetworkException{
        if(!users.contains(user)){
            throw new UserNotInNetworkException("User is not in the network");
        }
        else{
            userData.removeUser(user);
            user.removeUser(user);
            users.remove(user);
            for (Node n : users){
            Follow f = new Follow(n, user);
            if(n.getFollowers().contains(user)) n.getFollowers().remove(user);
            if(n.getFollowing().contains(user)) n.getFollowing().remove(user);
            if (n.getFollows().contains(f)) n.getFollows().remove(f);
        }
        System.out.println(user.getName() + " has been removed");
        }
    }

    //Updates the infromation about a specific user based on their userId
    public void updateUser(Node user, Person person){
        userData.updateUser(user, person);
        user.setPerson(person);
    }
}