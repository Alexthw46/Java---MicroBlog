package aq.progetto;

import javax.naming.LimitExceededException;
import java.util.*;

public class SocialNetwork {

    static Random random = new Random();
    static List<Post> PostList;

    public void init() {
        PostList = new ArrayList<>();
    }


    public void getPosts(){
        for (Post i : PostList){
            System.out.print(i.toString() + "\n");
        }
    }

    public Map<String, Set<String>> guessFollowers(List<Post> ps){
        return null;
    }

    public List<String> influencers(Map<String, Set<String>> followers) {
        return null;
    }

    public Set<String> getMentionedUsers(){
        return null;
    }

    public Set<String> getMentionedUsers(List<Post> ps){
        return null;
    }

    public List<Post> writtenBy(String username){
        List<Post> filteredList = new ArrayList<>();
        for (Post i : PostList){
            if (i.getAuthor().equals(username)){
                filteredList.add(i);
            }
        }
        return filteredList;
    }

    public List<Post> writtenBy(List<Post> ps, String username){
        List<Post> filteredList = new ArrayList<>();
        for (Post i : ps){
                if (i.getAuthor().equals(username)){
                    filteredList.add(i);
                }
            }
        return filteredList;
    }

    public List<Post> containing(List<String> words){
        List<Post> filteredList = new ArrayList<>();
        for (Post i : PostList){
            for (String j : words){
                if (i.text.contains(j)){
                    filteredList.add(i);
                    break;
                }
            }
        }

        return filteredList;
    }

    public void createPost(String currentUser, String text) throws LimitExceededException {

        int id = random.nextInt();

        PostList.add(new Post(id,currentUser,text));

    }
}
