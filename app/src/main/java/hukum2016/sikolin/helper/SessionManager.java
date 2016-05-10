package hukum2016.sikolin.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * Created by Ari on 4/10/2016.
 */
public class SessionManager {
    // LogCat tag
    private static String TAG = SessionManager.class.getSimpleName();

    // Shared Preferences
    SharedPreferences pref;

    SharedPreferences.Editor editor;
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Shared preferences file name
    private static final String PREF_NAME = "Sikolin";

    private static final String KEY_IS_LOGGEDIN = "isLoggedIn";
    private static final String KEY_ROLE = "role";
    private static final String KEY_AUTH = "authkey";

    public SessionManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setLogin(boolean isLoggedIn) {

        editor.putBoolean(KEY_IS_LOGGEDIN, isLoggedIn);

        // commit changes
        editor.commit();

        Log.d(TAG, "User login session modified!");
    }
    public void setRole(int role){

        editor.putInt(KEY_ROLE, role);

        // commit changes
        editor.commit();

        Log.d(TAG, "User role is assigned");
    }
    public void setKeyAuth(String authkey){
        editor.putString(KEY_AUTH,authkey);
        // commit changes
        editor.commit();

        Log.d(TAG, "User authkey is assigned");
    }
    public boolean isLoggedIn(){
        return pref.getBoolean(KEY_IS_LOGGEDIN, false);
    }
    public int role(){ return pref.getInt(KEY_ROLE,0); }
    public String getKeyAuth(){return pref.getString(KEY_AUTH,null);}
}
