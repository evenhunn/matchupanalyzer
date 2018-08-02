package no.matchupanalyzer.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

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
