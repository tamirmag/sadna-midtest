package Loggers;

import DB.LoggerDB;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

import java.util.ArrayList;

@Entity
public abstract class MyLogger implements IMyLogger {

    @Id
    private String filename = null;

    private ArrayList<String> content;

    public MyLogger(String filename) {
        this.filename = filename;
        content = new ArrayList<>();
        LoggerDB.getInstance().addNewLogger(this);
    }

    @Override
    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    @Override
    public ArrayList<String> getContentOfFile() {
        return content;
    }

    public void setContent(ArrayList<String> content) {
        this.content = content;
    }

    @Override
    public void writeToFile(String message) {
        content.add(message);
        LoggerDB.getInstance().saveLogger(this);
    }

    @Override
    public void clearLog() {
        content.clear();
        LoggerDB.getInstance().saveLogger(this);
    }

    @Override
    public void deleteFile() {
        filename = "";
        content.clear();
        LoggerDB.getInstance().deleteLogger(filename);
    }



    /*
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
    }*/
}
