//Diaconescu Florin, 322CB

import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * Clasa ce modeleaza o instanta cu atributele corespunzatoare entitatii din care face parte, ce foloseste
 * timeStamo-ul acesteia ca metoda de comparare. Astfel, o instanta are un nume, un atribut de tip cheie primara,
 * un timeStamp ce seteaza timpul la care a fost creata sau actualizata, precum si un ArrayList cu valorile atributelor.
 */
public class Instance implements Comparable<Instance> {
    private String name;
    private Attribute primaryKey = null;
    private long timeStamp;
    private ArrayList<Attribute> attributeList;

    public Instance(){

    }

    /**
     * Constructor pentru o instanta cu numele dorit.
     * @param name - numele instantei
     */
    public Instance(String name){
        this.name = name;
        this.attributeList = new ArrayList<>();
        setTimeStamp();
    }

    /**
     * Metoda pentru afisarea informatiilor dintr-o instanta, folosind lista de entitati pentru identificarea numelui
     * atributului, dar si a listei de valori de atribute. Astfel, vor fi afisate numele atributului, dar si valoarea
     * acestuia, pentru fiecare din atribute.
     *
     * @param entityList - lista de entitati a bazei de date
     * @param writer - writer-ul corespunzator fisierului de iesire
     */
    public void printInstance(ArrayList<Entity> entityList, PrintWriter writer){
        Entity entity = null;
        writer.write(this.name);
        int j = 0;
        for (int i = 0; i < attributeList.size(); i++){
            for (Entity auxEntity: entityList){
                if (this.name.equals(auxEntity.getName())){
                    entity = auxEntity;
                    break;
                }
            }
            writer.write(" " + entity.getAttributeList().get(j).getValue() + ":" + attributeList.get(i).getValue());
            j += 2;
        }
    }

    /**
     * Seteaza timestamp-ul cu timpul sistemului
     */
    public void setTimeStamp(){
        this.timeStamp = System.nanoTime();
    }

    /**
     * Seteaza timestamp-ul cu un timp trimis ca parametru.
     *
     * @param timeStamp - timestamp-ul dorit
     */
    public void setTimeStamp(long timeStamp){
        this.timeStamp = timeStamp;
    }

    /**
     * Seteaza cheia primara a instantei.
     *
     * @param primaryKey - cheia primara a instantei
     */
    public void setPrimaryKey(Attribute primaryKey) {
        this.primaryKey = primaryKey;
    }

    /**
     * Intoarce numele instantei.
     * @return - numele instantei
     */
    public String getName(){
        return this.name;
    }

    /**
     * Intoarce cheia primara a instantei.
     *
     * @return - cheia primara a instantei
     */
    public Attribute getPrimaryKey() {
        return this.primaryKey;
    }

    /**
     * Intoarce timestamp-ul instantei.
     * @return - timestamp-ul instantei
     */
    public long getTimeStamp(){
        return this.timeStamp;
    }

    /**
     * Intoarce lista de atribute corespunzatoare instantei.
     * @return - lista de atribute corespunzatoare instantei
     */
    public ArrayList<Attribute> getAttributeList() {
        return this.attributeList;
    }

    /**
     * Suprascrie clasa de comparare, folosind timeStamp-ul pentru compararea a doua instante.
     *
     * @param instance - instanta ce va fi comparata
     * @return - 1/0/-1, in functie de ordinea data de timeStamp
     */
    @Override
    public int compareTo(Instance instance){
        if (this.getTimeStamp() < instance.getTimeStamp())
            return 1;
        else if (this.getTimeStamp() == instance.getTimeStamp())
            return 0;
        else
            return -1;
    }

}
