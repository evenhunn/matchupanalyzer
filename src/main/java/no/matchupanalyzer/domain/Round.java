package no.matchupanalyzer.domain;

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
public class Round {
    private List<Match> resultList;
}
