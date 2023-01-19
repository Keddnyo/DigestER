package io.github.keddnyo.digester.repositories;

import java.util.ArrayList;

import io.github.keddnyo.digester.R;
import io.github.keddnyo.digester.entities.ForumSection;

public class ForumSections {
    public static final int ANDROID_APPS = 212;
    public static final int ANDROID_GAMES = 213;
    public static final int WEARABLE_DEVICES_APPS = 810;
    public static final int BOOK_STORE = 218;
    public static final String ANDROID_APPS_DIGEST_TOPIC_URL = "https://4pda.to/forum/index.php?showtopic=127361";
    public static final String ANDROID_GAMES_DIGEST_TOPIC_URL = "https://4pda.to/forum/index.php?showtopic=381335";
    public static final String WEARABLE_DEVICES_APPS_DIGEST_TOPIC_URL = "https://4pda.to/forum/index.php?showtopic=979689";
    public static final String BOOK_DEPOSITORY_DIGEST_TOPIC_URL = "https://4pda.to/forum/index.php?showtopic=902627";

    public ArrayList<ForumSection> getForumArrayList() {
        ArrayList<ForumSection> forumSectionArrayList = new ArrayList<>();

        forumSectionArrayList.add(new ForumSection(ANDROID_APPS, 1, true, R.string.android, R.string.apps, R.drawable.apps, ANDROID_APPS_DIGEST_TOPIC_URL));
        forumSectionArrayList.add(new ForumSection(ANDROID_GAMES, 1, true, R.string.android, R.string.games, R.drawable.games, ANDROID_GAMES_DIGEST_TOPIC_URL));
        forumSectionArrayList.add(new ForumSection(WEARABLE_DEVICES_APPS, 0, true, R.string.wearable_devices, R.string.apps, R.drawable.wearable_apps, WEARABLE_DEVICES_APPS_DIGEST_TOPIC_URL));
        forumSectionArrayList.add(new ForumSection(BOOK_STORE, 0, false, R.string.book_store, R.string.book_depository, R.drawable.books, BOOK_DEPOSITORY_DIGEST_TOPIC_URL));

        return forumSectionArrayList;
    }
}
