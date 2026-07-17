package it.unina.uninamulticloud.entity;

public class Universita {
    private long idUniversita;
    private String nome;
    private String citta;

    public Universita(String nome, String citta) {
        this.nome = nome;
        this.citta = citta;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCitta() {
        return citta;
    }

    public void setCitta(String citta) {
        this.citta = citta;
    }

    public long getIdUniversita() {
        return idUniversita;
    }

    public void setIdUniversita(long idUniversita){
        this.idUniversita = idUniversita;
    }
}
