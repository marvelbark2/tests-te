package json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;

import java.util.List;

public class App {
    public static void main(String[] args) throws JsonProcessingException {
        String json = "{\"event\":\"login\",\"data\":{\"username\":\"younes\",\"password\":\"1234\"}}";
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode jsonNode = objectMapper.readTree(json);
            String loginData = jsonNode.get("data").toString();
            Login login = objectMapper.readValue(loginData, Login.class);
            String loginJson = objectMapper.writeValueAsString(login);
            System.out.println(loginJson);
            String usr = jsonNode.get("data").get("username").toString();
            System.out.println(usr);

        } catch (Exception e) {
            e.printStackTrace();
        }

        String json2 = "{\"event\":\"login\",\"data\":[{\"username\":\"younes\",\"password\":\"1234\"},{\"username\":\"younes2\",\"password\":\"1234\"},{\"username\":\"younes3\",\"password\":\"1234\"}]}";
        JsonNode node = objectMapper.readTree(json2);
        List<Login> loginList = objectMapper.readValue(node.get("data").toString(), new TypeReference<>(){});
        System.out.println(loginList);

    }

}
