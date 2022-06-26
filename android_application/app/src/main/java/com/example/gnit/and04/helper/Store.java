package com.example.gnit.and04.helper;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

/**
 * Created by shyam on 10-01-2018.
 */

public class Store {
   static SharedPreferences sharedPreferences;
   static SharedPreferences.Editor editor;
    public static  void userDetails(Context context, int userid, String username,String role){
        sharedPreferences=context.getSharedPreferences("railway",Context.MODE_PRIVATE);
        editor=sharedPreferences.edit();
        editor.putInt("userid",userid);
        editor.putString("username",username);
        editor.putString("type",role);

        editor.commit();
    }






    public static HashMap<String,String> getUserDetails(Context context){
        sharedPreferences=context.getSharedPreferences("railway",Context.MODE_PRIVATE);
        HashMap map=new HashMap();
        map.put("username",sharedPreferences.getString("username",""));
        map.put("userid",sharedPreferences.getInt("userid",0));
        map.put("type",sharedPreferences.getString("type",""));
        return map;

    }
public static  void logout(Context context){
    sharedPreferences=context.getSharedPreferences("railway",Context.MODE_PRIVATE);
    editor=sharedPreferences.edit();
    editor.clear();
    editor.commit();


}

}
