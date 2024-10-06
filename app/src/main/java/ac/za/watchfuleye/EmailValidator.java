package ac.za.watchfuleye;

import static android.content.ContentValues.TAG;

import android.util.Log;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Call;
import okhttp3.Callback;
import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

public class EmailValidator {

    private static final String KEY = "d28wy7mrY0sepwgGRtbyg";
    private static String EMAILLISTVERIFY = "https://apps.emaillistverify.com/api/verifyEmail?secret=" + KEY + "&email=";

    private final OkHttpClient client = new OkHttpClient();

    public void checkEmailWithChatGPT(String email, EmailValidationCallback callback) {
        String url = EMAILLISTVERIFY + email; // Construct the full URL

        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callback.onFailure(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseData = response.body().string();

                        Log.d(TAG, "Response Data: " + responseData); // Log responseData

                        boolean isValid = (responseData.equalsIgnoreCase("ok"));
                        callback.onSuccess(isValid);

                } else {
                    callback.onFailure(new IOException("Unexpected code " + response));
                }
            }
        });
    }

    public interface EmailValidationCallback {
        void onSuccess(boolean isValid);
        void onFailure(Exception e);
    }
}
