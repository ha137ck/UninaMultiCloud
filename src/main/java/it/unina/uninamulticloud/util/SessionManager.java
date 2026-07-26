package it.unina.uninamulticloud.util;

import it.unina.uninamulticloud.entity.Utente;

public class SessionManager {

    private static Utente utenteLoggato;

    private SessionManager(){}

    public static  void login(Utente utente){utenteLoggato = utente;}

    public static void logout(){utenteLoggato = null;}

    public static Utente getUtenteLoggato(){
        return utenteLoggato;
    }

    public static boolean isLoggedIn(){
        return (utenteLoggato != null);
    }
}
