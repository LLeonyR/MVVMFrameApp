package com.leonyr.mvvm.net.converter;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonParseException;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.leonyr.lib.utils.LogUtil;

import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import retrofit2.Converter;

import static java.nio.charset.StandardCharsets.UTF_8;

final class GsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    private final Gson gson;
    private final TypeAdapter<T> adapter;

    GsonResponseBodyConverter(Gson gson, TypeAdapter<T> adapter) {
        this.gson = gson;
        this.adapter = adapter;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        String response = value.string();
        try {
            JSONObject json = new JSONObject(response);
            String data = json.optString("data");
            if (data != null && data.length() < 6){
                if (data.equals("null") || data.equals("{}") || data.equals("[]") || data.equals("")){
                    json.remove("data");
                }
            }
            LogUtil.e("Request Body: " + response.length());
            LogUtil.e("Request json: " + json.toString());
            MediaType contentType = value.contentType();
            Charset charset = contentType != null ? contentType.charset(UTF_8) : UTF_8;
            InputStream inputStream = new ByteArrayInputStream(json.toString().getBytes());
            Reader reader = new InputStreamReader(inputStream, charset);
            JsonReader jsonReader = gson.newJsonReader(reader);
            T result = adapter.read(jsonReader);
//            throw new NullPointerException("test");
            return result;
        } catch (JsonParseException e) {
            e.printStackTrace();
            throw e;
        } catch (Exception e){
            e.printStackTrace();
            throw new JsonParseException("JSON PARSE ERROR: ", new Throwable(response));
        }
        finally {
            value.close();
        }
    }
}
