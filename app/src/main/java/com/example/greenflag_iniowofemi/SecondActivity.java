package com.example.greenflag_iniowofemi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Pattern;

public class SecondActivity extends AppCompatActivity {
    private EditText editTextTextEmailAddress;
    private EditText editTextPassword1;
    private EditText editTextPassword2;
    private Button nextButton;
    private TextView emailInvalid;
    private TextView passwordMismatch;

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String EMAIL = "";
    public static final String PASS1 = "";
    public static final String PASS2 = "";

    private String emailText;
    private String Pass1;
    private String Pass2;
    public  boolean ini;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        editTextTextEmailAddress = (EditText) findViewById(R.id.editTextTextEmailAddress);
        editTextPassword1 = (EditText) findViewById(R.id.editTextTextPassword1);
        editTextPassword2 = (EditText) findViewById(R.id.editTextTextPassword2);
        nextButton = (Button) findViewById(R.id.nextButton);
        emailInvalid = findViewById(R.id.emailInvalid);
        passwordMismatch = findViewById(R.id.passwordsMismatch);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateInformation();
//                validatePasswords();
            }
        });
        loadInformation();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadInformation();
        updateInformation();
    }

    public void validateInformation() {
        isValidEmail(editTextTextEmailAddress.getText().toString());
        if (isValidEmail(editTextTextEmailAddress.getText().toString())) {
            saveInformation();
            emailInvalid.setVisibility(View.INVISIBLE);
            validatePasswords();
            // add the logic for the tick and green border here
        } else {
            // add the logic for the error message and red border here.
            emailInvalid.setVisibility(View.VISIBLE);
            //Toast.makeText(this, "Email is not valid", Toast.LENGTH_SHORT).show();
        }
    }

    public void validatePasswords() {
        boolean validatePass1 = isValidPass1(editTextPassword1.getText().toString());
        boolean validatePass2 = isValidPass2(editTextPassword2.getText().toString());

        if (!validatePass1) {
            Toast.makeText(this, "Your password isn't valid", Toast.LENGTH_SHORT).show();
        } else if (editTextPassword1.getText().toString().equals(editTextPassword2.getText().toString())) {
            passwordMismatch.setVisibility(View.INVISIBLE);
            Toast.makeText(this, "Your information has been saved", Toast.LENGTH_SHORT).show();
        } else passwordMismatch.setVisibility(View.VISIBLE);
            //Toast.makeText(this, "Your passwords don't match", Toast.LENGTH_SHORT).show();
//       if (validatePass1 && !validatePass2)
//            Toast.makeText(this, "Your passwords don't match", Toast.LENGTH_SHORT).show();

//        if (editTextPassword1.getText().toString().equals(editTextPassword2.getText().toString())) {
//            Toast.makeText(this, "Your information has been saved", Toast.LENGTH_SHORT).show();

        }




    // Check the email is valid logic
    public boolean isValidEmail(String emailText) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@" +  //part before @
                "(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (emailText == null)
            return false;
        return pat.matcher(emailText).matches();
    }

    // Check Password 1 for criteria logic
//    public boolean isValidPass1(String Pass1) {
//        // Use if statements
//        String emailRegex = "^(?=.*\\\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@!#$%]).{8,20}$";
//
//        Pattern pat = Pattern.compile(emailRegex);
//        if (Pass1 == null)
//            return false;
//        return pat.matcher(Pass1).matches();
//    }

    public static boolean isValidPass1(String Pass1) {
        boolean isValid1 = true;

        if (Pass1.length() > 20 || Pass1.length() < 8) {
            //System.out.println("Password must be less than 20 and more than 8 characters in length.");
            isValid1 = false;
        }

        String upperCaseChars = "(.*[A-Z].*)";
        if (!Pass1.matches(upperCaseChars )) {
            //System.out.println("Password must have atleast one uppercase character");
            isValid1 = false;
        }

        String lowerCaseChars = "(.*[a-z].*)";
        if (!Pass1.matches(lowerCaseChars )) {
            //System.out.println("Password must have atleast one lowercase character");
            isValid1 = false;
        }

        String numbers = "(.*[0-9].*)";
        if (!Pass1.matches(numbers )) {
            //System.out.println("Password must have atleast one number");
            isValid1 = false;
        }

        String specialChars = "(.*[@,#,$,%].*$)";
        if (!Pass1.matches(specialChars )) {
            //System.out.println("Password must have at least one special character among @#$%");
            isValid1 = false;
        }
        return isValid1;
    }

    // Check Password 2 == Password 1
    public boolean isValidPass2(String Pass2) {
        if (Pass2.equals(Pass1))
        {
            ini = true;

        }

        return Pass2.equals(Pass1);

    }

    public void saveInformation() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(EMAIL, editTextTextEmailAddress.getText().toString());
        // editor.putString(PASS1, editTextPassword1.getText().toString());
        // editor.putString(PASS2, editTextPassword2.getText().toString());

        editor.apply();

        // Toast.makeText(this, "Email added", Toast.LENGTH_SHORT).show();
    }

    public void loadInformation() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        emailText = sharedPreferences.getString(EMAIL, "");
    }

    public void updateInformation() {
        editTextTextEmailAddress.setText(emailText);
    }
}