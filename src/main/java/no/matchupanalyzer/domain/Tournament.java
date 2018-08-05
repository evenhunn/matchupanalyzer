package no.matchupanalyzer.domain;

import lombok.*;
import no.matchupanalyzer.domain.simplified.Event;
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
    private String type;
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

    public void setTournamentReferences() {
        this.resultsList.forEach(round -> round.setTournament(this));
        this.playerDeckList.forEach(pdl -> pdl.setTournament(this));
    }
}
