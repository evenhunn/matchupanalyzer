package no.matchupanalyzer.webanalyzer.scg;

import lombok.extern.slf4j.Slf4j;
import no.matchupanalyzer.domain.Organizer;
import no.matchupanalyzer.domain.Tournament;
import no.matchupanalyzer.util.DocumentExtractor;
import no.matchupanalyzer.util.FileReader;
import no.matchupanalyzer.webanalyzer.scg.dataextractors.MatchExtractor;
import no.matchupanalyzer.webanalyzer.scg.dataextractors.PlayerDeckLoader;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by hanseeve on 01.08.2018.
 */
@Slf4j
public class ScgTournamentLoader {

    private FileReader fileReader = new FileReader();
    private DocumentExtractor documentExtractor = new DocumentExtractor();
    private PlayerDeckLoader playerDeckLoader = new PlayerDeckLoader();
    private UrlFetcher urlFetcher = new UrlFetcher();
    private MatchExtractor matchExtractor = new MatchExtractor();
    private MatchPopulator matchPopulator = new MatchPopulator();

    private String baseurl = "http://www.starcitygames.com";

    public List<Tournament> execute(Organizer organizer) {
        List<Tournament> tournamentList = new ArrayList<>();
        try {
            List<String> scgInfoList = fileReader.readLinesFromFile(organizer.getFilename());
            log.info("Reading tournament results for:");
            for (String scgInfo : scgInfoList) {
                List<String> singleScgInfoList = Arrays.asList(scgInfo.split(";"));
                String scgUrl = singleScgInfoList.get(0);
                List<String> tournamentTypeList = singleScgInfoList.subList(1, singleScgInfoList.size());
                for (String tournamentType : tournamentTypeList) {
                    log.info(scgUrl);
                    tournamentList.add(tournamentBuilder(organizer, scgUrl, tournamentType));
                }
            }
        } catch (FileNotFoundException e) {
            log.error(e.getMessage());
            log.error("Failed to read file");
        }
        return tournamentList;
    }

    private Tournament tournamentBuilder(Organizer organizer, String scgUrl, String tournamentType) {
        try {

            Tournament tournament = new Tournament();
            tournament.setOrganizer(organizer);
            tournament.setBaseUrl(organizer.getBaseUrl());
            tournament.setTournamentPageUrl(scgUrl);
            tournament.setType(tournamentType);
            tournament.setTournamentPageDocument(documentExtractor.getHtmlFromUrl(tournament.getTournamentPageUrl()));
            tournament.setDeckNamePageUrl(urlFetcher.findPlayerDeckUrl(tournament));
            tournament.setPlayerDeckList(playerDeckLoader.execute(tournament.getDeckNamePageUrl()));
            tournament.setResultPageUrlList(urlFetcher.findResultPageUrlList(tournament));
            tournament.setResultsList(matchExtractor.loadResults(tournament));
            tournament.setTournamentReferences();

            matchPopulator.execute(tournament);
            return tournament;

        } catch (IOException e) {
            log.warn(e.getMessage());
            return null;
        }

    }

}
