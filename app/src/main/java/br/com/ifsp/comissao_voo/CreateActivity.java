package br.com.ifsp.comissao_voo;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class CreateActivity extends Activity {

    private EditText nameEditText;
    private EditText weightEditText;
    private EditText ageEditText;
    private RadioGroup genderRadioGroup;
    private RadioGroup dayRadioGroup;
    private EditText voucherDescriptionEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        nameEditText = findViewById(R.id.name_edit_text);
        weightEditText = findViewById(R.id.weight_edit_text);
        ageEditText = findViewById(R.id.age_edit_text);
        genderRadioGroup = findViewById(R.id.gender_radio_group);
        dayRadioGroup = findViewById(R.id.day_radio_group);
        voucherDescriptionEditText = findViewById(R.id.voucher_description_edit_text);

        Button btnCancel = findViewById(R.id.btn_cancel);
        btnCancel.setOnClickListener(v -> finish());

        Button btnSave = findViewById(R.id.btn_save);
        btnSave.setOnClickListener(v -> {
            if (isDataValid()) {
                saveDataAndFinish();
            } else {
                showToast("Insira todos os campos.");
            }
        });
    }

    private void saveDataAndFinish() {
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference().child("vouchers");

        String name = nameEditText.getText().toString();
        String weight = weightEditText.getText().toString();
        int age = Integer.parseInt(ageEditText.getText().toString());
        int genderRadioId = genderRadioGroup.getCheckedRadioButtonId();
        String gender = (genderRadioId == R.id.male_radio_button) ? "Male" : "Female";
        int dayRadioId = dayRadioGroup.getCheckedRadioButtonId();
        String dayOfWeek = getDayOfWeek(dayRadioId);
        String trainingDescription = voucherDescriptionEditText.getText().toString();

        String key = databaseRef.push().getKey();
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("name", name);
        childUpdates.put("weight", weight);
        childUpdates.put("age", age);
        childUpdates.put("gender", gender);
        childUpdates.put("dayOfWeek", dayOfWeek);
        childUpdates.put("description", trainingDescription);
        databaseRef.child(key).setValue(childUpdates);

        setResult(RESULT_OK);
        finish();
    }

    private boolean isDataValid() {
        String name = nameEditText.getText().toString();
        String weight = weightEditText.getText().toString();
        String ageText = ageEditText.getText().toString();
        int genderRadioId = genderRadioGroup.getCheckedRadioButtonId();
        int dayRadioId = dayRadioGroup.getCheckedRadioButtonId();
        String trainingDescription = voucherDescriptionEditText.getText().toString();

        return !name.isEmpty() && !weight.isEmpty() && !ageText.isEmpty() &&
                genderRadioId != -1 && dayRadioId != -1 && !trainingDescription.isEmpty();
    }

    private String getDayOfWeek(int radioButtonId) {
        if (radioButtonId == R.id.monday_radio_button) {
            return "Monday";
        } else if (radioButtonId == R.id.tuesday_radio_button) {
            return "Tuesday";
        } else if (radioButtonId == R.id.wednesday_radio_button) {
            return "Wednesday";
        } else if (radioButtonId == R.id.thursday_radio_button) {
            return "Thursday";
        } else if (radioButtonId == R.id.friday_radio_button) {
            return "Friday";
        } else if (radioButtonId == R.id.saturday_radio_button) {
            return "Saturday";
        } else if (radioButtonId == R.id.sunday_radio_button) {
            return "Sunday";
        } else {
            return "";
        }
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
