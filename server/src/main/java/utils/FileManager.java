package utils;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import data.SpaceMarine;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class FileManager {
    HashSet<SpaceMarine> marinesCollection;
    private final Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new LocalDateAdapter()).registerTypeAdapter(
            LocalDateTime.class, new LocalDateTimeAdapter()).setPrettyPrinting().create();
    private final String nameFile;

    public FileManager(String nameFile) {
        this.nameFile = nameFile;
    }

    public void writeFile(Collection<?> collection) {
        if (nameFile != null) {
            try (FileWriter collectionFileWriter = new FileWriter(new File(nameFile))) {
                collectionFileWriter.write(gson.toJson(collection));
                Console.println("Collection successfully saved to file!");
            } catch (IOException exception) {
                Console.printerror("The download file is a directory/cannot be opened!");
            }
        } else Console.printerror("Boot file system variable not found!");
    }

    public HashSet<SpaceMarine> readFromFile() {
        if (nameFile != null) {
            File file = new File(nameFile);
            if (file.canRead()) {
                try (Scanner collectionFileScanner = new Scanner(file)) {
                    HashSet<SpaceMarine> collection;
                    Type collectionType = new TypeToken<HashSet<SpaceMarine>>() {
                    }.getType();
                    String coll = null;
                    while (collectionFileScanner.hasNext()) {
                        String col = collectionFileScanner.nextLine().trim();
                        coll = coll + col;
                    }
                    if (coll != null) {
                    String a = coll.substring(4);
                    collection = gson.fromJson(a, collectionType);
                    HashSet<SpaceMarine> marines = collection;
                        for (SpaceMarine marine : marines) CollectionHandler.getArrayForId().add(marine.getId());
                        Console.println("Collection uploaded successfully!");
                        this.marinesCollection = collection;}
                    else{collection = new HashSet<>();
                        HashSet<SpaceMarine> marines = collection;
                        for (SpaceMarine marine : marines) CollectionHandler.getArrayForId().add(marine.getId());
                        Console.println("Collection uploaded successfully!");
                        this.marinesCollection = collection;}

                    int count = 0;
                    try {
                        for (SpaceMarine elem : collection) {
                            count++;
                        }
                    } catch (NullPointerException e) {
                        System.out.println("File is empty");
                    }
                    Long[] dublicate = new Long[count];
                    count = 0;
                    try {

                        for (SpaceMarine elem : marinesCollection) {
                            dublicate[count] = elem.getId();
                            if (elem.getId() <= 0) {
                                Console.printerror("The file has a negative ID, overwrite the file and try again");
                                System.exit(0);
                            }
                            if (elem.getId() == null) {
                                Console.printerror("id value cannot be null");
                                System.exit(0);
                            }
                            if (elem.getName().equals("null")){
                                Console.printerror("The file contains name with null value");
                                System.exit(0);
                            }
                            if (elem.getName().equals("")){
                                Console.printerror("Name cannot be an empty string");
                                System.exit(0);
                            }
                            if(elem.getCoordinates().getX()<-647F){
                                Console.printerror("The file contains the x coordinate less than -647");
                                System.exit(0);
                            }
                            if (String.valueOf(elem.getCoordinates().getX()).equals("null")){
                                Console.printerror("X coordinate cannot be null");
                                System.exit(0);
                            }
                            if(elem.getDateTime().equals("null")){
                                Console.printerror("Date cannot be null");
                                System.exit(0);
                            }
                            if(elem.getHealth()<0){
                                Console.printerror("The field value must be greater than 0");
                                System.exit(0);
                            }
                            if(String.valueOf(elem.getHealth()).equals("null")){
                                Console.printerror("The value of the helth field cannot be null");
                                System.exit(0);
                            }
                            count = count + 1;
                        }
                    }
                    catch (NullPointerException e) {
                        Console.printerror("Check if data is correct, standard collection loaded");
                    }
                    for (int i = 0; i < count; i++) {
                        for (int j = i + 1; j < count; j++) {
                            if (dublicate[i] == dublicate[j]) {
                                Console.printerror("The file contains duplicate id, overwrite the file and try again");
                                System.exit(0);
                            }
                        }
                    }
                    return collection;
                }catch (JsonSyntaxException e){
                    Console.printerror("Broken syntax in collection file");
                    System.exit(0);
                } catch (FileNotFoundException e) {
                    Console.printerror("Download file not found!");
                    throw new RuntimeException(e);
                } catch (NoSuchElementException exception) {
                    Console.printerror("Boot file is empty!");
                    throw new RuntimeException(exception);
                } catch (NullPointerException exception) {
                    Console.printerror("Required collection not found in upload file!");
                    throw new RuntimeException(exception);
                }
            }
        } else Console.printerror("Boot file system variable not found!");

        return new HashSet<SpaceMarine>();

    }
}
