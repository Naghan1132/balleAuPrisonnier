package fr.icom.info.m1.balleauprisonnier_mvn.Model;
public final class Singleton {
    private static Singleton instance;
    public Field gameField;

    private Singleton() {
        // The following code emulates slow initialization.
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        this.gameField = Field.getInstance();
    }

    public static Singleton getInstance() {
        if (instance == null) {
            instance = new Singleton();
        }
        return instance;
    }
}