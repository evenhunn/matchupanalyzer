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
public class Match {
    private PlayerDeck playerDeckA;
    private PlayerDeck playerDeckB;
    private String resultString;
    private Score score;
    private PlayerDeck winner;

    public boolean isRelevant() {
        return playerDeckA != null &&
                playerDeckA.hasDeck() &&
                playerDeckB != null &&
                playerDeckB.hasDeck();
    }
}
