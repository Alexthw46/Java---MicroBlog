package aq.progetto;

import javax.naming.LimitExceededException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class BestCase {

    static String currentUser;
    static SocialNetwork network;
    static SocialNetworkWithReport moderatedNetwork;

    public static void main(String[] args) throws LimitExceededException, SecurityException, NullPointerException, IllegalStateException, IllegalArgumentException{

        System.out.print("This class is meant as a simulation of the ideal case scenario," +
                " where no exceptions are thrown and there is no need to handle them\n" +
                "Refer to WorstCase class for the simulation that checks any runtime error\n");


        network = new SocialNetwork();

        currentUser = "user_1";

        network.createPost(currentUser,"first post ever");
        network.createPost(currentUser,"second post");

        currentUser = "user_2";

        network.toggleLikePost(currentUser,1);
        network.createPost(currentUser, "user_2 first post");

        currentUser = "user_3";

        network.toggleLikePost(currentUser, 3);
        network.toggleLikePost(currentUser,2);

        currentUser = "user_4";

        network.createPost(currentUser, "we need a bit more people here");
        network.createPost(currentUser, "i'll be the most influent user");

        network.toggleLikePost("user_1", 5);
        network.toggleLikePost("user_2", 5);
        network.toggleLikePost("user_3", 5);

        System.out.print("These are all the post published in the SocialNetwork until now: \n");

        //test of getAllPosts
        List<Post> postList = network.getAllPosts();
        for ( Post p : postList){
            System.out.print(p.toString() + "\n");
        }

        //test of editPost
        network.editPost("user_1",2, "this is the second post");

        System.out.print("List of the posts after editPost \n");

        postList = network.getAllPosts();
        for ( Post p : postList){
            System.out.print(p.toString() + "\n");
        }


        //test of containing
        List<String> filter = new ArrayList<>();
        filter.add("user");
        System.out.print("These are all the post published containing the word \"user\"\n");
        for ( Post p : network.containing(filter)){
            System.out.print(p.toString() + "\n");
        }

        //test of getMentionedUsers
        System.out.print("These users posted at least once: " + SocialNetwork.getMentionedUsers(postList) + "\n");

        //test of writtenBy
        List<Post> writtenByUser = SocialNetwork.writtenBy(postList,"user_1");
        System.out.print("These are all the post published by \"user_1\"\n");
        for (Post stamp : writtenByUser){
            System.out.println(stamp.toString());
        }
        //test of influencers
        System.out.print("Most influent users:\n");
        for (String user : network.influencers()){
            System.out.print(user+"\n");
        }

        //test of guessFollowers

        printSocialMap(network.guessFollowers());


        //test of toggleLikePost for unfollowing

        System.out.println("Testing the removal of Likes\n");

        network.toggleLikePost("user_1", 5);
        network.toggleLikePost("user_3", 5);

        //test with influencers & guessFollowers
        System.out.print("Most influent users:\n");
        for (String user : network.influencers()){
            System.out.print(user+"\n");
        }

        printSocialMap(network.guessFollowers());


        System.out.println("-----------------------------------------------------------"+
                "-----------------------------------------------------------\n");

        System.out.println("Showcasing the SocialNetworkWithReports class:\n");


        moderatedNetwork = new SocialNetworkWithReport("admin");

        //creating post for tests
        moderatedNetwork.createPost("user_1", "We are testing the Social Network with a report system");
        moderatedNetwork.createPost("user_2","I'll be reported for example");

        for ( Post p : moderatedNetwork.getAllPosts()){
            System.out.print(p.toString() + "\n");
        }

        //test of reportPost
        moderatedNetwork.reportPost("user_1",2,"Test for reportPost");
        moderatedNetwork.reportPost("user_3", 2, "Test n.2 for reportPost");

        //test of listReports
        moderatedNetwork.printReports("admin");

        //test of deletePost
        moderatedNetwork.deletePost("admin",2);

        System.out.println("Admin deleted the reported post\n");

        for ( Post p : moderatedNetwork.getAllPosts()){
            System.out.print(p.toString() + "\n");
        }

        //test of addAdmin & getAdmins

        System.out.println("Current admins of the SocialNetwork:\n");

        System.out.println(moderatedNetwork.getAdmins()+"\n");

        moderatedNetwork.addAdmin("admin", "user_1");

        System.out.println("Current admins of the SocialNetwork (after addAdmin):\n");

        System.out.println(moderatedNetwork.getAdmins()+"\n");

    }

    private static void printSocialMap(Map<String, Set<String>> guessFollowMap) {
        for (String users : network.getMentionedUsers()) {
            Set<String> followers = guessFollowMap.get(users);

            if (followers != null) {

                System.out.print("SocialMap of " + users + ":\n");

                if (followers.size() == 0){ System.out.println("none\n");}
                else {
                    for (String user : followers) {
                        System.out.print(user + "\n");
                    }
                }
            }
        }
    }


}
