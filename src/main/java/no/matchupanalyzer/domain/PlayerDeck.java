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
    String playername;
    String deckname;

    public boolean hasDeck() {
        return deckname != null && !deckname.isEmpty();
    }
}
