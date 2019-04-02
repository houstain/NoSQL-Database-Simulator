//Diaconescu Florin, 322CB

/**
 * Clasa ce modeleaza un atribut de tip Integer, si implementeaza functiile de setare si intoarcere a valorii acestuia.
 */
public class IntAttribute extends Attribute {
    private int value;

    public IntAttribute(){
    }

    public IntAttribute(String type, String valueString){
        super(type);
        value = Integer.parseInt(valueString);
    }

    /**
     * Seteaza valoarea atributului.
     *
     * @param value - valoarea atributului
     */
    @Override
    public void setValue(String value){
        this.value = Integer.parseInt(value);
    }

    /**
     * Intoarce valoarea atributului.
     *
     * @return - valoarea atributului.
     */
    @Override
    public String getValue(){
        return Integer.toString(this.value);
    }
}
