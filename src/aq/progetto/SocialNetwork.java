package aq.progetto;

import aq.progetto.interfaces.ISocialBlog;

import javax.naming.LimitExceededException;
import java.util.*;

public class SocialNetwork implements ISocialBlog {

    /**
     * OVERVIEW: SocialNetwork è una collezione mutabile di utenti e post in cui
     *  ogni utente può visualizzare, scrivere e mettere like (e quindi seguire)
     *  messaggi di massimo 140 caratteri chiamati Post.
     *
     * ABSTRACTION FUNCTION:
     *
     * α(C) = {C.posts.get(i) | 0 ≤ i < C.posts.size()}
     *
     *
     * TYPICAL ELEMENT:
     * (
     *  {user_1, ..., user_n}
     *  {post_1, ..., post_n}
     *  f: user -> {users} | f(user_1) = {user_i, ..., user_j}, ..., f(user_n) = {user_k, ..., user_h}
     *  f: user -> {posts} | f(user_1) = {post_i, ..., post_j}, ..., f(user_n) = {post_k, ..., post_h}
     * )
     *
     * REP INVARIANT:
     *  IR(Post) ^
     *  ∀ u utente : (u ≠ null ∧ u.length > 0 ∧ u ∈ this.UserList <==> (∃ p post ∈ this.SocialMap : p.getAuthor() = u)) ^
     *  ∀ v utente : (v ∈ this.followMap.get(u) <==> (∃ p post : p.getAuthor() = v ∧ (u ∈ p.getLikes()) )) ^
     *  ∀ (u, p) ∈ this.SocialMap : u = p.getAuthor() ∧
     *  ∀ (i, p) ∈ this.PostMap : i = p.getId() ^
     *  ∀ p,q post ∈ this.getPosts() : ( p.getId() == q.getId() <==> p == q )
     *
     **/

    //Set degli utenti che hanno pubblicato almeno un post
    private final Set<String> UserList;

    //Map tra utenti e post pubblicati
    private final Map<String, List<Post>> SocialMap;

    //Map tra id e post corrispondente per accesso veloce O(1)
    private final Map<Integer, Post> PostMap;

    //Map tra ogni utente e corrispondente lista di utenti seguiti
    private final Map<String, Set<String>> FollowMap;

    static private final Random random = new Random();


    public SocialNetwork(){

        UserList = new HashSet<>();
        SocialMap = new HashMap<>();
        PostMap = new HashMap<>();
        FollowMap = new HashMap<>();

    }

    /**
     *
     * EFFECTS: restituisce una lista contenente tutti i post presenti in SocialMap
     *
     * */
    public List<Post> getAllPosts(){

        List<Post> PostList = new ArrayList<>();

        for (String user : UserList) {
            List<Post> i = SocialMap.get(user);
            PostList.addAll(i);
        }

        return PostList;
    }

    /**
     *
     * REQUIRES: postList != null
     * THROWS: NullPointerException se postList == null
     * EFFECTS: utilizza la lista di post passata come argomento per creare una hashmap che rappresenta
     * la rete sociale degli utenti relativa alla lista
     *
     * */
    public static Map<String, Set<String>> guessFollowers(List<Post> postList) throws NullPointerException {

        if (postList == null) {
            throw new NullPointerException();
        }

        Map<String, Set<String>> followers = new HashMap<>();

        for (Post p : postList){

            followers.putIfAbsent(p.getAuthor(), new HashSet<>());

            followers.get(p.getAuthor()).addAll(p.getLikes());

        }

        return followers;

    }

    /**
     *
     * EFFECTS: restituisce la lista degli utenti più influenti (con più followers) del SocialNetwork this, in ordine decrescente
     *
     * */
    public List<String> influencers(){
        return influencers(FollowMap);
    }

    /**
     *
     * REQUIRES: followers != null
     * THROWS: NullPointerException se followers == null
     * EFFECTS: restituisce la lista degli utenti più influenti (con più followers) relativa alla mappa passata come
     * argomento e che associa ogni utente alla lista degli utenti seguiti, in ordine decrescente
     *
     * */
    public static List<String> influencers(Map<String, Set<String>> followMap) throws NullPointerException {

        if (followMap == null) {
            throw new NullPointerException();
        }

        //Lista di output
        List<String> influencers = new ArrayList<>();

        //Mappa tra utenti e numero di followers
        Map<String, Integer> followers = new HashMap<>();

        for( Map.Entry<String,Set<String>> userFollows : followMap.entrySet() ) {

            String follower = userFollows.getKey();
            Set<String> followed = userFollows.getValue();

                for (String user : followed){

                    followers.putIfAbsent(user, 0);

                    followers.put(user,followers.get(user)+1);

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

    /**
     *
     * EFFECTS: restituisce la lista degli utenti che hanno scritto almeno un post nel SocialNetwork this
     *
     * */
    public Set<String> getMentionedUsers(){
        return UserList;
    }

    /**
     *
     *REQUIRES: postList != null
     *THROWS: NullPointerException se postList == null
     *EFFECTS: restituisce la lista degli utenti che hanno scritto un post contenuto nella lista postList
     *
     */
    public static Set<String> getMentionedUsers(List<Post> postList) throws NullPointerException {

        if (postList == null) {
            throw new NullPointerException();
        }

        Set<String> users = new HashSet<>();

        for (Post post : postList){
            users.add(post.getAuthor());
        }

        return users;
    }

    /**
     *
     * REQUIRES: username != null
     * THROW: NullPointerException se username == null
     * EFFECTS: restituisce la lista dei post scritti da username presenti in SocialMap
     *
     * */
    public List<Post> writtenBy(String username) throws NullPointerException {
        if (username == null) {
            throw new NullPointerException();
        }
        return SocialMap.get(username);
    }

    /**
     *
     * REQUIRES: username != null ^ postList != null
     * THROW: NullPointerException se username == null v postList == null
     * EFFECTS: restituisce la lista dei post scritti da username presenti in postList
     *
     * */
    public static List<Post> writtenBy(List<Post> postList, String username) throws NullPointerException {

        if (postList == null || username == null) {
            throw new NullPointerException();
        }

        List<Post> filteredList = new ArrayList<>();

        for (Post post : postList){
            if (post.getAuthor().equals(username)){
                filteredList.add(post);
            }
        }

        return filteredList;
    }

    /**
     *
     *REQUIRES: words != null ^ words.lenght() > 0
     *THROWS: NullPointerException se words == null, IllegalArgumentException se words.lenght() == 0
     *EFFECTS: restituisce la lista dei post presenti nel SocialNetwork in cui appare almeno una delle parole contenuta in Words
     *
     */
    public List<Post> containing(List<String> words) throws NullPointerException, IllegalArgumentException {

        if (words == null) {
            throw new NullPointerException();
        }
        if (words.size() == 0){
            throw new IllegalArgumentException();
        }

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

    /**
     *
     * REQUIRES: currentUser != null ^ text != null ^ text,currentUser not blank ^ 0 > text.lenght() > 140
     * THROWS: NullPointerException se currentUser == null v text == null, Illegal Argument Exception se sono vuoti,
     * LimitExceededException se text.lenght() > 140
     * MODIFIES: this
     * EFFECTS: genera l'id univoco e crea il dato Post, se è il primo post dell'autore lo aggiunge alla UserList
     * e inizializza le entry di SocialMap e FollowMap corrispondenti, infine lo inserisce nelle map SocialMap e PostMap di this.
     *
     * */
     public void createPost(String currentUser, String text) throws NullPointerException, IllegalArgumentException, LimitExceededException {

        if (currentUser == null || text == null){
            throw new NullPointerException();
        }

        if (currentUser.trim().isEmpty() || text.trim().isEmpty()) {
            // il nome dell'autore e il messaggio non possono essere vuoti nè contenere solo spazi
            throw new IllegalArgumentException();
        }

        if (text.length() > 140){
            throw new LimitExceededException();
        }

        int id = random.nextInt(Integer.MAX_VALUE - 1 );

        while (PostMap.get(id) != null){
            id = random.nextInt(Integer.MAX_VALUE - 1 );
        }

        Post temp = new Post(id,currentUser,text);

        //condizione vera se l'utente non ha mai postato
        //falsa se è già registrato in UserList
        if (UserList.add(currentUser)) {
            //se user ha messo like, le map sono già inizializzate
            if (!SocialMap.containsKey(currentUser)) {
                //inizializzo le entry corrispondenti all'utente
                SocialMap.put(currentUser, new ArrayList<>());
                FollowMap.put(currentUser, new HashSet<>());
            }
            
        }
        //aggiungo il post alla lista corrispondente all'utente in SocialMap
        SocialMap.get(currentUser).add(temp);

        //aggiungo il post alla HashMap usando l'id come chiave
        PostMap.put(id,temp);

    }

    /**
     *
     * REQUIRES: user != null ^ id > 0 ^ PostMap.get(id) != null ^ PostMap.get(id).getAuthor != user
     * THROWS: NullPointerException se user == null, IllegalArgumentException se id <= 0,
     * IllegalStateException se user.equals(PostMap.get(id).getAuthor())
     * EFFECTS: se il post corrispondente all'id passato è presente nel SocialNetwork, viene messo un like al post da
     * parte dall'utente corrente e viene aggiornata la mappa delle relazioni. Se l'utente non ha mai postato, vengono
     * inizializzate le relative entry in SocialMap e FollowMap.
     * MODIFIES: this
     *
     * */
    public void likePost(String user, int id) throws NullPointerException, IllegalArgumentException, IllegalStateException {

        if (user == null){
            throw new NullPointerException();
        }

        if (id <= 0 ){
            throw new IllegalArgumentException();
        }

        Post post = PostMap.get(id);

        if (post == null){
            System.out.print("Permission denied: post id not found");
            return;
        }

        if (post.getAuthor().equals(user)){
            throw new IllegalStateException();
        }

        //se user non ha ancora pubblicato un post, inizializzo le entry delle map
        if (!UserList.contains(user)){
            SocialMap.put(user, new ArrayList<>());
            FollowMap.put(user, new HashSet<>());
        }

        //aggiungo user ai like di Post
        if (post.addLike(user)) {
            //aggiungo l'autore del post alla mappa dei seguiti di user (solo se il like non è già presente)
            FollowMap.get(user).add(post.getAuthor());
        }

    }

}
