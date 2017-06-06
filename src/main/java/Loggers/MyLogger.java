package Loggers;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public abstract class MyLogger implements IMyLogger {
    private String filename = null;
    private Path fullPath;
    private String filepath = "Logs";
    private File file;
    ReentrantReadWriteLock fileLock = new ReentrantReadWriteLock(true);
    final Lock fileRead = fileLock.readLock();
    final Lock fileWrite = fileLock.writeLock();

    public MyLogger(String filename, String filepath) {
        synchronized (this) {
            this.filename = filename;
            this.filepath = filepath;
            File dir = new File(this.filepath);
            dir.mkdirs();
            file = new File(dir, filename);
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            String s = this.filepath + "\\" + this.filename;
            Path inputPath = Paths.get(s);
            fullPath = inputPath.toAbsolutePath();
        }
    }

    public File getFile() {
        return file;
    }

    @Override
    public void writeToFile(String message) {
        fileWrite.lock();
        List<String> lines = Arrays.asList(message);
        try (FileWriter fw = new FileWriter(fullPath.toString(), true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            for (String s : lines) out.println(s);
        } catch (IOException e) {
            fileWrite.unlock();
            System.out.println("cannot write to a new file " + filename);
        }
        fileWrite.unlock();
    }


    @Override
    public ArrayList<String> getContentOfFile() {
        fileRead.lock();
        List<String> lines = new ArrayList<>();
        try {
            lines = Files.readAllLines(fullPath, Charset.defaultCharset());
            fileRead.unlock();
            return new ArrayList<>(lines);
        } catch (IOException e) {
            e.printStackTrace();
        }
        fileRead.unlock();
        return new ArrayList<>(lines);
    }

    @Override
    public void clearLog() {
        fileWrite.lock();
        try (PrintWriter pw = new PrintWriter(fullPath.toString())) {
            pw.close();
        } catch (FileNotFoundException e) {
            fileWrite.unlock();
            e.printStackTrace();
        }
        fileWrite.unlock();
    }

    @Override
    public String getFilename() {
        return filename;
    }

    @Override
    public Path getFullPath() {
        return fullPath;
    }

    public void deleteFile() {
        fileWrite.lock();
        try {
            Files.delete(file.toPath());
        } catch (IOException e) {
            fileWrite.unlock();
            e.printStackTrace();
        }
        fileWrite.unlock();
    }
}
