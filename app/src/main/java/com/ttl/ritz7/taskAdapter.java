package com.ttl.ritz7;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class taskAdapter extends FirebaseRecyclerAdapter<modelTask, taskAdapter.taskViewHolder> {

    private Context context;
    public taskAdapter(@NonNull FirebaseRecyclerOptions<modelTask> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull taskViewHolder holder, int position, @NonNull modelTask model) {
        holder.nameE.setText(model.getName());
        holder.description.setText(model.getTaskAssigned());
        holder.time.setText(model.getTime());
        String url = null;
        url =  model.getImageURL();
        Picasso.get().load(model.getImageURL()).into(holder.imageView);
        holder.btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase.getInstance().getReference()
                        .child("Task").child(getRef(position).getKey())
                        .setValue(null).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                    }
                });
            }
        });
        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DialogPlus dialog = DialogPlus.newDialog(context)
                        .setGravity(Gravity.CENTER)
                        .setMargin(50,0,50,0)
                        .setContentHolder(new ViewHolder(R.layout.content))
                        .setExpanded(false)  // This will enable the expand feature, (similar to android L share dialog)
                        .create();

                View holderView = (LinearLayout)dialog.getHolderView();

                final EditText name = holderView.findViewById(R.id.title);
                final EditText description = holderView.findViewById(R.id.description);
                final EditText time = holderView.findViewById(R.id.author);


                 name.setText(model.getName());
                description.setText(model.getTaskAssigned());
                time.setText(model.getTime());

                Button update = holderView.findViewById(R.id.update);

                update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Map<String,Object> map = new HashMap<>();
                        map.put("name",name.getText().toString());
                        map.put("taskAssigned",description.getText().toString());
                        map.put("time",time.getText().toString());

                        FirebaseDatabase.getInstance().getReference()
                                .child("Task")
                                .child(getRef(position).getKey())
                                .updateChildren(map)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        dialog.dismiss();
                                    }
                                });


                    }
                });
                dialog.show();
            }

        });
    }

    @NonNull
    @Override
    public taskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardtask,parent,false);
        return new taskViewHolder(v);
    }

    class  taskViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView nameE, description, time;
        ImageButton btnDel, btnEdit;
        public taskViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.item_image);
            nameE = itemView.findViewById(R.id.textName);
            description = itemView.findViewById(R.id.item_desc);
            time = itemView.findViewById(R.id.item_date);
            btnDel = itemView.findViewById(R.id.btnImgDelete);
            btnEdit = itemView.findViewById(R.id.btnImgEdit);
        }
    }

}
