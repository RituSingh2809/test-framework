package utility;


import org.everit.json.schema.Schema;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.testng.Assert;

public class JsonSchemaValidator {

    public static boolean performSchemaValidation(String jsonResponse, String jsonSchema) {
        try {

            JSONObject rawSchema = new JSONObject(new JSONTokener(jsonSchema));
            Schema schema = SchemaLoader.load(rawSchema);
            schema.validate(new JSONObject(jsonResponse));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }


    public static void assertSchemaValidation(String jsonResponse, String jsonSchema) {
        boolean isValid = performSchemaValidation(jsonResponse, jsonSchema);
        Assert.assertTrue(isValid, "API response does not match the JSON schema");
    }
}
