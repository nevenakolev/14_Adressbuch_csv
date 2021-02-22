package model;

import java.io.*;

import java.util.HashMap;
import java.util.Map;

public class PhoneBook {
    //Variables
    private final String FILE_PATH = System.getProperty("user.dir") + "\\src\\files\\Phonebook.csv";
    private HashMap<Integer, Person> phonebook = new HashMap<>();
    int currentID = 0;

    //Methods
    /**
     * This method returns the previous person in the phonebook
     */
    public Person goToPrevious() {
        //checks whether there is a contact that comes before the current one in the phonebook
        if ((currentID -1) >= 0) {
            currentID--;
        }
        return phonebook.get(currentID);
    }

    /**
    * This method returns the next person in the phonebook
    */
    public Person goToNext() {
        //checks whether there is a contact that follows the current one in the phonebook
        if ((currentID +1) < phonebook.size()) {
            currentID++;
        }
        return phonebook.get(currentID);
    }

    /**
     * @param name
     * @param address
     * @param phoneNr
     * This method creates a new person with the provided information and
     * adds it to the phonebook.
     */
    public void addContact(String name, String address, String phoneNr) {
        //checks if the needed information is provided
        if (isAllInfoGiven(name, address, phoneNr)) {
            //the persons id equals the size of the phonebook
            phonebook.put(phonebook.size(), new Person(name, address, phoneNr));
        } else {
            System.out.println("Please give all required information.");
        }
    }

    /**
     * This method deletes a contact that has the provided phone number.
     * @param phoneNr
     */
    public void deleteContact(String phoneNr) {
        int newIdForPerson = 0;

        //Checks if a phone nr is provided
        if (isAllInfoGiven(phoneNr)) {
            for (Map.Entry entry : phonebook.entrySet()) {
                Person person = (Person) entry.getValue();

                //to avoid the blank spot in the hashmap, it reinitializes the hashmap while skipping the correct entry
                if (!person.getPhoneNr().equals(phoneNr)) {
                    phonebook.replace(newIdForPerson, person);
                    newIdForPerson++;
                }
            }
            //deletes the last entry in the hashmap, which would cause redundancy
            phonebook.remove(phonebook.size() -1);
        }
    }

    /**
     * This method updates the changed information on the current index
     * @param name
     * @param address
     * @param phoneNr
     */
    public void saveChanges(String name, String address, String phoneNr) {
        phonebook.replace(currentID, new Person(name, address, phoneNr));
    }

    /**
     * This method loads the data from the csv file into the phonebook (HashMap)
     */
    public void loadFromCsv() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))){
            //clears the phonebook so it can be reinitialized
            phonebook.clear();

            String row;
            //reader.readLine() reads the whole line
            while ((row = reader.readLine()) != null) {
                String[] attributes = row.split(";");

                //position 0 = name, position 1 = address, position 2 = phoneNr
                addContact(attributes[0], attributes[1], attributes[2]);
            }
        } catch (IOException ex) {
            System.out.println("An error occurred while opening the reader.");
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

                writer.write(person.toString());
                writer.newLine();
            }
        } catch (IOException ex) {
            System.out.println("An error occurred while opening the writer.");
        }
    }

    //Additional Methods
    /**
     * This method checks if all parameters are not null.
     * @param name
     * @param address
     * @param phoneNr
     * @return
     */
    public boolean isAllInfoGiven(String name, String address, String phoneNr) {
        return !name.equals("") && !address.equals("") && !phoneNr.equals("");
    }

    /**
     * This method checks if all parameters are not null.
     * @param phoneNr
     * @return
     */
    public boolean isAllInfoGiven(String phoneNr) {
        return !phoneNr.equals("");
    }
}
