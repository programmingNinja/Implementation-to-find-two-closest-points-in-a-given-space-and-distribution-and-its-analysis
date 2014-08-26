/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Assignment 5 Closest Pair
 * Author: Rohan D. Shah
 * A01943549
 * Language: JAVA
 * IDE: NetBeans IDE 7.4
 * 
 * Description:
 * This class computes various points using various distribution that are placed
 * in the 100x100 grid 
 * There are three approaches that are used to distribute the points
 * 1) simple random distribution
 * 2) Hexagonal distribution in which the points are laid in the form of hexagonal lattice
 * 3) Mixed distribution in which 95% of the points are laid in the form of hexagonal lattice and the rest in 
 * random fashion
 * 
 */
package closestpair;

import java.text.DecimalFormat;
import java.util.*;

public class randomGeneration 
{
    static Point[] simpleRandomGeneration(int noOfPoints)
    {
        Random randomGenerator = new Random();
        double forDistribution;
        double xCoordinate, yCoordinate;
        Point[] allPoints = new Point[noOfPoints];
        DecimalFormat oneDigit = new DecimalFormat("#.#####");
       
        // Fill in size and values of items with random numbers between -1 and 1 inclusive
        for(int i=0 ; i<noOfPoints ; i++)
        {   
            forDistribution = randomGenerator.nextDouble()*100.0;
            xCoordinate = Double.valueOf(oneDigit.format(forDistribution));
            
            forDistribution = randomGenerator.nextDouble()*100.0;
            yCoordinate = Double.valueOf(oneDigit.format(forDistribution));
                    
    
            allPoints[i] = new Point();
            allPoints[i].setLocation(xCoordinate, yCoordinate);
                   
        }
        return allPoints;
    }
    
    static Point[] hexagonalDistribution(int noOfPoints)
    {
        Point[] allPoints = new Point[noOfPoints];
        
        long m = Math.round(Math.sqrt(noOfPoints)+0.5);
        
        float horizontalSpacing = (float)99/m;
        double x = 0.00001;
        double y = 0.00001;
        double offset = horizontalSpacing/2;
        boolean applyOffset = false;
        int a = 0;
        for(int i=0 ; i<m ; i++ )
        {
            if(applyOffset)
                x+=offset;
            for(int j = 0 ; j<m && a<noOfPoints;  j++)
            {
                allPoints[a] = new Point(x,y);
                x+=horizontalSpacing; 
                a++;
            }
            x = 0.00001;
            y+=Math.sqrt(3/2)*horizontalSpacing;
            applyOffset = (applyOffset) ? false:true;
        }
        
        return allPoints;
        
    }
    
    static Point[] mixedDistribution(int noOfPoints)
    {
        Point[] allPoints = new Point[noOfPoints];
        double m = Math.round(Math.sqrt(noOfPoints)+0.5);
        long pointsForHexDistribution = Math.round(0.95*noOfPoints);
        long pointsForRandDistribution = noOfPoints - pointsForHexDistribution;
        Random randomGenerator = new Random();
        double forDistribution;
        
        float horizontalSpacing = (float) ((float)99/m);
        double x = 0.00001;
        double y = 0.00001;
        double offset = horizontalSpacing/2;
        boolean applyOffset = false;
        int a = 0;
        for(int i=0 ; i<m ; i++ )
        {
            if(applyOffset)
                x+=offset;
            for(int j = 0 ; j<m && a<pointsForHexDistribution;  a++,j++)
            {
                allPoints[a] = new Point(x,y);
                x+=horizontalSpacing;                
            }
            x = 0.00001;
            y+=Math.sqrt(3/2)*horizontalSpacing;
            applyOffset = (applyOffset) ? false:true;
        }
        DecimalFormat oneDigit = new DecimalFormat("#.#####");
        for(long j = pointsForHexDistribution ; j<noOfPoints ; j++)
        {
            forDistribution = randomGenerator.nextDouble()*100.0;
            x = Double.valueOf(oneDigit.format(forDistribution));
            
            forDistribution = randomGenerator.nextDouble()*100.0;
            y = Double.valueOf(oneDigit.format(forDistribution));
            allPoints[(int)j] = new Point(x, y);
        }
        
        return allPoints;
    }
    
    /*
    for testing the class
    public static void main(String[] args)
    {
        Point[] points = new Point[256];
        points = mixedDistribution(256);
        for(int i=0 ; i<points.length ; i++)
        {
            System.out.println(points[i].getX());
        }
        System.out.println("yfbdzbd");
        for(int i=0 ; i<points.length ; i++)
        {
            System.out.println(points[i].getY());
        }
    }*/
}
