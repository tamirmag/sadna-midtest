package Loggers;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Semaphore;

public abstract class MyLogger implements IMyLogger {
    private String filename = null;
    private static Semaphore filePermission = new Semaphore(1,true);
    private Path fullPath;
    private String filepath = "Logs";

    public MyLogger(String filename, String filepath) {
        this.filename = filename;
        this.filepath = filepath;
        File dir = new File(this.filepath);
        dir.mkdirs();
        File tmp = new File(dir, filename);
        try {
            tmp.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String s = this.filepath + "\\" + this.filename;
        Path inputPath = Paths.get(s);
        fullPath = inputPath.toAbsolutePath();
    }

    @Override
    public void writeToFile(String message) {
        try {
            filePermission.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        {
            List<String> lines = Arrays.asList(message);
            try (FileWriter fw = new FileWriter(fullPath.toString(), true);
                 BufferedWriter bw = new BufferedWriter(fw);
                 PrintWriter out = new PrintWriter(bw)) {
                for (String s : lines)
                    out.println(s);
            } catch (IOException e) {
                System.out.println("cannot write to a new file " + filename);
            }
        }
        filePermission.release();
    }


    @Override
    public ArrayList<String> getContentOfFile() {
        try {
            filePermission.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        List<String> lines = new ArrayList<>();
        try {
            lines = Files.readAllLines(fullPath, Charset.defaultCharset());
            filePermission.release();
            return new ArrayList<>(lines);
            } catch (IOException e) {
                e.printStackTrace();
            }
         filePermission.release();
        return new ArrayList<>(lines);
    }

    @Override
    public void clearLog() {
        try {
            filePermission.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try (PrintWriter pw = new PrintWriter(fullPath.toString())) {
            pw.close();
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        filePermission.release();
    }

    @Override
    public String getFilename() {
        return filename;
    }

    @Override
    public Path getFullPath() {
        return fullPath;
    }

    public void deleteFile()
    {
        try {
            filePermission.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            Files.delete(fullPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        filePermission.release();
    }
}
