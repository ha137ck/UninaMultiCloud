package it.unina.uninamulticloud.entity;

import it.unina.uninamulticloud.entity.enums.Genere;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Utente {
    private String matricola;
    private String nome;
    private String cognome;
    private String username;
    private String password;
    private String email;
    private LocalDate dataNascita;
    private LocalDateTime dataIscrizione;
    private Genere genere;

    public Utente(String matricola, String nome, String cognome, String username, 
                  String password, String email, LocalDate dataNascita, 
                  LocalDateTime dataIscrizione, Genere genere) {
        this.matricola = matricola;
        this.nome = nome;
        this.cognome = cognome;
        this.username = username;
        this.password = password;
        this.email = email;
        this.dataNascita = dataNascita;
        this.dataIscrizione = dataIscrizione;
        this.genere = genere;
    }
    //costruttore default per DAO
    public Utente() {}

    public String getMatricola() {
        return matricola;
    }

    public void setMatricola(String matricola) {
        this.matricola = matricola;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getDataNascita() {
        return dataNascita;
    }

    public void setDataNascita(LocalDate dataNascita) {
        this.dataNascita = dataNascita;
    }

    public LocalDateTime getDataIscrizione() {
        return dataIscrizione;
    }

    public void setDataIscrizione(LocalDateTime dataIscrizione) {
        this.dataIscrizione = dataIscrizione;
    }

    public Genere getGenere() {
        return genere;
    }

    public void setGenere(Genere genere) {
        this.genere = genere;
    }


}
