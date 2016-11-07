package com.herocorp.infra.parsers;

import android.os.AsyncTask;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class AppJsonParser {

    private static AppJsonParser appJsonParser;

    private final ObjectWriter objectWriter;
    private final JsonFactory jsonFactory;

    private AppJsonParser() {
        ObjectMapper objectMapper = new ObjectMapper();
        this.objectWriter = objectMapper.writer();
        this.jsonFactory = objectMapper.getFactory();
    }

    public static AppJsonParser getInstance() {
        if (appJsonParser == null) {
            appJsonParser = new AppJsonParser();
        }
        return appJsonParser;
    }

/*    public <T> void getObjectFromString(final String string, final Class<T> clazz,
                                        final ResponseListener<T> listener) {
        AsyncTask<Void, Void, T> asyncTask = new AsyncTask<Void, Void, T>() {
            Exception mException;

            @Override
            protected T doInBackground(Void... params) {
                try {
                    return getObjectFromString(string, clazz);
                } catch (IOException e) {
                    e.printStackTrace();
                    mException = e;
                    return null;
                }
            }

            @Override
            protected void onPostExecute(T t) {
                super.onPostExecute(t);
                if (mException != null) {
                    listener.onFailure(mException);
                } else {
                    listener.onSuccess(t);
                }
            }
        };
        asyncTask.execute();
    }*/

    public <T> T getObjectFromString(String string, Class<T> clazz) throws IOException {
        try {
            JsonParser parser = jsonFactory.createParser(stringToInputStream(string));
            T data = parser.readValueAs(clazz);
            parser.close();
            return data;
        } catch (JsonMappingException e) {
            if (string.contains("\"data\":[],")) {
                JsonParser parser = jsonFactory.createParser(stringToInputStream(string.replace("\"data\":[],", "")));
                T data = parser.readValueAs(clazz);
                parser.close();
                return data;
            }
        }
        throw new IOException("Parsing error occurred.");
    }

    public <T> T getObjectFromInputStream(InputStream inputStream, Class<T> clazz) throws IOException {
        JsonParser parser = jsonFactory.createParser(inputStream);
        T data = parser.readValueAs(clazz);
        parser.close();
        return data;
    }

    public String getStringFromObject(Object object) throws IOException {
        return objectWriter.writeValueAsString(object);
    }

    private InputStream stringToInputStream(String string) throws IOException {

        // convert String into InputStream
        InputStream is = new ByteArrayInputStream(string.getBytes("utf-8"));

        // read it with BufferedReader
        BufferedReader br = new BufferedReader(new InputStreamReader(is));

        br.close();
        return is;
    }
}
