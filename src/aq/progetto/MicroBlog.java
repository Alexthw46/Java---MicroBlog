package aq.progetto;


import javax.naming.LimitExceededException;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class MicroBlog {

    static String currentUser;
    static SocialNetwork network;

    public static void main(String[] args) throws LimitExceededException,NumberFormatException {

        network = new SocialNetwork();

        System.out.print("Welcome to Micro-Blog\n");

        Scanner input = new Scanner(System.in);

        System.out.print("Choose your username\n");


        currentUser = input.nextLine();

        System.out.print("Welcome "+ currentUser + "\n");

        while (true){

            System.out.print("What you want to do?\n" + "Command List: \n" +
                    " Post - Write new post \n" +
                    " List - List all posts\n" +
                    " Like - Like the post with the given id\n"+
                    " Logout - Change user or quit\n" +
                    " Followmap - Print followmap\n" +
                    " Influencers - Print influencers()\n"
            );

            String command = input.nextLine();

            if (command.equals("post")) {
                String message = input.nextLine();
                network.createPost(currentUser, message);
                continue;
            }
            if (command.equals("follow") || command.equals("like")) {
                int id = 0;
                try {
                    id = input.nextInt();
                } catch (NumberFormatException e){
                    e.printStackTrace();
                }
                network.likePost(currentUser, id);
                continue;
            }
            if (command.equals("list")) {
                for ( Post p : network.getAllPosts()){
                    System.out.print(p.toString() + "\n");
                }
                continue;
            }
            if (command.equals("logout")) {
                System.out.print("Quit or change user?\n");
                command = input.nextLine();
                if (command.equals("quit")) break;
                else {
                    System.out.print("Log in with your username\n");
                    currentUser = input.nextLine();
                }
            }
            if (command.equals("followmap")){
                Set<String> followers = network.guessFollowers().get(currentUser);

                if (followers != null){

                    for (String user : followers){
                        System.out.print(user+"\n");
                    }

                }
            }
            if (command.equals("influencers")){
                System.out.print("Most influent users:\n");
                for (String user : network.influencers()){
                    System.out.print(user+"\n");
                }
            }
        }


        input.close();
        System.out.print("Goodbye!\n");

    }

}
