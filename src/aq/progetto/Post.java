package aq.progetto;

import aq.progetto.interfaces.IPost;

import javax.naming.LimitExceededException;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

public class Post implements IPost {

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
     *  Il tipo di dato Post è una quintupla il cui quinto elemento è una lista di stringhe contenente gli utenti che
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

        if (author == null || text == null) {
            throw new NullPointerException("author or text was null");
        }

        if (id <= 0 || author.trim().isEmpty() || text.trim().isEmpty()) {
            // l'id deve essere maggiore di 0;
            // il nome dell'autore e il messaggio non possono essere vuoti nè contenere solo spazi
            throw new IllegalArgumentException("author or text was blank or id was zero or negative");
        }

        if (text.length() > 140) {
            // limite di caratteri del post
            throw new LimitExceededException("text limit of 140 chars exceeded");
        }

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
     *
     * REQUIRES: user != null ^ newText != null ^ user not blank ^ newText not blank ^ user == this.author
     * THROWS: NullPointerException se user == null v newText == null,
     * IllegalArgumentException se user o newText sono composti solo da spazi,
     * LimitExceededException se newText.lenght() > 140,
     * SecurityException se user != this.author
     * EFFECTS: modifica this.text in newText se l'utente è l'autore del post e newText rispetta il formato
     *
     * */
    public void editText(String user, String newText) throws LimitExceededException, NullPointerException, IllegalArgumentException {

        if (user == null || newText == null) {
            throw new NullPointerException("user or new text was null");
        }

        if (user.trim().isEmpty() || newText.trim().isEmpty()) {
            // l'id deve essere maggiore di 0;
            // il nome dell'autore e il messaggio non possono essere vuoti nè contenere solo spazi
            throw new IllegalArgumentException("author or text was blank");
        }

        if (newText.length() > 140) {
            // limite di caratteri del post
            throw new LimitExceededException("text limit of 140 chars exceeded");
        }

        this.text = newText;

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
     *EFFECTS: aggiunge user alla lista dei likes di this se user != this.author e user non è già presente in likes
     * @return false se user era già presente in this.likes, else true
     */
    public boolean addLike(String user) throws NullPointerException, IllegalArgumentException, IllegalStateException {

        if (user == null) {
            throw new NullPointerException("user was null");
        }

        if (user.trim().isEmpty()) {
            throw new IllegalArgumentException("user was blank");
        }

        if (user.equals(this.author)) {
            throw new IllegalStateException("users can't follow themselves");
        }

        // il like non viene messo se già presente
        if (likes.contains(user)) return false;

        this.likes.add(user);

        return true;
    }

    /**
     *REQUIRES: true
     *EFFECTS: restituisce i dati del post in formattati in una stringa
     */
    public String toString() {
        return getAuthor() +
                " wrote: \n" +
                getText() +
                "\n" +
                " at " +
                this.creationTime.toString() +
                "\nID: " +
                getId();
    }

}
