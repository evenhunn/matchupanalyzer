package no.matchupanalyzer.domain.simplified;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import no.matchupanalyzer.domain.PlayerDeck;
import no.matchupanalyzer.domain.Round;

import java.util.List;

/**
 * Created by hanseeve on 02.08.2018.
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
public class Event {
    List<Round> roundList;
    List<PlayerDeck> playerDeckList;
}
