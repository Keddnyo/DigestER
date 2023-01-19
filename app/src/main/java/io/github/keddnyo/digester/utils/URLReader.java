package io.github.keddnyo.digester.utils;

import static io.github.keddnyo.digester.repositories.Constants.RESPONSE_TIMEOUT;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class URLReader {
    public String readText(String urlString, String charset) {
        try {
            URL url = new URL(urlString);
            URLConnection connection = url.openConnection();

            connection.setConnectTimeout(RESPONSE_TIMEOUT);
            connection.setReadTimeout(RESPONSE_TIMEOUT);

            InputStream inputStream = connection.getInputStream();

            int buffedSize = 1024;
            char[] buffer = new char[buffedSize];

            StringBuilder out = new StringBuilder();

            Reader in;

            if (charset == null) {
                in = new InputStreamReader(inputStream);
            } else {
                in = new InputStreamReader(inputStream, charset);
            }

            for (int numRead; (numRead = in.read(buffer, 0, buffer.length)) > 0; ) {
                out.append(buffer, 0, numRead);
            }

            return out.toString();
        } catch (MalformedURLException e) {
            return null;
        } catch (IOException e) {
            return null;
        }
    }
}
