package it.unina.uninamulticloud.dao;

import it.unina.uninamulticloud.entity.Universita;
import java.util.List;

public interface UniversitaDAO {

    Universita findById(long idUniversita);

    List<Universita> findAll();

    void save(Universita universita);
}
