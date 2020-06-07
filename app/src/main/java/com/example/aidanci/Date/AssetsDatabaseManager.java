package com.example.aidanci.Date;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

public class AssetsDatabaseManager {

    private static String databasepath = "/data/data/com.example.wsh.worddemo/database";

    private Map<String, SQLiteDatabase> databases = new HashMap<String,SQLiteDatabase>();

    private Context context = null;

    private static AssetsDatabaseManager mInstance = null;

    private AssetsDatabaseManager(Context context){
        this.context = context;
    }

    public static void initManager(Context context){
        if(mInstance == null)
            mInstance = new AssetsDatabaseManager(context);
    }

    public static AssetsDatabaseManager getManager(){
        return mInstance;
    }

    public SQLiteDatabase getDatabase(String dbfile){
        if (databases.get(dbfile) != null)
            return databases.get(dbfile);
        if (context == null)
            return null;
        String spath = getDatabaseFilepath();
        String sfile = getDatabaseFile(dbfile);
        File file = new File(sfile);
        SharedPreferences dbs = context.getSharedPreferences(AssetsDatabaseManager.class.toString(),0);
        boolean flag = dbs.getBoolean(dbfile,false);
        if (!flag || !file.exists()){
            file = new File(spath);
            if (!file.exists() && !file.mkdirs()){
                Log.i("WSH", "Create \""+spath+"\" fail!");
                return null;
            }
            if (!copyAssetsToFilesystem(dbfile, sfile)){
                Log.i("WSH", "Copy \""+spath+"\" fail!");
                return null;
            }

            dbs.edit().putBoolean(dbfile,true).commit();
        }
        SQLiteDatabase db = SQLiteDatabase.openDatabase(sfile, null, SQLiteDatabase.NO_LOCALIZED_COLLATORS);
        if(db != null){
            databases.put(dbfile, db);
        }
        return db;
    }

    private boolean copyAssetsToFilesystem(String assetsSrc, String des) {
        Log.i("WSH", "Copy "+assetsSrc+" to "+des);
        InputStream istream = null;
        OutputStream ostream = null;
        try{
            AssetManager am = context.getAssets();
            istream = am.open(assetsSrc);
            ostream = new FileOutputStream(des);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = istream.read(buffer))>0){
                ostream.write(buffer, 0, length);
            }
            istream.close();
            ostream.close();
        }
        catch(Exception e){
            e.printStackTrace();
            try{
                if(istream!=null)
                    istream.close();
                if(ostream!=null)
                    ostream.close();
            }
            catch(Exception ee){
                ee.printStackTrace();
            }
            return false;
        }
        return true;
    }

    private String getDatabaseFile(String dbfile) {
        return getDatabaseFilepath()+"/"+dbfile;
    }

    private String getDatabaseFilepath() {
        return String.format(databasepath,context.getApplicationInfo().packageName);
    }



    public boolean closeDatabase(String dbfile){
        if(databases.get(dbfile) != null){
            SQLiteDatabase db = (SQLiteDatabase) databases.get(dbfile);
            db.close();
            databases.remove(dbfile);
            return true;
        }
        return false;
    }

    static public void closeAllDatabase(){
        Log.i("WSH", "closeAllDatabase");
        if(mInstance != null){
            for(int i=0; i<mInstance.databases.size(); ++i){
                if(mInstance.databases.get(i)!=null){
                    mInstance.databases.get(i).close();
                }
            }
            mInstance.databases.clear();
        }
    }
}
