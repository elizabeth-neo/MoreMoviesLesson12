package sg.edu.rp.c346.id22025164.moremovieslesson12;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText etMovie;
    EditText etGenre;
    EditText etYear;
    Spinner spnRating;
    Button btnInsert,btnShowList;
    ArrayList<Movie> alMovie;
    String spnValue;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etMovie = findViewById(R.id.etMovieTitle);
        etGenre = findViewById(R.id.etGenre);
        etYear = findViewById(R.id.etYearOfRelease);
        spnRating = findViewById(R.id.spinner);
        btnInsert = findViewById(R.id.btnInsert);
        btnShowList = findViewById(R.id.btnShowList);
        alMovie = new ArrayList<>();


        spnRating.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Handle item selection here
                switch (position){
                    case 0:
                        spnValue = "G";
                        break;
                    case 1:
                        spnValue = "PG";
                        break;
                    case 2:
                        spnValue = "PG13";
                        break;
                    case 3:
                        spnValue = "NC16";
                        break;
                    case 4:
                        spnValue = "M18";
                        break;
                    case 5:
                        spnValue = "R21";
                        break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Handle the case where nothing is selected (optional)
            }
        });

        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper db = new DBHelper(MainActivity.this);
                db.insertMovie(etMovie.getText().toString(), etGenre.getText().toString(), Integer.parseInt(etYear.getText().toString()), spnValue);
                Toast.makeText(MainActivity.this, "Added successfully",
                        Toast.LENGTH_SHORT).show();
                etMovie.setText("");
                etGenre.setText("");
                etYear.setText("");
                db.close();
            }
        });

        btnShowList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper db = new DBHelper(MainActivity.this);

                // Retrieve all songs from the database
                ArrayList<Movie> movieList = db.getAllMovies();

                // Pass the songList to the SecondActivity
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                intent.putExtra("movieList", movieList);
                startActivity(intent);

                db.close();
            }});

    }

}