//Diaconescu Florin, 322CB

/**
 * Clasa abstracta ce modeleaza un atribut, de un anumit tip.
 */
public abstract class Attribute {
    private String type;

    public Attribute(){

    }

    /**
     * Constructor ce seteaza tipul atributului.
     *
     * @param name - tipul atributului
     */
    public Attribute(String name){
        this.type = name;
    }

    /**
     * Intoarce tipul atributului.
     *
     * @return - tipul atributului
     */
    public String getType() {
        return this.type;
    }

    public abstract void setValue(String value);
    public abstract String getValue();
}
