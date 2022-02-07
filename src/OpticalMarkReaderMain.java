import FileIO.PDFHelper;
import Filters.DisplayInfoFilter;
import core.DImage;
import processing.core.PImage;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;

// Author: David Dobervich (this is my edit)
// ANOTHER EDIT.
public class OpticalMarkReaderMain {

    public static void main(String[] args) {
        String pathToPdf = fileChooser();
        System.out.println("Loading pdf at " + pathToPdf);
        for (int pg = 0; pg <7 ; pg++) {
            PImage in = PDFHelper.getPageImage(pathToPdf,pg);
            DImage img = new DImage(in);       // you can make a DImage from a PImage
            System.out.println("Running filter on page " +pg);
            findAnswersFilter filter = new findAnswersFilter();
            writeDataToFile("answers.txt", "Answers for page "+pg);
            filter.processImage(img);
            writeDataToFile("answers.txt", filter.getData());

        }




        /*
        Your code here to...
        (1).  Load the pdf
        (2).  Loop over its pages
        (3).  Create a DImage from each page and process its pixels
        (4).  Output 2 csv files
         */

    }
    public static void writeDataToFile(String filePath, String data) {
        try (FileWriter f = new FileWriter(filePath, true);
             BufferedWriter b = new BufferedWriter(f);
             PrintWriter writer = new PrintWriter(b);) {


            writer.println(data);


        } catch (IOException error) {
            System.err.println("There was a problem writing to the file: " + filePath);
            error.printStackTrace();
        }
    }



    private static String fileChooser() {
        String userDirLocation = System.getProperty("user.dir");
        File userDir = new File(userDirLocation);
        JFileChooser fc = new JFileChooser(userDir);
        int returnVal = fc.showOpenDialog(null);
        File file = fc.getSelectedFile();
        return file.getAbsolutePath();
    }
}
