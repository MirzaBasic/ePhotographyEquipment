package hci_130218.photography.Fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.net.URL;

import hci_130218.photography.Helper.MyApp;
import hci_130218.photography.Helper.MyRunnable;
import hci_130218.photography.Models.KorisniciVM;
import hci_130218.photography.R;
import hci_130218.photography.Utils.NetworkUtils;

/**
 * Created by Developer on 06.06.2017..
 */


public class RegistracijaFragment extends DialogFragment {

    public RegistracijaFragment() {


    }

    public static void openFragmentAsDialog(FragmentActivity activity, MyRunnable<KorisniciVM> onSuccess) {

        final RegistracijaFragment fragment = new RegistracijaFragment();
        final Bundle args = new Bundle();
        args.putSerializable("MY_RUNNABLE", onSuccess);
        fragment.setArguments(args);
        FragmentManager fm = activity.getSupportFragmentManager();
        fragment.show(fm, "nesto");


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_registracija, container, false);

        getDialog().setTitle("Registration");
        Button btnRegistration = (Button) view.findViewById(R.id.btnRegistration);

        btnRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                do_registration(view);
            }
        });


        Button btnCancel = (Button) view.findViewById(R.id.btnCancel);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                do_CancelRegistration();
            }
        });

        return view;

    }

    private void do_CancelRegistration() {

        this.dismiss();
    }

    private void do_registration(final View view) {

        EditText txtUsername = (EditText) view.findViewById(R.id.txtUsername);
        EditText txtFirstName = (EditText) view.findViewById(R.id.txtFirstName);
        EditText txtLastName = (EditText) view.findViewById(R.id.txtLastName);
        EditText txtEmail = (EditText) view.findViewById(R.id.txtEmail);
        EditText txtPassword = (EditText) view.findViewById(R.id.txtPassword);
        EditText txtPasswordRepeate = (EditText) view.findViewById(R.id.txtPasswordRepeate);

        String pass = txtPassword.getText().toString();
        String passRepeat = txtPasswordRepeate.getText().toString();
        String firstName = txtFirstName.getText().toString();
        String lastName = txtLastName.getText().toString();
        String username = txtUsername.getText().toString();
        String email = txtEmail.getText().toString();

        if (!pass.contentEquals(passRepeat)) {

            Toast.makeText(MyApp.getContext(), "Password repeate must be same as password!", Toast.LENGTH_LONG).show();
        } else if (firstName.contentEquals("") || lastName.contentEquals("") || email.contentEquals("") || username.contentEquals("")) {


            Toast.makeText(MyApp.getContext(), "All fields are reqired!", Toast.LENGTH_LONG).show();
        } else {


            final KorisniciVM k = new KorisniciVM();
            k.Username = txtUsername.getText().toString();
            k.Ime = txtFirstName.getText().toString();
            k.Prezime = txtLastName.getText().toString();
            k.Email = txtEmail.getText().toString();
            k.Password = txtPassword.getText().toString();


            new AsyncTask<URL, Void, Boolean>() {
                @Override
                protected Boolean doInBackground(URL... params) {

                    Boolean success = true;
                    try {
                        NetworkUtils.postResponseToHttpUrl(params[0], "");
                    } catch (IOException e) {
                        e.printStackTrace();
                        success = false;
                    }
                    return success;
                }

                @Override
                protected void onPostExecute(Boolean success) {
                    super.onPostExecute(success);
                    if (success) {
                        Toast.makeText(MyApp.getContext(), "Registracija uspješna!", Toast.LENGTH_LONG).show();
                        final MyRunnable<KorisniciVM> onSuccess = (MyRunnable<KorisniciVM>) getArguments().getSerializable("MY_RUNNABLE");

                        onSuccess.run(k);

                        dismiss();

                    } else {
                        Toast.makeText(MyApp.getContext(), "Došlo je do greške!", Toast.LENGTH_LONG).show();
                    }
                }


            }.execute(NetworkUtils.buildPostKorisnikURL(k));


        }
    }

}