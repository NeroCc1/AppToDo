package com.example.todo;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class RecordatoriosAdapter extends RecyclerView.Adapter<RecordatoriosAdapter.RecordatorioViewHolder> {
    private ArrayList<String> recordatoriosList;

    public RecordatoriosAdapter(ArrayList<String> recordatoriosList) {
        this.recordatoriosList = recordatoriosList;
    }

    @Override
    public RecordatorioViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        return new RecordatorioViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecordatorioViewHolder holder, int position) {
        String recordatorio = recordatoriosList.get(position);
        holder.bind(recordatorio);
    }

    @Override
    public int getItemCount() {
        return recordatoriosList.size();
    }

    public class RecordatorioViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;

        public RecordatorioViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(android.R.id.text1);
        }

        public void bind(String recordatorio) {
            textView.setText(recordatorio);
        }
    }
}
