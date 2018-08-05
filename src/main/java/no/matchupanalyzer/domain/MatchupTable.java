package no.matchupanalyzer.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hanseeve on 03.08.2018.
 */
@Getter
@Setter
public class MatchupTable {
    Map<String, DeckMatchups> matchupsMap = new HashMap<>();
}
