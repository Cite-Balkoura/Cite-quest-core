package fr.xamez.cite_quest_core.enumerations;


public enum MessagesEnum {

    PREFIX_CMD("§6[§2Balkoura-Quête§6] "),
    PREFIX_CONSOLE("[Balkoura-Quest] ");

    private final String text;

    MessagesEnum(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
