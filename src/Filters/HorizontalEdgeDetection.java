package Filters;

import Interfaces.PixelFilter;
import core.DImage;

import java.sql.SQLOutput;

public class HorizontalEdgeDetection implements PixelFilter {
    public HorizontalEdgeDetection() {
        System.out.println("Edge Detection filter running...");
    }

    @Override
    public DImage processImage(DImage img) {
        short[][] pixels = img.getBWPixelGrid();
        short[][] outputPixels = img.getBWPixelGrid();  // <-- overwrite these values

        int n = 3;

        for (int r = 0; r < outputPixels.length-(n-1); r++) {
            for (int c = 0; c < outputPixels[0].length-(n-1); c++) {
                double [][] edgeDetectionKernel = makeEdgeDetection(n);
                int output = applyKernel(n, outputPixels, r, c, edgeDetectionKernel);
                if(kernelWeight(edgeDetectionKernel) != 0) {
                    output = output / kernelWeight(edgeDetectionKernel);
                }
                if (output < 0) { output = 0;}
                if (output > 255) { output = 255;}
                int center = n/2;
                outputPixels[r+center][c+center] = (short) output;
            }
        }

        img.setPixels(outputPixels);
        return img;

    }

    private double[][] makeEdgeDetection(int size) {
        double[][] edgeDetectionKernel = new double[size][size];
        for (int r = 0; r < edgeDetectionKernel.length; r++) {
            for (int c = 0; c < edgeDetectionKernel[r].length; c++) {
                edgeDetectionKernel[r][c] = (-1.0);
                if (r == 1) {
                    edgeDetectionKernel[r][c] = 2.0;
                }
            }
        }
        return edgeDetectionKernel;
    }

    private int applyKernel(int n, short[][] pixels, int r, int c, double [][] kernel) {
        int output = 0;
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                double kernelVal = kernel[row][col];
                int pixelVal = pixels[row+r][col+c];
                output += kernelVal * pixelVal;
            }
        }
        return output;
    }

    private int kernelWeight (double [][] kernel) {
        int sum = 0;
        for (int i = 0; i < kernel.length; i++) {
            for (int j = 0; j < kernel[0].length; j++) {
                sum+=kernel[i][j];
            }
        }
        return sum;
    }

}
