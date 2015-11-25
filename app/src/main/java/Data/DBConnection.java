package Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

import Domain.Track;
import Domain.User;

public class DBConnection extends SQLiteOpenHelper{

     public String sqlCreateTableList = "CREATE TABLE playlist (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," + "track VARCHAR(50), id_user INTEGER," +
            "FOREIGN KEY(id_user) REFERENCES user(id_user))";
     public String sqlCreateTableUser = "CREATE TABLE user (id_user INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," + "username TEXT NOT NULL UNIQUE)";

    SQLiteDatabase helper;

    public DBConnection(Context context){
        super(context, "RockolaDB.db", null, 1);
        helper = getWritableDatabase();
        Log.d("///////DATABASE ROCKOLA SO", "DATABASE....----.....CREATED" );
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(sqlCreateTableList);
        Log.d("////////TABLA PLAYLIST", "TABLE CREATED");
        db.execSQL(sqlCreateTableUser);
        Log.d("///TABLA USER CREATED", "TABLE CREATED");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    // Metodo que agrega canciones
    public boolean addSong(String track, String user){
        boolean flag = false;
        try{
            ContentValues val = new ContentValues();
            //val.put("id", track.getId());
            val.put("track", track);
            val.put("id_user", callIdUser(user));
            helper.insert("playlist", null, val);
            flag = true;
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        return flag;
    }

    // Metodo que captura el id del usuario
    public int callIdUser(String user){
        int usuario=0;
        String query ="select id_user from user where username = '"+user+"'";
            try{
                Cursor cursor = helper.rawQuery(query, null);
                cursor.moveToFirst();
                usuario = cursor.getInt(0);
            }catch (Exception e){
                e.printStackTrace();
                Log.e("Fallo:", "$$$$$$$$$$$$$$$$$$$$$$$---$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$Problem" );
            }
        return usuario;
    }

    // Metodo que elimina la cancion
    public boolean deleteSong(Track track){
        boolean flag = false;
        try {
            helper.execSQL("delete from playlist where id =" + track.getId() + ";");
            flag = true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return flag;
    }

    //Metodo que trae una lista de canciones
    public ArrayList<Track> getPlayList() {
        ArrayList<Track> list = new ArrayList<Track>();
        String query = "select * from playlist";
        try{
            Cursor cursor = helper.rawQuery(query, null);
            if(cursor.moveToFirst()){
                do{
                    Track track = new Track();
                    track.setId(cursor.getInt(0));
                    track.setName(cursor.getString(1));
                    list.add(track);
                }while(cursor.moveToNext());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }

    // Metodo que agrega un usuario
    public boolean addUser(User user){
        boolean flag = false;
        try{
            Cursor cur = helper.rawQuery("Select * from user where username= '"+ user +"';", null);
            if(cur.moveToFirst() == false){
                ContentValues value = new ContentValues();
                //value.put("id_user", user.getId());
                value.put("username", user.getName());
                helper.insert("user", null, value);
                flag = true;
                Log.d("///////////INSERTADO", "SI SE INSERTO BIEN EL USUARIO");
            }else {
                Log.d("YAAAA", "USUARIOOOO SI EXISTEEEEEE$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
            }
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        return flag;
    }


}
