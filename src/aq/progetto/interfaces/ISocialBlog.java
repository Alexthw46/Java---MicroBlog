package aq.progetto.interfaces;

import aq.progetto.Post;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface ISocialBlog {

    /**
     *REQUIRES: true
     *THROWS:
     *EFFECTS:
     *MODIFIES:
     */
    static Map<String, Set<String>> guessFollowers(List<Post> ps) throws NullPointerException {
        throw new UnsupportedOperationException();
    }

    /**
     *REQUIRES: true
     *THROWS:
     *EFFECTS:
     *MODIFIES:
     */
    static List<String> influencers(Map<String, Set<String>> followers) throws NullPointerException {
        throw new UnsupportedOperationException();
    }

    Set<String> getMentionedUsers();


    static Set<String> getMentionedUsers(List<Post> ps) throws NullPointerException {
        throw new UnsupportedOperationException();
    }

    List<Post> writtenBy(String username);

    static List<Post> writtenBy(List<Post> ps, String username) throws NullPointerException, IllegalArgumentException {
        throw new UnsupportedOperationException();
    }

    List<Post> containing(List<String> words) throws NullPointerException, IllegalArgumentException;






}
