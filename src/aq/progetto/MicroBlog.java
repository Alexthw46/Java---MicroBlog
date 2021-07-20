package aq.progetto;


import javax.naming.LimitExceededException;
import java.util.Scanner;

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

            System.out.print("What you want to do?\n" + "Command List: \n Post - Write new post \n List - List all posts\n");

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
            if (command.equals("quit")) break;
        }





        input.close();
        System.out.print("Goodbye!\n");

    }

}
