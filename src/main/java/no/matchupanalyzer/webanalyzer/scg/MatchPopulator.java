package no.matchupanalyzer.webanalyzer.scg;

import no.matchupanalyzer.domain.PlayerDeck;
import no.matchupanalyzer.domain.Round;
import no.matchupanalyzer.domain.Match;
import no.matchupanalyzer.domain.Tournament;

import java.util.Arrays;
import java.util.List;

/**
 * Created by hanseeve on 02.08.2018.
 */
public class MatchPopulator {

    private Tournament tournament;

    public void execute(Tournament tournament) {
        this.tournament = tournament;

        for (Round round : tournament.getResultsList()) {
            for (Match match : round.getMatchList()) {
                populateIfAble(match.getPlayerDeckA());
                populateIfAble(match.getPlayerDeckB());
            }

        }
    }

    private void populateIfAble(PlayerDeck playerDeck) {
        if (playerDeck == null || playerDeck.getPlayername().isEmpty()) {
            return;
        }
        tournament.getPlayerDeckList().forEach(p -> {
            if (nameMatches(playerDeck, p)) {
                playerDeck.setDeckname(p.getDeckname());
            }
        });
    }

    private boolean nameMatches(PlayerDeck playerDeck, PlayerDeck p) {
       List<String> roundPlayer = Arrays.asList(playerDeck.getPlayername().replace(",","").split(" "));
       List<String> playerWithDeck = Arrays.asList(p.getPlayername().replace(",", "").split(" "));
        for (String s : roundPlayer) {
            if (!playerWithDeck.contains(s)) {
                return false;
            }
        }
        return true;
    }
}
