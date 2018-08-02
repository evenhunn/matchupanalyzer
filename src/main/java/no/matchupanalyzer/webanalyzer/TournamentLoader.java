package no.matchupanalyzer.webanalyzer;

import lombok.extern.slf4j.Slf4j;
import no.matchupanalyzer.domain.Organizer;
import no.matchupanalyzer.domain.Tournament;
import no.matchupanalyzer.util.FileReader;
import no.matchupanalyzer.webanalyzer.scg.ScgTournamentLoader;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by hanseeve on 01.08.2018.
 */
@Slf4j
public class TournamentLoader {
    private FileReader fileReader = new FileReader();
    private ScgTournamentLoader scgTournamentLoader = new ScgTournamentLoader();

    public List<Tournament> run() throws FileNotFoundException {
        return loadTournaments(loadOrganizers());
    }

    List<Organizer> loadOrganizers() throws FileNotFoundException {
        List<String> organizerString = fileReader.readLinesFromFile("tournament-files.txt");
        return organizerString.stream().map(s -> {
            List<String> organizerInfo = Arrays.asList(s.split(";"));
            return Organizer.builder()
                    .name(organizerInfo.get(0))
                    .filename(organizerInfo.get(1))
                    .baseUrl(organizerInfo.get(2))
                    .build();
        }).collect(Collectors.toList());
    }

    private List<Tournament> loadTournaments(List<Organizer> organizerList) {
        List<Tournament> tournamentList = new ArrayList<>();
        for (Organizer organizer : organizerList) {
            try {
                List<String> scgUrlList = fileReader.readLinesFromFile(organizer.getFilename());

                log.info("Reading tournament results for:");
                for (String scgUrl : scgUrlList) {
                    log.info(scgUrl);

                    Tournament tournament = new Tournament();
                    tournament.setOrganizer(organizer);
                    tournament.setBaseUrl(organizer.getBaseUrl());
                    tournament.setTournamentPageUrl(scgUrl);
                    scgTournamentLoader.execute(tournament);

                    tournamentList.add(tournament);

                }
            } catch (FileNotFoundException e) {
                log.error(e.getMessage());
                log.error("Failed to read file");
            }
        }
        return tournamentList;
    }
}
