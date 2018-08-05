package no.matchupanalyzer.analytics;

import no.matchupanalyzer.domain.DeckMatchups;
import no.matchupanalyzer.domain.Match;
import no.matchupanalyzer.domain.MatchupTable;

import java.util.List;

/**
 * Created by hanseeve on 03.08.2018.
 */
public class MatchAnalyzer {
    DeckAnalyzer deckAnalyzer = new DeckAnalyzer();

    public MatchupTable createMatchupTable(List<Match> matchList) {
        MatchupTable matchupTable = new MatchupTable();

        List<String> deckNameList = deckAnalyzer.findAllDecks(matchList);

        deckNameList.forEach(dnl -> matchupTable
                .getMatchupsMap()
                .put(dnl, findDeckMatchups(dnl, matchList)));
        return matchupTable;
    }

    private DeckMatchups findDeckMatchups(String deckName, List<Match> matchList) {
        DeckMatchups deckMatchups = new DeckMatchups();
        deckMatchups.setDeckName(deckName);
        for (Match match : matchList) {
            if (match.containsDeck(deckName)) {
                deckMatchups.addMatchToDeck(match, deckName);
            }
        }
        deckMatchups.calculateTotalScores();

        return deckMatchups;
    }
}
