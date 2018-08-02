package no.matchupanalyzer.webanalyzer.scg;

import lombok.extern.slf4j.Slf4j;
import no.matchupanalyzer.domain.Tournament;
import no.matchupanalyzer.util.DocumentExtractor;
import no.matchupanalyzer.webanalyzer.scg.dataextractors.MatchupExtractor;
import no.matchupanalyzer.webanalyzer.scg.dataextractors.PlayerDeckLoader;

import java.io.IOException;

/**
 * Created by hanseeve on 01.08.2018.
 */
@Slf4j
public class ScgTournamentLoader {

    private DocumentExtractor documentExtractor = new DocumentExtractor();
    private PlayerDeckLoader playerDeckLoader = new PlayerDeckLoader();
    private UrlFetcher urlFetcher = new UrlFetcher();
    private MatchupExtractor matchupExtractor = new MatchupExtractor();
    private MatchPopulator matchPopulator = new MatchPopulator();

    private String baseurl = "http://www.starcitygames.com";

    public Tournament execute(Tournament tournament) {
        try {

            tournament.setTournamentPageDocument(documentExtractor.getHtmlFromUrl(tournament.getTournamentPageUrl()));
            tournament.setDeckNamePageUrl(urlFetcher.findPlayerDeckUrl(tournament));
            tournament.setPlayerDeckList(playerDeckLoader.execute(tournament.getDeckNamePageUrl()));
            tournament.setResultPageUrlList(urlFetcher.findResultPageUrlList(tournament));
            tournament.setResultsList(matchupExtractor.loadResults(tournament));

            matchPopulator.execute(tournament);
        } catch (IOException e) {
            log.warn(e.getMessage());
            return null;
        }

        return tournament;
    }
}
