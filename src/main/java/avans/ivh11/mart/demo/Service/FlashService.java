package avans.ivh11.mart.demo.Service;

import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class FlashService {

    /**
     * creates a HashMap with a type and text for usage of flashmessage
     *
     * @param type
     * @param text
     * @return
     */
    public HashMap<String, String> createFlash(String type, String text)
    {
        HashMap<String, String> flash = new HashMap<>();
        flash.put("type", type);
        flash.put("text", text);

        return flash;
    }
}
