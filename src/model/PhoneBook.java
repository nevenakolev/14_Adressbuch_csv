package model;

import java.io.*;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

public class PhoneBook {
    //Variables
    private final String FILE_PATH = System.getProperty("user.dir") + "\\src\\files\\Phonebook.csv";
    private HashMap<Integer, Person> phonebook = new HashMap<>();
    int currentID = 0; //currently displayed persons' id
    private Timestamp timestamp; //for the errors
    private static final SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss"); //for the timestamp


    //Methods
    /**
     * This method returns the previous person in the phonebook
     */
    public Person getPreviousPerson() {
        //checks whether there is a contact that comes before the current one in the phonebook
        if ((currentID -1) >= 0 && !phonebook.isEmpty()) {
            currentID--;
            return phonebook.get(currentID);
        }
        printMessage("You can not go further, or the phonebook is empty.");
        return null;
    }

    /**
    * This method returns the next person in the phonebook
    */
    public Person getNextPerson() {
        //checks whether there is a contact that follows the current one in the phonebook
        if ((currentID +1) < phonebook.size() && !phonebook.isEmpty()) {
            currentID++;
            return phonebook.get(currentID);
        }
        printMessage("You can not go further, or the phonebook is empty.");
        return null;
    }

    /**
     * This method creates a new person with the provided information and
     * adds it to the phonebook. The phonebook position will be updated.
     */
    public int addContact(String name, String address, String phoneNr) {
        if (!isInformationMissing(name, address, phoneNr)) {
            phonebook.put(phonebook.size(), new Person(name, address, phoneNr));
            currentID = 0;

            return 0;
        } else {
            printMessage("Please give all required information.");
            return -1;
        }
    }

    /**
     * This method deletes a contact that has the provided phone number.
     */
    public int deleteContact(String phoneNr) {
        int newIdForPerson = 0;

        if (!isInformationMissing(phoneNr)) {
            if (!phonebook.isEmpty()) {
                for (Map.Entry entry : phonebook.entrySet()) {
                    Person person = (Person) entry.getValue();

                    //to avoid the blank spot in the hashmap, it reinitializes the hashmap while skipping the correct entry
                    if (!person.getPhoneNr().equals(phoneNr)) {
                        phonebook.replace(newIdForPerson, person);
                        newIdForPerson++;
                    }
                }

                //checks if a person with the provided number was found (means the statement in the if was not skipped
                //which also means that at the end the newIdForPerson should be as high as before
                if (newIdForPerson == phonebook.size()) {
                    printMessage("A person with this phone number was not found. Please try again.");
                    return -1;
                } else {
                    //deletes the last entry in the hashmap, which would cause redundancy
                    phonebook.remove(phonebook.size() - 1);
                    printMessage("The person has been removed.");
                    currentID = 0;

                    return 0;
                }
            } else {
                printMessage("The Phonebook is empty.");
                return -1;
            }
        } else {
            printMessage("Please give the phone number of the person that has to be deleted.");
            return -1;
        }
    }

    /**
     * This method updates the changed information on the current index
     */
    public void saveChanges(String name, String address, String phoneNr) {
        if (!isInformationMissing(name, address, phoneNr))
            phonebook.replace(currentID, new Person(name, address, phoneNr));
        else
            printMessage("Please fill out all fields.");
    }

    /**
     * This method loads the data from the csv file into the phonebook (HashMap)
     * and skips the lines that have an error (missing / empty entry).
     * The phonebook position will be updated.
     */
    public void loadFromCsv() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))){
            phonebook.clear();
            currentID = -1;
            int linecounter = 0;

            String row;
            while ((row = reader.readLine()) != null) {
                linecounter++;

                String[] attributes = row.split(";");
                if (attributes.length == 3) {
                    if (!attributes[0].isBlank() && !attributes[1].isBlank() && !attributes[2].isBlank()) {
                        addContact(attributes[0], attributes[1], attributes[2]); //0 = name, 1 = address, 2 = phoneNr
                        currentID++;
                    } else {
                        printMessage("An attribute is empty on line " + linecounter);
                    }
                } else {
                    printMessage("An attribute is missing on line " + linecounter + ".");
                }
            }
            //resets the current id
            currentID = 0;
        } catch (IOException ex) {
            printMessage("An error occurred while opening the reader.");
        }
    }

    /**
     * This method saves the contacts of the phonebook
     * (entries in the HashMap) into the csv file.
     */
    public void saveToCsv() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))){
            for (Map.Entry entry : phonebook.entrySet()) {
                Person person = (Person) entry.getValue();

                writer.write(person.toString()); //writes a row with ; as a separator
                writer.newLine();
            }
        } catch (IOException ex) {
            printMessage("An error occurred while opening the writer.");
        }
    }


    //Additional Methods
    /**
     * This method checks if all parameters are not null.
     */
    public boolean isInformationMissing(String name, String address, String phoneNr) {
        return name.equals("") || address.equals("") || phoneNr.equals("");
    }

    /**
     * This method checks if all parameters are not null.
     */
    public boolean isInformationMissing(String phoneNr) {
        return phoneNr.equals("");
    }

    /**
     * This method prints out an error with a given message.
     */
    public void printMessage(String msg) {
        timestamp = new Timestamp(System.currentTimeMillis());
        System.out.println(sdf.format(timestamp) +  " | Info: " + msg);
    }

    /**
     * This method updates the position in the phonebook.
     */
    public String getNewPbPosition() {
        if (!phonebook.isEmpty())
            return (currentID+1 + " / " + phonebook.size());
        else
            return "0 / 0";
    }

    /**
     * This method shows the first person in the phonebook.
     */
    public Person getFirstPerson() {
        if (!phonebook.isEmpty())
            return phonebook.get(0);
        else
            return null;
    }
}
