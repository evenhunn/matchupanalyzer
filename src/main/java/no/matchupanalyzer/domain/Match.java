package no.matchupanalyzer.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by hanseeve on 01.08.2018.
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
public class Match {
    private Tournament tournament;
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

    public boolean containsDeck(String deckName) {
        return playerDeckA.getDeckname().equalsIgnoreCase(deckName) || playerDeckB.getDeckname().equalsIgnoreCase(deckName);
    }

    public String getOpposingDeck(String deckName) {
        if (playerDeckA.getDeckname().equalsIgnoreCase(deckName)) {
            return playerDeckB.getDeckname();
        } else if (playerDeckB.getDeckname().equalsIgnoreCase(deckName)) {
            return playerDeckA.getDeckname();
        }
        return null;
    }

    public void setTournament(Tournament tournament) {
        if (this.playerDeckA != null) {
            this.playerDeckA.setTournament(tournament);
        }
        if (this.playerDeckB != null) {
            this.playerDeckB.setTournament(tournament);
        }
        this.tournament = tournament;
    }
}
