package com.sptech.codesafe;



import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import org.json.JSONObject;

public class Slack {

    private static FileHandler fh;
    private static Logger logger = Logger.getLogger("SlackLogger");
    
    private static HttpClient client = HttpClient.newHttpClient();
    private static final String url = "https://hooks.slack.com/services/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";

    public static void enviarMensagem(JSONObject content) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder(URI.create(url))
                .header("accept", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(content.toString()))
                .build();
        
         try {  
             fh = new FileHandler("../SlackLog.log");  
             logger.addHandler(fh);
             SimpleFormatter formatter = new SimpleFormatter();  
             fh.setFormatter(formatter);  

         } catch (SecurityException | IOException e) {  
             logger.severe(String.format("Erro ao inicializar logs em txt: %s", e));
         }
         
        logger.info(String.format("Slack - Enviando mensagem: %s", content.toString()));

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        logger.info(String.format("Slack - Mensagem Enviada, Respostas: StatusCode: %s Body: ", response.statusCode(), response.body()));
        System.out.println(String.format("Status: %s", response.statusCode()));
        System.out.println(String.format("Responde: %s", response.body()));
    }

}
