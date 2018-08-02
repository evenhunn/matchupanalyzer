package no.matchupanalyzer.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by hanseeve on 01.08.2018.
 */
@Getter
@Setter
@Builder
public class Organizer {
    private String name;
    private String filename;
    private String baseUrl;
}
