package data;

import java.io.Serializable;
import java.time.LocalDateTime;

public class SpaceMarine implements Serializable {
    private Long id;
    private String name;
    private Coordinates coordinates;
    private java.time.LocalDateTime creationDate;
    private Float health;
    private float height;
    private Weapon weaponType;
    private MeleeWeapon meleeWeapon;
    private Chapter chapter;
    private String creator;
    private boolean saved;

    public SpaceMarine(Long id, String name, LocalDateTime creationDate, Coordinates coordinates,
                       Float health, float height, Weapon weaponType,
                       MeleeWeapon meleeWeapon,Chapter chapter, String creator) {
        this.id = id;
        this.creationDate = creationDate;
        this.name = name;
        this.coordinates = coordinates;
        this.health = health;
        this.height = height;
        this.weaponType = weaponType;
        this.meleeWeapon = meleeWeapon;
        this.chapter = chapter;
        this.creator = creator;
        this.saved = false;
    }public SpaceMarine(Long id, String name, LocalDateTime creationDate, Coordinates coordinates,
                        Float health, float height, Weapon weaponType,
                        MeleeWeapon meleeWeapon, String creator) {
        this.id = id;
        this.creationDate = creationDate;
        this.name = name;
        this.coordinates = coordinates;
        this.health = health;
        this.height = height;
        this.weaponType = weaponType;
        this.meleeWeapon = meleeWeapon;
        this.creator = creator;
        this.saved = false;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public LocalDateTime getDateTime(){return creationDate;}
    public Coordinates getCoordinates() {
        return coordinates;
    }
    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }
    public java.time.LocalDateTime getCreationDate(){return creationDate;}
    public void setCreationDate(java.time.LocalDateTime creationDate){this.creationDate = creationDate;}
    public float getHealth(){return health;}
    public void setHealth(Float health){this.health = health;}
    public float getHeight(){return height;}
    public void setHeight(Integer height){this.height = height;}
    public Weapon getWeaponType(){return weaponType;}
    public void setWeaponType(Weapon weaponType){this.weaponType = weaponType;}
    public MeleeWeapon getMeleeWeapon(){return meleeWeapon;}
    public void setMeleeWeapon(MeleeWeapon meleeWeapon){this.meleeWeapon = meleeWeapon;}
    public Chapter getChapter(){return chapter;}
    public void setChapter(Chapter chapter){this.chapter = chapter;}

    public int healthCompareTo(SpaceMarine marineObj) {
        return health.compareTo(marineObj.getHealth());
    }
    public String getCreator(){return creator;}
    public void setCreator(String creator){
        this.creator = creator;
    }
    public void setSaved(){this.saved = true;}
    public boolean getSaved(){return saved;}

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj instanceof SpaceMarine) {
            SpaceMarine marineObj = (SpaceMarine) obj;
            return name.equals(marineObj.getName()) && coordinates.equals(marineObj.getCoordinates()) &&
                    (health == marineObj.getHealth()) && (height == marineObj.getHeight()) &&
                    (weaponType == marineObj.getWeaponType()) && (meleeWeapon == marineObj.getMeleeWeapon()) &&
                    chapter.equals(marineObj.getChapter());
        }
        return false;
    }


    @Override
    public String toString() {
        String info = "";
        info += "Soldier id - " + id;
        info += " (added " + creationDate.toLocalDate() + " " + creationDate.toLocalTime() + ")";
        info += "\n name: " + name;
        info += "\n Location: " + coordinates;
        info += "\n Health: " + health;
        info += "\n Height: " + height;
        info += "\n Weapon: " + weaponType;
        info += "\n Melee Weapon: " + meleeWeapon;
        info += "\n " + chapter;
        info += "\n Creator: " + creator;
        info += "\n";
        return info;
    }
}