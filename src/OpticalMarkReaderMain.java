import FileIO.PDFHelper;
import Filters.DisplayInfoFilter;
import core.DImage;
import processing.core.PImage;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;

// Author: David Dobervich (this is my edit)
// ANOTHER EDIT
public class OpticalMarkReaderMain {
    private static int numStudentPages = 2;

    public static void main(String[] args) {
        String pathToPdf = fileChooser();
        PageResult[] studentAnswers = new PageResult[numStudentPages];
        System.out.println("Loading pdf at " + pathToPdf);

        PageResult answerKey = setFilter(0, pathToPdf);
        for (int i = 1; i < numStudentPages + 1; i++) {
            studentAnswers[i - 1] = setFilter(i, pathToPdf);
        }
        String data = gradeTest(studentAnswers, answerKey);
        PDFHelper.writeDataToFile("answers.csv", data);
        String analysis = itemAnalysis(studentAnswers, answerKey);
        PDFHelper.writeDataToFile("itemAnalysis.csv", analysis);
    }

    public static String itemAnalysis(PageResult[] studentAnswers, PageResult answerKey) {
        String data = "";
        ArrayList<String> correctAnswers = answerKey.getAnswers();
        ArrayList<String> questions = studentAnswers[0].getAnswers();
        for (int i = 1; i < questions.size() + 1; i++) {
            int count = 0;
            for (PageResult student : studentAnswers) {
                ArrayList<String> answers = student.getAnswers();
                if (answers.get(i - 1).equals(correctAnswers.get(i - 1))) {
                    count++;
                }
            }
            data += "For question " + i + " the score for the students is " + count + "/" + studentAnswers.length + "\n";
        }

        return data;
    }

    public static String gradeTest(PageResult[] studentAnswers, PageResult answerKey) {
        String data = "";
        ArrayList<String> correctAnswers = answerKey.getAnswers();
        int studentNum = 1;
        for (PageResult student : studentAnswers) {
            int correctNum = 0;
            data += "Grading for student " + studentNum + ": \n";
            ArrayList<String> answers = student.getAnswers();
            for (int i = 1; i < answers.size() + 1; i++) {
                if (answers.get(i - 1).equals(correctAnswers.get(i - 1))) {
                    data += "Question " + i + " is correct.\n";
                    correctNum++;
                } else {
                    data += "Question " + i + " is wrong.\n";
                }
            }
            data += "Student " + studentNum + " got " + correctNum + " answers correct out of " + answers.size() + " questions.\n";
            studentNum++;
        }
        return data;
    }

    public static PageResult setFilter(int pageNum, String pathToPdf) {
        PImage in = PDFHelper.getPageImage(pathToPdf, pageNum);
        DImage img = new DImage(in);
        System.out.println("Running filter on page " + pageNum);
        findAnswersFilter filter = new findAnswersFilter();
        filter.processImage(img);
        PageResult result = filter.getResult();
        return result;
    }





        /*
        Your code here to...
        (1).  Load the pdf
        (2).  Loop over its pages
        (3).  Create a DImage from each page and process its pixels
        (4).  Output 2 csv files
         */


    private static String fileChooser() {
        String userDirLocation = System.getProperty("user.dir");
        File userDir = new File(userDirLocation);
        JFileChooser fc = new JFileChooser(userDir);
        int returnVal = fc.showOpenDialog(null);
        File file = fc.getSelectedFile();
        return file.getAbsolutePath();
    }
}
