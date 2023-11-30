package hansung.ac.movie;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {
    private ImageView posterImageView;
    private TextView titleTextView, voteAverageTextView, genresTextView, runtimeTextView, voteCountTextView, taglineTextView, overviewTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_page);

        posterImageView = findViewById(R.id.poster_image);
        titleTextView = findViewById(R.id.title_text);
        voteAverageTextView = findViewById(R.id.vote_average_text);
        genresTextView = findViewById(R.id.genres_text);
        runtimeTextView = findViewById(R.id.runtime_text);
        voteCountTextView = findViewById(R.id.vote_count_text);
        taglineTextView = findViewById(R.id.tagline_text);
        overviewTextView = findViewById(R.id.overview_text);

        // Get data from intent
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            int poster = extras.getInt("poster");
            String title = extras.getString("title");
            double voteAverage = extras.getDouble("vote_average");
            String genres = extras.getString("genres");
            int runtime = extras.getInt("runtime");
            int voteCount = extras.getInt("vote_count");
            String tagline = extras.getString("tagline");
            String overview = extras.getString("overview");

            // Set data to views
            posterImageView.setImageResource(poster);
            titleTextView.setText(title);
            voteAverageTextView.setText(String.valueOf(voteAverage));
            genresTextView.setText(genres);
            runtimeTextView.setText("/ " + String.valueOf(runtime) + "분");
            voteCountTextView.setText("별점을 남긴 사람: " + voteCount + "명");
            taglineTextView.setText("\"" + tagline + "\"");
            overviewTextView.setText(overview);
        }
    }
}
