package io.github.keddnyo.digester.entities;

import androidx.annotation.DrawableRes;
import androidx.annotation.StringRes;

public class Forum {
    int id, recursive;
    boolean hasApps;
    @StringRes
    int title;
    @StringRes int subtitle;
    @DrawableRes
    int icon;

    public Forum(int id, int recursive, boolean hasApps, int title, int subtitle, int icon) {
        this.id = id;
        this.recursive = recursive;
        this.hasApps = hasApps;
        this.title = title;
        this.subtitle = subtitle;
        this.icon = icon;
    }

    public int getId() {
        return id;
    }

    public boolean getHasApps() {
        return hasApps;
    }

    public int getRecursive() {
        return recursive;
    }

    public int getTitle() {
        return title;
    }
    public int getSubtitle() {
        return subtitle;
    }

    public int getIcon() {
        return icon;
    }
}
