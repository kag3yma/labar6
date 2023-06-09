package commands;

import requests.Request;

public interface  Command {
    String getName();
    String getDescription();
    /**
     * Проверка количества и типа аргументов
     * @param arg
     */
    boolean argCheck(String arg);


    String execute(Request request);
}