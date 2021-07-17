package aq.progetto.interfaces;

import java.sql.Timestamp;

public interface IPost {

    /**
    *REQUIRES: true
    *EFFECTS: restituisce l'identificatore univoco del post
    *MODIFIES:
    */
    int getId();

    /**
    *REQUIRES: true
    *EFFECTS: restituisce l'autore del post
    *MODIFIES:
    */
    String getAuthor();

    /**
    *REQUIRES: true
    *THROWS:
    *EFFECTS: restituisce il testo del post
    *MODIFIES:
    */
    String getText();

    /**
    *REQUIRES: true
    *THROWS:
    *EFFECTS: restituisce la data e l'ora in cui e' stato scritto il post
    *MODIFIES:
    */
    Timestamp getTimestamp();

    /**
    *REQUIRES: true
    *THROWS:
    *EFFECTS: restituisce i dati del post in formattati in una stringa
    *MODIFIES:
    */
    String toString();
}
