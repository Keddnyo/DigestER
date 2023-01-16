package io.github.keddnyo.digester.adapters;

import static io.github.keddnyo.digester.repositories.Constants.FORUM_ID;
import static io.github.keddnyo.digester.repositories.Constants.FORUM_SUBTITLE;
import static io.github.keddnyo.digester.repositories.Constants.FORUM_TITLE;
import static io.github.keddnyo.digester.repositories.Constants.HAS_APPS;
import static io.github.keddnyo.digester.repositories.Constants.RECURSIVE;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;

import io.github.keddnyo.digester.R;
import io.github.keddnyo.digester.activities.RequestActivity;
import io.github.keddnyo.digester.entities.Forum;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private final ArrayList<Forum> forumArrayList = new ArrayList<>();

    Context context;

    public RecyclerViewAdapter(Context context) {
        this.context = context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        MaterialCardView cardView;
        ImageView icon;
        TextView title, subtitle;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.cardView = itemView.findViewById(R.id.forumCardView);
            this.icon = itemView.findViewById(R.id.forumIcon);
            this.title = itemView.findViewById(R.id.forumTitle);
            this.subtitle = itemView.findViewById(R.id.forumSubtitle);
        }
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder((LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_item, parent, false)));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, int position) {
        Forum forum = forumArrayList.get(position);

        holder.icon.setImageResource(forum.getIcon());
        holder.title.setText(forum.getTitle());
        holder.subtitle.setText(forum.getSubtitle());

        holder.cardView.setOnClickListener(v -> {
            Intent intent = new Intent(context, RequestActivity.class);
            intent.putExtra(FORUM_ID, forum.getId());
            intent.putExtra(FORUM_TITLE, context.getString(forum.getTitle()));
            intent.putExtra(FORUM_SUBTITLE, context.getString(forum.getSubtitle()));
            intent.putExtra(RECURSIVE, forum.getRecursive());
            intent.putExtra(HAS_APPS, forum.getHasApps());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return forumArrayList.size();
    }

    public void addForums(ArrayList<Forum> forums) {
        forumArrayList.addAll(forums);
        notifyItemRangeInserted(0, forums.size());
    }
}
