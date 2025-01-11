import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class Graph implements Network{
    private ArrayList<Node> users;
    
    public Graph(){
        this.users = new ArrayList<Node>();
    }

    public Node addUser(Users user){
        Node node = new Node(user);
        this.users.add(node);
        return node;
    }

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

    public boolean isFollowing(Node follower, Node followed){
        for(Follow f : follower.getFollows()){
            if(f.getFollowed() == followed){
                return true;
            }
        }
        return false;
    }

    public boolean areFriends(Node user1, Node user2){
        for(Follow f : user1.getFollows()){
            if(f.getFollowed() == user2){
                for(Follow f2 : user2.getFollows()){
                    if(f2.getFollowed() == user1){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public ArrayList<Node> getRecommendedFriends(Node user1){
        ArrayList<Node> recommendedFriends = new ArrayList<Node>();
        for(Node user2 : this.users){
            if(user2 != user1 && isFollowing(user1, user2)){
                for(Node n : user2.getFollowing()){
                    if(!isFollowing(user1, n) && !user1.equals(n)){
                        recommendedFriends.add(n);
                    }
                }
            }
        }
        return recommendedFriends;
    }

    public ArrayList<Node> removeDuplicates(ArrayList<Node> list){
        ArrayList<Node> noDuplicates = new ArrayList<>();
        for(Node n : list){
            if(!noDuplicates.contains(n)){
                noDuplicates.add(n);
            }
        }
        return noDuplicates;
    }

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
        if(age1 == age2) return 20;
        else if(age1 < age2 && age1+1 == age2) return 10;
        else if(age1 > age2 && age1-1 == age2) return 10;
        else return 0;
    }

    public int compareHobbies(Node user1, Node user2){
        String[] l1 = user1.getUserFromNode().getHobbies();
        String[] l2 = user2.getUserFromNode().getHobbies();
        int percent=0;
        for(int i=0; i<l1.length;i++){
            for(int j=0; j<l2.length;j++){
                if(l1[i]==l2[j]) percent += 4;
            }
        }
        return percent;
    }

    public int compareGender(Node user1, Node user2){
        if(user1.getUserFromNode().getGender()==user2.getUserFromNode().getGender()) return 10;
        else return 5;
    }

    public ArrayList<String> getMutuals(Node user1, Node user2){
        ArrayList<Node> m = removeDuplicates(getRecommendedFriends(user1));
        ArrayList<String> mutuals = new ArrayList<>();
        for(Node n : m){
            if(n==user2){
                for(Node n1 : user2.getFollowers()){
                    if(isFollowing(user1, n1)) mutuals.add(n1.getName());
                }
            }
        }
        return mutuals;
    }

    public int compareMutuals(Node user1, Node user2){
        int percent = 0;
        ArrayList<String> mutuals = getMutuals(user1, user2);
        for(int i=0; i<mutuals.size();i++){
            if(percent<=45){  
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
            int mutuals = compareMutuals(user1, user2);
            return age+hobbies+gender+mutuals;
        }

    public ArrayList<Node> canBeRecommended(Node user1){
        ArrayList<Node> arr = new ArrayList<>();
        for(Node n : users){
            if(!isFollowing(user1, n) && n!=user1){
                arr.add(n);
            }
        }
        return arr;
    }

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

    public ArrayList<String> removeDuplicatesFromString(ArrayList<String> list){
        ArrayList<String> noDuplicates = new ArrayList<>();
        for(String s : list){
            if(!noDuplicates.contains(s)){
                noDuplicates.add(s);
            }
        }
        return noDuplicates;
    }
    
    public ArrayList<String> recommend(Node user1){
        ArrayList<String> recommendations = new ArrayList<>();
        ArrayList<Node> canBeRecommended = canBeRecommended(user1);
        int[] scale = recommendedUsersToScale(user1, canBeRecommended);
        reverseInsertionSort(scale, scale.length);
        for(int i=0; i<scale.length;i++){
            for(int j=0; j<canBeRecommended.size();j++){
                String userName = canBeRecommended.get(j).getName();
                if(!recommendations.contains(userName) && scale[i]==calculateScale(user1, canBeRecommended.get(j))){
                    recommendations.add(canBeRecommended.get(j).getName() + " " + String.valueOf(scale[i]));
                }
            }
        }
        return removeDuplicatesFromString(recommendations);
    }


    public void printRecommendations(Node user1){
        Scanner scan = new Scanner(System.in);
        System.out.println(user1.getName() + "'s recommended users are:");
        ArrayList<String> recommendations = recommend(user1);
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

    public static void main(String[] args) {
        Network greekIn = new Graph();
        String[] hobbies = {"Football","Basketball","Chess","Coding","Dancing"};
        Users John = new Users("John", 25, 'M', "consultant", "Bayern", hobbies);
        Users Jane = new Users("Jane", 28, 'F', "engineer", "Berlin", hobbies);
        Users Jack = new Users("Jack", 30, 'M', "doctor", "Munich", hobbies);
        Users Jill = new Users("Jill", 22, 'F', "student", "Hamburg", hobbies);
        Users Brock = new Users("Brock", 27, 'T', "teacher", "Cologne", hobbies);
        Users Freida = new Users("Freida", 55, 'F', "whatever", "wherever", hobbies);
        Users Max = new Users("Max", 55, 'M', "whatever", "wherever", hobbies);

        Node nodeJohn = greekIn.addUser(John);
        Node nodeJane = greekIn.addUser(Jane);
        Node nodeJack = greekIn.addUser(Jack);
        Node nodeJill = greekIn.addUser(Jill);
        Node nodeBrock = greekIn.addUser(Brock);
        Node nodeFreida = greekIn.addUser(Freida);
        Node nodeMax = greekIn.addUser(Max);

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

        greekIn.printRecommendations(nodeJane);

        // greekIn.recommend(nodeJane);
    }
}