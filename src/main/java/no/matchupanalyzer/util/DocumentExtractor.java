package no.matchupanalyzer.util;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

/**
 * Created by hanseeve on 01.08.2018.
 */
@Slf4j
public class DocumentExtractor {
    public Document getHtmlFromUrl(String url) throws IOException {
        log.info(String.format("Reading content from url: %s", url));
        try {
            Document document = Jsoup.connect(url).get();
            return document;
        } catch (IOException e) {
            log.warn(String.format("Failed to read content from url: %s", url));
            throw new IOException();
        }
    }
}
