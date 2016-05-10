package hukum2016.sikolin.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashMap;

import hukum2016.sikolin.R;
import hukum2016.sikolin.helper.SQLiteHandler;
import hukum2016.sikolin.helper.SessionManager;

public class SellerActivity extends AppCompatActivity {

    private TextView txtUsername;
    private TextView txtRole;
    private Button btnLogout;

    private SQLiteHandler db;
    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer);

        txtUsername = (TextView) findViewById(R.id.username);
        txtRole = (TextView) findViewById(R.id.role);
        btnLogout = (Button) findViewById(R.id.btnLogout);

        // SqLite database handler
        db = new SQLiteHandler(getApplicationContext());

        // session manager
        session = new SessionManager(getApplicationContext());

        if (!session.isLoggedIn()) {
            logoutUser();
        }

        // Fetching user details from sqlite
        HashMap<String, String> user = db.getUserDetails();

        String username = user.get("username");
        String role = user.get("role");
        int selectedRole = Integer.parseInt(role);
        // Displaying the user details on the screen
        txtUsername.setText(username);
        if(selectedRole == 0){
            txtRole.setText("Buyer");
        }else{
            txtRole.setText("Seller");
        }


        // Logout button click event
        btnLogout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                logoutUser();
            }
        });
    }

    /**
     * Logging out the user. Will set isLoggedIn flag to false in shared
     * preferences Clears the user data from sqlite users table
     * */
    private void logoutUser() {
        session.setLogin(false);

        db.deleteUsers();

        // Launching the login activity
        Intent intent = new Intent(SellerActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
