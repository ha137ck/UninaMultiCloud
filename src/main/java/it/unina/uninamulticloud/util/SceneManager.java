package it.unina.uninamulticloud.util;

import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;
/* Serve un solo punto da cui cambiare schemata in tutta l'app: se ogni boundary creasse una propria istanza,
 ognuno avrebbe un riferimento diverso allo Stage, non si capirebbe chi mostra cosa. Per implementare il pattern singleton si
 usa un costruttore privato e il metodo statico getInstance per restituire l'istanza.
 Serve inoltre il metodo init(Stage) separato dal costruttore di SceneManager, perché l'istanza di SceneManager viene creata prima
 senza Stage, in un momento successivo deve essere inizializzato.
*
* */


public class SceneManager {

    private static SceneManager instance;
    private Stage mainStage;
    private Map<String, String> fxmlMap = new HashMap<>();

    //construttore privato per il pattern singleton
    private SceneManager() {
        //Mappa  i nomi delle pagine ai file fxml in src/main/resources
        fxmlMap.put("login.fxml", "/it/unina/uninamulticloud/view/Login.fxml");
        fxmlMap.put("registrazione.fxml", "/it/unina/uninamulticloud/view/Registrazione.fxml");
        fxmlMap.put("home.fxml", "/it/unina/uninamulticloud/view/Home.fxml");
        //aggingere le alttre pagine
    }

    public static SceneManager getInstance(){
        if(instance == null) instance = new SceneManager();
        return instance;
    }

    //da chiamare al lancio dell'applicazione nel metodo start()
    public void init(Stage stage){
        this.mainStage = stage;
    }

    //da completare
    public void cambiaScena(String fxmlFileName){
        try {
            String fxmlPath = fxmlMap.get(fxmlFileName);

        } catch (Exception e) {

        }
    }

}
