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
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class projectAdapter extends FirebaseRecyclerAdapter<modelProject, projectAdapter.projectViewHolder> {

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    private Context context;
    public projectAdapter(@NonNull FirebaseRecyclerOptions<modelProject> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull projectViewHolder holder, int position, @NonNull modelProject model) {
        holder.nameE.setText(model.getProjectName());
        holder.description.setText(model.getProDescription());
        holder.time.setText(model.getProCost());
        String url = null;
        url =  model.getImageURLpro();
        Picasso.get().load(model.getImageURLpro()).into(holder.imageView);

        holder.btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase.getInstance().getReference()
                        .child("Project").child(getRef(position).getKey())
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


                name.setText(model.getProjectName());
                description.setText(model.getProDescription());
                time.setText(model.getProCost());

                Button update = holderView.findViewById(R.id.update);

                update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Map<String,Object> map = new HashMap<>();
                        map.put("projectName",name.getText().toString());
                        map.put("proDescription",description.getText().toString());
                        map.put("proCost",time.getText().toString());

                        FirebaseDatabase.getInstance().getReference()
                                .child("Project")
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
    public projectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardtask,parent,false);
        return new projectViewHolder(v);
    }

    class  projectViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView nameE, description, time;
        ImageButton btnDel, btnEdit;

        public projectViewHolder(@NonNull View itemView) {
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
