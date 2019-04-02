//Diaconescu Florin, 322CB

import java.util.ArrayList;
import java.util.TreeSet;
import java.io.*;

/**
 * Clasa ce modeleaza un nod din baza de date, avand ca si caracteristici numarul nodului, capacitatea maxima a
 * acestuia, precum si un TreeSet de instante.
 */
public class Node {
    private int number;
    private int maxCapacity;
    private TreeSet<Instance> instanceTree;

    public Node(){

    }

    /**
     * Constructor pentru un nod cu numarul si capacitatea maxima a acestuia specificate ca parametrii.
     *
     * @param number - numarul nodului
     * @param maxCapacity - capacitatea maxima a nodului
     */
    public Node(int number, int maxCapacity) {
        this.number = number;
        this.maxCapacity = maxCapacity;
        this.instanceTree = new TreeSet<>();
    }

    /**
     * Insereaza instanta trimisa ca parametru in lista de instante.
     *
     * @param instance - instanta ce se doreste a fi inserata in lista de instante
     */
    public void insertInstance(Instance instance){
        instanceTree.add(instance);
    }

    /**
     * Metoda pentru afisarea informatiilor corespunzatoare tuturor instantelor din nodurile bazei de date.
     *
     * @param entityList - lista de entitati a bazei de date
     * @param writer - writer-ul pentru scrierea in fisierul de iesire
     */
    public void printNode(ArrayList<Entity> entityList, PrintWriter writer){
        writer.println("Nod" + this.number);

        for (Instance auxInstance : instanceTree){
            auxInstance.printInstance(entityList, writer);
            writer.write("\n");
        }
    }

    /**
     * Metoda ce sterge o instanta cu numele si cheia primara trimise ca parametrii, din lista de entitati.
     * @param name - numele instantei
     * @param key - numele cheii primare a instantei
     * @return - true, daca s-a sters o instanta, sau false in caz contrat
     */
    public boolean deleteNode(String name, String key){
        for (Instance auxInstance : instanceTree){
            if (name.equals(auxInstance.getName())){
                if (key.equals(auxInstance.getPrimaryKey().getValue())){
                    instanceTree.remove(auxInstance);
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Metoda incearca gasirea unei instante cu numele si numele cheii primare trimise ca parametru in lista de
     * instante, returnand instanta corespunzatoare sau null, in cazul in care nu a fost gasita.
     *
     * @param name - numele instantei
     * @param key - numele cheii primare a instantei
     * @return - instanta gasita sau null, in cazul in care nu a fost gasita
     */
    public Instance findInstance(String name, String key){
        for (Instance auxInstance : instanceTree){
            if (name.equals(auxInstance.getName())){
                if (key.equals(auxInstance.getPrimaryKey().getValue())){
                    return auxInstance;
                }
            }
        }
        return null;
    }

    /**
     * Metoda ce updateaza informatiile dintr-un nod al bazei de date, mai intai cautand instanta cu numele si cheia
     * primara trimise ca parametru, apoi identifica entitatea corespunzatoare si seteaza campurile ce se doresc
     * updatate la noile valori, prin metoda setValue.
     *
     * @param entityList - lista de entitati
     * @param tokenize - parametrii ce vor fi actualizati, precum si informatiile instantei
     * @param currentTime - timp-ul sistemului, ce va fi actualizat la instantele ce au fost updatate
     */
    public void updateNode(ArrayList<Entity> entityList, String[] tokenize, long currentTime){
        String name = tokenize[1];
        String key = tokenize[2];
        Instance foundInstance = null;

        for (Instance auxInstance : instanceTree){
            if (name.equals(auxInstance.getName())){
                if (key.equals(auxInstance.getPrimaryKey().getValue())){
                    foundInstance = auxInstance;
                    break;
                }
            }
        }

        if (foundInstance != null){
            Entity entity = null;
            for (Entity auxEntity : entityList){
                if (auxEntity.getName().equals(tokenize[1])){
                    entity = auxEntity;
                }
            }

            instanceTree.remove(foundInstance);
            int j = 0;
            for (int i = 3; i <tokenize.length; i += 2){
                int index = entity.findAttribute(tokenize[i]);
                Attribute auxAttribute = entity.getAttributeList().get(index);
                if(auxAttribute != null){
                    foundInstance.getAttributeList().get(index / 2).setValue(tokenize[i + 1]);
                }
            }

            foundInstance.setTimeStamp(currentTime);
            instanceTree.add(foundInstance);
        }
    }

    /**
     * Metoda sterge instantele cu un timeStamp mai mic decat cel trimis ca parametru (deci mai vechi), folosind o
     * lista auxiliara de instante, pentru a evita aruncarea unui ConcurrentModificationException daca se incearca
     * stergerea unei instante in timpul iterarii prin lista corespunzatoare acesteia.
     *
     * @param timestamp - timestamp-ul folosit ca referinta in stergerea instantelor
     */
    public void cleanNode(long timestamp){
        ArrayList<Instance> auxList = new ArrayList<>(); //ConcurrentModificationException

        for (Instance auxInstance : instanceTree){
            if (auxInstance.getTimeStamp() < timestamp){
                auxList.add(auxInstance);
            }
        }

        for(Instance auxInstance : auxList){
            instanceTree.remove(auxInstance);
        }

    }

    /**
     * Intoarce TreeSet-ul de instance.
     * @return - TreeSet-ul de instante
     */
    public TreeSet<Instance> getInstanceTree() {
        return this.instanceTree;
    }

    /**
     * Intoarce numarul nodului.
     * @return - numarul nodului
     */
    public int getNumber() {
        return this.number;
    }
}
