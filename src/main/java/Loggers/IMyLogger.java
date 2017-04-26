package Loggers;

import java.nio.file.Path;
import java.util.ArrayList;


public interface IMyLogger {
    void writeToFile(String message);

    ArrayList<String> getContentOfFile();

    void clearLog();

    String getFilename();

    Path getFullPath();
}
