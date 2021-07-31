package aq.progetto;

import aq.progetto.interfaces.IPost;

import javax.naming.LimitExceededException;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

public class Post implements IPost, Comparable<Post> {

    private final int id;
    private final String author;
    private String text;
    private final Set<String> likes;
    private final Timestamp creationTime;

    /**
     *ABSTRACTION FUNCTION:
     *
     * α(C) = (C.id, C.author, C.text, System.currentTimeMillis(), {C.likes.get(i) | 0 ≤ i < c.likes.length()})
     *
     * TYPICAL ELEMENT:
     *
     * (id, author, text, creationTime, [like_1, ..., like_n])
     *  Il tipo di dato Post è una quintupla il cui quinto elemento è un set di stringhe contenente gli utenti che
     *  hanno messo like a this
     *
     * REP INVARIANT:
     *
     * this.id > 0 ∧
     * this.author ≠ null ∧ this.author.length > 0 ^ this.author not blank ^
     * this.text ≠ null ∧ 0 < this.text.length ≤ 140 ∧ this.text not blank ^
     * this.creationTime != null ^
     * this.likes != null ^ ∀ like ∈ this.likes : ( like != null ^ like.lenght !=0 ^ like not blank ^ like != author) ^
     * ∀ i,j ∈ (0, this.likes.lenght()) : (this.likes(i) == this.likes(j) <==> i == j)
     *
     */

    public Post(int id, String author, String text) throws NullPointerException, LimitExceededException, IllegalArgumentException {

        //controllo i dati di input e sollevo eccezioni se necessario
        if (usernameCheck(author) && textCheck(text)) {

            if ( id <= 0 ) {
                // l'id deve essere maggiore di 0;
                throw new IllegalArgumentException("id was zero or negative");
            }

        }

        //inizializzo il post con i dati in input e il tempo di creazione
        this.id = id;
        this.text = text;
        this.author = author;
        this.creationTime = new Timestamp(System.currentTimeMillis());
        this.likes = new HashSet<>();

    }


    /**
     *EFFECTS: restituisce l'identificatore univoco del post
     */
    public int getId(){
        return this.id;
    }

    /**
     *EFFECTS: restituisce l'autore del post
     */
    public String getAuthor(){
        return this.author;
    }

    /**
     *EFFECTS: restituisce il testo del post
     */
    public String getText(){
        return this.text;
    }

    /**
     * THROWS: NullPointerException se username == null,
     *  IllegalArgumentException se username è composto solo da spazi o vuoto
     *
     * EFFECTS: Controlla se il parametro è valido per l'uso con il dato Post
     *
     */
    public static boolean usernameCheck(String username) throws NullPointerException, IllegalArgumentException {
        if (username == null) {
            // il nome dell'autore non può essere null
            throw new NullPointerException("username was null");
        }
        if (username.trim().isEmpty()) {
            // il nome dell'autore non può essere vuoto nè contenere solo spazi
            throw new IllegalArgumentException("username was blank");
        }

        return true;
    }

    /**
     * THROWS: NullPointerException se text == null,
     *  IllegalArgumentException se text è composto solo da spazi o vuoto,
     *  LimitExceededException se text è composto da più di 140 caratteri
     * EFFECTS: Controlla se il parametro è valido per l'uso con il dato Post
     *
     */
    public static boolean textCheck(String text) throws LimitExceededException, IllegalArgumentException, NullPointerException {

        if (text == null) {
            // text non può essere null
            throw new NullPointerException("text was null");
        }

        if (text.trim().isEmpty()) {
            // il messaggio non può essere vuoto nè contenere solo spazi
            throw new IllegalArgumentException("text was blank");
        }

        if (text.length() > 140) {
            // limite di caratteri del post
            throw new LimitExceededException("text limit of 140 chars exceeded");
        }
        return true;
    }

    /**
     *
     * REQUIRES: user != null ^ newText != null ^ user not blank ^ newText not blank ^ user == this.author
     * THROWS: NullPointerException se user == null v newText == null,
     * IllegalArgumentException se user o newText sono composti solo da spazi,
     * LimitExceededException se newText.lenght() > 140,
     * EFFECTS: modifica this.text in newText se l'utente è l'autore del post e newText rispetta il formato
     *
     * */
    public void editText(String user, String newText) throws LimitExceededException, NullPointerException, IllegalArgumentException {

        if ( usernameCheck(user) && textCheck(newText) ) this.text = newText;

    }

    /**
     *EFFECTS: restituisce la data e l'ora in cui e' stato scritto il post
     */
    public Timestamp getTimestamp() {
        return this.creationTime;
    }

    /**
     *EFFECTS: restituisce la lista di persone che hanno messo il like (seguono) il post
     */
    public Set<String> getLikes(){
        return this.likes;
    }

    /**
     *REQUIRES: user != null
     *THROWS: NullPointerException se user == null,
     *  IllegalArgumentException se user è composto solo da spazi,
     *  IllegalStateException se user sta tentando di mettere like a sè stesso
     *MODIFIES: this.likes
     *EFFECTS: aggiunge user alla lista dei likes di this se user != this.author, se user è già presente in likes invece
     * viene rimosso
     * @return true se il like viene aggiunto, false se viene rimosso
     */
    public boolean toggleLike(String user) throws NullPointerException, IllegalArgumentException, IllegalStateException {

        usernameCheck(user);

        if (user.equals(this.author)) {
            throw new IllegalStateException("users can't follow themselves");
        }

        // il like viene rimosso se già presente
        if (likes.contains(user)) {
            this.likes.remove(user);
            return false;
        } else {
            this.likes.add(user);
            return true;
        }

    }

    /**
     *REQUIRES: true
     *EFFECTS: restituisce i dati del post in formattati in una stringa
     */
    public String toString() {
        return getAuthor() +
                " wrote: \n\"" +
                getText() +
                "\"\n" +
                "at " +
                this.creationTime.toString() +
                "\nID: " +
                getId() + "\n";
    }

    /**
     *
     * EFFECTS: if this.id > otherPost.getId() returns 1 else 0, for ordering Post elements
     *
     * */
    @Override
    public int compareTo(Post otherPost) {
        return Integer.compare(this.id, otherPost.getId());
    }

}
