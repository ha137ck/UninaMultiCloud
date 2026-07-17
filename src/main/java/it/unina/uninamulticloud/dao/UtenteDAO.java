package it.unina.uninamulticloud.dao;

import it.unina.uninamulticloud.entity.Utente;

import java.util.List;
import java.util.Optional;

public interface UtenteDAO {
    Optional<Utente> findByMatricola(String matricola);
    //Optional<Utente> findByUsernameAndPassword(String username, String password);
    //List<Utente> findAll();
    //void insert(Utente utente);
    //void update(Utente utente);
    //void delete(String matricola);
}
