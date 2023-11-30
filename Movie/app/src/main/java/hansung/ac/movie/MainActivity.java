package hansung.ac.movie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<MetaData> metaDatas = new ArrayList<>();
    private LinearLayout moviesLayout, actionLayout, sfLayout, animationLayout;
    private TextView title, top20, action, sf, animation;
    private ImageButton search;
    private View search_page;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        readMetaData();

        title = findViewById(R.id.title);
        top20 = findViewById(R.id.top20);
        action = findViewById(R.id.action);
        sf = findViewById(R.id.sf);
        animation = findViewById(R.id.animation);
        search = findViewById(R.id.search);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 검색 페이지를 띄우는 코드 추가
                showSearchPage();
            }
        });

        moviesLayout = findViewById(R.id.movies_layout);
        actionLayout = findViewById(R.id.action_layout);
        sfLayout = findViewById(R.id.sf_layout);
        animationLayout = findViewById(R.id.animation_layout);
        populateTop20Movies();
        populateTop20ActionMovies();
        populateTop20SfMovies();
        populateTop20AnimationMovies();
    }

    private void readMetaData() {
        InputStream is = getResources().openRawResource(R.raw.movies_metadata);
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(is, Charset.forName("UTF-8"))
        );
        String line = "";
        try {
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                // split by ','
                String[] tokens = line.split(",");
                // read the data
                MetaData data = new MetaData();
                data.setId(Integer.parseInt(tokens[0]));
                data.setOverview(tokens[1]);
                data.setRuntime(Integer.parseInt(tokens[2]));
                data.setTagline(tokens[3]);
                data.setTitle(tokens[4]);
                data.setVote_average(Double.parseDouble(tokens[5]));
                data.setVote_count(Integer.parseInt(tokens[6]));
                data.setGenres((tokens[7]));
                metaDatas.add(data);

                Log.d("MyActivity", "Just created: " + data);
            }
        } catch (IOException e) {
            Log.wtf("MyActivity", "Error reading datafile on line" + line, e);
            e.printStackTrace();
        }
    }

    private void populateTop20Movies() {
        Collections.sort(metaDatas, new Comparator<MetaData>() {
            @Override
            public int compare(MetaData movie1, MetaData movie2) {
                return Integer.compare(movie2.getVote_count(), movie1.getVote_count());
            }
        });

        // Add top 20 movies to the layout
        int count = Math.min(20, metaDatas.size());
        for (int i = 0; i < count; i++) {
            MetaData movie = metaDatas.get(i);
            addMovieToLayout(movie, moviesLayout);
        }
    }

    private void populateTop20ActionMovies() {
        // Filter movies by genre "action"
        List<MetaData> actionMovies = new ArrayList<>();
        for (MetaData movie : metaDatas) {
            if (movie.getGenres().contains("action")) {
                actionMovies.add(movie);
            }
        }

        Collections.sort(actionMovies, new Comparator<MetaData>() {
            @Override
            public int compare(MetaData movie1, MetaData movie2) {
                return Integer.compare(movie2.getVote_count(), movie1.getVote_count());
            }
        });

        int numMovies = Math.min(20, actionMovies.size());

        for (int i = 0; i < numMovies; i++) {
            MetaData movie = actionMovies.get(i);
            addMovieToLayout(movie, actionLayout);
        }
    }

    private void populateTop20SfMovies() {
        // Filter movies by genre "sf"
        List<MetaData> sfMovies = new ArrayList<>();
        for (MetaData movie : metaDatas) {
            if (movie.getGenres().contains("sf")) {
                sfMovies.add(movie);
            }
        }

        Collections.sort(sfMovies, new Comparator<MetaData>() {
            @Override
            public int compare(MetaData movie1, MetaData movie2) {
                return Integer.compare(movie2.getVote_count(), movie1.getVote_count());
            }
        });

        int numMovies = Math.min(20, sfMovies.size());

        for (int i = 0; i < numMovies; i++) {
            MetaData movie = sfMovies.get(i);
            addMovieToLayout(movie, sfLayout);
        }
    }

    private void populateTop20AnimationMovies() {
        // Filter movies by genre "animation"
        List<MetaData> animationMovies = new ArrayList<>();
        for (MetaData movie : metaDatas) {
            if (movie.getGenres().contains("animation")) {
                animationMovies.add(movie);
            }
        }

        Collections.sort(animationMovies, new Comparator<MetaData>() {
            @Override
            public int compare(MetaData movie1, MetaData movie2) {
                return Integer.compare(movie2.getVote_count(), movie1.getVote_count());
            }
        });

        int numMovies = Math.min(20, animationMovies.size());

        for (int i = 0; i < numMovies; i++) {
            MetaData movie = animationMovies.get(i);
            addMovieToLayout(movie, animationLayout);
        }
    }

    private void addMovieToLayout(MetaData movie, LinearLayout layout) {
        LinearLayout movieLayout = new LinearLayout(this);
        movieLayout.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
        layoutParams.setMargins(25, 0, 0, 0);
        movieLayout.setLayoutParams(layoutParams);

        LinearLayout.LayoutParams itemParams = new LinearLayout.LayoutParams(
                300, LinearLayout.LayoutParams.WRAP_CONTENT);

        ImageView imageView = new ImageView(this);
        imageView.setImageResource(R.drawable.poster_sample);
        LinearLayout.LayoutParams imageParams = new LinearLayout.LayoutParams(
                300, 400);
        imageView.setLayoutParams(imageParams);
        imageParams.setMargins(0, 7, 7, 7);
        movieLayout.addView(imageView);

        TextView textView = new TextView(this);
        textView.setText(movie.getTitle());
        textView.setTextColor(Color.WHITE);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
        textView.setLayoutParams(itemParams);
        itemParams.setMargins(5, 2, 0, 0);
        movieLayout.addView(textView);

        movieLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 영화 상세 페이지로 이동하는 코드 추가
                showDetailPage(movie);
            }
        });

        layout.addView(movieLayout);
    }

    private void showSearchPage() {
        search_page = View.inflate(MainActivity.this, R.layout.search_page, null);
        setContentView(search_page);

        Button searchButton = search_page.findViewById(R.id.search_button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 검색 버튼 클릭 시 영화 검색 및 결과 표시
                performSearch();
            }
        });
    }

    private void performSearch() {
        TextView searchKeyword = search_page.findViewById(R.id.search_keyword);
        String keyword = searchKeyword.getText().toString().trim();

        if (!keyword.isEmpty()) {
            List<MetaData> searchResults = searchMovies(keyword);

            if (searchResults.isEmpty()) {
                Toast.makeText(this, "검색 결과가 없습니다.", Toast.LENGTH_SHORT).show();
            } else {
                displaySearchResults(searchResults);
            }
        } else {
            Toast.makeText(this, "검색어를 입력해주세요.", Toast.LENGTH_SHORT).show();
        }
    }

    private List<MetaData> searchMovies(String keyword) {
        List<MetaData> results = new ArrayList<>();

        for (MetaData movie : metaDatas) {
            if (movie.getTitle().toLowerCase().contains(keyword.toLowerCase())) {
                results.add(movie);
            }
        }

        // Sort search results by vote average in descending order
        Collections.sort(results, new Comparator<MetaData>() {
            @Override
            public int compare(MetaData movie1, MetaData movie2) {
                return Double.compare(movie2.getVote_average(), movie1.getVote_average());
            }
        });

        // Limit the number of search results to 5
        return results.subList(0, Math.min(5, results.size()));
    }

    private void displaySearchResults(List<MetaData> searchResults) {
        LinearLayout searchResultsLayout = search_page.findViewById(R.id.search_results_layout);
        searchResultsLayout.removeAllViews();

        for (MetaData movie : searchResults) {
            LinearLayout movieLayout = new LinearLayout(this);
            movieLayout.setOrientation(LinearLayout.HORIZONTAL);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(25, 0, 25, 30);
            movieLayout.setLayoutParams(layoutParams);

            LinearLayout.LayoutParams itemParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            ImageView imageView = new ImageView(this);
            imageView.setImageResource(R.drawable.poster_sample);
            LinearLayout.LayoutParams imageParams = new LinearLayout.LayoutParams(
                    300, 400);
            imageView.setLayoutParams(imageParams);
            imageParams.setMargins(0, 7, 14, 15);
            movieLayout.addView(imageView);

            LinearLayout textLayout = new LinearLayout(this);
            textLayout.setOrientation(LinearLayout.VERTICAL);
            LinearLayout.LayoutParams textLayoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            textLayout.setLayoutParams(textLayoutParams);

            TextView titleTextView = new TextView(this);
            titleTextView.setText(movie.getTitle());
            titleTextView.setTextColor(Color.WHITE);
            titleTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
            titleTextView.setLayoutParams(itemParams);
            itemParams.setMargins(10, 2, 0, 15);
            textLayout.addView(titleTextView);

            TextView overviewTextView = new TextView(this);
            String overview = movie.getOverview();
            if (overview.length() > 100) {
                overview = overview.substring(0, 100) + "...";
            }
            overviewTextView.setText(overview);
            overviewTextView.setTextColor(Color.WHITE);
            overviewTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            overviewTextView.setLayoutParams(itemParams);
            itemParams.setMargins(10, 10, 0, 0);
            textLayout.addView(overviewTextView);

            movieLayout.addView(textLayout);

            movieLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 영화 상세 페이지로 이동하는 코드 추가
                    showDetailPage(movie);
                }
            });

            searchResultsLayout.addView(movieLayout);
        }
    }


    private void showDetailPage(MetaData movie) {
        // 상세 페이지로 이동하는 로직 작성
        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
        intent.putExtra("poster", R.drawable.poster_sample);
        intent.putExtra("title", movie.getTitle());
        intent.putExtra("vote_average", movie.getVote_average());
        intent.putExtra("genres", movie.getGenres());
        intent.putExtra("runtime", movie.getRuntime());
        intent.putExtra("vote_count", movie.getVote_count());
        intent.putExtra("tagline", movie.getTagline());
        intent.putExtra("overview", movie.getOverview());
        startActivity(intent);
    }
}