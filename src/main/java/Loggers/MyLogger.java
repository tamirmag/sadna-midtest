package Loggers;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class MyLogger {
    private String filename = null;
    private Path fullPath;



    private String filepath = "Logs";

    public MyLogger(String filename,String filepath) {
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

    public synchronized void writeToFile(String message) {

        List<String> lines = Arrays.asList(message);
        try (FileWriter fw = new FileWriter(fullPath.toString(), true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)){

            for(String s : lines)
                out.println(s);
        } catch (IOException e) {
            System.out.println("cannot write to a new file " + filename);
        }
    }


    public ArrayList<String> getContentOfFile() {

        List<String> lines = new ArrayList<>();
        try {
            lines = Files.readAllLines(fullPath, Charset.defaultCharset());
            return new ArrayList<>(lines);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<>(lines);
    }

    public void clearLog() {
        try (PrintWriter pw = new PrintWriter(fullPath.toString())) {
            pw.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    public String getFilename() {
        return filename;
    }

    public Path getFullPath() {
        return fullPath;
    }
}

      /*boolean fileExists = new File(getFilename()).exists();
        if(fileExists)
        {
            try {
                out =new PrintWriter(new BufferedWriter(new FileWriter(filename, true)));
            } catch (IOException e) {
                out.close();
                e.printStackTrace();
            }
            finally {
                out.close();
            }
        }*/
      /*  boolean fileExists = new File(getFilename()).exists();
        if(fileExists)
        {
            try(PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(filename, true)))){
                out.println(message);
            } catch (IOException e) {
                System.out.println("cannot write to existing file " + filename);
            }
        }
        else
        {*/