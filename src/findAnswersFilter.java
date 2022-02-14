import Interfaces.PixelFilter;
import core.DImage;

import java.awt.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class findAnswersFilter implements PixelFilter {
    private  static int firstQuestionX=128;
    private static int firstQuestionY=456;
    private static int xDist=282;
    private static int yDist=37;
    private static int answerDist=38;
    private static int numQuestionsPerPage=25;
    private String data;
    private String id;
    ArrayList<String>answerDoc=new ArrayList<>();

    public findAnswersFilter() {
        System.out.println("Filter running...");
        data="";
        id="";
    }
    public String getData(){
        return data;
    }
    public PageResult getResult(){
        PageResult p=new PageResult();
        p.setStudentId(id);
        p.addAnswers(answerDoc);
        return p;
    }
    public ArrayList<String> getAnswerDoc() {
        return answerDoc;
    }

    @Override
    public DImage processImage(DImage img) {
        short[][] grid = img.getBWPixelGrid();
        System.out.println("Image is " + grid.length + " by "+ grid[0].length);
        //id=findAnswer()

        ArrayList<Point> questions=findQuestions(img);
        for (int i = 1; i < questions.size()+1; i++) {
            String answer=findAnswer(questions.get(i-1), grid, answerDist);
           data+="the answer to question "+i+" is "+answer+"\n";
           answerDoc.add(answer);
        }
        return img;
    }

    public static ArrayList<Point> findQuestions( DImage img){
        ArrayList<Point>points=new ArrayList<>();
        Point firstQuestion=new Point(firstQuestionX,firstQuestionY);
        for (int x = firstQuestionX; x < img.getWidth(); x+=xDist) {
            for (int y = firstQuestionY; y <firstQuestionY+yDist*numQuestionsPerPage ; y+=yDist) {
                Point question=new Point(x,y);
                points.add(question);
            }
        }
        return points;
    }
    private static String findAnswer(Point question, short[][]grid, int answerDist){

        int a1count=getBlackPixels(question.getX(), question.getY(), grid );
        int a2count=getBlackPixels(question.getX()+answerDist,question.getY(),grid);
        int a3count=getBlackPixels(question.getX()+(2*answerDist),question.getY(),grid);
        int a4count=getBlackPixels(question.getX()+(3*answerDist),question.getY(),grid);
        int a5count=getBlackPixels(question.getX()+(4*answerDist),question.getY(),grid);
        System.out.println(a1count+" "+a2count+" "+a3count+" "+a4count+" "+a5count);
        if(a1count>a2count&&a1count>a3count&&a1count>a4count&&a1count>a5count){
            return "answer 1";
        }else if(a2count>a1count&&a2count>a3count&&a2count>a4count&&a2count>a5count){
            return "answer 2";
        }else if(a3count>a1count&&a3count>a2count&&a3count>a4count&&a3count>a5count){
            return "answer 3";
        }else if(a4count>a1count&&a4count>a3count&&a4count>a3count&&a4count>a5count){
            return "answer 4";
        }else {
            return  "answer 5";
        }
    }

    private static int getBlackPixels( double x, double y, short[][]grid ){
        int count=0;
        for (int j = (int) y; j <y+yDist ; j++) {
            for (int k = (int) x; k <x+answerDist ; k++) {
                if(grid[j][k]<5){
                    count+=4;
                }else if(grid[j][k]<10){
                    count+=3;
                }else if(grid[j][k]<20){
                    count+=2;
                }else if (grid[j][k]<30){
                    count++;
                }
            }
        }
        return count;
    }


}
