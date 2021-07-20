package aq.progetto.interfaces;

import java.sql.Timestamp;
import java.util.Set;

public interface IPost {

    /**
    *REQUIRES: true
    *EFFECTS: restituisce l'identificatore univoco del post
    */
    int getId();

    /**
    *REQUIRES: true
    *EFFECTS: restituisce l'autore del post
    */
    String getAuthor();

    /**
    *REQUIRES: true
    *EFFECTS: restituisce il testo del post
    */
    String getText();

    /**
    *REQUIRES: true
    *EFFECTS: restituisce la data e l'ora in cui e' stato scritto il post
    */
    Timestamp getTimestamp();

    /**
    *REQUIRES: true
    *EFFECTS: restituisce i dati del post in formattati in una stringa
    */
    String toString();

    /**
     *REQUIRES: true
     *EFFECTS: restituisce la lista di persone che hanno messo il like (seguono) il post
     */
    Set<String> getLikes();

}
