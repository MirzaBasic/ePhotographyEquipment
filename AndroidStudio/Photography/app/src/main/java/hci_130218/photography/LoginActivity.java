package hci_130218.photography;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import android.os.AsyncTask;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.net.URL;

import hci_130218.photography.Fragments.RegistracijaFragment;
import hci_130218.photography.Helper.GsonConverter;
import hci_130218.photography.Helper.MyRunnable;
import hci_130218.photography.Models.KorisniciVM;
import hci_130218.photography.Utils.NetworkUtils;
import hci_130218.photography.Helper.Sesija;


public class LoginActivity extends AppCompatActivity {


    private EditText mUsernameView;
    private EditText mPasswordView;
    private View mProgressView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mUsernameView = (EditText) findViewById(R.id.tv_username);


        mPasswordView = (EditText) findViewById(R.id.tv_password);


        Button mSignInButton = (Button) findViewById(R.id.sign_in_button);

        mSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                doLogin();
            }
        });

        Button mRegistracijaButton = (Button) findViewById(R.id.register_button);

        mRegistracijaButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                doRegistracija();
            }
        });


        mProgressView = findViewById(R.id.login_progress);
    }


    private void doLogin() {


        mUsernameView.setError(null);
        mPasswordView.setError(null);


        String username = mUsernameView.getText().toString();
        String password = mPasswordView.getText().toString();


        doAutentifikacija(username, password);

    }


    private void doAutentifikacija(String username, String password) {
        new AsyncTask<URL, Void, String>() {

            @Override
            protected String doInBackground(URL... params) {

                String responseFromHttpUrl;


                try {
                    responseFromHttpUrl = NetworkUtils.getResponseFromHttpUrl(params[0]);
                } catch (IOException e) {
                    return null;
                }


                return responseFromHttpUrl;
            }

            @Override
            protected void onPostExecute(final String response) {

                mProgressView.setVisibility(View.GONE);



                KorisniciVM korisnik = GsonConverter.JsonToObject(response, KorisniciVM.class);

                if (korisnik != null) {
                    Sesija.SaveUser(korisnik);

                    Intent intent = new Intent(LoginActivity.this, NavigationDrawerActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    mPasswordView.setError(getString(R.string.error_incorrect_password));
                    mPasswordView.requestFocus();
                }





            }
        }.execute(NetworkUtils.buildAutentifikacijaURL(username, password));

    }



    private void doRegistracija() {

        RegistracijaFragment.openFragmentAsDialog(this, new MyRunnable<KorisniciVM>() {
            @Override
            public void run(KorisniciVM result) {

                mUsernameView.setText(result.Username);
                mPasswordView.setText(result.Password);
              doLogin();
            }
        });

    }

}


