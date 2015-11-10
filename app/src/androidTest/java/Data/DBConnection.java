package Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import Domain.Track;


/**
 * Created by Danicormu on 10/11/2015.
 */
public class DBConnection extends SQLiteOpenHelper{
    private String createTable = "CREATE TABLE playlist (id INTEGER PRIMARY KEY NOT NULL," + "track VARCHAR(50))";
    public DBConnection(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    SQLiteDatabase helper;

    public DBConnection(Context context){
        super(context, "soDB", null, 1);
        helper = getWritableDatabase();
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public boolean addSong(Track track){
        boolean flag = false;
        try{
            ContentValues val = new ContentValues();
            val.put("id", track.getId());
            val.put("track", track.getName());
            helper.insert("playlist",null,val);
            flag = true;
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        return flag;
    }

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
}
