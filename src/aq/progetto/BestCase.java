package aq.progetto;

import javax.naming.LimitExceededException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class BestCase {

    static String currentUser;
    static SocialNetwork network;
    static SocialNetworkWithReport moderatedNetwork;

    public static void main(String[] args) throws LimitExceededException,NumberFormatException {

        network = new SocialNetwork();

        currentUser = "user_1";

        network.createPost(currentUser,"first post ever");
        network.createPost(currentUser,"second post");
        network.editPost(currentUser,2, "this is the second post");

        currentUser = "user_2";

        network.likePost(currentUser,1);
        network.createPost(currentUser, "user_2 first post");

        currentUser = "user_3";

        network.likePost(currentUser, 3);
        network.likePost(currentUser,2);

        System.out.print("These are all the post published in the SocialNetwork: \n");

        //test of writtenBy using getAllPosts
        for ( Post p : network.getAllPosts()){
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
        System.out.print("These users posted at least once: " + network.getMentionedUsers() + "\n");

        //test of influencers
        System.out.print("Most influent users:\n");
        for (String user : network.influencers()){
            System.out.print(user+"\n");
        }

        //test of guessFollowers
        for (String users : network.getMentionedUsers()) {
            Set<String> followers = network.guessFollowers().get(users);

            if (followers != null) {

                System.out.print("SocialMap of " + users + "\n");
                for (String user : followers) {
                    System.out.print(user + "\n");
                }

            }
        }

        System.out.println("-----------------------------------------------------------"+
                "-----------------------------------------------------------\n");

        System.out.println("Showcasing the SocialNetworkWithReports class:\n");



    }



}
