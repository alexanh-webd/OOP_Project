package com.example.oop_project;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.oop_project.DataProvider.DataProvider;
import com.example.oop_project.container.DataContainer;
import com.example.oop_project.model.Lutemon;

public class cr_new_ltmActivity extends AppCompatActivity {
    private RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cr_new_ltm);
        radioGroup = findViewById(R.id.radioGroup);

        setupButtonListeners();

        //Button createButton = findViewById(R.id.btnCreate);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    private void setupButtonListeners() {
        Button btnCreate = findViewById(R.id.btnCreate);
        btnCreate.setOnClickListener(v -> {
            DataProvider.getInstance().addNewLutemon(createNewLutemon());
            finish();
        });
        Button btnCancel = findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(v -> {
            finish();
        });
    }
    private Lutemon createNewLutemon() {
        int selectedid = radioGroup.getCheckedRadioButtonId();
        RadioButton selectedRadio = findViewById(selectedid);
        EditText nameInput = findViewById(R.id.et_nameInput);
        System.out.println(nameInput.getText());
        String lutemonName = nameInput.getText().toString();
        Lutemon lutemon = null;
        if (selectedRadio.getId() == R.id.radioWhite) {
            lutemon = new Lutemon(lutemonName, "White", 5,4,0,20, "https://archives.bulbagarden.net/media/upload/thumb/1/1c/1008Miraidon.png/375px-1008Miraidon.png");
        } else if (selectedRadio.getId() == R.id.radioGreen) {
            lutemon = new Lutemon(lutemonName, "Green", 6, 3, 0, 19, "https://archives.bulbagarden.net/media/upload/thumb/a/a2/1007Koraidon.png/375px-1007Koraidon.png");
        } else if (selectedRadio.getId() == R.id.radioPink) {
            lutemon = new Lutemon(lutemonName, "Pink", 7, 2, 0, 18,"https://archives.bulbagarden.net/media/upload/thumb/f/fb/0718Zygarde-Complete.png/165px-0718Zygarde-Complete.png");
        } else if (selectedRadio.getId() == R.id.radioOrange) {
            lutemon = new Lutemon(lutemonName, "Orange", 8,1,0, 17, "https://archives.bulbagarden.net/media/upload/thumb/1/17/0716Xerneas.png/375px-0716Xerneas.png");
        } else if (selectedRadio.getId() == R.id.radioBlack) {
            lutemon = new Lutemon(lutemonName, "Black", 9,0,0, 14,"https://archives.bulbagarden.net/media/upload/thumb/1/1d/0717Yveltal.png/375px-0717Yveltal.png");
        }
        return lutemon;
    }
}