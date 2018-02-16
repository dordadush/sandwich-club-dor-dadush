package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import org.w3c.dom.Text;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    /**
     * Helper function I created to create a comma separated string out of a list of strings
     * @param listOfStrings - List of strings
     * @return "None" if list is empty, comma separated string otherwise.
     */
    private String createStringFromList(List<String> listOfStrings) {
        String result;

        if (listOfStrings.size() == 0) {
            // If it's an empty list, show the word None
            result = getResources().getString(R.string.none);
        } else {
            result = TextUtils.join(", ", listOfStrings);
        }

        return result;
    }

    /**
     * Helper function I created to show "Not Available" in case the string is empty.
     * @param detail - string given
     * @return a result string in the format to show the user
     */
    private String buildDetailString(String detail) {
        return TextUtils.isEmpty(detail) ? getString(R.string.not_available) : detail;
    }

    private void populateUI(Sandwich sandwich) {
        // Description TextView
        String description = sandwich.getDescription();
        TextView descriptionTextView = findViewById(R.id.description_tv);
        descriptionTextView.setText(buildDetailString(sandwich.getDescription()));

        // AlsoKnownAs TextView
        TextView alsoKnownAsTextView = findViewById(R.id.also_known_tv);
        alsoKnownAsTextView.setText(createStringFromList(sandwich.getAlsoKnownAs()));

        // Place of Origin TextView
        TextView placeOfOriginTextView = findViewById(R.id.origin_tv);
        placeOfOriginTextView.setText(buildDetailString(sandwich.getPlaceOfOrigin()));

        // Ingredients TextView
        TextView ingredientsTextView = findViewById(R.id.ingredients_tv);
        ingredientsTextView.setText(createStringFromList(sandwich.getIngredients()));
    }
}
