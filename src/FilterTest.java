import FileIO.PDFHelper;
import Filters.DisplayInfoFilter;
import Interfaces.PixelFilter;
import core.DImage;
import core.DisplayWindow;
import processing.core.PImage; 

public class FilterTest {
    //hellothere12
    public static String currentFolder = System.getProperty("user.dir") + "/";

    public static void main(String[] args) {
        SaveAndDisplayExample();

        RunTheFilter();
    }

    private static void RunTheFilter() {
        System.out.println("Loading pdf....");
        PImage in = PDFHelper.getPageImage("assets/scantron_sample.pdf",0);
        DImage img = new DImage(in);       // you can make a DImage from a PImage

        System.out.println("Running filter on page 1....");
        DisplayInfoFilter filter = new DisplayInfoFilter();
        filter.processImage(img);  // if you want, you can make a different method
                                   // that does the image processing an returns a DTO with
                                   // the information you want
    }

    private static void SaveAndDisplayExample() {
        PImage img = PDFHelper.getPageImage("assets/scantron_sample.pdf",0);
        img.save(currentFolder + "assets/page2.png");

        DisplayWindow.showFor("assets/page2.png");
    }
}