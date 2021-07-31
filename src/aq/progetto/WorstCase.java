package aq.progetto;

import javax.naming.LimitExceededException;
import java.util.ArrayList;

public class WorstCase {
    static String currentUser;
    static SocialNetwork network;
    static SocialNetworkWithReport moderatedNetwork;
    static final String over140 = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";

    private static void sleeper() {
        try {
            Thread.sleep(25);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws LimitExceededException, SecurityException, NullPointerException, IllegalStateException, IllegalArgumentException{

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
            network.createPost("over 140 chars",over140);
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
            network.editPost(currentUser, network.getLastId(), over140);
        }catch (LimitExceededException e){
            e.printStackTrace();
        }

        sleeper();

        System.out.println("NullPointerException on likePost");

        try {
            network.toggleLikePost(null,1);
        } catch (NullPointerException e){
            e.printStackTrace();
        }

        sleeper();

        System.out.println("IllegalArgumentException on likePost");

        try {
            network.toggleLikePost("negative id",-1);
        } catch (IllegalArgumentException e){
            e.printStackTrace();
        }

        try {
            network.toggleLikePost("not found id",1);
        } catch (IllegalArgumentException e){
            e.printStackTrace();
        }

        sleeper();

        System.out.println("IllegalStateException on likePost");

        try {
            // the post used for testing editPost is used to test likePost
            network.toggleLikePost(currentUser, network.getLastId());
        } catch (IllegalStateException e){
            e.printStackTrace();
        }

        sleeper();

        System.out.println("SocialNetwork class testing ends here\n");

        System.out.println("-----------------------------------------------------------"+
                "-----------------------------------------------------------\n");

        System.out.println("Testing the SocialNetworkWithReports class:\n");

        sleeper();

        System.out.println("NullPointerException on constructor");

        try {
            moderatedNetwork = new SocialNetworkWithReport(null);
        }catch (NullPointerException e){
            e.printStackTrace();
        }

        sleeper();

        System.out.println("IllegalArgumentException on constructor");

        try {
            moderatedNetwork = new SocialNetworkWithReport("");
        }catch (IllegalArgumentException e){
            e.printStackTrace();
        }

        sleeper();

        moderatedNetwork = new SocialNetworkWithReport(currentUser);

        System.out.println("NullPointerException on addAdmin");

        try{
            moderatedNetwork.addAdmin(currentUser,null);
        }catch (NullPointerException e){
            e.printStackTrace();
        }

        try{
            moderatedNetwork.addAdmin(null,"new");
        }catch (NullPointerException e){
            e.printStackTrace();
        }

        sleeper();
        System.out.println("IllegalArgumentException on addAdmin");

        try{
            moderatedNetwork.addAdmin("","new");
        }catch (IllegalArgumentException e){
            e.printStackTrace();
        }

        try{
            moderatedNetwork.addAdmin(currentUser,"");
        }catch (IllegalArgumentException e){
            e.printStackTrace();
        }

        sleeper();
        System.out.println("SecurityException on AddAdmin");
        try{
            moderatedNetwork.addAdmin("new",currentUser);
        }catch (SecurityException e){
            e.printStackTrace();
        }

        sleeper();
        System.out.println("Creating Posts for testing of deletePost and reports\n");

        try{
            moderatedNetwork.createPost(currentUser, "hello i'm the first admin of this site");
            moderatedNetwork.createPost("Mark", "hi, i just registered");
            moderatedNetwork.createPost("Luke", "i can't wait to be reported, mr admin");
            for (Post stamp : moderatedNetwork.getAllPosts()){
                System.out.println(stamp.toString());
            }
        }catch (NullPointerException | IllegalArgumentException | LimitExceededException e){
            e.printStackTrace();
        }

        sleeper();
        System.out.println("NullPointerException on reportPost");

        try{
            moderatedNetwork.reportPost(null, 3, "null username");
        }catch (NullPointerException e){
            e.printStackTrace();
        }

        try{
            moderatedNetwork.reportPost("null reason", 3, null);
        }catch (NullPointerException e){
            e.printStackTrace();
        }

        sleeper();
        System.out.println("IllegalArgumentException on reportPost");

        try{
            moderatedNetwork.reportPost("", 3, "invalid username");
        }catch (IllegalArgumentException e){
            e.printStackTrace();
        }

        try{
            moderatedNetwork.reportPost("invalid reason", 3, "");
        }catch (IllegalArgumentException e){
            e.printStackTrace();
        }

        try{
            moderatedNetwork.reportPost(currentUser, 0, "invalid id");
        }catch (IllegalArgumentException e){
            e.printStackTrace();
        }

        try{
            moderatedNetwork.reportPost(currentUser, 10, "id not found");
        }catch (IllegalArgumentException e){
            e.printStackTrace();
        }

        sleeper();
        System.out.println("LimitExceededException on reportPost");

        try{
            moderatedNetwork.reportPost(currentUser, 3, over140);
        }catch (LimitExceededException e){
            e.printStackTrace();
        }

        sleeper();

        System.out.println("Generating reports for testing");

        try {

            moderatedNetwork.reportPost(currentUser, 3, "test report");
            moderatedNetwork.reportPost("Mark", 3 , "Luke wanted it");

        }catch (LimitExceededException| NullPointerException| IllegalArgumentException e){
            e.printStackTrace();
        }

        sleeper();

        System.out.println("NullPointerException on listReports");

        try{
            moderatedNetwork.printReports(null);
        }catch (NullPointerException e){
            e.printStackTrace();
        }

        sleeper();
        System.out.println("IllegalArgumentException on listReports");

        try{
            moderatedNetwork.printReports("");
        }catch (IllegalArgumentException e) {
            e.printStackTrace();
        }

        sleeper();
        System.out.println("SecurityException on listReports");

        try {
            moderatedNetwork.printReports("Mark");
        }catch (SecurityException e){
            e.printStackTrace();
        }

        sleeper();
        System.out.println("NullPointerException on deletePost");

        try{
            moderatedNetwork.deletePost(null, 3);
        }catch (NullPointerException e){
            e.printStackTrace();
        }

        sleeper();
        System.out.println("IllegalArgumentException on deletePost");

        try{
            moderatedNetwork.deletePost("", 3);
        }catch (IllegalArgumentException e){
            e.printStackTrace();
        }

        try{
            moderatedNetwork.deletePost("id not valid", 0);
        }catch (IllegalArgumentException e){
            e.printStackTrace();
        }

        try{
            moderatedNetwork.deletePost("id not found", 10);
        }catch (IllegalArgumentException e){
            e.printStackTrace();
        }

        sleeper();
        System.out.println("SecurityException on deletePost");

        try{
            moderatedNetwork.deletePost("Luke", 2);
        }catch (SecurityException e){
            e.printStackTrace();
        }

    }

}

