package no.matchupanalyzer.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Created by hanseeve on 01.08.2018.
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
public class Round {
    private Tournament tournament;
    private List<Match> matchList;

    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
        this.matchList.forEach(m -> m.setTournament(tournament));
    }
}
