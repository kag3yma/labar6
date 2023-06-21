package utils;

import data.MeleeWeapon;
import data.SpaceMarine;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileTime;
import java.time.LocalDateTime;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.concurrent.locks.ReentrantLock;

public class CollectionHandler{
    private static HashSet<Long> setForId = new HashSet<>();
    private LocalDateTime initDateTime;
    private LocalDateTime saveDateTime;
    private FileManager fileManager;
    private ReentrantLock reentrantLock;
    private HashSet<SpaceMarine> collection;


    public CollectionHandler() {
        this.initDateTime = LocalDateTime.now();
        this.saveDateTime = null;
        reentrantLock = new ReentrantLock();
        collection = new HashSet<>();

    }
    public void addToCollection(SpaceMarine marine) {
        collection.add(marine);
    }
    public static HashSet<Long> getArrayForId() {
        return setForId;
    }

    public String collectionType() {
        return collection.getClass().getName();
    }
    public int collectionSize() {
        return collection.size();
    }

    public void removeFromCollection(SpaceMarine marine) {
        collection.remove(marine);
    }
    public SpaceMarine getById(Long id) {
        reentrantLock.lock();
        try {
            for (SpaceMarine marine : collection) {
                if (marine.getId().equals(id)) return marine;
            }
            return null;
        }finally {
            reentrantLock.unlock();
        }
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

    public LocalDateTime getInitDateTime() {
        FileTime nothing = null;
        reentrantLock.lock();
        try{
            return initDateTime;
        } finally {
            reentrantLock.unlock();
        }
    }
    public LocalDateTime getLastSaveTime() {
        reentrantLock.lock();
        try {
            return saveDateTime;
        }finally {
            reentrantLock.unlock();
        }
    }
    public HashSet<SpaceMarine> getCollection(){
        reentrantLock.lock();
        try {
            return collection;
        }finally {
            reentrantLock.unlock();
        }
    }

    public void clearCollection(){
        reentrantLock.lock();
        try {
            collection.clear();
        }finally {
            reentrantLock.unlock();
        }
    }

    public Long generateNextId(){
        OptionalLong maxId = collection.stream()
                .mapToLong(SpaceMarine::getId)
                .max();
        Long nextId = maxId.orElse(0L);
        return nextId+1;
    }
    public Float averageHealth() {
        reentrantLock.lock();
        try {

            if (collection.isEmpty()) return 0F;
            OptionalDouble avgHealth = collection.stream()
                    .mapToDouble(SpaceMarine::getHealth)
                    .average();
            return (Float) (float) avgHealth.orElse(0);
        }finally {
            reentrantLock.unlock();
        }
    }
    public int enumeration(Float health) {
        int executeStatus = 0;
        ArrayList<SpaceMarine> ToDelete = new ArrayList<>();
        if (collection.isEmpty()) return 0;
        for(SpaceMarine Marine: collection)
            if(Marine.getHealth() < health) {
                ToDelete.add(Marine);
                executeStatus = 1;
            }
        if (executeStatus == 0) return 2;
        for(SpaceMarine marineToDelete: ToDelete){
            collection.remove(marineToDelete);
        }
        return 1;
    }
    public HashSet enumerationMelee(MeleeWeapon meleeweapon) {
        HashSet<SpaceMarine> marinesWithMeleeWeapon = new HashSet<>();
        if (collection.isEmpty()) return marinesWithMeleeWeapon;
        for(SpaceMarine Marine: collection)
            if(Marine.getMeleeWeapon().toString().length() > meleeweapon.toString().length()) {
                marinesWithMeleeWeapon.add(Marine);
            }
        return marinesWithMeleeWeapon;
    }
    public HashSet namestart(String startname) {
        HashSet<SpaceMarine> marinesWithRightNames = new HashSet<>();
        for (SpaceMarine marine: collection){
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
        reentrantLock.lock();
        try {

            if (collection.isEmpty()) return 999999999L;
            Optional<SpaceMarine> minHealthMarine = collection.stream()
                    .min(Comparator.comparing(SpaceMarine::getHealth));
            Float minHealth = minHealthMarine.map(SpaceMarine::getHealth).orElse(999999999F);
            Long minId = minHealthMarine.map(SpaceMarine::getId).orElse(999999999L);
            return minId;
        }finally {
            reentrantLock.unlock();
        }
    }
    public Long getMax() {
        reentrantLock.lock();
        try {


            if (collection.isEmpty()) return 0L;
            Optional<SpaceMarine> maxHealthMarine = collection.stream()
                    .max(Comparator.comparing(SpaceMarine::getHealth));
            Float maxHealth = maxHealthMarine.map(SpaceMarine::getHealth).orElse(0F);
            Long maxId = maxHealthMarine.map(SpaceMarine::getId).orElse(0L);
            return maxId;
        }finally {
            reentrantLock.unlock();
        }


      //  for(SpaceMarine firstMarine: marinesCollection)
        //    if(firstMarine.getHealth() > maxhealth) {
          //      maxhealth = firstMarine.getHeight();
            //    maxId = firstMarine.getId();
            //}
    }


    public void saveCollection() {
        reentrantLock.lock();
        try {
            fileManager.writeFile(collection);
            saveDateTime = LocalDateTime.now();
        }finally {
            reentrantLock.unlock();
        }
    }

    public void loadCollection(){
        reentrantLock.lock();
        try {

           collection = fileManager.readFromFile();
            initDateTime = LocalDateTime.now();
        }finally {
            reentrantLock.unlock();
        }
    }
    public void setCollection (HashSet<SpaceMarine> sp){
        reentrantLock.lock();
        try {
            this.collection = sp;
        }finally {
            reentrantLock.unlock();
        }
    }
    @Override
    public String toString() {
        if (collection.isEmpty()) return "Collection is empty!";

        String info = "";
        Iterator iterator = collection.iterator();
        while (iterator.hasNext()){
            info += iterator.next();
        }

        return info;
    }

    public void printSpaceMarinesList(PrintWriter printWriter){
        reentrantLock.lock();
        try {
            for (SpaceMarine spaceMarine: collection){
                printWriter.println("id: "+spaceMarine.getId());
                printWriter.println("name: "+spaceMarine.getName());
                printWriter.println("coordinates: x: "+spaceMarine.getCoordinates().getX()+"y: "+ spaceMarine.getCoordinates().getY());
                printWriter.println("creation_date: "+spaceMarine.getCreationDate());
                printWriter.println("height: "+spaceMarine.getHeight());
                printWriter.println("health: "+spaceMarine.getHealth());
                printWriter.println("Chapter: "+spaceMarine.getChapter().getName()+" | Parent legion: "+spaceMarine.getChapter().getParentLegion()+" | World: "+spaceMarine.getChapter().getWorld()
                +" | Marines Count: "+ spaceMarine.getChapter().getMarinesCount());
                printWriter.println("********************************************");
            }
        }finally {
            reentrantLock.unlock();
        }
    }
    public void printSpaceMarine(SpaceMarine spaceMarine, PrintWriter printWriter){
        reentrantLock.lock();
        try {
            printWriter.println("id: "+spaceMarine.getId());
            printWriter.println("name: "+spaceMarine.getName());
            printWriter.println("coordinates: x: "+spaceMarine.getCoordinates().getX()+"y: "+ spaceMarine.getCoordinates().getY());
            printWriter.println("creation_date: "+spaceMarine.getCreationDate());
            printWriter.println("height: "+spaceMarine.getHeight());
            printWriter.println("health: "+spaceMarine.getHealth());
            printWriter.println("Chapter: "+spaceMarine.getChapter().getName()+" | Parent legion: "+spaceMarine.getChapter().getParentLegion()+" | World: "+spaceMarine.getChapter().getWorld()
                    +" | Marines Count: "+ spaceMarine.getChapter().getMarinesCount());
            printWriter.println("********************************************");
        }finally {
            reentrantLock.lock();
        }
    }
}
