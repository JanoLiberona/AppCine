package com.example.appcine.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import androidx.recyclerview.widget.RecyclerView;

import com.example.appcine.R;

import java.util.List;

public class AdapterDatosComentarios extends BaseAdapter {
    //Creamos los atributos
    private Context context;
    private int layout;
    private List<String[]> comentarios;

    public AdapterDatosComentarios(Context context, int layout, List<String[]> comentarios) {
        this.context = context;
        this.layout = layout;
        this.comentarios = comentarios;
    }

    @Override
    public int getCount() {
        return this.comentarios.size();
    }

    @Override
    public Object getItem(int position) {
        return this.comentarios.get(position);
    }

    @Override
    public long getItemId(int id) {
        return id;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder holder;
        if(convertView == null){
            LayoutInflater layoutInflater = LayoutInflater.from(this.context);
            //convertView = layoutInflater.inflate(R.layout.list_items,null);
            holder = new ViewHolder();
            holder.rcvComentarios = (RecyclerView) convertView.findViewById(R.id.rcvComentarios);
            convertView.setTag(holder);

        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        //Copiamos la vista y pasamos valores
        String[] currentData = comentarios.get(position);
        //holder.rcvComentarios.setText(currentData[0]);
        return convertView;
    }

    static class ViewHolder{
        private RecyclerView rcvComentarios;

    }
}
