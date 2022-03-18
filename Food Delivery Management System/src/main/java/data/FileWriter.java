package data;

import java.io.IOException;

public class FileWriter {
    private java.io.FileWriter fileWriter;
    private boolean closed = false;

    public FileWriter(String name){
        try {
            fileWriter = new java.io.FileWriter(name);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void write(String text){
        try{
            fileWriter.write(text);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void close(){
        try{
            fileWriter.close();
            closed = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isClosed(){
        return closed;
    }
}
