
import java.io.*;
import java.net.*;

public class MultiServer implements Runnable {
    private Socket clientSocket; //clientSocket: Rappresenta il canale di comunicazione con il client.
    private BufferedWriter writer; // Permette di inviare messaggi al client.
    private BufferedReader reader; // permette di leggere dal client

    public MultiServer(Socket clientSocket) {
        this.clientSocket = clientSocket; 
        try {  // try
            writer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream())); // flusso wr e reader associati al socket
            reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (IOException e) {
            System.err.println("Error initializing communication: " + e.getMessage()); // fallito il try, comunicazione non puo' avvenire
        }
    }

    @Override
    public void run() {
        try { //  apriamo subito un try
            
            writer.write("Benvenuto al server, mandami un messaggio \n"); // diciamo al client di mandarci int
            writer.flush(); // ripulisci canale

           
            String input = reader.readLine(); // leggi la variabile spedita dal client
            try { // apriamo un'altro try
                int number = Integer.parseInt(input);  // ricevuto come STRING quindi convertiamo in int per calcolare
                if (number >= 1 && number <= 9) {
                    writer.write("OK\n"); // rispondiamo al client con output ricevuto
                } else {
                    writer.write("Errore\n"); // hai mandato al server un numero non valido
                }
            } catch (IOException e) {
                  // exception gestire numeri non validi
                writer.write("Error numero invalido"); // in questo caso dammi un input valido
            }
            writer.flush(); // ripulisci canale
        } catch (IOException e) { // eccezzioni gestire input output errori
            System.err.println("Communication error: " + e.getMessage()); // stampa a schermo errore comunicazione
        } finally {
            try {
                clientSocket.close(); // finito quindi PROVIAMO A chiudere comunicazione
            } catch (IOException e) {
                System.err.println("Errore a chiudere la comunicazione: " + e.getMessage()); // dagli messaggio errore in chiusura
            }
        }
    }

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(9998)) { // avvia il server in ascolto in porta 9999
            System.out.println("Server is running on port 9998...");
            while (true) { // per mantenere aperto il server
                Socket clientSocket = serverSocket.accept(); // se ricevuta una richiestacdi comunicazione allora accetta
                System.out.println("Client connected."); 
                new Thread(new MultiServer(clientSocket)).start(); //Crea un nuovo thread per gestire il client, in modo che il server possa accettare altre connessioni contemporaneamente.
            }
        } catch (IOException e) {
            System.err.println("Server error: " + e.getMessage()); // se non possibile avviare il server lo comunica con IOException
        }
    }
}