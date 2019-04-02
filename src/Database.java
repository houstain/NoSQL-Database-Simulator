//Diaconescu Florin, 322CB

import java.util.ArrayList;
import java.io.*;

/**
 * Clasa care modeleaza o baza de date, avand ca si caracteristici numele acesteia, numarul de noduri, capacitatea
 * maxima si doua ArrayList-uri, corespunzatoare listei de noduri si a celei de entitati.
 */
public class Database {
    private String name;
    private int noNodes;
    private int maxCapacity;
    private ArrayList<Node> nodeList;
    private ArrayList<Entity> entityList;

    public Database(){

    }

    /**
     * Constructor pentru o baza de date, in care se initializeaza parametrii acesteia, precum se si creeaza lista
     * de noduri, pentru a putea fi folosita in cadrul operatiilor ulterioare.
     *
     * @param name - numele bazei de date
     * @param noNodes - numarul de noduri
     * @param maxCapacity - capacitatea maxima a unui nod
     */
    public Database(String name, int noNodes, int maxCapacity) {
        this.name = name;
        this.noNodes = noNodes;
        this.maxCapacity = maxCapacity;
        this.nodeList = new ArrayList<>();
        this.entityList = new ArrayList<>();

        for (int i = 1; i <= noNodes; i++){
            Node aux = new Node(i, maxCapacity);
            nodeList.add(aux);
        }
    }

    /**
     * Metoda prin care se creaza o entitate cu numele si factorul de replicare dorit, iar mai apoi adauga atributele
     * de forma StringAttribute (pentru ca este vorba de tipul atributului). Dupa ce toate atributele au fost adaugate,
     * entitatea se adauga in lista de entitati.
     *
     * @param tokenize - numele, factorul de replicare al bazei de date, precum si atributele aferente
     */
    public void createEntity(String[] tokenize){
        Attribute auxAttribute;
        String entityName = tokenize[1];
        int replicationFactor = Integer.parseInt(tokenize[2]);
        int noAttributes = Integer.parseInt(tokenize[3]);

        Entity entity = new Entity(entityName, replicationFactor, noAttributes);

        for (int i = 4; i < tokenize.length; i += 2){
            if ("Integer".equals(tokenize[i + 1])) {
                auxAttribute = new StringAttribute(tokenize[i + 1], tokenize[i]);
                entity.addAtribute(auxAttribute);
            }
            else if ("Float".equals(tokenize[i + 1])){
                auxAttribute = new StringAttribute(tokenize[i + 1], tokenize[i]);
                entity.addAtribute(auxAttribute);
            }
            else{
                auxAttribute = new StringAttribute(tokenize[i + 1], tokenize[i]);
                entity.addAtribute(auxAttribute);
            }
            entity.attributeList.add(auxAttribute);
        }
        entityList.add(entity);
    }

    /**
     *  Metoda creaza o instanta cu atributele dorite, verificand tipul atributului din cadrul entitatii din care
     *  face parte, creand apoi, pe rand, atribute (Integer, String sau Float, dupa caz), pana la terminarea
     *  acestora. De asemenea, primul atribut va fi mereu cheia primara, fiind verificat acest aspect. La final, este
     *  returnata instanta proaspat creata.
     *
     * @param tokenize - valorile atributelor instantei ce va fi creata
     * @return - instanta ce tocmai a fost creata
     */
    public Instance createInstance(String[] tokenize){
        Entity auxEntity = findEntity(tokenize[1]);
        Instance auxInstance = new Instance(tokenize[1]);
        int j = 1;

        for (int i = 2; i < tokenize.length; i++){
            ArrayList<Attribute> attributeList = auxEntity.getAttributeList();
            Attribute auxAttribute = attributeList.get(j);
            String value = auxAttribute.getType();

            if (value.equals("Integer")){
                IntAttribute auxInt = new IntAttribute(value, tokenize[i]);
                if (auxInstance.getPrimaryKey() == null){
                    auxInstance.setPrimaryKey(auxInt);
                }
                auxInstance.getAttributeList().add(auxInt);
            }
            else if (value.equals("Float")){
                FloatAttribute auxFloat = new FloatAttribute(value, tokenize[i]);
                if (auxInstance.getPrimaryKey() == null){
                    auxInstance.setPrimaryKey(auxFloat);
                }
                auxInstance.getAttributeList().add(auxFloat);
            }
            else{
                StringAttribute auxString = new StringAttribute(value, tokenize[i]);
                if (auxInstance.getPrimaryKey() == null){
                    auxInstance.setPrimaryKey(auxString);
                }
                auxInstance.getAttributeList().add(auxString);
            }
            j += 2;
        }
        return auxInstance;
    }

    /**
     * Metoda doreste inserarea unei instante create pe baza atributelor trimise ca parametru, in 'replicationFactor'
     * noduri, atata timp cat nodul nu a ajuns deja la capacitate maxima. In cazul in care nu mai sunt noduri de
     * inserat, se creaza un nou nod, cu aceeasi capacitate maxima ca a celorlator noduri, ce va fi inserata in lista
     * de noduri.
     *
     * @param tokenize - valorile atributelor instantei ce se doreste inserata in noduri
     */
    public void insertInstance(String[] tokenize) {
        Entity auxEntity = findEntity(tokenize[1]);
        int replicationFactor = auxEntity.getReplicationFactor();
        Instance auxInstance = createInstance(tokenize);

        for (int i = 0; i < nodeList.size(); i++){
            Node auxNode = nodeList.get(i);
            if (replicationFactor == 0){
                break;
            }

            else{
                if (auxNode.getInstanceTree().size() < maxCapacity){
                    auxNode.insertInstance(auxInstance);
                    replicationFactor--;
                }
            }
        }

        if (replicationFactor > 0){
            for (int j = 0; j < replicationFactor; j++) {
                Node auxNode = new Node(this.noNodes + j + 1, this.maxCapacity);
                nodeList.add(auxNode);
                nodeList.get(this.noNodes + j).insertInstance(auxInstance);
            }

            this.noNodes += replicationFactor;
        }
    }

    /**
     * Metoda pentru identificarea (daca exista) a unei entitati din lista de entitati.
     *
     * @param name - numele entitatii ce se doreste a fi gasita
     * @return - returneaza entitatea gasita sau null, daca nu exista
     */
    public Entity findEntity(String name){
        for (int i = 0; i < entityList.size(); i++){
            if (name.equals(entityList.get(i).getName())){
                return entityList.get(i);
            }
        }
        return null;
    }

    /**
     * Metoda cauta in lista de instante a fiecarui nod din baza de date, daca aceasta exista, astfel eliminand-o
     * si setand un flag corespunzator. In caz contrar, se va afisa un mesaj de eroare.
     *
     * @param tokenize - numele si cheia primara a instantei ce va fi stearsa
     * @param writer - writer-ul pentru a scrie in fisierul de iesire
     */
    public void deleteInstance(String[] tokenize, PrintWriter writer){
        int noInstanceFoundFlag = 0;
        for (int i = 0; i < nodeList.size(); i++){
            Node auxNode = nodeList.get(i);
            if (auxNode.getInstanceTree().size() > 0){
                if(auxNode.deleteNode(tokenize[1], tokenize[2])){
                    noInstanceFoundFlag = 1;
                }
            }
        }

        if (noInstanceFoundFlag == 0){
            writer.println("NO INSTANCE TO DELETE");
        }

    }

    /**
     *  Metoda va cauta, in lista de noduri, daca exista instanta cu numele trimis ca parametru, iar in cazul in care
     *  este gasita, va afisa toate nodurile in care se afla si valorile atributelor sale. In cazul in care nicio
     *  astfel de instanta nu s-a gasit, va fi afisat un mesaj de eroare.
     *
     * @param tokenize - numele si cheia primara a instantei
     * @param writer - writer-ul pentru a scrie in fisierul de iesire
     */
    public void getInstance(String[] tokenize, PrintWriter writer){
        String name = tokenize[1];
        String key = tokenize[2];
        Instance foundInstance = null;

        for (int i =0; i < nodeList.size(); i++){
            Node auxNode = nodeList.get(i);
            Instance auxInstance = auxNode.findInstance(name, key);

            if (auxInstance != null){
                writer.write("Nod" + auxNode.getNumber() + " ");
                foundInstance = auxInstance;
            }
        }

        if (foundInstance != null){
            foundInstance.printInstance(entityList, writer);
            writer.write("\n");
        }
        else{
            writer.println("NO INSTANCE FOUND");
        }
    }

    /**
     * Metoda actualizeaza unul sau mai multe atribute ale unei instante, din toate nodurile in care este stocata,
     * setand, de asemenea, un nou timestamp.
     *
     * @param tokenize - numele si cheia primara a instantei, precum si atributele ce vor fi updatate
     */
    public void updateInstance(String[] tokenize){
        long currentTime = System.nanoTime();

        for (int i = 0; i < nodeList.size(); i++){
            Node auxNode = nodeList.get(i);
            if (auxNode.getInstanceTree().size() > 0){
                auxNode.updateNode(entityList, tokenize, currentTime);
            }
        }
    }

    /**
     * Metoda traverseaza fiecare nod al listei de noduri, care are cel putin o instanta inserata in el, urmand
     * sa afiseze informatia corespunzatoare a nodului. Daca nu exista niciun nod cu cel putin o instanta, se va
     * afisa un mesaj de eroare.
     *
     * @param writer - writer-ul corespunzator fisierului de iesire
     */
    public void snapshotDB(PrintWriter writer){
        int notEmptyFlag = 0;
        for (int i = 0; i < nodeList.size(); i++){
            Node auxNode = nodeList.get(i);
            if (auxNode.getInstanceTree().size() > 0){
                auxNode.printNode(entityList, writer);
                notEmptyFlag = 1;
            }
        }
        if (notEmptyFlag == 0){
            writer.println("EMPTY DB");
        }
    }

    /**
     * Metoda itereaza in lista de noduri, iar pentru fiecare nod care are cel putin o instanta, va elimina
     * acele instante care au un timestamp mai mic decat parametrul dorit (deci, mai vechi).
     *
     * @param tokenize - numele bazei de date si timestamp-ul folosit ca reper in stergerea instantelor
     */
    public void cleanup(String[] tokenize){
        String name = tokenize[1];
        long timestamp = Long.parseLong(tokenize[2]);

        for (int i = 0; i < nodeList.size(); i++) {
            Node auxNode = nodeList.get(i);
            if (auxNode.getInstanceTree().size() > 0){
                auxNode.cleanNode(timestamp);
            }
        }
    }

}
