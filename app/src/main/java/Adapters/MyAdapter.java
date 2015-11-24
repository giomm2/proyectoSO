package Adapters;

import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.geo_2.proyectoso.PlayActivity;
import com.example.geo_2.proyectoso.PlayListActivity;
import com.example.geo_2.proyectoso.R;

import java.util.ArrayList;
import java.util.HashMap;

import Domain.Track;

/**
 * Created by Danicormu on 21/11/2015.
 */
public class MyAdapter extends BaseAdapter implements ListAdapter{

    private ArrayList<String> listSong = new ArrayList<String>();
    private Context context;
    private int layoutResourceId;


    public MyAdapter(Context context, int list_row, ArrayList<String> songList) {
        this.context = context;
        this.layoutResourceId = list_row;
        this.listSong = songList;
    }

    @Override
    public int getCount() {
        return listSong.size();
    }

    @Override
    public Object getItem(int position) {
        return listSong.get(position);
    }

    @Override
    public long getItemId(int position) { return position; }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View v = convertView;
        ViewHolder holder = null;
        if(v == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.list_row, null);
            holder = new ViewHolder();
            holder.title = (TextView) v.findViewById(R.id.title_song);
           // holder.button = (Button) v.findViewById(R.id.button_Add);
            holder.checkBox = (CheckBox) v.findViewById(R.id.chk_add);
            v.setTag(holder);
        }else{
            holder = (ViewHolder)v.getTag();
        }
        //Track track = listSong.get(position);
        String track = listSong.get(position);
        holder.title.setText((CharSequence) getItem(position));

      /*  holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "You added " + getItem(position), Toast.LENGTH_LONG).show();
            }
        });
        */
        if(holder.checkBox.isChecked()){
            Toast.makeText(context, "You added " + getItem(position) , Toast.LENGTH_LONG).show();
        }


        return v;
    }

    public static class ViewHolder{
        TextView title;
       // Button button;
        CheckBox checkBox;
    }
}
