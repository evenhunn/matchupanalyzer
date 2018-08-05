package no.matchupanalyzer.analytics;

import no.matchupanalyzer.domain.Match;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Created by hanseeve on 03.08.2018.
 */
public class DeckAnalyzer {

    public List<String> findAllDecks(List<Match> matchList) {
        List<String> deckNameList = new ArrayList<>();

        for (Match match : matchList) {
            addToListIfMissing(match.getPlayerDeckA().getDeckname(), deckNameList);
            addToListIfMissing(match.getPlayerDeckB().getDeckname(), deckNameList);
        }

        deckNameList.sort(Comparator.comparing(Object::toString));

        return deckNameList;
    }

    private void addToListIfMissing(String deckName, List<String> deckNameList) {
        for (String deckNameInList : deckNameList) {
            if (deckName.equalsIgnoreCase(deckNameInList)) {
                return;
            }
        }
        deckNameList.add(deckName);
    }
}
