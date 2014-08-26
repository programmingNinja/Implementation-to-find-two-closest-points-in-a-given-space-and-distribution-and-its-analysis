/**
 * Assignment 5 Closest Pair
 * Author: Rohan D. Shah
 * A01943549
 * Language: JAVA
 * IDE: NetBeans IDE 7.4
 * 
 * Description:
 * This contains the method which is used to calculate the minimum distance using two for loops.
 * this is the slowest approach and cannot be used to calculate minimum distance between many points.
 */
package closestpair;



import static closestpair.randomGeneration.*;
import java.awt.geom.Point2D;
import static java.lang.Math.min;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Rohan
 */
public class naiveNSquare 
{

    /**
     * @param args the command line arguments
     */
   
    static Point[] naiveBruteForce(Point[] points)
    {
        if(points.length == 1)
            return points;
        
        Point[] closePoints = new Point[2];
        Point best1 = null, best2 = null;
        double minDist = Double.POSITIVE_INFINITY;
        
        for(int i=0 ; i<points.length ; i++)
        {
            for(int j=i+1 ; j<points.length ; j++)
            {
                if(points[j] !=null)
                {
                double temp = points[j].distance(points[i]);
                if(temp < minDist)
                {
                    minDist = temp;
                    closePoints[0] = points[j];
                    closePoints[1] = points[i];                    
                }
                }
            }
        }
       
        return closePoints;
    }
    /*
    for testing the class
    
    public static void main(String[] args) 
    {
        Point[] allPoints = new Point[noOfPoints];
        Point[] closePoints = new Point[2];
        allPoints = hexagonalDistribution(noOfPoints);
        
       /* for(int i=0 ; i<allPoints.length ; i++)
        {
            System.out.println("point "+(i+1)+" = "+allPoints[i].getLocation()[0]+" , "+allPoints[i].getLocation()[1]);
        }*/
        
        /*closePoints = naiveBruteForce(allPoints);
        System.out.println("The closest points are ");
        for(int i=0 ; i<closePoints.length ; i++)
        {
            System.out.println("points "+(i+1)+" = "+closePoints[i].getLocation()[0]+" , "+closePoints[i].getLocation()[1]);
        }
        System.out.println("with distance "+ closePoints[0].distance(closePoints[1])+" between them");
        
    }
    */
}
