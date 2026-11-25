package it.unina.uninamulticloud.boundary;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;

import java.io.File;

public class MediaCardBoundary {

    @FXML private VBox cardRoot;
    @FXML private ImageView coverImageView;
    @FXML private Label badgeLabel;
    @FXML private Label titoloLabel;
    @FXML private Label descrizioneLabel;

    @FXML
    public void initialize() {
        // Arrotonda gli angoli dell'ImageView per farli combaciare con il riquadro CSS
        Rectangle clip = new Rectangle(175.0, 175.0);
        clip.setArcWidth(24.0);
        clip.setArcHeight(24.0);
        coverImageView.setClip(clip);
    }

    /**
     * Popola la card con i dati e imposta l'azione al click.
     */
    public void setData(String titolo, String descrizione, String percorsoCopertina, String tipoBadge, Runnable onCardClick) {
        titoloLabel.setText(titolo != null ? titolo : "Senza titolo");
        descrizioneLabel.setText(descrizione != null && !descrizione.isBlank() ? descrizione : "Nessuna descrizione");
        badgeLabel.setText(tipoBadge);

        caricaCopertina(percorsoCopertina);

        if (onCardClick != null) {
            cardRoot.setOnMouseClicked(event -> onCardClick.run());
        }
    }

    private void caricaCopertina(String percorso) {
        if (percorso == null || percorso.isBlank()) {
            coverImageView.setImage(null); // Mostra lo sfondo grigio sfumato di default del CSS
            return;
        }

        try {
            String uri;
            if (percorso.startsWith("http://") || percorso.startsWith("https://") || percorso.startsWith("file:")) {
                uri = percorso;
            } else {
                File fileLocale = new File(percorso);
                if (fileLocale.exists()) {
                    uri = fileLocale.toURI().toString();
                } else {
                    var res = getClass().getResource(percorso);
                    if (res == null) return;
                    uri = res.toExternalForm();
                }
            }
            // Carica l'immagine adattandola al quadrato 175x175 in background
            coverImageView.setImage(new Image(uri, 175.0, 175.0, false, true, true));
        } catch (Exception e) {
            coverImageView.setImage(null);
        }
    }
}