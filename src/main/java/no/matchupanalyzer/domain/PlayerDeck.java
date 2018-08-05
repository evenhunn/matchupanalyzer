package no.matchupanalyzer.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by hanseeve on 01.08.2018.
 */
@Getter
@Setter
@Builder
public class PlayerDeck {
    private Tournament tournament;
    private String playername;
    private String deckname;

    public boolean hasDeck() {
        return deckname != null && !deckname.isEmpty();
    }
}
