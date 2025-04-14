package edu.jsu.mcis.cs408.crosswordmagic.model;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.net.ssl.HttpsURLConnection;

import edu.jsu.mcis.cs408.crosswordmagic.controller.CrosswordMagicController;

public class WebServiceModel extends AbstractModel {

    private static final String TAG = "WebServiceModel";

    private static final String GET_URL = "http://ec2-3-142-171-53.us-east-2.compute.amazonaws.com:8080/CrosswordMagicServer/puzzle";

    private JSONObject jsonData;

    private final ExecutorService requestThreadExecutor;
    private final Runnable httpGetRequestThread;
    private Future<?> pending;

    public WebServiceModel(Context context) {

        requestThreadExecutor = Executors.newSingleThreadExecutor();

        httpGetRequestThread = new Runnable() {

            @Override
            public void run() {

                /* If a previous request is still pending, cancel it */

                if (pending != null) { pending.cancel(true); }

                /* Begin new request now, but don't wait for it */

                try {
                    pending = requestThreadExecutor.submit(new HTTPRequestTask("GET", GET_URL));
                }
                catch (Exception e) { Log.e(TAG, " Exception: ", e); }

            }

        };
    }

    // Start GET Request (called from Controller)

    public void getPuzzleListFromAPI() {
        httpGetRequestThread.run();
    }

    // Setter / Getter Methods for JSON LiveData

    public JSONObject getJsonData() {
        if (jsonData == null) {
            jsonData = new JSONObject();
        }
        return jsonData;
    }

    // Private Class for HTTP Request Threads

    private class HTTPRequestTask implements Runnable {

        private static final String TAG = "HTTPRequestTask";
        private final String method, urlString;

        HTTPRequestTask(String method, String urlString) {
            this.method = method;
            this.urlString = urlString;
        }

        @Override
        public void run() {
            try {
                JSONArray results = doRequest(urlString);

                ArrayList<PuzzleListItem> puzzles = new ArrayList<>();

                for (int i = 0; i < results.length(); i++) {
                    JSONObject puzzleObj = results.getJSONObject(i);
                    int puzzleId = puzzleObj.getInt("id");
                    String puzzleName = puzzleObj.getString("name");
                    PuzzleListItem puzzleListItem = new PuzzleListItem(puzzleId, puzzleName);

                    puzzles.add(puzzleListItem);
                }

                firePropertyChange(CrosswordMagicController.PUZZLE_LIST_FROM_API_PROPERTY, null, puzzles.toArray(new PuzzleListItem[]{}));

            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }

        /* Create and Send Request */

        private JSONArray doRequest(String urlString) {

            StringBuilder r = new StringBuilder();
            String line;

            HttpURLConnection conn = null;
            JSONArray results = null;

            /* Log Request Data */

            try {

                /* Check if task has been interrupted */

                if (Thread.interrupted()) {
                    throw new InterruptedException();
                }

                /* Create Request */

                URL url = new URL(urlString);

                conn = (HttpURLConnection)url.openConnection();

                conn.setReadTimeout(10000); /* ten seconds */
                conn.setConnectTimeout(15000); /* fifteen seconds */

                conn.setRequestMethod(method);
                conn.setDoInput(true);

                /* Send Request */
                conn.connect();

                /* Check if task has been interrupted */

                if (Thread.interrupted()) {
                    throw new InterruptedException();
                }

                /* Get Reader for Results */

                int code = conn.getResponseCode();

                if (code == HttpsURLConnection.HTTP_OK) {

                    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                    /* Read Response Into StringBuilder */

                    do {
                        line = reader.readLine();
                        if (line != null)
                            r.append(line);
                    }
                    while (line != null);

                }

                /* Check if task has been interrupted */

                if (Thread.interrupted())
                    throw new InterruptedException();

                /* Parse Response as JSON */

                results = new JSONArray(r.toString());

            }
            catch (Exception e) {
                Log.e(TAG, " Exception: ", e);
            }
            finally {
                if (conn != null) { conn.disconnect(); }
            }

            /* Finished; Log and Return Results */

            Log.d(TAG, " JSON: " + r.toString());

            return results;

        }

    }

}