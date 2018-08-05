package no.matchupanalyzer.analytics;

import no.matchupanalyzer.domain.simplified.Event;
import no.matchupanalyzer.domain.Match;
import no.matchupanalyzer.domain.Round;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hanseeve on 02.08.2018.
 */
public class RelevantMatchExtractor {
    public List<Match> execute(List<Event> events) {
        List<Match> relevantMatches = new ArrayList<>();

        for (Event event : events) {
            for (Round round : event.getRoundList()) {
                for (Match match : round.getMatchList()) {
                    if (match.isRelevant()) {
                        relevantMatches.add(match);
                    }
                }
            }
        }
        return relevantMatches;
    }
}
