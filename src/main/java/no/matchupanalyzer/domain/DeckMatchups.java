package no.matchupanalyzer.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hanseeve on 03.08.2018.
 */
@Setter
@Getter
public class DeckMatchups {
    private String deckName;
    private Map<String, TotalScores> matchups = new HashMap<>();

    private Integer countTotalGames() {
        Integer count = 0;
        for (TotalScores ts : matchups.values()) {
            count = count + ts.countTotalGames();
        }
        return count;
    }

    public void addMatchToDeck(Match match, String deckName) {
        String opposingDeck = match.getOpposingDeck(deckName);
        if (opposingDeck.equalsIgnoreCase(deckName)) {
            return;
        }
        TotalScores totalScores = createOrGetScoreForMatchup(matchups, opposingDeck);
        totalScores.getMatchList().add(match);
    }

    private TotalScores createOrGetScoreForMatchup(Map<String, TotalScores> matchups, String opposingDeck) {
        if (!matchups.containsKey(opposingDeck)) {
            matchups.put(opposingDeck, new TotalScores());
        }
        return matchups.get(opposingDeck);
    }

    public void calculateTotalScores() {
        getMatchups().values().forEach(v -> v.calculateTotalScore(deckName));
    }
}
