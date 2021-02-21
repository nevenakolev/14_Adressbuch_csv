package viewcontroller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/* TODO: 21.02.2021 - Anlegen neuer Einträge ("Add")
                    - Löschen bestehender Einträge ("Delete")
                    - Bearbeiten von bestehenden Registerkarten ("Save Changes")
                    - Vor- und Zurückblättern im Adressbuch ("<<" und ">>")
                    - Anzeige der Gesamtanzahl von Registerkarten bzw. Anzeige der aktuellen Seitenanzahl
                    - Speichern und Laden des Telefonbuchs in bzw. aus einer CSV Datei
                        - Die CSV-Datei soll in Excel importiert werden können
 */
public class Controller {
    //Variables
    @FXML TextField txtf_name = new TextField();
    @FXML TextField txtf_address = new TextField();
    @FXML TextField txtf_phone = new TextField();
    @FXML Label lbl_pagePosition = new Label();


}
