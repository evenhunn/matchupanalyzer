package no.matchupanalyzer.webanalyzer.scg.dataextractors;

import no.matchupanalyzer.domain.PlayerDeck;
import no.matchupanalyzer.util.DocumentExtractor;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by hanseeve on 01.08.2018.
 */
public class PlayerDeckLoader {
    private DocumentExtractor documentExtractor = new DocumentExtractor();

    public List<PlayerDeck> execute(String playerDeckUrl) throws IOException {
        List<PlayerDeck> playerDeckList = new ArrayList<>();

        Document playerDeckDocument = documentExtractor.getHtmlFromUrl(playerDeckUrl);
        Optional<Element> tableElements = playerDeckDocument.getElementsByTag("table")
                .stream()
                .filter(e -> matchesDeckTable(e.text().toLowerCase()))
                .findFirst();
        List<Element> tableRowElements = tableElements.get().getElementsByTag("tr").stream().filter(e -> e.toString().toLowerCase().contains("finish_display")).collect(Collectors.toList());
        tableRowElements.forEach(e -> {
            Elements elements = e.getElementsByTag("td");
            playerDeckList.add(PlayerDeck.builder()
                    .playername(elements.get(2).text())
                    .deckname(elements.get(0).text())
                    .build());
        });
        return playerDeckList;
    }

    private boolean matchesDeckTable(String text) {
        return text.contains("deck") &&
                text.contains("finish") &&
                text.contains("player") &&
                text.contains("event") &&
                text.contains("format") &&
                text.contains("date") &&
                text.contains("location");
    }
}
