package io.github.keddnyo.digester.adapters;

import static io.github.keddnyo.digester.repositories.Constants.FORUM_ID;
import static io.github.keddnyo.digester.repositories.Constants.FORUM_MAIN_URL;
import static io.github.keddnyo.digester.repositories.Constants.FORUM_SUBTITLE;
import static io.github.keddnyo.digester.repositories.Constants.FORUM_TITLE;
import static io.github.keddnyo.digester.repositories.Constants.HAS_APPS;
import static io.github.keddnyo.digester.repositories.Constants.RECURSIVE;
import static io.github.keddnyo.digester.repositories.Constants.DIGEST_TOPIC_LINK;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
import io.github.keddnyo.digester.entities.ForumSection;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private final ArrayList<ForumSection> forumSectionArrayList = new ArrayList<>();

    final Context context;

    public RecyclerViewAdapter(Context context) {
        this.context = context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final MaterialCardView cardView;
        final ImageView icon, route;
        final TextView title;
        final TextView subtitle;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.cardView = itemView.findViewById(R.id.forumSectionCardView);
            this.icon = itemView.findViewById(R.id.forumSectionIcon);
            this.route = itemView.findViewById(R.id.forumSectionRoute);
            this.title = itemView.findViewById(R.id.forumSectionTitle);
            this.subtitle = itemView.findViewById(R.id.forumSectionSubtitle);
        }
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder((LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_item, parent, false)));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, int position) {
        ForumSection forumSection = forumSectionArrayList.get(position);

        holder.icon.setImageResource(forumSection.getIcon());
        holder.title.setText(forumSection.getTitle());
        holder.subtitle.setText(forumSection.getSubtitle());

        holder.route.setOnClickListener(v -> {
            String url = FORUM_MAIN_URL + forumSection.getId();
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(url));
            context.startActivity(intent);
        });

        holder.cardView.setOnClickListener(v -> {
            Intent intent = new Intent(context, RequestActivity.class);
            intent.putExtra(FORUM_ID, forumSection.getId());
            intent.putExtra(FORUM_TITLE, forumSection.getTitle());
            intent.putExtra(FORUM_SUBTITLE, forumSection.getSubtitle());
            intent.putExtra(RECURSIVE, forumSection.getRecursive());
            intent.putExtra(HAS_APPS, forumSection.getHasApps());
            intent.putExtra(DIGEST_TOPIC_LINK, forumSection.getTopicLink());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return forumSectionArrayList.size();
    }

    public void addForums(ArrayList<ForumSection> forumSections) {
        forumSectionArrayList.addAll(forumSections);
        notifyItemRangeInserted(0, forumSections.size());
    }
}
