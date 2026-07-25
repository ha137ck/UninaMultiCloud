package it.unina.uninamulticloud.boundary;

import it.unina.uninamulticloud.control.ReportControl;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class ReportBoundary {

    private ReportControl reportControl = new ReportControl();

    @FXML private Label numeroPlaylistLabel;
    @FXML private Label numeroPubblicazioniLabel;

    @FXML
    public void initialize() {
        //numeroPlaylistLabel.setText(String.valueOf(reportControl.getNumeroPlaylistUtente()));
        //numeroPubblicazioniLabel.setText(String.valueOf(reportControl.getNumeroPubblicazioniUtente()));
    }

    @FXML
    public void onChiudi(javafx.event.ActionEvent event) {
        javafx.scene.Node source = (javafx.scene.Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }
}