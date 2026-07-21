package it.unina.uninamulticloud.dao;

import it.unina.uninamulticloud.entity.Utente;

public interface UtenteDAO {
    //Optional<Utente> findByMatricola(String matricola);
    //Optional<Utente> findByUsernameAndPassword(String username, String password);
    //List<Utente> findAll();
    //void insert(Utente utente);
    //void update(Utente utente);
    //void delete(String matricola);

    //Utente findByUsernameAndPassword(String email, String password);

   Utente findByEmail(String email);

   boolean existsByEmail(String email);

   boolean existsByUsername(String username);

   boolean existsByMatricola(String matricola);

   void saveUtente(Utente utente);

}
