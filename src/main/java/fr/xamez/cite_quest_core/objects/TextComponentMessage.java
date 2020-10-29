package fr.xamez.cite_quest_core.objects;


public class TextComponentMessage {

    private final String key;
    private final String identifier;
    private final int step;
    private final String extra;

    public TextComponentMessage(String key, String identifier, int step, String extra) {
        this.key = key;
        this.identifier = identifier;
        this.step = step;
        this.extra = extra;
    }

    public String getKey() {
        return key;
    }

    public String getIdentifier() {
        return identifier;
    }

    public int getStep() {
        return step;
    }

    public String getExtra() {
        return extra;
    }
}
