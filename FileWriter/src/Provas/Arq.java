package Provas;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;


public class Arq {

    public static String read(String name) throws IOException {
        FileReader file = null;
        File arq = new File(name);
        try {
            file = new FileReader(name);
        } catch (FileNotFoundException ex) {
            arq.createNewFile();
        }
        
        BufferedReader reader = new BufferedReader(file);
        String file_content = "";
        String line;
        
        try {
            line = reader.readLine();
            while(line != null) {
                file_content += line + "\n";
                line = reader.readLine();
            }
            file.close();
        }
        catch(IOException e) {
            return "Error";
        }
                
        return file_content;
    }
    
    public static boolean write(String txt, String name) throws IOException {
        File arq = null;
        FileWriter file = null;
        try {
            arq = new File(name);
            file = new FileWriter(name);
        } catch (IOException ex) {
            arq.createNewFile();
        }
        
        PrintWriter writer = new PrintWriter(file);
        writer.printf(txt);
        
        try {
            file.close();
        } catch (IOException ex) {
            return false;
        }
        
        return true;
    }
}
