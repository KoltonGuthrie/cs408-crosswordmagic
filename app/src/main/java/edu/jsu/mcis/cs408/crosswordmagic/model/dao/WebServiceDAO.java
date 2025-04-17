package edu.jsu.mcis.cs408.crosswordmagic.model.dao;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.net.ssl.HttpsURLConnection;

import edu.jsu.mcis.cs408.crosswordmagic.model.Puzzle;
import edu.jsu.mcis.cs408.crosswordmagic.model.PuzzleListItem;
import edu.jsu.mcis.cs408.crosswordmagic.model.Word;

public class WebServiceDAO {
    private final String TAG = "WebServiceDAO";
    private final DAOFactory daoFactory;
    WebServiceDAO(DAOFactory daoFactory) { this.daoFactory = daoFactory; }
    private static final String HTTP_METHOD = "GET";
    private static final String ROOT_URL = "http://ec2-3-142-171-53.us-east-2.compute.amazonaws.com:8080/CrosswordMagicServer/puzzle";
    private String requestUrl;
    private ExecutorService pool;

    public int downloadPuzzle(int id) {
        requestUrl = ROOT_URL + "?id=" + id;
        int puzzleId = -1;

        try {
            pool = Executors.newSingleThreadExecutor();
            Future<String> pending = pool.submit(new CallableHTTPRequest());
            String response = pending.get();
            pool.shutdown();

            JSONObject json = new JSONObject(response);

            JSONArray words = json.getJSONArray("puzzle");

            HashMap<String, String> puzzleParams = new HashMap<>();

            puzzleParams.put("name", json.get("name").toString());
            puzzleParams.put("description", json.get("description").toString());
            puzzleParams.put("width", json.get("width").toString());
            puzzleParams.put("height", json.get("height").toString());

            Puzzle puzzle = new Puzzle(puzzleParams);

            puzzleId = daoFactory.getPuzzleDAO().create(puzzle);

            for(int i = 0; i < words.length(); ++i) {
                JSONObject element = (JSONObject)words.get(i);

                HashMap<String, String> wordParams = new HashMap<>();

                wordParams.put("puzzleid", String.valueOf(puzzleId));
                wordParams.put("clue", element.get("clue").toString());
                wordParams.put("column", element.get("column").toString());
                wordParams.put("box", element.get("box").toString());
                wordParams.put("row", element.get("row").toString());
                wordParams.put("word", element.get("word").toString());
                wordParams.put("direction", element.get("direction").toString());

                Word word = new Word(wordParams);

                daoFactory.getWordDAO().create(word);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return puzzleId;

    }

    public ArrayList<PuzzleListItem> list() {
        requestUrl = ROOT_URL;
        ArrayList<PuzzleListItem> result = null;
        try {
            pool = Executors.newSingleThreadExecutor();
            Future<String> pending = pool.submit(new CallableHTTPRequest());
            String response = pending.get();
            pool.shutdown();

            JSONArray json = new JSONArray(response);

            result = new ArrayList<>();

            for (int i = 0; i < json.length(); ++i) {
                JSONObject element = (JSONObject) json.get(i);

                int id = Integer.parseInt(element.get("id").toString());
                String name = element.get("name").toString();

                result.add(new PuzzleListItem(id, name));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;

    }

    public class CallableHTTPRequest implements Callable<String> {
        @Override
        public String call() {
            StringBuilder r = new StringBuilder();
            String line;

            HttpURLConnection conn = null;

            try {
                /* Create Request */
                Log.i("TEST", "REQUEST URL: " + requestUrl);
                URL url = new URL(requestUrl);

                if (Thread.interrupted()) {
                    throw new InterruptedException();
                }

                conn = (HttpURLConnection) url.openConnection();

                conn.setReadTimeout(10000); /* ten seconds */
                conn.setConnectTimeout(15000); /* fifteen seconds */

                conn.setRequestMethod(HTTP_METHOD);
                conn.setDoInput(true);

                /* Send Request */
                conn.connect();

                /* Check if task has been interrupted */

                if (Thread.interrupted()) {
                    throw new InterruptedException();
                }

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
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            return r.toString().trim();
        }

    }

}
