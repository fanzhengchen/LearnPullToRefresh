package com.example.administrator.learneventbus;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.StringTokenizer;


public class InputReader {
    private StringTokenizer tokenizer;
    private BufferedReader reader;

    public InputReader(InputStream inputStream) {
        this(inputStream, StandardCharsets.UTF_8);
    }

    public InputReader(InputStream stream, Charset charset) {
        tokenizer = null;
        reader = new BufferedReader(new InputStreamReader(stream, charset));

    }


    public boolean hasNext() {
        while (tokenizer == null || !tokenizer.hasMoreTokens()) {
            try {
                tokenizer = new StringTokenizer(reader.readLine());
            } catch (Exception e) {
                return false;
            }
        }
        return true;
    }

    public String next() {
        if (!hasNext()) {
            throw new RuntimeException();
        }
        return tokenizer.nextToken();
    }
}
