package aq.progetto;

import javax.naming.LimitExceededException;
import java.util.*;

import static aq.progetto.Post.textCheck;
import static aq.progetto.Post.usernameCheck;

public class SocialNetworkWithReport extends SocialNetwork{

    //Set degli utenti con permessi di moderazione (modifica ed eliminazione di qualsiasi post)
    private final Set<String> admins;

    //Mappa delle segnalazioni
    private final Map<Integer, List<Report>> ReportsMap;

    /*
     *TYPICAL ELEMENT:
     * (
     *  {user_1, ..., user_n}
     *  f: user -> {users} | f(user_1) = {user_i, ..., user_j}, ..., f(user_n) = {user_k, ..., user_h}
     *  f: user -> {posts} | f(user_1) = {post_i, ..., post_j}, ..., f(user_n) = {post_k, ..., post_h}
     *  f: id -> post |  f(id_1) = post_1, ..., f(id_n) = post_n
     *  f: id -> {reports} | f(id_1) = {report_i, ..., report_j}, ..., f(id_n) = {report_k, ..., report_h}
     *  {admin_1, ..., admin_n}
     * )
     */

    /**
     *
     * REQUIRES: administrator != null ^ administrator not blank
     * THROWS: NullPointerException se administrator == null,
     * IllegalArgumentException se administrator is blank
     *
     * */
    public SocialNetworkWithReport(String administrator) throws NullPointerException, IllegalArgumentException {
        this();
        usernameCheck(administrator);
        admins.add(administrator);
    }

    public SocialNetworkWithReport(){
        super();
        admins = new HashSet<>();
        ReportsMap = new HashMap<>();
    }

    /**
     *
     * REQUIRES: currentUser,newAdmin != null, currentUser,newAdmin not blank
     * THROWS: NullPointerException se currentUser == null v newAdmin == null,
     * IllegalArgumentException se currentUser is blank v newAdmin is blank
     * SecurityException se currentUser !∈ admins
     * EFFECTS: aggiunge newAdmin alla lista degli admin del SocialNetwork, l'operazione può essere richiesta solo da
     * un utente già nel gruppo admin
     *
     * */
    public void addAdmin(String currentUser, String newAdmin ){

        usernameCheck(currentUser);
        usernameCheck(newAdmin);

        if (admins.contains(currentUser)){
            admins.add(newAdmin);
        }else{
            throw new SecurityException("Permission denied, user not admin");
        }

    }

    /**
     *
     * EFFECTS: Restituisce la lista contenente i nomi degli admin del SocialNetwork
     *
     * */
    public Set<String> getAdmins() {
        return admins;
    }

    /**
     *
     * REQUIRES: currentUser != null ^ text != null ^ text,currentUser not blank ^ 0 > text.lenght() > 140
     * THROWS: NullPointerException se username == null v reason == null,
     * Illegal Argument Exception se sono vuoti v id <= 0 v id !∈ PostMap,
     * LimitExceededException se reason.lenght() > 140
     * MODIFIES: this.ReportsMap
     * EFFECTS: Crea una segnalazione relativa al post corrispondente a id e la aggiunge
     * */
    public void reportPost(String username, int id, String reason) throws LimitExceededException, NullPointerException, IllegalArgumentException {

        usernameCheck(username);
        textCheck(reason);

        //controllo id
        if ( id <= 0 || !PostMap.containsKey(id) ){
            throw new IllegalArgumentException("id given not valid");
        }

        //aggiungo la segnalazione alla lista relativa al post
        ReportsMap.putIfAbsent(id, new ArrayList<>());
        ReportsMap.get(id).add(new Report(username,reason));

    }

    /**
     *
     * REQUIRES: username != null ^ username not null
     * THROWS: NullPointerException se username == null,
     * IllegalArgumentException se username è composto solo da spazi o vuoto
     * SecurityException se username !∈ admin
     * EFFECTS: Stampa la lista delle segnalazioni per ogni post, strumento per gli admin
     *
     * */
    public void listReports(String username){

        usernameCheck(username);

        //controllo permessi
        if (admins.contains(username)){

            for (Map.Entry<Integer,List<Report>> PostReports : ReportsMap.entrySet()){

                System.out.print("Reported post #"+ PostReports.getKey() + " by " + PostReports.getValue().size() +" users\n");

                for (Report rep : PostReports.getValue()){
                    System.out.print("Reported by " + rep.reporter + "\nreason : " + rep.reason +"\n" );
                }

            }

        } else{
            throw new SecurityException("Permission denied");
        }

    }


    /**
     *
     * REQUIRES: user != null ^ user not blank ^ id > 0
     * THROWS: NullPointerException se user == null,
     * IllegalArgumentException se user is blank v id <= 0 v id !∈ PostMap
     * EFFECTS: se user è autore del post o amministratore, elimina il Post corrispondente a id da SocialMap e PostMap
     * elimina anche le segnalazioni relative al post
     * MODIFIES: this
     *
     * */
    public void deletePost(String user, int id) throws NullPointerException, IllegalArgumentException, SecurityException {

        usernameCheck(user);

        if (id <= 0 || !PostMap.containsKey(id)){
            throw new IllegalArgumentException("id not valid");
        }

        Post post = PostMap.get(id);

        if (authorizePostAction(user, post)) {
            PostMap.remove(id);
            SocialMap.get(post.getAuthor()).remove(post);
            ReportsMap.remove(id);
        }else {
            throw new SecurityException();
        }

    }

    /**
     *
     * REQUIRES: post != null ^ user != null ^ user not blank
     * THROWS: NullPointerException se user == null v post == null,
     * IllegalArgumentException se user is blank
     * EFFECTS: true se user è l'autore di Post o user ∈ this.admins , false altrimenti
     *
     * */
    @Override
    protected boolean authorizePostAction(String user, Post post) {
        usernameCheck(user);
        if (post == null){
            throw new NullPointerException("post was null");
        }
        return user.equals(post.getAuthor()) || admins.contains(user);
    }

    /**
     *
     * TYPICAL ELEMENT: (reporter, reason)
     * OVERVIEW: Tipo di dato usato per memorizzare le segnalazioni degli utenti, memorizza l'autore della segnalazione
     * e le motivazioni per cui il post dovrebbe essere rimosso/censurato da un admin
     *
     * */
    private static class Report{

        public final String reporter;
        public final String reason;

        public Report(String author, String reason) throws LimitExceededException {

            usernameCheck(author);
            textCheck(reason);

            reporter = author;
            this.reason = reason;

        }

    }

}
