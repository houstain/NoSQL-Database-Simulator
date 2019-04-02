//Diaconescu Florin, 322CB

import java.util.ArrayList;

/**
 * Clasa ce modeleaza o entitate a unei baze de date, avand ca si caracteristici numele acesteia, factorul de
 * replicare, numarul de atribute, precum si un ArrayList de atribute ce vor caracteriza instantele acestei entitati.
 */
public class Entity {
    private String name;
    private int replicationFactor;
    private int noAttributes;
    public ArrayList<Attribute> attributeList;

    public Entity(){

    }

    /**
     * Constructorul creeaza o entitate cu caracteristicile trimise ca parametrii.
     *
     * @param name - numele entitatii
     * @param replicationFactor - factorul de replicare al entitatii
     * @param noAttributes - numarul de atribute al entitatii
     */
    public Entity(String name, int replicationFactor, int noAttributes) {
        this.name = name;
        this.replicationFactor = replicationFactor;
        this.noAttributes = noAttributes;
        this.attributeList = new ArrayList<>();
    }

    /**
     * Metoda adauga un atribut trimis ca parametru in lista de atribute.
     *
     * @param attribute - atributul ce va fi adaugat in lista de atribute
     */
    public void addAtribute(Attribute attribute){
        this.attributeList.add(attribute);
    }

    /**
     * Metoda cauta in lista de atribute, atributul cu numele corespunzator celui trimis ca parametru. In cazul in care
     * a fost gasit, returneaza index-ul acestuia, iar in caz contrar va returna -1.
     *
     * @param name - numele atributului ce se doreste a fi gasit
     * @return
     */
    public int findAttribute(String name){
        for (int i = 0; i < attributeList.size(); i++){
            Attribute auxAttribute = attributeList.get(i);
            if (name.equals(auxAttribute.getValue())){
                return i;
            }
        }

        return -1;
    }

    /**
     * Returneaza numele entitatii.
     *
     * @return - numele entitatii
     */
    public String getName() {
        return this.name;
    }

    /**
     * Returneaza factorul de replicare al entitatii.
     *
     * @return - factorul de replicare al entitatii
     */
    public int getReplicationFactor() {
        return this.replicationFactor;
    }

    /**
     * Returneaza lista de atribute a entitatii.
     * @return - lista de atribute a entitatii
     */
    public ArrayList<Attribute> getAttributeList() {
        return this.attributeList;
    }
}
