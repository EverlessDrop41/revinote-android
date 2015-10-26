package com.everlesslycoding.revinote;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

public class ForgotPassword extends AppCompatActivity {

    EditText emailInput;
    Button submitBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        Firebase.setAndroidContext(getApplicationContext());

        emailInput = (EditText) findViewById(R.id.EmailInput);
        submitBtn = (Button) findViewById(R.id.submit_btn);

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Email = emailInput.getText().toString();

                Firebase ref = new Firebase("https://revinote.firebaseio.com");
                ref.resetPassword(Email, new Firebase.ResultHandler() {
                    @Override
                    public void onSuccess() {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(ForgotPassword.this);

                            builder.setMessage("Successfully sent reset email").setTitle("Succsess!");

                            builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                                @Override
                                public void onDismiss(DialogInterface dialog) {
                                    finish();
                                }
                            });

                            AlertDialog dialog = builder.create();
                            dialog.show();
                        } else { finish(); }
                    }

                    @Override
                    public void onError(FirebaseError firebaseError) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(ForgotPassword.this);

                        builder.setMessage(firebaseError.getMessage()).setTitle("Error Sending Reset Email");

                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }
                });
            }
        });
    }
}
