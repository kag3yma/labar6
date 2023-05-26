package utils;

import data.MeleeWeapon;
import data.SpaceMarine;

import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileTime;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CollectionHandler implements Serializable {
    HashSet<SpaceMarine> marinesCollection =  new HashSet<>();
    private static HashSet<Long> setForId = new HashSet<>();
    private LocalDateTime initDateTime;
    private LocalDateTime saveDateTime;
    private FileManager fileManager;


    public CollectionHandler(FileManager fileManager) {
        this.initDateTime = null;
        this.saveDateTime = null;
        this.fileManager = fileManager;

        loadCollection();
    }
    public void addToCollection(SpaceMarine marine) {
        marinesCollection.add(marine);
    }
    public static HashSet<Long> getArrayForId() {
        return setForId;
    }

    public String collectionType() {
        return marinesCollection.getClass().getName();
    }
    public int collectionSize() {
        return marinesCollection.size();
    }

    public void removeFromCollection(SpaceMarine marine) {
        marinesCollection.remove(marine);
    }
    public SpaceMarine getById(Long id) {
        for (SpaceMarine marine : marinesCollection) {
            if (marine.getId().equals(id)) return marine;
        }
        return null;
    }

    public FileTime getInitDateTime() {
        FileTime nothing = null;
        try{
            Path file = Paths.get("collectionWithMarines.json");
            FileTime creationTime =
                    (FileTime) Files.getAttribute(file, "creationTime");
            return creationTime;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return nothing;
    }
    public LocalDateTime getLastSaveTime() {
        return saveDateTime;
    }
    public HashSet<SpaceMarine> getCollection(){return marinesCollection;}

    public void clearCollection(){
        marinesCollection.clear();
    }

    public Long generateNextId(){
        Long nextId = Long.valueOf(0);
        for(SpaceMarine spaceMarine : marinesCollection){
            if (spaceMarine.getId() >= nextId){
                nextId = spaceMarine.getId();
            }
        }
        return nextId+1;
    }
    public Float averageHealth() {
        if (marinesCollection.isEmpty()) return 0F;
        Float avghealth;
        Float sumhealth = 0F;
        Float quantity = 0F;
        for(SpaceMarine Marine: marinesCollection) {
            sumhealth += Marine.getHealth();
            quantity += 1F;
        }
        avghealth = sumhealth / quantity;
        return avghealth;
    }
    public int enumeration(Float health) {
        int executeStatus = 0;
        ArrayList<SpaceMarine> ToDelete = new ArrayList<>();
        if (marinesCollection.isEmpty()) return 0;
        for(SpaceMarine Marine: marinesCollection)
            if(Marine.getHealth() < health) {
                ToDelete.add(Marine);
                executeStatus = 1;
            }
        if (executeStatus == 0) return 2;
        for(SpaceMarine marineToDelete: ToDelete){
            marinesCollection.remove(marineToDelete);
        }
        return 1;
    }
    public HashSet enumerationMelee(MeleeWeapon meleeweapon) {
        HashSet<SpaceMarine> marinesWithMeleeWeapon = new HashSet<>();
        if (marinesCollection.isEmpty()) return marinesWithMeleeWeapon;
        for(SpaceMarine Marine: marinesCollection)
            if(Marine.getMeleeWeapon().toString().length() > meleeweapon.toString().length()) {
                marinesWithMeleeWeapon.add(Marine);
            }
        return marinesWithMeleeWeapon;
    }
    public HashSet namestart(String startname) {
        HashSet<SpaceMarine> marinesWithRightNames = new HashSet<>();
        for (SpaceMarine marine: marinesCollection){
            startname = "^" + startname + ".*";
            Pattern pattern = Pattern.compile(startname);
            Matcher matcher = pattern.matcher(marine.getName());
            boolean found = matcher.matches();
            if(found)
                marinesWithRightNames.add(marine);
        }
        return marinesWithRightNames;
    }

    public Long getMin() {
        if (marinesCollection.isEmpty()) return 999999999L;
        Float minhealth = 999999.0F;
        Long minId = 999999999L;
        for(SpaceMarine firstMarine: marinesCollection)
            if(firstMarine.getHealth() < minhealth) {
                minhealth = firstMarine.getHeight();
                minId = firstMarine.getId();
            }
        return minId;
    }
    public Long getMax() {
        if (marinesCollection.isEmpty()) return 0L;
        Float maxhealth = 0F;
        Long maxId = 0L;
        for(SpaceMarine firstMarine: marinesCollection)
            if(firstMarine.getHealth() > maxhealth) {
                maxhealth = firstMarine.getHeight();
                maxId = firstMarine.getId();
            }
        return maxId;
    }


    public void saveCollection() {
        fileManager.writeFile(marinesCollection);
        saveDateTime = LocalDateTime.now();
    }

    public void loadCollection(){
        marinesCollection = fileManager.readFromFile();
        initDateTime = LocalDateTime.now();
    }
    @Override
    public String toString() {
        if (marinesCollection.isEmpty()) return "Collection is empty!";

        String info = "";
        Iterator iterator = marinesCollection.iterator();
        while (iterator.hasNext()){
            info += iterator.next();
        }

        return info;
    }
}
