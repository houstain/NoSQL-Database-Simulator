//Diaconescu Florin, 322CB

import java.io.*;

/**
 * Clasa principala a programului, folosita pentru parsarea input-ului din fisierul de intrare, generarea fisierului
 * de output, inchiderea acestora si tratarea exceptiilor.
 *
 * @author florin.diaconescu
 */
public class Tema2 {
    /**
     * Deschide fisierele de intrare, respectiv de iesire, creeaza baza de date in care se vor efectua operatiile,
     * cu dimensiunile specificate, apoi parseaza comenzile, apeland metodele din clasa Database, pentru prelucrarea
     * bazei de date implementate. De asemenea, trateaza exceptiile care pot aparea la operatiile cu fisiere.
     *
     * @param args - argumentele primite in linia de comanda
     * @throws FileNotFoundException
     * @throws IOException
     */

    public static void main(String[] args) throws FileNotFoundException, IOException{
        Database database;
        try{
            BufferedReader br = new BufferedReader(new FileReader(args[0]));
            PrintWriter writer;
            writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(args[0]+"_out")));

            String fileRead = br.readLine();
            String[] tokenize = fileRead.split(" ");

            String databaseName = tokenize[1];
            int noNodes = Integer.parseInt(tokenize[2]);
            int maxCapacity = Integer.parseInt(tokenize[3]);
            database = new Database(databaseName, noNodes, maxCapacity);

            while (fileRead != null){
                tokenize = fileRead.split(" ");
                if ("CREATE".equals(tokenize[0])){
                    database.createEntity(tokenize);
                }
                else if ("INSERT".equals(tokenize[0])){
                    database.insertInstance(tokenize);
                }
                else if ("DELETE".equals(tokenize[0])){
                    database.deleteInstance(tokenize, writer);
                }
                else if ("UPDATE".equals(tokenize[0])){
                    database.updateInstance(tokenize);
                }
                else if ("GET".equals(tokenize[0])){
                    database.getInstance(tokenize, writer);
                }
                else if ("SNAPSHOTDB".equals(tokenize[0])){
                    database.snapshotDB(writer);
                }
                else if ("CLEANUP".equals(tokenize[0])){
                    database.cleanup(tokenize);
                }
                fileRead = br.readLine();
            }

            br.close();
            writer.close();
        }
        catch (FileNotFoundException e){
            System.out.println("File not found!");
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}
