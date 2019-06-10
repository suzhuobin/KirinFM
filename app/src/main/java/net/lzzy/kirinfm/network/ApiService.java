package net.lzzy.kirinfm.network;

import android.text.TextUtils;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


/**
 * @author Administrator
 */
public class ApiService {
    private static final OkHttpClient client = new OkHttpClient();

    public static String get(String address) throws IOException {
        URL url = new URL(address);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        try {
            conn.setRequestMethod("GET");
            /**
             * 连接超时时间
             */
            conn.setConnectTimeout(6 * 1000);
            /**
             * 读取超时时间
             */
            conn.setReadTimeout(6 * 1000);
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            StringBuilder builder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line).append("\n");
            }
            reader.close();
            return builder.toString();
        } finally {
            conn.disconnect();
        }
    }

    public static void post(String address, JSONObject json) throws IOException {
        URL url = new URL(address);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);
        conn.setChunkedStreamingMode(0);
        conn.setRequestProperty("Content-type", "application/json");
        byte[] data = json.toString().getBytes(StandardCharsets.UTF_8);
        conn.setRequestProperty("Content-Length", String.valueOf(data.length));
        //设置缓存
        conn.setUseCaches(false);
        try (OutputStream stream = conn.getOutputStream()) {
            //写数据
            stream.write(data);
            //提交
            stream.flush();
        } finally {
            conn.disconnect();
        }
    }

    public static String okGet(String address) throws IOException {
        Request request = new Request.Builder()
                .url(address)
                .build();
        //同步：Response response = client.newCall(request).execute()
        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                assert response.body() != null;
                return response.body().string();
            } else {
                throw new IOException("错误码：" + response.code());
            }
        }
        //region 异步：
        /*Response response = client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });*/
        //endregion
    }

    public static String okGet(String address, String args, HashMap<String, Object> headers) throws IOException {
        if (!TextUtils.isEmpty(args)) {
            address = address.concat("?").concat(args);
        }
        Request.Builder builder = new Request.Builder().url(address);
        if (headers != null && headers.size() > 0) {
            for (Object o : headers.entrySet()) {
                Map.Entry entry = (Map.Entry) o;
                String kay = entry.getKey().toString();
                Object val = entry.getValue();
                if (val instanceof String) {
                    builder = builder.header(kay, val.toString());
                } else if (val instanceof List) {
                    for (String v : ApiService.<List<String>>cast(val)) {
                        builder = builder.addHeader(kay, v);
                    }
                }
            }
        }
        Request request = builder.build();
        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                return response.body().string();
            } else {
                throw new IOException("错误码：" + response.code());
            }
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T cast(Object object) {
        return (T) object;
    }

    public static int okPost(String address, JSONObject json) throws IOException {
        //请求体
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
                json.toString());
        Request request = new Request.Builder()
                .url(address)
                .post(requestBody)
                .build();
        try (Response response = client.newCall(request).execute()) {
            return response.code();
        }
    }

    public static String okRequest(String address, JSONObject json) throws IOException {
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
                json.toString());
        Request request = new Request.Builder()
                .url(address)
                .post(requestBody)
                .build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }
}

