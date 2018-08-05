package no.matchupanalyzer.codeflow;

import no.matchupanalyzer.analytics.MatchAnalyzer;
import no.matchupanalyzer.analytics.RelevantMatchExtractor;
import no.matchupanalyzer.domain.*;
import no.matchupanalyzer.domain.simplified.Event;
import no.matchupanalyzer.webanalyzer.TournamentLoader;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by hanseeve on 02.08.2018.
 */
public class Flow {
    private TournamentLoader tournamentLoader = new TournamentLoader();
    private RelevantMatchExtractor relevantMatchExtractor = new RelevantMatchExtractor();
    private MatchAnalyzer matchAnalyzer = new MatchAnalyzer();

    public static void main(String[] args) throws FileNotFoundException {
        Flow flow = new Flow();
        flow.run();
    }

    private void run() throws FileNotFoundException {
        List<Tournament> tournamentList = tournamentLoader.run();



        List<Match> matchList = relevantMatchExtractor.execute(mapToEvent(tournamentList));

        MatchupTable deckMatchups = matchAnalyzer.createMatchupTable(matchList);

        System.out.println("tested");
    }

    private List<Event> mapToEvent(List<Tournament> tournamentList) {
        return tournamentList.stream().map(Tournament::asEvent).collect(Collectors.toList());
    }

}
