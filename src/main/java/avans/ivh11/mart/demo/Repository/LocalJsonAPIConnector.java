package avans.ivh11.mart.demo.Repository;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class LocalJsonAPIConnector {

    private final String apiURL = "http://localhost:8080/products.json";

    public JSONArray getProducts() {
        return this.callAPI(this.apiURL).getJSONArray("products");
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
