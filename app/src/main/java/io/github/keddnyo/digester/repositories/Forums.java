package io.github.keddnyo.digester.repositories;

import java.util.ArrayList;

import io.github.keddnyo.digester.R;
import io.github.keddnyo.digester.entities.Forum;

public class Forums {
    public static final int ANDROID_APPS = 212;
    public static final int ANDROID_GAMES = 213;
    public static final int WEARABLE_DEVICES_APPS = 810;
    public static final int BOOK_STORE = 218;

    public ArrayList<Forum> getForumArrayList() {
        ArrayList<Forum> forumArrayList = new ArrayList<>();

        forumArrayList.add(new Forum(ANDROID_APPS, 1, true, R.string.android, R.string.apps, R.drawable.apps));
        forumArrayList.add(new Forum(ANDROID_GAMES, 1, true, R.string.android, R.string.games, R.drawable.games));
        forumArrayList.add(new Forum(WEARABLE_DEVICES_APPS, 0, true, R.string.wearable_devices, R.string.apps, R.drawable.wearable_apps));
        forumArrayList.add(new Forum(BOOK_STORE, 0, false, R.string.book_store, R.string.book_depository, R.drawable.books));

        return forumArrayList;
    }
}
