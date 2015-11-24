package Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.geo_2.proyectoso.R;

import java.util.ArrayList;

/**
 * Created by Danicormu on 22/11/2015.
 */
public class MyPlayListAdapter extends BaseAdapter implements ListAdapter{
    private ArrayList<String> myListSong = new ArrayList<String>();
    private Context context;
    private String[] clicked;

    public MyPlayListAdapter(ArrayList<String> mySongs, Context context){
        this.myListSong = mySongs;
        this.context = context;
        //clicked = songN;
    }

    @Override
    public int getCount() {
        return myListSong.size();
    }

    @Override
    public Object getItem(int position) {
        return myListSong.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View vi = convertView;

        if(vi == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            vi = inflater.inflate(R.layout.list_element, null);
        }

        TextView listText = (TextView)vi.findViewById(R.id.title_song);
        listText.setText(myListSong.get(position));

        Button btnDel = (Button)vi.findViewById(R.id.button_del);
        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "You added " + myListSong.get(position), Toast.LENGTH_LONG).show();
            }
        });
        return vi;
    }
}
