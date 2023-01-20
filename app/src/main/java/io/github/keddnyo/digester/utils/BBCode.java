package io.github.keddnyo.digester.utils;

import androidx.annotation.NonNull;

import java.util.HashMap;
import java.util.Map;

public class BBCode {
    @NonNull
    public static String parse(String text) {
        String html = text;

        Map<String,String> bbMap = new HashMap<>();

        bbMap.put("(\r\n|\r|\n|\n\r)", "<br/>");
        bbMap.put("\\[(?i)b\\](.+?)\\[/(?i)b\\]", "<strong>$1</strong>");
        bbMap.put("\\[(?i)i\\](.+?)\\[/(?i)i\\]", "<span style='font-style:italic;'>$1</span>");
        bbMap.put("\\[(?i)u\\](.+?)\\[/(?i)u\\]", "<span style='text-decoration:underline;'>$1</span>");
        bbMap.put("\\[(?i)quote\\](.+?)\\[/(?i)quote\\]", "<blockquote>$1</blockquote>");
        bbMap.put("\\[(?i)center\\](.+?)\\[/(?i)center\\]", "<div align='center'>$1");
        bbMap.put("\\[(?i)color=(.+?)\\](.+?)\\[/(?i)color\\]", "<span style='color:$1;'>$2</span>");
        bbMap.put("\\[(?i)size=(.+?)\\](.+?)\\[/(?i)size\\]", "<span style='font-size:$1;'>$2</span>");
        bbMap.put("\\[(?i)img\\](.+?)\\[/(?i)img\\]", "");
        bbMap.put("\\[(?i)url=(.+?)\\](.+?)\\[/(?i)url\\]", "<a href='$1'>$2</a>");
        bbMap.put("\\[(?i)list\\]", "\r");
        bbMap.put("\\[(?i)list=1\\]", "\r");
        bbMap.put("\\[/(?i)list\\]", "\r");
        bbMap.put("\\[\\*\\]", "• ");
        bbMap.put("\n\n", "\n");

        for (Map.Entry<String, String> entry: bbMap.entrySet()) {
            html = html.replaceAll(entry.getKey(), entry.getValue());
        }

        return html.trim();
    }
}
