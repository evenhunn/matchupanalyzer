package no.matchupanalyzer.domain;

import lombok.*;
import org.jsoup.nodes.Document;

import java.util.List;

/**
 * Created by hanseeve on 01.08.2018.
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Tournament {
    private String baseUrl;
    private String tournamentPageUrl;
    private String deckNamePageUrl;
    private List<String> resultPageUrlList;
    private Organizer organizer;
    private List<Round> resultsList;
    private List<PlayerDeck> playerDeckList;
    private Document tournamentPageDocument;

    public Event asEvent() {
        return Event.builder()
                .roundList(resultsList)
                .playerDeckList(playerDeckList)
                .build();
    }
}
