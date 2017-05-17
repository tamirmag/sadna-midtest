package Loggers;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;


public interface IMyLogger {
    void writeToFile(String message);

    ArrayList<String> getContentOfFile();

    void clearLog();

    String getFilename();

    Path getFullPath();

    void deleteFile();

    File getFile();
}
