package br.edu.ifsp.arq.ads.dmos5.easycooking.adapter;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import br.edu.ifsp.arq.ads.dmos5.easycooking.RecipeRegisterActivity;
import br.edu.ifsp.arq.ads.dmos5.easycooking.MyRecipesListActivity;
import br.edu.ifsp.arq.ads.dmos5.easycooking.R;
import br.edu.ifsp.arq.ads.dmos5.easycooking.model.Recipe;

public class RecipeAdapter extends ArrayAdapter<Recipe> {

    private List<Recipe> recipes;
    private Context context;

    public RecipeAdapter(Context context, int textViewResourceId, List<Recipe> recipes) {
        super(context, textViewResourceId, recipes);
        this.recipes = recipes;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        Recipe activity = recipes.get(position);
        ViewHolder holder = null;
        if (convertView == null) {
            Log.d("teste", "View nova => position: " + position);
            convertView = LayoutInflater.from(context).inflate(R.layout.activity_list_layout, null);
            holder = new ViewHolder();
            holder.type = convertView.findViewById(R.id.activity_type);
            holder.name = convertView.findViewById(R.id.activity_name);
            holder.details = convertView.findViewById(R.id.activity_details);
            holder.date = convertView.findViewById(R.id.activity_date);
            holder.btnEdit = convertView.findViewById(R.id.btn_edt);
            holder.btnEdit.setOnClickListener(view -> edit(position));
            holder.btnDelete = convertView.findViewById(R.id.btn_del);
            holder.btnDelete.setOnClickListener(view -> delete(position));
            convertView.setTag(holder);
        }else{
            Log.d("teste", "View existente => position: " + position);
            holder = (ViewHolder)convertView.getTag();
        }

        if(activity ==  null){
            return convertView;
        }

        holder.type.setText(activity.getType().getType());
        holder.date.setText(activity.getDate());
        holder.name.setText(String.format("Nome: %s", activity.getRecipeName()));
        holder.details.setText(String.format("%s", activity.getDetails()));

        return convertView;
    }

    private void edit(int pos){
        Intent intent = new Intent(context, RecipeRegisterActivity.class);
        intent.putExtra("recipe", recipes.get(pos));
        context.startActivity(intent);
    }

    private void delete(int pos){
        AlertDialog.Builder msgBox = new AlertDialog.Builder(context);
        msgBox.setTitle("Excluindo...");
        msgBox.setIcon(android.R.drawable.ic_menu_delete);
        msgBox.setMessage("Deseja realmente excluir esta receita?");

        msgBox.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MyRecipesListActivity myRecipesListActivity = (MyRecipesListActivity) context;
                myRecipesListActivity.deleteRecipe(pos);
            }
        });

        msgBox.setNegativeButton("NÃO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {}
        });

        msgBox.show();
    }

    static class ViewHolder{
        TextView type;
        TextView date;
        TextView name;
        TextView details;
        Button btnEdit;
        Button btnDelete;
    }
}