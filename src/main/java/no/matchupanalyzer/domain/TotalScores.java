package no.matchupanalyzer.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hanseeve on 03.08.2018.
 */
@Getter
@Setter
public class TotalScores {
    private Integer wins = 0;
    private Integer losses = 0;

    private List<Match> matchList = new ArrayList<>();

    public Integer countTotalGames() {
        return wins + losses;
    }

    public void calculateTotalScore(String deckName) {
        matchList.forEach(m -> {
            if (m.getScore() != null) {
                if (m.getPlayerDeckA().getDeckname().equalsIgnoreCase(deckName)) {
                    wins = wins + m.getScore().getPlayerAScore();
                    losses = losses + m.getScore().getPlayerBScore();
                } else {
                    wins = wins + m.getScore().getPlayerBScore();
                    losses = losses + m.getScore().getPlayerAScore();
                }
            }
        });
    }
}
