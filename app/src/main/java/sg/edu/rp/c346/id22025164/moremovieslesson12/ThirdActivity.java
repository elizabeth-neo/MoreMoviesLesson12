package sg.edu.rp.c346.id22025164.moremovieslesson12;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class ThirdActivity extends AppCompatActivity {

    EditText etID;
    EditText etMovie;
    EditText etGenre;
    EditText etYear;
    Spinner spnRating;
    Button btnUpdate,btnDelete,btnCancel;
    Movie data;
    String spnValue;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        etID = findViewById(R.id.etMovieID);
        etMovie = findViewById(R.id.etMovieTitle);
        etGenre = findViewById(R.id.etGenre);
        etYear = findViewById(R.id.etYearOfRelease);
        spnRating = findViewById(R.id.spinner);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);
        btnCancel = findViewById(R.id.btnCancel);

        Intent i = getIntent();
        data = (Movie) i.getSerializableExtra("data");

        etID.setText(String.valueOf(data.getId()));
        etMovie.setText(String.valueOf(data.getTitle()));
        etGenre.setText(String.valueOf(data.getGenre()));
        etYear.setText(String.valueOf(data.getYear()));

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

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbh = new DBHelper(ThirdActivity.this);
                data.setTitle(etMovie.getText().toString());
                data.setGenre(etGenre.getText().toString());
                data.setYear(Integer.valueOf(etYear.getText().toString()));
                data.setRating(spnValue);
                dbh.updateMovie(data);
                dbh.close();
                finish();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder myBuilder = new AlertDialog.Builder(ThirdActivity.this);
                myBuilder.setTitle("Danger");
                myBuilder.setMessage("Are you sure you want to delete the movie\n" + data.getTitle());
                myBuilder.setCancelable(false);
                myBuilder.setNeutralButton("CANCEL",null);

                myBuilder.setNegativeButton("DELETE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DBHelper dbh = new DBHelper(ThirdActivity.this);
                        dbh.deleteMovie(data.getId());
                        finish();
                    }
                });
                AlertDialog myDialog = myBuilder.create();
                myDialog.show();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder myBuilder = new AlertDialog.Builder(ThirdActivity.this);
                myBuilder.setTitle("Danger");
                myBuilder.setMessage("Are you sure you want discard the changes");
                myBuilder.setCancelable(false);
                myBuilder.setNeutralButton("DO NOT DISCARD",null);

                myBuilder.setNegativeButton("DISCARD", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
                AlertDialog myDialog = myBuilder.create();
                myDialog.show();
            }
        });
    }
}