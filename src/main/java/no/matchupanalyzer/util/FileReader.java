package no.matchupanalyzer.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by hanseeve on 01.08.2018.
 */
public class FileReader {
    private ClassLoader classLoader = getClass().getClassLoader();

    public List<String> readLinesFromFile(String filename) throws FileNotFoundException {
        File file = new File(classLoader.getResource(filename).getFile());
        Scanner scanner = new Scanner(file);

        List<String> lineList = new ArrayList<>();

        while (scanner.hasNextLine()) {
            lineList.add(scanner.nextLine());
        }

        return lineList;
    }
}
