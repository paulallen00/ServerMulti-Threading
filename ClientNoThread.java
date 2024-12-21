
import java.io.*;            //java.io.*: Per gestire input e output, come la lettura e scrittura di dati attraverso la rete.
import java.net.*;           //java.io.*: Per gestire input e output, come la lettura e scrittura di dati attraverso la rete.
import java.util.Scanner;   // java.util.Scanner: Per leggere l'input dell'utente dalla console.

public class ClientNoThread {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);  // dichiariamo uno scanner

        try (
            Socket socket = new Socket("127.0.0.1", 9998);
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))
        ) {
        
            String welcomeMessage = reader.readLine();
            System.out.println("Server: " + welcomeMessage);  // messaggio benvenuto dal server

        
            System.out.print("DAMMI UN INT COMPRESO TRA 1 E 9: ");  
            int number = scanner.nextInt();  // immagazzino da tastiera l'int da mandare a server
            writer.write(number + "\n"); // spediamolo al server
            writer.flush();  // ripulisci il writer per evitare errori ed pronto per essere riusato

            // Receiving the server's response
            String serverResponse = reader.readLine();  // dichiaro una STR e ci immagazzino risposta dal server
            System.out.println("Server: " + serverResponse);  // stampo a schermo al client il suo risultato che voleva


            /*
            System.out.print("IL MESSAGGIO DA CIFRARE ");  
            String messaggio = scanner.nextLine();
            writer.write(messaggio + "\n");
            */


        } catch (IOException e) {
            System.err.println("Communication error: " + e.getMessage()); // risoluzione del catch in caso di errori
        } finally {
            scanner.close(); // chiudo lo scanner liberando risorse
        }
    }
}