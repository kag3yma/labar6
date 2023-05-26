package data;

public enum Weapon {
    FLAMER,
    GRENADE_LAUNCHER,
    HEAVY_FLAMER;
    public static String nameList() {
        String nameList = "";
        for (Weapon weaponType : values()) {
            nameList += weaponType.name() + ", ";
        }
        return nameList.substring(0, nameList.length()-2);
    }
}
