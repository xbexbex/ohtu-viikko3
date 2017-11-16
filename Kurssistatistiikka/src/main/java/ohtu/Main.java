package ohtu;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import java.io.IOException;
import org.apache.http.client.fluent.Request;
import com.google.gson.JsonParser;
import com.google.gson.JsonObject;

public class Main {

    public static void main(String[] args) throws IOException {
        // vaihda oma opiskelijanumerosi seuraavaan, ÄLÄ kuitenkaan laita githubiin omaa opiskelijanumeroasi
        String studentNr = "011120775";
        if ( args.length>0) {
            studentNr = args[0];
        }

        String url = "https://studies.cs.helsinki.fi/ohtustats/students/"+studentNr+"/submissions";

        String bodyText = Request.Get(url).execute().returnContent().asString();
        
        String ohtuUrl = "https://studies.cs.helsinki.fi/ohtustats/courseinfo";
        
        String ohtuText = Request.Get(ohtuUrl).execute().returnContent().asString();
        
        Gson mapper = new Gson();
        Courseinfo courseinfo = mapper.fromJson(ohtuText, Courseinfo.class);
                
        System.out.println("Kurssi: " + courseinfo.getName() + ", " + courseinfo.getTerm());
        System.out.println("");
        
        System.out.println("opiskelijanumero: " + studentNr);
        System.out.println("");
        
        Submission[] subs = mapper.fromJson(bodyText, Submission[].class);

        int tunnit = 0;
        int tehtavat = 0;
        for (Submission submission : subs) {
            System.out.println(" viikko " + submission + ":");
            System.out.println("   tehtyjä tehtäviä yhteensä: " + submission.getExercises().length + 
                    ", aikaa kului " + submission.getHours() + ", tehdyt tehtävät: " 
                    + String.join(" ", submission.getExercises()));
            tunnit += submission.getHours();
            tehtavat += submission.getExercises().length;
        }
        System.out.println("");
        System.out.println("yhteensä: " + tehtavat + " tehtävää " + tunnit + " tuntia");
        
        String statsUrl = "https://studies.cs.helsinki.fi/ohtustats/stats";
        String statsResponse = Request.Get(statsUrl).execute().returnContent().asString();
        JsonParser parser = new JsonParser();
        JsonObject parsedData = parser.parse(statsResponse).getAsJsonObject();
        int palautukset = 0;
        int kaikkiTehtavat = 0;
        for (int i = 1; i <= parsedData.size(); i++) {
            JsonObject pd = parsedData.get("" + i).getAsJsonObject();
            palautukset += Integer.parseInt(pd.get("students").toString());
            kaikkiTehtavat += Integer.parseInt(pd.get("exercise_total").toString());
        }
        System.out.println("");
        System.out.println("kurssilla yhteensä " + palautukset + " palautusta, palautettuja"
                + "tehtäviä " + kaikkiTehtavat + " kpl");

    }
}