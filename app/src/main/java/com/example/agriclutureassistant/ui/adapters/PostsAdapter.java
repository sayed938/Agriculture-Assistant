package com.example.agriclutureassistant.ui.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.agriclutureassistant.R;
import com.example.agriclutureassistant.pojo.PostsModel;
import com.example.agriclutureassistant.ui.features_activities.AddPost;
import com.example.agriclutureassistant.ui.features_activities.Comments;

import java.util.ArrayList;
import java.util.List;



public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.postholder> {
    public static class postholder extends RecyclerView.ViewHolder{
        TextView name,post;
        LinearLayout layout;
        Button clickIcon;
        public postholder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.user_post);
            post=itemView.findViewById(R.id.text_post);
            layout= itemView.findViewById(R.id.to_comment_linear);
            clickIcon = itemView.findViewById(R.id.add_comment);
        }
    }
    List<PostsModel>list=new ArrayList<>();
    public PostsAdapter(List<PostsModel>list) {
        this.list=list;
    }

    @NonNull
    @Override
    public PostsAdapter.postholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v=LayoutInflater.from(parent.getContext()).inflate(R.layout.posts_custom,parent,false);
        return new postholder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PostsAdapter.postholder holder, int position) {
           PostsModel model=list.get(position);
           holder.name.setText(model.getName());
           holder.post.setText(model.getPost());
           holder.layout.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   view.getContext().startActivity(new Intent(view.getContext(), Comments.class));
               }
           });
           holder.clickIcon.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   view.getContext().startActivity(new Intent(view.getContext(), Comments.class));

               }
           });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
