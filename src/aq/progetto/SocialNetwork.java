package aq.progetto;

import aq.progetto.interfaces.ISocialBlog;

import javax.naming.LimitExceededException;
import java.util.*;
import java.util.stream.Collectors;

import static aq.progetto.Post.textCheck;
import static aq.progetto.Post.usernameCheck;

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
     *  f: user -> {users} | f(user_1) = {user_i, ..., user_j}, ..., f(user_n) = {user_k, ..., user_h}
     *  f: user -> {posts} | f(user_1) = {post_i, ..., post_j}, ..., f(user_n) = {post_k, ..., post_h}
     *  f: id -> post |  f(id_1) -> post_1, ..., f(id_n) -> post_n
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
    protected final Map<String, List<Post>> SocialMap;

    //Map tra id e post corrispondente per accesso veloce O(1)
    protected final Map<Integer, Post> PostMap;

    //Map tra ogni utente e corrispondente lista di utenti seguiti
    private final Map<String, Set<String>> FollowMap;

    //Identificatore unico dell'ultimo post pubblicato
    private int lastId;

    public SocialNetwork(){

        UserList = new HashSet<>();
        SocialMap = new HashMap<>();
        PostMap = new HashMap<>();
        FollowMap = new HashMap<>();
        lastId = 0;

    }

    /**
     *
     * EFFECTS: restituisce l'id dell'ultimo post pubblicato sul social network
     */
    public int getLastId() {
        return this.lastId;
    }

    /**
     *
     * EFFECTS: restituisce una lista contenente tutti i post presenti in SocialMap
     *
     * */
    public List<Post> getAllPosts(){

        List<Post> PostList = new ArrayList<>();

        for (String user : UserList) {
            List<Post> i = writtenBy(user);
            PostList.addAll(i);
        }

        PostList.sort(null);

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
            throw new NullPointerException("Argument was null");
        }

        //mappa tra utenti e followers
        Map<String, Set<String>> followers = new HashMap<>();

        //per ogni post nella lista, estraggo gli utenti che hanno messo like/seguito un altro
        for (Post p : postList){

            //inizializza il set dei followers di author se necessario
            followers.putIfAbsent(p.getAuthor(), new HashSet<>());
            //aggiunge i follower alla entry di author
            followers.get(p.getAuthor()).addAll(p.getLikes());

        }

        return followers;

    }

    /**
     *
     * EFFECTS: crea una hashmap che rappresenta la rete sociale degli utenti relativa all'intero SocialNetwork
     *
     * */
    public Map<String, Set<String>> guessFollowers(){
        return guessFollowers(getAllPosts());
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
            throw new NullPointerException("Argument was null");
        }

        //Mappa tra utenti e numero di followers
        Map<String, Integer> followers = new HashMap<>();

        //navigo fra le entry della followMap, mappa tra utenti e set di utenti seguiti, per costruire followers
        for ( Map.Entry<String,Set<String>> userFollows : followMap.entrySet() ){

            //per ogni entry di FollowMap, prendo gli utenti seguiti
            Set<String> followed = userFollows.getValue();

            //safeguard in caso di mappe non correttamente inizializzate
            if (followed == null){
                continue;
            }

            //ogni utente seguito viene inizializzato nella mappa e viene incrementato il suo counter di followers
            for (String user : followed){

                followers.putIfAbsent(user, 0);
                followers.put(user,followers.get(user)+1);

            }

        }

        //converto followers in una Lista di coppie <String, Integer> per poterla ordinare
        List<Map.Entry<String, Integer>> sortedEntryList =  new ArrayList<>(followers.entrySet());

        //ordino la lista per ordine decrescente di follower
        sortedEntryList.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));

        //infine la converto nella lista di output con i primi 10 influencer.
        List<String> influencers = sortedEntryList.stream().limit(11).map(Map.Entry::getKey).collect(Collectors.toList());

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
            throw new NullPointerException("Parameter was null");
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
            throw new NullPointerException("username was null");
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
            throw new NullPointerException("One or both arguments are null");
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
            throw new NullPointerException("words filter was null");
        }
        if (words.size() == 0){
            throw new IllegalArgumentException("words filter was empty");
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
            throw new NullPointerException("text or user are null");
        }

        if (currentUser.trim().isEmpty() || text.trim().isEmpty()) {
            // il nome dell'autore e il messaggio non possono essere vuoti nè contenere solo spazi
            throw new IllegalArgumentException("text or user are blank");
        }

        if (text.length() > 140){
            throw new LimitExceededException("text limit for posts is 140");
        }

        int id = ++lastId;

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
     * THROWS: NullPointerException se user == null, IllegalArgumentException se id <= 0 v PostMap.containsKey(id) == false v user is blank,
     * IllegalStateException se user.equals(PostMap.get(id).getAuthor())
     * EFFECTS: se il post corrispondente all'id passato è presente nel SocialNetwork, viene messo un like al post da
     * parte dall'utente corrente e viene aggiornata la mappa delle relazioni. Se l'utente non ha mai postato, vengono
     * inizializzate le relative entry in SocialMap e FollowMap.
     * MODIFIES: this
     *
     * */
    public void likePost(String user, int id) throws NullPointerException, IllegalArgumentException, IllegalStateException {

        //controllo che user non sia nè null nè vuoto
        usernameCheck(user);

        if ( id <= 0 || !PostMap.containsKey(id) ){
            throw new IllegalArgumentException("id given not valid");
        }

        Post post = PostMap.get(id);

        if (post.getAuthor().equals(user)){
            throw new IllegalStateException("Users can't like/follow themselves");
        }

        //se user non ha ancora pubblicato nè seguito un post, inizializzo le entry delle map
        if (!(UserList.contains(user) || FollowMap.containsKey(user))){
            SocialMap.put(user, new ArrayList<>());
            FollowMap.put(user, new HashSet<>());
        }

        //aggiungo user ai like di Post
        if (post.addLike(user)) {
            //aggiungo l'autore del post alla mappa dei seguiti di user (solo se il like non è già presente)
            FollowMap.get(user).add(post.getAuthor());
        }

    }

    /**
     *
     * REQUIRES: id > 0 ^ user != null ^ newText != null ^ user not blank ^ newText not blank ^ PostMap.containsKey(id) ^ user == PostMap.get(id).getAuthor()
     * THROWS: NullPointerException se user == null v newText == null,
     * IllegalArgumentException se id <= 0 v user o newText sono composti solo da spazi,
     * LimitExceededException se newText.lenght() > 140,
     * IllegalStateException se user != PostMap.get(id).getAuthor()
     * EFFECTS: modifica il testo del post relativo a id in newText se l'utente è l'autore del post e newText rispetta il formato
     *
     * */
    public void editPost(String user, int id, String newText) throws LimitExceededException, IllegalStateException, IllegalArgumentException, NullPointerException {

        //controllo la validità di user e newText
        if ( usernameCheck(user) && textCheck(newText) ) {

            //controllo che l'id sia valido ed esista un post correlato
            if (id <= 0 || !PostMap.containsKey(id)) {
                throw new IllegalArgumentException("id not valid");
            }

        }

        Post post = PostMap.get(id);

        // di base solo l'autore può modificare i propri post
        if (authorizePostAction(user,post)){

            post.editText(user,newText);

        }else{

            throw new IllegalStateException("only the author can modify their posts");
        }

    }

    /**
     *
     * REQUIRES: post != null ^ user != null
     * EFFECTS: true se user è l'autore di Post, false altrimenti
     *
     * */

    protected boolean authorizePostAction(String user, Post post) {
        return user.equals(post.getAuthor());
    }

}
