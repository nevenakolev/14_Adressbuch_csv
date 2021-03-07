package viewcontroller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.Person;
import model.PhoneBook;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    // Variables
    PhoneBook pb = new PhoneBook();
    @FXML TextField txtf_myContactsName = new TextField();
    @FXML TextField txtf_myContactsAddress = new TextField();
    @FXML TextField txtf_myContactsPhoneNr = new TextField();
    @FXML TextField txtf_addContactName = new TextField();
    @FXML TextField txtf_addContactAddress = new TextField();
    @FXML TextField txtf_addContactPhoneNr = new TextField();
    @FXML TextField txtf_deleteContactPhoneNr = new TextField();
    @FXML TextField txtf_myContacts_prefix = new TextField();
    @FXML TextField txtf_addContacts_prefix = new TextField();
    @FXML TextField txtf_deleteContact_prefix = new TextField();
    @FXML Label lbl_phonebookPosition = new Label();
    @FXML Button btn_sortContacts = new Button();


    // Methods for the buttons
    public void showPreviousPerson() {
        Person p = pb.getPreviousPerson();
        if (p != null) {
            txtf_myContactsName.setText(p.getName());
            txtf_myContactsAddress.setText(p.getAddress());
            txtf_myContacts_prefix.setText(p.getPrefix());
            txtf_myContactsPhoneNr.setText(p.getPhoneNr());

            updatePhonebookPosition();
        }
    }

    public void showNextPerson() {
        Person p = pb.getNextPerson();
        if (p != null) {
            txtf_myContactsName.setText(p.getName());
            txtf_myContactsAddress.setText(p.getAddress());
            txtf_myContacts_prefix.setText(p.getPrefix());
            txtf_myContactsPhoneNr.setText(p.getPhoneNr());
        }
        updatePhonebookPosition();
    }

    public void addContact() {
        if (pb.addContact(txtf_addContactName.getText(), txtf_addContactAddress.getText(), txtf_addContacts_prefix.getText(), txtf_addContactPhoneNr.getText()) != -1) {
            clearFields("Add contact");
        }
        updatePhonebook();
    }

    public void deleteContact() {
        if (pb.deleteContact(txtf_deleteContactPhoneNr.getText()) != -1) {
            clearFields("Delete contact");
        }
        updatePhonebook();
    }

    public void saveChanges() {
        pb.saveChanges(txtf_myContactsName.getText(), txtf_myContactsAddress.getText(), txtf_myContacts_prefix.getText(), txtf_myContactsPhoneNr.getText());
    }

    public void loadFromCsv() {
        pb.loadFromCsv();
        updatePhonebook();
    }

    public void saveToCsv() {
        pb.saveToCsv();
    }

    public void updatePhonebook() {
        if (pb.getFirstPerson() != null) {
            Person p = pb.getFirstPerson();
            txtf_myContactsName.setText(p.getName());
            txtf_myContactsAddress.setText(p.getAddress());
            txtf_myContacts_prefix.setText(p.getPrefix());
            txtf_myContactsPhoneNr.setText(p.getPhoneNr());
        } else {
            clearFields("My contacts"); //clears the form after the phonebook is empty again
        }

        updatePhonebookPosition();
    }

    public void sortContacts() {
        pb.sortContacts();
        loadFromCsv();
    }


    //Additional methods
    public void updatePhonebookPosition() {
        lbl_phonebookPosition.setText(pb.getNewPbPosition());
    }

    public void clearFields(String form) {
        switch (form) {
            case "My contacts":
                txtf_myContactsName.setText("");
                txtf_myContactsAddress.setText("");
                txtf_myContacts_prefix.setText("");
                txtf_myContactsPhoneNr.setText("");
                break;

            case "Add contact":
                txtf_addContactName.setText("");
                txtf_addContactAddress.setText("");
                txtf_addContacts_prefix.setText("");
                txtf_addContactPhoneNr.setText("");
                break;

            case "Delete contact":
                txtf_deleteContact_prefix.setText("");
                txtf_deleteContactPhoneNr.setText("");
        }
    }

    /**
     * This method is called at the start of the application. It can be used to initialize
     * variables or to run methods that are essential for the start of a program.
     */
    @Override public void initialize(URL location, ResourceBundle resources) {
        //loads the contacts from the csv into the book at the start of the program
        loadFromCsv();

        //when the application is closed, the saveToCsv() method will be called
        //https://stackoverflow.com/questions/5824049/running-a-method-when-closing-the-program/5824066
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            public void run() {
                saveToCsv();
            }
        }, "Shutdown-thread"));

        initTextFieldListeners();
    }


    //Textfield listeners
    /**
     * This methods prevents input of text characters.
     * Source: https://www.java-forum.org/thema/changelistener-auf-mehrere-textfelder.176352/
     */
    public void checkForStringInput(TextField txtf_) {
        txtf_.textProperty().addListener( (observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                txtf_.setText(oldValue);
            }
        });
    }

    /**
     * This methods prevents input of text characters and only lets the user to type a
     * maximum of 3 characters.
     * Source: https://www.java-forum.org/thema/changelistener-auf-mehrere-textfelder.176352/
     */
    public void checkForStringInput_prefix(TextField txtf_) {
        txtf_.textProperty().addListener( (observable, oldValue, newValue) -> {
            if (newValue.matches("[a-zA-Z]") || newValue.length() > 3) {
                txtf_.setText(oldValue);
            }
        });
    }

    /**
     * This methods prevents input of numbers.
     * Sources: https://www.vogella.com/tutorials/JavaRegularExpressions/article.html
     */
    public void checkForNumInput(TextField txtf_) {
        txtf_.textProperty().addListener( (observable, oldValue, newValue) -> {
            if (!newValue.matches("\\D*")) {
                txtf_.setText(oldValue);
            }
        });
    }

    public void initTextFieldListeners() {
        checkForStringInput(txtf_myContactsPhoneNr);
        checkForStringInput(txtf_addContactPhoneNr);
        checkForStringInput(txtf_deleteContactPhoneNr);

        checkForNumInput(txtf_myContactsName);
        checkForNumInput(txtf_addContactName);

        checkForStringInput_prefix(txtf_myContacts_prefix);
        checkForStringInput_prefix(txtf_addContacts_prefix);
        checkForStringInput_prefix(txtf_deleteContact_prefix);
    }
}
