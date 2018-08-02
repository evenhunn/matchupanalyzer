package no.matchupanalyzer.webanalyzer.scg.dataExtractors;

import no.matchupanalyzer.domain.*;
import no.matchupanalyzer.util.DocumentExtractor;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hanseeve on 30.07.2018.
 */
public class MatchupExtractor {
    @Autowired
    private DocumentExtractor documentExtractor = new DocumentExtractor();

    public List<Round> loadResults(Tournament tournament) throws IOException {
        List<Round> roundList = new ArrayList<>();

        for (String resultUrl : tournament.getResultPageUrlList()) {
            Document document = documentExtractor.getHtmlFromUrl(resultUrl);
            List<Element> resultHtmlElementList = extractRelevantResults(document);
            roundList.add(parseResults(resultHtmlElementList));
        }
        return roundList;
    }

    private Round parseResults(List<Element> resultTableElementList) {
        List<Match> resultList = new ArrayList<>();
        for (Element resultTableElement : resultTableElementList) {
            resultList.add(parseResult(resultTableElement));
        }
        return Round.builder().resultList(resultList).build();
    }

    private Match parseResult(Element resultString) {
        List<Element> columns = resultString.getElementsByTag("td");

        PlayerDeck playerDeckA = PlayerDeck.builder()
                .playername(columns.get(1).text())
                .build();

        PlayerDeck playerDeckB = PlayerDeck.builder()
                .playername(columns.get(5).text())
                .build();

        Match specialCase = checkIfSpecialCase(columns.get(3).text().toLowerCase(), playerDeckA, playerDeckB);
        if (specialCase!=null) {
            return specialCase;
        }

        Match result = Match.builder()
                .playerDeckA(playerDeckA)
                .playerDeckB(playerDeckB)
                .score(toScore(columns.get(3)))
                .build();
        if (result.getScore().getPlayer1Score() > result.getScore().getPlayer2Score()) {
            result.setWinner(result.getPlayerDeckA());
        } else if (result.getScore().getPlayer1Score() < result.getScore().getPlayer2Score()) {
            result.setWinner(result.getPlayerDeckB());
        }
        return result;
    }

    private Match checkIfSpecialCase(String score, PlayerDeck playerDeckA, PlayerDeck playerDeckB) {

        if (score.matches(".*bye.*")) {
            return Match.builder()
                    .playerDeckA(playerDeckA)
                    .score(Score.builder().build())
                    .build();
        } else if (score.matches(".*pending.*")) {
            return Match.builder()
                    .playerDeckA(playerDeckA)
                    .playerDeckB(playerDeckB)
                    .build();
        } else if (score.matches(".*draw d-d.*")) {
            return Match.builder()
                    .playerDeckA(playerDeckA)
                    .playerDeckB(playerDeckB)
                    .build();
        } else if (score.matches(".*lost l-l.*")) {
            return Match.builder()
                    .playerDeckA(playerDeckA)
                    .playerDeckB(playerDeckB)
                    .build();
        } else if (score.matches(".*loss.*")) {
            return Match.builder()
                    .playerDeckA(playerDeckA)
                    .playerDeckB(playerDeckB)
                    .build();
        }
        return null;
    }

    private Score toScore(Element element) {
        return Score.parseToScore(element.text());
    }

    private List<Element> extractRelevantResults(Document document) {
        Elements resultTableElements = document.getElementsByTag("tr");
        return resultTableElements.subList(1, resultTableElements.size()); //exclude top row
    }
}
