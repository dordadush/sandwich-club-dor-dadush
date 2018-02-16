package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    private static final String JSON_NAME = "name";
    private static final String JSON_MAIN_NAME = "mainName";
    private static final String JSON_KNOWN_AS = "alsoKnownAs";
    private static final String JSON_PLACE_OF_ORIGIN = "placeOfOrigin";
    private static final String JSON_DESCRIPTION = "description";
    private static final String JSON_IMAGE = "image";
    private static final String JSON_INGREDIENTS = "ingredients";

    public static Sandwich parseSandwichJson(String json) {
        try {
            JSONObject sandwichJson = new JSONObject(json);

            // Parse mainName and alsoKnownAs from JSON object
            JSONObject name = sandwichJson.getJSONObject(JSON_NAME);

            String mainName = name.getString(JSON_MAIN_NAME);

            List<String> alsoKnownAs = new ArrayList<>();
            JSONArray alsoKnownAsJsonArray = name.getJSONArray(JSON_KNOWN_AS);

            // Iterate on the JSONArray and add to our List
            for (int i = 0; i < alsoKnownAsJsonArray.length(); i++) {
                alsoKnownAs.add(alsoKnownAsJsonArray.getString(i));
            }


            // Parse Place of Origin
            String placeOfOrigin = sandwichJson.getString(JSON_PLACE_OF_ORIGIN);

            // Parse Description
            String description = sandwichJson.getString(JSON_DESCRIPTION);

            // Parse Image URL
            String image = sandwichJson.getString(JSON_IMAGE);

            // Parse Ingredients
            List<String> ingredients = new ArrayList<>();
            JSONArray ingredientsJsonArray = sandwichJson.getJSONArray(JSON_INGREDIENTS);

            for (int i = 0; i < ingredientsJsonArray.length(); i++) {
                ingredients.add(ingredientsJsonArray.getString(i));
            }

            return new Sandwich(mainName,
                    alsoKnownAs,
                    placeOfOrigin,
                    description,
                    image,
                    ingredients);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }
}
