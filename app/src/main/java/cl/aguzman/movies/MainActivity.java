package cl.aguzman.movies;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import cl.aguzman.movies.models.Movie;

public class MainActivity extends AppCompatActivity {

    private List<Movie> listMovie;
    public static final String MOVIE_ID = "cl.aguzman.movies.KEY.MOVIE_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText nameMovieEditText = (EditText) findViewById(R.id.movieNameEditText);
        Button saveMovieBtn = (Button) findViewById(R.id.saveMovieBtn);
        Button lastMovieBtn = (Button) findViewById(R.id.lastMovieBtn);

        saveMovieBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameMovie = nameMovieEditText.getText().toString().trim();
                if(nameMovie.length() > 0){
                    Movie film = new Movie(nameMovie, false);
                    film.save();
                    nameMovieEditText.setText("");
                    Toast.makeText(MainActivity.this, "Pèlicula Guardada", Toast.LENGTH_SHORT).show();
                    listMovie = getMovies();
                }else {
                    Toast.makeText(MainActivity.this, "Ingresa Nombre de la Pelìcula", Toast.LENGTH_SHORT).show();
                }

            }
        });

        lastMovieBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int sizeListMovie = listMovie.size();
                if(sizeListMovie > 0){
                    Movie movie = listMovie.get(sizeListMovie-1);
                    long movieId = movie.getId();
                    Intent intent = new Intent(MainActivity.this, MovieActivity.class);
                    intent.putExtra(MOVIE_ID, movieId);
                    startActivity(intent);
                }else{
                    Toast.makeText(MainActivity.this, "No hay pelìculas", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        listMovie = getMovies();
    }

    //get Movies
    public List<Movie> getMovies(){
        return Movie.find(Movie.class, "watched = 0");
    }
}