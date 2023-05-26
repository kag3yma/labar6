package utils;

import data.Chapter;
import data.Coordinates;
import data.MeleeWeapon;
import data.Weapon;
import exceptions.IncorrectInputInScriptException;
import exceptions.MustBeNotEmptyException;
import exceptions.NotInDeclaredLimitsException;
import run.App1;

import java.io.Serializable;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class MarineAsker {
    private final Float MIN_X = Float.valueOf(-647);
    private final Float MIN_HEALTH = Float.valueOf(1);
    private final int MIN_MARINES = 1;
    private final int MAX_MARINES = 1000;

    private Scanner userScanner;
    private boolean fileMode;

    public MarineAsker(Scanner userScanner) {
        this.userScanner = userScanner;
        fileMode = false;
    }

    public void setUserScanner(Scanner userScanner) {
        this.userScanner = userScanner;
    }

    public Scanner getUserScanner() {
        return userScanner;
    }

    public void setFileMode() {
        fileMode = true;
    }

    public void setUserMode() {
        fileMode = false;
    }

    public String askName() throws IncorrectInputInScriptException {
        String name;
        while (true) {
            try {
                Console.println("Enter name:");
                Console.print(App1.PS2);
                name = userScanner.nextLine().trim();
                if (fileMode) Console.println(name);
                if (name.equals("")) throw new MustBeNotEmptyException();
                break;
            } catch (NoSuchElementException exception) {
                Console.printerror("Name not recognized!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (MustBeNotEmptyException exception) {
                Console.printerror("Name cannot be empty!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (IllegalStateException exception) {
                Console.printerror("Unexpected error!");
                System.exit(0);
            }
        }
        return name;
    }

    public float askHeight() throws IncorrectInputInScriptException {
        String height;
        float h;
        while (true) {
            try {
                Console.println("Enter height:");
                Console.print(App1.PS2);
                height = userScanner.nextLine().trim();
                if (fileMode) Console.println(height);
                h = Float.parseFloat(height);
                break;
            } catch (NoSuchElementException exception) {
                Console.printerror("Height not recognized!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (NumberFormatException exception) {
                Console.printerror("Height must be represented by a number!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (NullPointerException | IllegalStateException exception) {
                Console.printerror("Unexpected error!");
                System.exit(0);
            }
        }
        return h;
    }

    public Float askX() throws IncorrectInputInScriptException {
        String strX;
        Float x;
        while (true) {
            try {
                Console.println("Enter coordinate X > " + (MIN_X) + ":");
                Console.print(App1.PS2);
                strX = userScanner.nextLine().trim();
                if (fileMode) Console.println(strX);
                x = Float.parseFloat(strX);
                if (x < MIN_X) throw new NotInDeclaredLimitsException();
                break;
            } catch (NoSuchElementException exception) {
                Console.printerror("X coordinate not recognized!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (NotInDeclaredLimitsException exception) {
                Console.printerror("X coordinate cannot be less than " + MIN_X + "!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (NumberFormatException exception) {
                Console.printerror("The X coordinate must be represented by a number!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (NullPointerException | IllegalStateException exception) {
                Console.printerror("Unexpected error!");
                System.exit(0);
            }
        }
        return x;
    }

    public float askY() throws IncorrectInputInScriptException {
        String strY;
        float y;
        while (true) {
            try {
                Console.println("Enter coordinate Y:");
                Console.print(App1.PS2);
                strY = userScanner.nextLine().trim();
                if (fileMode) Console.println(strY);
                y = Float.parseFloat(strY);
                break;
            } catch (NoSuchElementException exception) {
                Console.printerror("Y coordinate not recognized!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (NumberFormatException exception) {
                Console.printerror("The Y coordinate must be represented by a number!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (NullPointerException | IllegalStateException exception) {
                Console.printerror("Unexpected error!");
                System.exit(0);
            }
        }
        return y;
    }

    public Coordinates askCoordinates() throws IncorrectInputInScriptException {
        Float x;
        float y;
        x = askX();
        y = askY();
        return new Coordinates(x, y);
    }
    public boolean askWantChapter()throws IncorrectInputInScriptException {
        String question = "Do you want create chapter?";
        String finalQuestion = question + " (+/-):";
        String answer;
        while (true) {
            try {
                Console.println(finalQuestion);
                Console.print(App1.PS2);
                answer = userScanner.nextLine().trim();
                if (fileMode) Console.println(answer);
                if (!answer.equals("+") && !answer.equals("-")) throw new NotInDeclaredLimitsException();
                break;
            } catch (NoSuchElementException exception) {
                Console.printerror("Answer not recognized!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (NotInDeclaredLimitsException exception) {
                Console.printerror("The answer must be in characters. '+' or '-'!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (IllegalStateException exception) {
                Console.printerror("Unexpected error!");
                System.exit(0);
            }
        }
        return (answer.equals("+")) ? true : false;
    }


    public Float askHealth() throws IncorrectInputInScriptException {
        String strHealth;
        Float health;
        while (true) {
            try {
                Console.println("Enter health:");
                Console.print(App1.PS2);
                strHealth = userScanner.nextLine().trim();
                if (fileMode) Console.println(strHealth);
                health = Float.parseFloat(strHealth);
                if (health < MIN_HEALTH) throw new NotInDeclaredLimitsException();
                break;
            } catch (NoSuchElementException exception) {
                Console.printerror("Health not recognized!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (NotInDeclaredLimitsException exception) {
                Console.printerror("Health must be greater than zero!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (NumberFormatException exception) {
                Console.printerror("Health must be represented by a number!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (NullPointerException | IllegalStateException exception) {
                Console.printerror("Unexpected error!");
                System.exit(0);
            }
        }
        return health;
    }

    public Weapon askWeaponType() throws IncorrectInputInScriptException {
        String strWeaponType;
        Weapon weaponType;
        while (true) {
            try {
                Console.println("List of weapons - " + Weapon.nameList());
                Console.println("Enter weapons:");
                Console.print(App1.PS2);
                strWeaponType = userScanner.nextLine().trim();
                if (fileMode) Console.println(strWeaponType);
                weaponType = Weapon.valueOf(strWeaponType.toUpperCase());
                break;
            } catch (NoSuchElementException exception) {
                Console.printerror("Weapon not recognized!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (IllegalArgumentException exception) {
                Console.printerror("Weapon not listed!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (IllegalStateException exception) {
                Console.printerror("Unexpected error!");
                System.exit(0);
            }
        }
        return weaponType;
    }

    public MeleeWeapon askMeleeWeapon() throws IncorrectInputInScriptException {
        String strMeleeWeapon;
        MeleeWeapon meleeWeapon;
        while (true) {
            try {
                Console.println("List of melee weapons - " + MeleeWeapon.nameList());
                Console.println("Enter the melee weapon:");
                Console.print(App1.PS2);
                strMeleeWeapon = userScanner.nextLine().trim();
                if (fileMode) Console.println(strMeleeWeapon);
                meleeWeapon = MeleeWeapon.valueOf(strMeleeWeapon.toUpperCase());
                break;
            } catch (NoSuchElementException exception) {
                Console.printerror("Weapon not recognized!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (IllegalArgumentException exception) {
                Console.printerror("Weapon not listed!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (IllegalStateException exception) {
                Console.printerror("Unexpected error!");
                System.exit(0);
            }
        }
        return meleeWeapon;
    }

    public String askChapterName() throws IncorrectInputInScriptException {
        String chapterName;
        while (true) {
            try {
                Console.println("Enter the name of the order:");
                Console.print(App1.PS2);
                chapterName = userScanner.nextLine().trim();
                if (fileMode) Console.println(chapterName);
                if (chapterName.equals("")) throw new MustBeNotEmptyException();
                break;
            } catch (NoSuchElementException exception) {
                Console.printerror("The name of the order is not recognized!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (MustBeNotEmptyException exception) {
                Console.printerror("Order name cannot be empty!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (IllegalStateException exception) {
                Console.printerror("Unexpected error!");
                System.exit(0);
            }
        }
        return chapterName;
    }
    public String askParentLegion() throws IncorrectInputInScriptException {
        String parentLegion;
        while (true) {
            try {
                Console.println("Enter parent legion:");
                Console.print(App1.PS2);
                parentLegion = userScanner.nextLine().trim();
                if (fileMode) Console.println(parentLegion);
                break;
            }   catch (IllegalStateException exception) {
                Console.printerror("Unexpected error!");
                System.exit(0);
            }
        }
        return parentLegion;
    }

    public int askChapterMarinesCount() throws IncorrectInputInScriptException {
        String strMarinesCount;
        int marinesCount;
        while (true) {
            try {
                Console.println("Enter the number of soldiers in the order less " + (MAX_MARINES + 1) + " and more " + MIN_MARINES + ":");
                Console.print(App1.PS2);
                strMarinesCount = userScanner.nextLine().trim();
                if (fileMode) Console.println(strMarinesCount);
                marinesCount = Integer.parseInt(strMarinesCount);
                if (marinesCount < MIN_MARINES || marinesCount > MAX_MARINES) throw new NotInDeclaredLimitsException();
                break;
            } catch (NoSuchElementException exception) {
                Console.printerror("The number of soldiers in the order is not recognized!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (NotInDeclaredLimitsException exception) {
                Console.printerror("The number of soldiers in the order must be positive and not exceed " + MAX_MARINES + "!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (NumberFormatException exception) {
                Console.printerror("The number of soldiers in the order must be represented by the number!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (NullPointerException | IllegalStateException exception) {
                Console.printerror("Unexpected error!");
                System.exit(0);
            }
        }
        return marinesCount;
    }
    public String askWorld() throws IncorrectInputInScriptException {
        String world;
        while (true) {
            try {
                Console.println("Enter world name:");
                Console.print(App1.PS2);
                world = userScanner.nextLine().trim();
                if (fileMode) Console.println(world);
                break;
            } catch (NoSuchElementException exception) {
                Console.printerror("The name of the order is not recognized!");
                if (fileMode) throw new IncorrectInputInScriptException();
            }  catch (IllegalStateException exception) {
                Console.printerror("Unexpected error!");
                System.exit(0);
            }
        }
        return world;
    }

    public Chapter askChapter() throws IncorrectInputInScriptException {
        String name;
        String parentLegion;
        int marinesCount;
        String world;
        name = askChapterName();
        parentLegion = askParentLegion();
        marinesCount = askChapterMarinesCount();
        world = askWorld();
        return new Chapter(name, parentLegion, marinesCount, world);
    }

    public boolean askQuestion(String question) throws IncorrectInputInScriptException {
        String finalQuestion = question + " (+/-):";
        String answer;
        while (true) {
            try {
                Console.println(finalQuestion);
                Console.print(App1.PS2);
                answer = userScanner.nextLine().trim();
                if (fileMode) Console.println(answer);
                if (!answer.equals("+") && !answer.equals("-")) throw new NotInDeclaredLimitsException();
                break;
            } catch (NoSuchElementException exception) {
                Console.printerror("Answer not recognized!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (NotInDeclaredLimitsException exception) {
                Console.printerror("The answer must be in characters. '+' or '-'!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (IllegalStateException exception) {
                Console.printerror("Unexpected error!");
                System.exit(0);
            }
        }
        return (answer.equals("+")) ? true : false;
    }
}