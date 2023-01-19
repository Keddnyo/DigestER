package io.github.keddnyo.digester.requests;

import io.github.keddnyo.digester.utils.URLReader;

public class DigestRequest {
    final int forumId, recursive;
    final boolean hasApps;
    final String periodStart, periodEnd;

    public DigestRequest(int forumId, int recursive, boolean hasApps, String periodStart, String periodEnd) {
        this.forumId = forumId;
        this.hasApps = hasApps;
        this.recursive = recursive;
        this.periodStart = periodStart;
        this.periodEnd = periodEnd;
    }

    public String getDigest() {
        String digestType;

        if (hasApps) {
            digestType = "dig_an_prog";
        } else {
            digestType = "digest2";
        }

        String url = "https://4pda.to/forum/" + digestType + ".php?act=nocache&f=" + forumId + "&date_from=" + periodStart + "&date_to=" + periodEnd + "&recursive=" + recursive;

        return new URLReader().readText(url, "CP1251");
    }
}