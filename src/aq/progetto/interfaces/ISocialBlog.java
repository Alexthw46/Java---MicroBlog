package aq.progetto.interfaces;

import aq.progetto.Post;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface ISocialBlog {

    /**
     *
     * REQUIRES: postList != null
     * THROWS: NullPointerException se postList == null
     * EFFECTS: restituisce una hashmap che rappresenta
     * la rete sociale degli utenti relativa alla lista di post passata come argomento
     *
     */
    static Map<String, Set<String>> guessFollowers(List<Post> ps) throws NullPointerException {
        throw new UnsupportedOperationException();
    }

    /**
     *
     * REQUIRES: followers != null
     * THROWS: NullPointerException se followers == null
     * EFFECTS: restituisce la lista degli utenti più influenti (con più followers) relativa alla mappa passata come
     * argomento e che associa ogni utente alla lista degli utenti seguiti, in ordine decrescente
     *
     */
    static List<String> influencers(Map<String, Set<String>> followers) throws NullPointerException {
        throw new UnsupportedOperationException();
    }

    /**
     *
     * EFFECTS: restituisce la lista degli utenti che hanno scritto almeno un post nel SocialNetwork this
     *
     * */
    Set<String> getMentionedUsers();

    /**
     *
     *REQUIRES: postList != null
     *THROWS: NullPointerException se postList == null
     *EFFECTS: restituisce la lista degli utenti che hanno scritto un post contenuto nella lista postList
     *
     */
    static Set<String> getMentionedUsers(List<Post> postList) throws NullPointerException {
        throw new UnsupportedOperationException();
    }

    /**
     *
     *REQUIRES: username != null
     *THROWS: NullPointerException se username == null
     *EFFECTS: restituisce una lista contenente tutti i post scritti da username presenti nel SocialNetwork
     *
     */
    List<Post> writtenBy(String username) throws NullPointerException;

    /**
     *
     * REQUIRES: username != null ^ postList != null
     * THROW: NullPointerException se username == null v postList == null
     * EFFECTS: restituisce una lista contenente tutti i post scritti da username presenti nella lista ps
     *
     */
    static List<Post> writtenBy(List<Post> postList, String username) throws NullPointerException, IllegalArgumentException {
        throw new UnsupportedOperationException();
    }

    /**
     *
     *REQUIRES: words != null ^ words.lenght() > 0
     *THROWS: NullPointerException se words == null, IllegalArgumentException se words.lenght() == 0
     *EFFECTS: restituisce la lista dei post presenti nel SocialNetwork in cui appare almeno una delle parole contenuta in Words
     *
     */
    List<Post> containing(List<String> words) throws NullPointerException, IllegalArgumentException;






}
