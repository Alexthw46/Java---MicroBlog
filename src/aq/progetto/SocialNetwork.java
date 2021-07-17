package aq.progetto;

import aq.progetto.interfaces.ISocialBlog;

import javax.naming.LimitExceededException;
import java.util.*;

public class SocialNetwork implements ISocialBlog {

    static Random random = new Random();

    //Set degli utenti che hanno pubblicato almeno un post
    Set<String> UserList;
    //Map tra utenti e post pubblicati
    Map<String, List<Post>> SocialMap;
    //Map tra id e post per accesso veloce
    Map<Integer, Post> PostMap;
    //Map tra utenti e corrispondenti liste di followers
    Map<String, Set<String>> FollowMap;

    public void init() {
        UserList = new HashSet<>();
        SocialMap = new HashMap<>();
        PostMap = new HashMap<>();
        FollowMap = new HashMap<>();
    }

    public void getPosts(){

        for (String user : UserList) {
            List<Post> i = SocialMap.get(user);

            for (Post p : i) {
                System.out.print(p.toString() + "\n");
            }

        }
    }

    public static Map<String, Set<String>> guessFollowers(List<Post> ps){

        if (ps == null) {
            throw new NullPointerException();
        }

        Map<String, Set<String>> followers = new HashMap<>();

        


        return followers;
    }

    public List<String> influencers(){
        return influencers(FollowMap);
    }

    public static List<String> influencers(Map<String, Set<String>> followMap) {

        if (followMap == null) {
            throw new NullPointerException();
        }

        //Lista di output
        List<String> influencers = new ArrayList<>();

        //Mappa tra utenti e numero di followers
        Map<String, Integer> followers = new HashMap<>();

        for( Map.Entry<String,Set<String>> user : followMap.entrySet() ) {

            int followerCount = user.getValue().size();

            if ( followerCount > 0) {
                followers.put(user.getKey(), followerCount);
            }

        }

        List<Map.Entry<String, Integer>> sortedEntryList =  new ArrayList<>(followers.entrySet());

        //ordino la lista per ordine decrescente di follower
        sortedEntryList.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));

        //infine aggiungo i nomi degli influencer alla mia lista di influencer.
        for (Map.Entry<String, Integer> i : sortedEntryList) {
            influencers.add(i.getKey());
        }

        return influencers;
    }

    public Set<String> getMentionedUsers(){
        return UserList;
    }

    public static Set<String> getMentionedUsers(List<Post> ps){

        Set<String> users = new HashSet<>();

        for (Post post : ps){
            users.add(post.getAuthor());
        }

        return users;
    }

    public List<Post> writtenBy(String username){
        return SocialMap.get(username);
    }

    public static List<Post> writtenBy(List<Post> ps, String username){

        List<Post> filteredList = new ArrayList<>();

        for (Post post : ps){
            if (post.getAuthor().equals(username)){
                filteredList.add(post);
            }
        }

        return filteredList;
    }

    public List<Post> containing(List<String> words){
        List<Post> filteredList = new ArrayList<>();

        for (String user : UserList) {
            List<Post> i = SocialMap.get(user);

            for (Post p : i) {
                for (String j : words){
                    if (p.getText().contains(j)){
                        filteredList.add(p);
                        break;
                    }
                }
            }

        }

        return filteredList;
    }

    public void createPost(String currentUser, String text) throws LimitExceededException {

        int id = random.nextInt(Integer.MAX_VALUE - 1 );

        while (PostMap.get(id) != null){
            id = random.nextInt(Integer.MAX_VALUE - 1 );
        }

        Post temp = new Post(id,currentUser,text);

        if (UserList.add(currentUser)){
            SocialMap.put(currentUser, new ArrayList<>());
        }
        SocialMap.get(currentUser).add(temp);
        PostMap.put(id,temp);

    }
}
