package aq.progetto;


import javax.naming.LimitExceededException;
import java.util.Scanner;

public class MicroBlog {

    static String currentUser;
    static SocialNetwork network;

    public static void main(String[] args) throws LimitExceededException {

        network = new SocialNetwork();
        network.init();

        System.out.print("Welcome to Micro-Blog\n");

        Scanner input = new Scanner(System.in);

        System.out.print("Choose your username\n");


        currentUser = input.nextLine();

        System.out.print("Welcome "+ currentUser +"\n"+"What you want to do?\n");

        while (true){
            String command = input.nextLine();
            if (command.equals("post")) {
                String message = input.nextLine();
                network.createPost(currentUser, message );
                continue;
            }
            if (command.equals("follow")) {
                continue;
            }
            if (command.equals("like")) {
                continue;
            }
            if (command.equals("list")) {
                network.getPosts();
                continue;
            }
            if (command.equals("quit")) break;
        }





        input.close();
        System.out.print("Goodbye!\n");
    }

}
