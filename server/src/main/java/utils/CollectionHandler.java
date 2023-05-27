package utils;

import data.MeleeWeapon;
import data.SpaceMarine;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileTime;
import java.time.LocalDateTime;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

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
    public String getInitDateTime1() {
        Process proc;
        BufferedReader br = null;

        try {

            proc = Runtime.getRuntime()
                    .exec("stat -x collectionWithMarines.json");

            br = new BufferedReader(
                    new InputStreamReader(proc.getInputStream()));

            String data = "";
            for (int i = 0; i < 8; i++) {
                data = br.readLine();
            }
            return data;

        } catch (IOException e) {

            e.printStackTrace();

        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return "didn't find creation time :(";
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
        OptionalLong maxId = marinesCollection.stream()
                .mapToLong(SpaceMarine::getId)
                .max();
        Long nextId = maxId.orElse(0L);
        return nextId+1;
    }
    public Float averageHealth() {
        if (marinesCollection.isEmpty()) return 0F;
        OptionalDouble avgHealth = marinesCollection.stream()
                .mapToDouble(SpaceMarine::getHealth)
                .average();
        return (Float) (float) avgHealth.orElse(0);
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
        Optional<SpaceMarine> minHealthMarine = marinesCollection.stream()
                .min(Comparator.comparing(SpaceMarine::getHealth));
        Float minHealth = minHealthMarine.map(SpaceMarine::getHealth).orElse(999999999F);
        Long minId = minHealthMarine.map(SpaceMarine::getId).orElse(999999999L);
        return minId;
    }
    public Long getMax() {
        if (marinesCollection.isEmpty()) return 0L;
        Optional<SpaceMarine> maxHealthMarine = marinesCollection.stream()
                .max(Comparator.comparing(SpaceMarine::getHealth));
        Float maxHealth = maxHealthMarine.map(SpaceMarine::getHealth).orElse(0F);
        Long maxId = maxHealthMarine.map(SpaceMarine::getId).orElse(0L);


      //  for(SpaceMarine firstMarine: marinesCollection)
        //    if(firstMarine.getHealth() > maxhealth) {
          //      maxhealth = firstMarine.getHeight();
            //    maxId = firstMarine.getId();
            //}
        return maxId;
    }


    public void saveCollection() {
        // lab7 - сделать потокобезопасным
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
