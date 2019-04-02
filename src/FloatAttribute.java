//Diaconescu Florin, 322CB

import java.text.DecimalFormat;

/**
 * Clasa ce modeleaza un atribut de tip String, si implementeaza functiile de setare si intoarcere a valorii acestuia.
 */
public class FloatAttribute extends Attribute {
    private float value;
    private DecimalFormat format;

    public FloatAttribute(){

    }

    /**
     * Constructor pentru un atribut de tip Float, ce va seta si formatul de afisare specificat.
     *
     * @param type - tipul atributului
     * @param valueString - valoarea atributului
     */
    public FloatAttribute(String type, String valueString){
        super(type);
        value = Float.parseFloat(valueString);
        format = new DecimalFormat("#.##");
    }

    /**
     * Seteaza valoarea atributului.
     *
     * @param value - valoarea atributului
     */
    @Override
    public void setValue(String value){
        this.value = Float.parseFloat(value);
    }

    /**
     * Intoarce valoarea atributului.
     *
     * @return - valoarea atributului.
     */
    @Override
    public String getValue(){
        return format.format(value);
    }
}
