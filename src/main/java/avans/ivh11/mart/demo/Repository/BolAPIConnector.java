package avans.ivh11.mart.demo.Repository;

import com.fasterxml.jackson.databind.util.JSONPObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import org.json.*;

public class BolAPIConnector {

    private final String apiURL = "https://api.bol.com/catalog/v4";
    private final String apiKey = "25C4742A92BF468EB2BD888FC8FBFF40";
    private final String apiFormat = "json";

    private final String computerCategoryID = "3134";

    public JSONArray getComputerCategoryProducts() {
        String api = apiURL + "/lists/?ids=" + computerCategoryID + "&apikey=" + apiKey + "&format=" + apiFormat;
        return this.callAPI(api).getJSONArray("products");
    }

    private JSONObject callAPI(String apiUrl)
    {
        InputStream inputStream = null;
        BufferedReader reader = null;
        String response = "";

        try {
            URL url = new URL(apiUrl);
            URLConnection connection = url.openConnection();

            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            response = reader.readLine().toString();
            String line;
            while ((line = reader.readLine()) != null) {
                response += line;
            }
        } catch (MalformedURLException e) {
//            LOGGER.e("TAG", e.getLocalizedMessage());
            return null;
        } catch (IOException e) {
//            Log.e("TAG", e.getLocalizedMessage());
            return null;
        } catch (Exception e) {
//            Log.e("TAG", e.getLocalizedMessage());
            return null;
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
//                    Log.e("TAG", e.getLocalizedMessage());
                    return null;
                }
            }
        }

        return new JSONObject(response);
    }
}
