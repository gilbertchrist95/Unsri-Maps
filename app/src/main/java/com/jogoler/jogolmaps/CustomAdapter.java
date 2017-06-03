package com.jogoler.jogolmaps;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

/**
 * Created by Arief Wijaya on 06-Oct-16.
 */


public class CustomAdapter extends CursorAdapter {
   // TextView txtId,txtName,txtDesc;
    private LayoutInflater mInflater;

    public CustomAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public View newView(final Context context, Cursor cursor, ViewGroup parent) {
        View   view    =    mInflater.inflate(R.layout.item, parent, false);
        ViewHolder holder  =   new ViewHolder();
        holder.txtId    =   (TextView)  view.findViewById(R.id.txtId);
        holder.txtName    =   (TextView)  view.findViewById(R.id.txtLocationName);
        holder.txtDesc   =   (TextView)  view.findViewById(R.id.txtDesc);
        holder.txtLatitude   =   (TextView)  view.findViewById(R.id.txtLatitude);
        holder.txtLongitude   =   (TextView)  view.findViewById(R.id.txtLongitude);
        view.setTag(holder);

        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        //If you want to have zebra lines color effect uncomment below code
        if(cursor.getPosition()%2==1) {
             view.setBackgroundResource(R.drawable.item_list_backgroundcolor);
        } else {
            view.setBackgroundResource(R.drawable.item_list_backgroundcolor2);
        }

        ViewHolder holder  =   (ViewHolder)    view.getTag();
        holder.txtId.setText(cursor.getString(cursor.getColumnIndex(Location.KEY_ID)));
        holder.txtName.setText(cursor.getString(cursor.getColumnIndex(Location.KEY_name)));
        holder.txtDesc.setText(cursor.getString(cursor.getColumnIndex(Location.KEY_desc)));
        holder.txtLatitude.setText(cursor.getString(cursor.getColumnIndex(Location.KEY_latitude)));
        holder.txtLongitude.setText(cursor.getString(cursor.getColumnIndex(Location.KEY_longitude)));

    }

    static class ViewHolder {
        TextView txtId;
        TextView txtName;
        TextView txtDesc;
        TextView txtLongitude;
        TextView txtLatitude;
    }
}
