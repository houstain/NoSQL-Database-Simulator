//Diaconescu Florin, 322CB

/**
 * Clasa ce modeleaza un atribut de tip String, si implementeaza functiile de setare si intoarcere a valorii acestuia.
 */
public class StringAttribute extends Attribute{
    private String value;

    public StringAttribute(){

    }

    /**
     * Constructor pentru un atribut de tip String, cu o anumita valoare.
     *
     * @param type - tipul atributului
     * @param valueString - valoarea atributului
     */
    public StringAttribute(String type, String valueString){
        super(type);
        this.value = valueString;
    }

    /**
     * Seteaza valoarea atributului.
     *
     * @param value - valoarea atributului
     */
    @Override
    public void setValue(String value){
        this.value = value;
    }

    /**
     * Intoarce valoarea atributului.
     *
     * @return - valoarea atributului.
     */
    @Override
    public String getValue(){
        return this.value;
    }
}
