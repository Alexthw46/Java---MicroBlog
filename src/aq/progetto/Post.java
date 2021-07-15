package aq.progetto;

import javax.naming.LimitExceededException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class Post {

    final int id;
    String author;
    String text;
    List<String> likes;
    Timestamp creationTime;


    public Post(int id, String author, String text) throws NullPointerException, LimitExceededException, IllegalArgumentException {
        if (author == null || text == null) {
            throw new NullPointerException();
        }

        if (id < 0 || author.trim().isEmpty() || text.trim().isEmpty()) {
            // l'id non può essere minore di 0;
            // il nome dell'autore e il messaggio non possono essere vuoti nè contenere solo spazi
            throw new IllegalArgumentException();
        }

        if (text.length() > 140) {
            // limite di caratteri del post
            throw new LimitExceededException();
        }

        this.id = id;
        this.likes = new ArrayList<>();
        this.author = author;
        if (text.length() < 140){
            this.text =text;
        }
        this.creationTime = new Timestamp(System.currentTimeMillis());
    }

    int getId(){
        return this.id;
    }

    String getAuthor(){
        return this.author;
    }

    List<String> getLikes(){
        return this.likes;
    }

    public void addLike(String user) throws NullPointerException, IllegalArgumentException, IllegalStateException {
        if(user == null) {
            throw new NullPointerException();
        }
        if(user.trim().isEmpty()) {
            throw new IllegalArgumentException();
        }
        if(user.compareTo(this.author) == 0) {
            throw new IllegalStateException();
        }

        // il like non viene messo se già presente

        if (!likes.contains(user)) {
            this.likes.add(user);
        }
    }
    // EFFECTS: restituisce una rappresentazione dell'istanza (this) come stringa
    public String toString() {
        return "\"" +
                this.text +
                "\" - " +
                this.author +
                ", " +
                this.creationTime.toString() +
                ", " +
                this.id;
    }

}
