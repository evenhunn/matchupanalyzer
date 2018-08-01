package no.matchupanalyzer.webanalyzer.scg;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * Created by hanseeve on 30.07.2018.
 */
public class ResultsUrlExtractor {
    public void getHtmlFromUrl() throws Exception {
        URL oracle = new URL("http://www.starcitygames.com/events/280718_indianapolis.html");
        BufferedReader in = new BufferedReader(
                new InputStreamReader(oracle.openStream()));

        String inputLine;
        while ((inputLine = in.readLine()) != null)
            System.out.println(inputLine);
        in.close();
    }
}
