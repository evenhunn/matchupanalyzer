package no.matchupanalyzer.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import no.matchupanalyzer.exception.ParsingException;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Created by hanseeve on 01.08.2018.
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
public class Score {
    private Integer player1Score;
    private Integer player2Score;

    private static final Pattern scorePattern = Pattern.compile("[0-9]+-[0-9]+");

    public static Score parseToScore(String score) {
        Matcher matcher = scorePattern.matcher(score);
        if (matcher.find()) {
            List<String> scoreList = Arrays.stream(matcher.group().split("-"))
                    .map(String::trim)
                    .collect(Collectors.toList());
            return new Score(Integer.parseInt(scoreList.get(0)), Integer.parseInt(scoreList.get(1)));
        }
        throw new ParsingException(String.format("Failed to parse score. The score is: %s", score));
    }
}
