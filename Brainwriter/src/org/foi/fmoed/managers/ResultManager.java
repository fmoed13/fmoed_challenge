package org.foi.fmoed.managers;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;


public class ResultManager extends Activity {

    String RESULT_URL = "http://evodeployment.evolaris.net/brainwriting/results?group=";
    Context rm_context;
    Document doc;

    public ResultManager(Context c, String groupName) {
        Log.i("group_name", groupName);
        rm_context = c;
        RESULT_URL += groupName;
    }

    public ResultManager(String resultURL) {
        RESULT_URL = resultURL;
    }

    public void getResult() {
        try {
            doc = Jsoup.connect(RESULT_URL).get();
            Element table = doc.select("table").first();
            Elements tds = table.getElementsByTag("td");
            for (Element td: tds) {
                Log.i("RESULT", td.text());
            }
        } catch(IOException e) {
            Log.e("IOERROR", "There was an error.");
            Toast.makeText(rm_context, "Failed fetching results from server.", Toast.LENGTH_LONG);
        }
    }

    public int countRounds() {
        return 0;
    }

    public String[] getUsers(int round) {
        return null;
    }

    public String[] getIdea(int round, String user) {
        return null;
    }


}
