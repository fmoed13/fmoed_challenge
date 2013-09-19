package org.foi.fmoed.managers;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;


public class ResultManager {

    String RESULT_URL = "http://evodeployment.evolaris.net/brainwriting/apiTest";

    Document doc;

    ResultManager(String resultURL) {
        RESULT_URL = resultURL;
    }

    public void getResult() throws IOException {
        doc = Jsoup.connect(RESULT_URL).get();
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
