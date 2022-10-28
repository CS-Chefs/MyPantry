package com.example.mypantry.Pantry;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.example.mypantry.R;
import java.util.List;


//CUSTOM ADAPTER ALLOWS YOU TO CUSTOMIZE YOUR LISTVIEW TO INCLUDE MULTIPLE ITEMS IN ROW
public class PantryAdapter extends ArrayAdapter<pantryItem> {

    Context context;
    List<pantryItem> items;

    public PantryAdapter(Context context, int resource, int textViewResourceId, List<pantryItem> objects) {
        super(context, resource, textViewResourceId, objects);
        this.context = context;
        this.items = objects;
    }


    // GETS THE APPROPRIATE VIEW FOR LISTVIEW ROW FROM LAYOUT (ITEM.XML)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.pantry_item_activity, parent, false);
        }

            pantryItem item = getItem(position);
            TextView itemName = (TextView) convertView.findViewById(R.id.pantryItemName);


            itemName.setText(item.getName());
            return convertView;
        }

}



