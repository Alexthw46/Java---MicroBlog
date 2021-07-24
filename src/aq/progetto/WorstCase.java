package aq.progetto;

import javax.naming.LimitExceededException;
import java.util.ArrayList;

public class WorstCase {
    static String currentUser;
    static SocialNetwork network;

    private static void sleeper() {
        try {
            Thread.sleep(25);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws LimitExceededException,NumberFormatException {

        network = new SocialNetwork();
        currentUser = "tester_0";

        System.out.print("This class is meant as a simulation of the worst case scenario," +
                " where all the exceptions are thrown and handled\n" +
                "Refer to BestCase class for the ideal simulation without any runtime error\n");

        System.out.print("The debug messages will be delayed by 20ms for easier reading\n");

        System.out.print("Testing the Post class constructor using SocialNetwork createPost():\n");

        try {
            Thread.sleep(50);
        }catch (InterruptedException e){
            e.printStackTrace();
        }

        System.out.print("NullPointerException testing\n");

        try {
            network.createPost(null, "null author test");
        }catch (NullPointerException e){
            e.printStackTrace();
        }

        try {
            network.createPost("null text test",null);
        }catch (NullPointerException e){
            e.printStackTrace();
        }

        sleeper();

        System.out.print("IllegalArgumentException testing\n");

        try {
            network.createPost(" ","blank author test");
        }catch (IllegalArgumentException e){
            e.printStackTrace();
        }

        try {
            network.createPost("blank text test"," ");
        }catch (IllegalArgumentException e){
            e.printStackTrace();
        }

        sleeper();

        System.out.print("LimitExceededException testing\n");

        try {
            network.createPost("over 140 chars","aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        }catch (LimitExceededException e){
            e.printStackTrace();
        }

        System.out.println("Post class testing end\n");

        System.out.println("-----------------------------------------------------------"+
                "-----------------------------------------------------------\n");

        System.out.println("Testing the SocialNetwork class:\n");

        System.out.println("NullPointerException on guessFollower");

        try {
            SocialNetwork.guessFollowers(null);
        } catch (NullPointerException e){
            e.printStackTrace();
        }

        sleeper();

        System.out.println("NullPointerException on influencers");

        try {
            SocialNetwork.influencers(null);
        } catch (NullPointerException e){
            e.printStackTrace();
        }

        sleeper();

        System.out.println("NullPointerException on getMentionedUsers");

        try {
            SocialNetwork.getMentionedUsers(null);
        } catch (NullPointerException e){
            e.printStackTrace();
        }

        sleeper();

        System.out.println("NullPointerException on writtenBy");

        try {
            network.writtenBy(null);
        } catch (NullPointerException e){
            e.printStackTrace();
        }

        try {
            SocialNetwork.writtenBy(null, "null List");
        } catch (NullPointerException e){
            e.printStackTrace();
        }

        try {
            SocialNetwork.writtenBy(new ArrayList<>(), null);
        } catch (NullPointerException e){
            e.printStackTrace();
        }

        sleeper();

        System.out.println("NullPointerException on containing");

        try {
            network.containing(null);
        } catch (NullPointerException e){
            e.printStackTrace();
        }

        sleeper();

        System.out.println("IllegalArgumentException on containing");

        try {
            network.containing(new ArrayList<>());
        } catch (IllegalArgumentException e){
            e.printStackTrace();
        }

        sleeper();
        System.out.println("NullPointerException on editPost");

        //creazione di un post di debug con id = 1
        network.createPost(currentUser, "base_text");

        try {
            network.editPost("text null test", network.getLastId(), null);
        }catch (NullPointerException e){
            e.printStackTrace();
        }

        try {
            network.editPost(null, network.getLastId(), "user null test");
        }catch (NullPointerException e){
            e.printStackTrace();
        }

        sleeper();
        System.out.println("IllegalArgumentException on editPost");

        try {
            network.editPost(" ", network.getLastId(), "blank user");
        }catch (IllegalArgumentException e){
            e.printStackTrace();
        }

        try {
            network.editPost("blank text", network.getLastId(), " ");
        }catch (IllegalArgumentException e){
            e.printStackTrace();
        }

        try {
            network.editPost(currentUser, -1, "id not valid");
        }catch (IllegalArgumentException e){
            e.printStackTrace();
        }

        try {
            network.editPost(currentUser, 10, "id not valid");
        }catch (IllegalArgumentException e){
            e.printStackTrace();
        }

        sleeper();

        System.out.println("IllegalStateException on editPost");

        try {
            network.editPost("differentUser", network.getLastId(), "not the author of the post");
        }catch (IllegalStateException e){
            e.printStackTrace();
        }

        sleeper();

        System.out.println("LimitExceededException on editPost");

        try {
            network.editPost(currentUser, network.getLastId(), "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        }catch (LimitExceededException e){
            e.printStackTrace();
        }

        sleeper();

        System.out.println("NullPointerException on likePost");

        try {
            network.likePost(null,1);
        } catch (NullPointerException e){
            e.printStackTrace();
        }

        sleeper();

        System.out.println("IllegalArgumentException on likePost");

        try {
            network.likePost("negative id",-1);
        } catch (IllegalArgumentException e){
            e.printStackTrace();
        }

        try {
            network.likePost("not found id",1);
        } catch (IllegalArgumentException e){
            e.printStackTrace();
        }

        sleeper();

        System.out.println("IllegalStateException on likePost");

        try {
            // the post used for testing editPost is used to test likePost
            network.likePost(currentUser, network.getLastId());
        } catch (IllegalStateException e){
            e.printStackTrace();
        }

        sleeper();

        System.out.println("SocialNetwork class testing ends here\n");

    }

}
