package no.matchupanalyzer.webanalyzer.scg;

import no.matchupanalyzer.domain.Tournament;
import no.matchupanalyzer.exception.NoUniqueElementException;
import no.matchupanalyzer.util.DocumentExtractor;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by hanseeve on 02.08.2018.
 */
public class UrlFetcher {

    private DocumentExtractor documentExtractor = new DocumentExtractor();

    public String findPlayerDeckUrl(Tournament tournament) throws IOException {
        List<Element> elementList = tournament.getTournamentPageDocument()
                .getElementsByClass("article_column")
                .stream()
                .filter(e -> e.text().contains("DECKLISTS"))
                .filter(e -> e.text().toLowerCase().contains("modern open"))
                .collect(Collectors.toList());
        uniqueElementValidator(elementList);
        //Narrowed down to section containting decklists, top 8 profiles, metagame, etc.

        List<Element> mainSectionElementList = elementList.get(0).getElementsByClass("article_section")
                .stream()
                .filter(e -> e.text().contains("DECKLISTS"))
                .collect(Collectors.toList());
        uniqueElementValidator(mainSectionElementList);

        String urlPath = mainSectionElementList.get(0).getElementsByTag("a").get(0).attr("href");
        return tournament.getOrganizer().getBaseUrl() + urlPath;
    }

    private void uniqueElementValidator(List<Element> elementList) {
        if (elementList.size() > 1) {
            throw new NoUniqueElementException("HtmlElement is not uniquely defined");
        } else if (elementList.isEmpty()) {
            throw new NoUniqueElementException("HtmlElement was not found");
        }
    }

    public List<String> findResultPageUrlList(Tournament tournament) {
        List<String> urlList = new ArrayList<>();

        List<Element> elementList = tournament.getTournamentPageDocument()
                .getElementsByClass("standings_column")
                .stream()
                .filter(e -> e.text().toLowerCase().contains("modern open"))
                .collect(Collectors.toList());
        uniqueElementValidator(elementList);
        //Narrowed down to section containing winner and rounds

        List<Element> roundRows = elementList.get(0)
                .getElementsByTag("tr");

        roundRows.stream()
                .filter(tr -> !tr.toString().toLowerCase().contains("pairings"))
                .forEach(tr -> urlList.add(tr.child(1).child(0).attr("href")));

        return urlList;
    }
}
