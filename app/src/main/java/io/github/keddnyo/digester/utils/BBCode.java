package io.github.keddnyo.digester.utils;

import androidx.annotation.NonNull;

import java.util.HashMap;
import java.util.Map;

public class BBCode {
    @NonNull
    public static String parse(String text, boolean addListBreak) {
        String html = text;

        Map<String,String> bbMap = new HashMap<>();

        String listBreak = addListBreak ? "</br>" : "";

        bbMap.put("(\r\n|\r|\n|\n\r)", "<br/>");
        bbMap.put("\\[(?i)b\\](.+?)\\[/(?i)b\\]", "<strong>$1</strong>");
        bbMap.put("\\[(?i)i\\](.+?)\\[/(?i)i\\]", "<span style='font-style:italic;'>$1</span>");
        bbMap.put("\\[(?i)u\\](.+?)\\[/(?i)u\\]", "<span style='text-decoration:underline;'>$1</span>");
        bbMap.put("\\[(?i)quote\\](.+?)\\[/(?i)quote\\]", "<blockquote>$1</blockquote>");
        bbMap.put("\\[(?i)center\\](.+?)\\[/(?i)center\\]", "<div style='text-align:center'>$1</div>");
        bbMap.put("\\[(?i)color=(.+?)\\](.+?)\\[/(?i)color\\]", "<span style='color:$1;'>$2</span>");
        bbMap.put("\\[(?i)size=(.+?)\\](.+?)\\[/(?i)size\\]", "<span style='font-size:$1;'>$2</span>");
        bbMap.put("\\[(?i)img\\](.+?)\\[/(?i)img\\]", "");
        bbMap.put("\\[(?i)url=(.+?)\\](.+?)\\[/(?i)url\\]", "<a href='$1'>$2</a>");
        bbMap.put("\\[(?i)list\\]", "");
        bbMap.put(listBreak + "\\[(?i)list=1\\]", "");
        bbMap.put("\\[/(?i)list\\]", listBreak);
        bbMap.put("\\[\\*\\]", "• ");

        for (Map.Entry<String, String> entry: bbMap.entrySet()) {
            html = html.replaceAll(entry.getKey(), entry.getValue());
        }

        return html.trim();
    }
}
