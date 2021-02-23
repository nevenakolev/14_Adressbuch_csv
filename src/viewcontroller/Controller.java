package viewcontroller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.Person;
import model.PhoneBook;

public class Controller {
    // Variables
    PhoneBook pb = new PhoneBook();
    @FXML TextField txtf_myContactsName = new TextField();
    @FXML TextField txtf_myContactsAddress = new TextField();
    @FXML TextField txtf_myContactsPhoneNr = new TextField();
    @FXML TextField txtf_addContactName = new TextField();
    @FXML TextField txtf_addContactAddress = new TextField();
    @FXML TextField txtf_addContactPhoneNr = new TextField();
    @FXML TextField txtf_deleteContactPhoneNr = new TextField();
    @FXML Label lbl_phonebookPosition = new Label();


    // Methods for the buttons
    public void showPreviousPerson() {
        Person p = pb.getPreviousPerson();
        if (p != null) {
            txtf_myContactsName.setText(p.getName());
            txtf_myContactsAddress.setText(p.getAddress());
            txtf_myContactsPhoneNr.setText(p.getPhoneNr());

            updatePhonebookPosition();
        }
    }

    public void showNextPerson() {
        Person p = pb.getNextPerson();
        if (p != null) {
            txtf_myContactsName.setText(p.getName());
            txtf_myContactsAddress.setText(p.getAddress());
            txtf_myContactsPhoneNr.setText(p.getPhoneNr());
        }
        updatePhonebookPosition();
    }

    public void addContact() {
        if (pb.addContact(txtf_addContactName.getText(), txtf_addContactAddress.getText(), txtf_addContactPhoneNr.getText()) != -1) {
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
        pb.saveChanges(txtf_myContactsName.getText(), txtf_myContactsAddress.getText(), txtf_myContactsPhoneNr.getText());
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
            txtf_myContactsPhoneNr.setText(p.getPhoneNr());
        } else {
            clearFields("My contacts"); //clears the form after the phonebook is empty again
        }

        updatePhonebookPosition();
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
                txtf_myContactsPhoneNr.setText("");
                break;

            case "Add contact":
                txtf_addContactName.setText("");
                txtf_addContactAddress.setText("");
                txtf_addContactPhoneNr.setText("");
                break;

            case "Delete contact":
                txtf_deleteContactPhoneNr.setText("");
        }
    }
}
