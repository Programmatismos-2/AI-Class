import java.io.BufferedReader;     //Διαβαζει το site που του δινουμε σε bytes 
import java.io.IOException;        // Εντοπίζει/έχει exceptions
import java.io.InputStreamReader;  //Κανει τα Bytes characters , μπορει να δουλεψει με το buffer (BufferedReader in = new BufferedReader(new InputStreamReader(System.in));)
import java.io.OutputStream;       //Βαζει ενα string σε ενα textfile σε μορφη date
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection; //Αιτημα ια να χρησιμοποιηση ενος site
import java.net.URL;               //Εντοπιζει την διευθυνση του site  στο google 



public class FirstAttempt {
    
    public static void main(String[] args) {
        System.out.println(chatGPT("how are you?"));
    }
    
    
    
    public static String chatGPT(String message) {
        String url = "https://api.openai.com/v1/chat/completions";
        String apiKey = "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";
        String model = "gpt-3.5-turbo";
    
        try {

        URL object = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) object.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Authorization", "Bearer "+ apiKey);
        connection.setRequestProperty("Content-Type", "application/json");

        //Ζηταμε απο το chatgpt την απαντηση στην ερωτηση μας
        String body = "{\"model\": \"" + model + "\", \"messages\": [{\"role\": \"user\", \"content\": \"" + message + "\"}]}";
        connection.setDoOutput(true);
        OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
        writer.write(body); //γραφει τα bytes στο output stream
        writer.flush();     //γραφει τα δεδομενα στο output stream στον προορισμο
        writer.close();     //κλεινει το output stream

        //Εδω παιρνουμε την απαντηση
        BufferedReader input = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuffer responce = new StringBuffer(); //Το stringbuffer ειναι ενα mutable string δηλαδη μπορει να αλλαζει
        while ((inputLine = input.readLine()) != null) {
            responce.append(inputLine); //Η μεθοδος append βαζει το αποτελεσμα ενος string στην τιμη που θελουμε
        }
        input.close();
        
        return (responce.toString()); //.split("\"content\":\"")[1].split("\"")[0]).substring(4);



        } catch (IOException e) {
            throw new RuntimeException(e); // περιλαμβάνει ολες τις εξαιρεσεις
        }
    }
}
