package no.matchupanalyzer.webanalyzer.scg.dataextractors;

import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class MatchExtractor {
    @Autowired
    private DocumentExtractor documentExtractor = new DocumentExtractor();

    public List<Round> loadResults(Tournament tournament) throws IOException {
        List<Round> roundList = new ArrayList<>();

        for (String resultUrl : tournament.getResultPageUrlList()) {
            try {
                Document document = documentExtractor.getHtmlFromUrl(resultUrl);
                List<Element> resultHtmlElementList = extractRelevantResults(document);
                roundList.add(parseResults(resultHtmlElementList));
            } catch (IndexOutOfBoundsException e) {
                log.warn(String.format("Error encountered during parsing of results from url: %s - exception message is %s", resultUrl, e.getMessage()));
                log.info("Skipping this round.");
            }
        }
        return roundList;
    }

    private Round parseResults(List<Element> resultTableElementList) {
        List<Match> resultList = new ArrayList<>();
        for (Element resultTableElement : resultTableElementList) {
            resultList.add(parseMatch(resultTableElement));
        }
        return Round.builder().matchList(resultList).build();
    }

    private Match parseMatch(Element resultString) {
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
        if (result.getScore().getPlayerAScore() > result.getScore().getPlayerBScore()) {
            result.setWinner(result.getPlayerDeckA());
        } else if (result.getScore().getPlayerAScore() < result.getScore().getPlayerBScore()) {
            result.setWinner(result.getPlayerDeckB());
        }
        return result;
    }

    private Match checkIfSpecialCase(String scoreString, PlayerDeck playerDeckA, PlayerDeck playerDeckB) {
        if (scoreString.matches(".*bye.*")) {
            return Match.builder()
                    .playerDeckA(playerDeckA)
                    .score(Score.builder().build())
                    .build();
        } else if (scoreString.matches(".*pending.*")) {
            return Match.builder()
                    .playerDeckA(playerDeckA)
                    .playerDeckB(playerDeckB)
                    .build();
        } else if (scoreString.matches(".*draw d-d.*")) {
            return Match.builder()
                    .playerDeckA(playerDeckA)
                    .playerDeckB(playerDeckB)
                    .build();
        } else if (scoreString.matches(".*lost l-l.*")) {
            return Match.builder()
                    .playerDeckA(playerDeckA)
                    .playerDeckB(playerDeckB)
                    .build();
        } else if (scoreString.matches(".*loss.*")) {
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
