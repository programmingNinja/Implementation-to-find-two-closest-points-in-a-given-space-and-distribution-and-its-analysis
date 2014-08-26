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
 * This class calculates the minimum distance between the two points placed
 * in the 100x100 grid and returns those points which gives the minimum distance
 * There are three approaches that are used to calculate the minimum distance and then are:
 * 1) Naive approach: the complexity is n^2
 * 2) Slow Divide and conquer approach in which the points are first sorted by X coordinate and then 
 * the minimum distance is calculated by divide and approach mechanism.
 * 3) Fast divide and conquer approach in which the points are first sorted by X coordinate and then 
 * given to the function which divides the points and lastly these points are sorted by Y again and merged.
 * This is faster as it computes distances between few selected points and other unnecessary points are discarded
 * 
 * The timing analysis is done by using a stopwatch (from java class, stopwatch.java). The power of the number of 
 * points is increased after every iteration and the timing data is stored in array. The average of 10 time for each
 * number of points is taken and plotted on the graph.
 */
package closestpair;
import static closestpair.naiveNSquare.naiveBruteForce;
import static closestpair.randomGeneration.simpleRandomGeneration;
import closestpair.StopWatch.*;
import static closestpair.randomGeneration.hexagonalDistribution;
import static closestpair.randomGeneration.mixedDistribution;
import java.util.*;
/**
 *
 * @author Rohan
 */
public class ClosestPair
{

    /**
     * @param args the command line arguments
     */
    static int noOfPoints = 64;
    
    // to control the number of points
    static int power = 6;
    
    // stores the points which gives minimum distance by slow DnC
    static Point[] bestPointsForSlow = new Point[2];
    
    // stores the minimum distance by slow DnC
    static double bestDistanceForSlow = Double.POSITIVE_INFINITY;
    
    // stores the points which gives minimum distance by fast DnC
    static Point[] bestPointsForFast = new Point[2];
    
    // stores the minimum distance by slow DnC
    static double bestDistanceForFast = Double.POSITIVE_INFINITY;
    
    // computes the points which gives minimum distance
    // takes an array of points and its start and end
    static double closePointsDCSlow(Point[] points, int low, int high)
    {
        // cannot be further divided
       if (low+1 == high) 
       {
           return Double.POSITIVE_INFINITY;
       }
       
        // point of division
        int mid = low + (high - low) / 2;
              
        Point middle = points[mid];
        int halfLength = points.length/2;
        Point[] leftArray = new Point[halfLength];
        Point[] rightArray = new Point[halfLength]; 
        
        // dividing the points in two arrays,
        // first half goes in leftArray and second half in the rightArray
        for(int i=0 ; i<halfLength ; i++)
        {
            leftArray[i] = points[i];
            rightArray[i] = points[i+halfLength];
        }
        
        // dividing the problem
        double minLeft = closePointsDCSlow(leftArray, 0, leftArray.length-1);
        double minRight = closePointsDCSlow(rightArray, 0, rightArray.length-1);
        
        // taking the minimum distance
        double min = Math.min(minLeft, minRight);
        Vector<Point> t = new Vector(0,1);
        
        // gathering points which lies within the minimum distance of left and right array
        int M=0;
        for (int i = low; i <= high; i++) 
        {
            if(points[i] != null)
            {
                if (Math.abs(points[i].getX()- middle.getX()) < min)
                {
                    t.add(points[i]);
                    M++;
                }
            }
        }
       Point[] temp = new Point[t.capacity()];
       for(int j=0 ; j<temp.length ; j++)
       {
           temp[j] = t.elementAt(j);
       }
        // computes distance between the crossover region
         for (int i = 0; i < M; i++) 
         {
            // a geometric packing argument shows that this loop iterates at most 7 times
            for (int j = i+1; (j < M) && (temp[j].getX()- temp[i].getX() < min); j++) 
            {
                double distance = temp[i].distance(temp[j]);
                if (distance < min) 
                {
                    min = distance;
                    if (min < bestDistanceForSlow) 
                    {
                        bestDistanceForSlow = min;
                        bestPointsForSlow[0] = temp[i];
                        bestPointsForSlow[1] = temp[j];
                    }
                }
            }
        }
        return Math.min(min,bestDistanceForSlow);
    }
    // same as closePointsDCSlow except the points are sorted in Y before
    // finding the distance between the points in the crossover region
    static double closePointsDCFast(Point[] points, int low, int high)
    {
       if (low+1 == high) 
       {
           return Double.POSITIVE_INFINITY;
       }
        int mid = low + (high - low) / 2;
                //(low + high )/ 2;
        Point middle = points[mid];
        int halfLength = points.length/2;
        Point[] leftArray = new Point[halfLength];
        Point[] rightArray = new Point[halfLength];        
        for(int i=0 ; i<halfLength ; i++)
        {
            leftArray[i] = points[i];
            rightArray[i] = points[i+halfLength];
        }

        double minLeft = closePointsDCFast(leftArray, 0, leftArray.length-1);
        double minRight = closePointsDCFast(rightArray, 0, rightArray.length-1);
        double min = Math.min(minLeft, minRight);
        Vector<Point> t = new Vector(0,1);

        int M=0;

        for (int i = low; i <= high; i++) 
        {
            if(points[i] != null)
            {
                if (Math.abs(points[i].getY()- middle.getY()) < min)
                {
                    t.add(points[i]);
                    M++;
                }
            }
        }

       Point[] temp = new Point[t.capacity()];
       for(int j=0 ; j<temp.length ; j++)
       {
           temp[j] = t.elementAt(j);
       }
       // sorting on Y
       Arrays.sort(temp, new Comparator<Point>(){
               public int compare(Point p, Point q) {
              if (p.getY() < q.getY()) return -1;
                  if (p.getY() > q.getY()) return +1;
              return 0;
            }
        });
        
         for (int i = 0; i < M; i++) 
         {
            // a geometric packing argument shows that this loop iterates at most 6 times
            for (int j = i+1; (j < M) && (temp[j].getY()- temp[i].getY() < min); j++) 
            {
                double distance = temp[i].distance(temp[j]);
                if (distance < min) 
                {
                    min = distance;
                    if (min < bestDistanceForFast) 
                    {
                        bestDistanceForFast = min;
                        bestPointsForFast[0] = temp[i];
                        bestPointsForFast[1] = temp[j];
                    }
                }
            }
         }
        return Math.min(min,bestDistanceForFast);
    }

    public static void main(String[] args) 
    {   
        // stop watches to time the execution of various algorithms
        StopWatch forNaive = new StopWatch();
        StopWatch forSlowDC = new StopWatch();
        StopWatch forFastDC = new StopWatch();
        
        // stores the time of each iteration
        double[] timeForNaive = new double[10];
        double[] timeForSlowDC = new double[10];
        double[] timeForFastDC = new double[10];
        
        // stores the average of the time
        // the index is the value of t
        // and the time would be for the power = (t + the starting power)
        double[] AvgTimeForNaive = new double[21];
        double[] AvgTimeForSlowDC = new double[21];
        double[] AvgTimeForFastDC = new double[21];
        
        // for computing the average
        double sumForNaive = 0;
        double sumForSlowDc = 0;
        double sumForFastDC = 0;
        // t corresponds to the power, the actual power will be t+power
        for(int t=0 ; t<14 ; t++)
        {
            
            sumForNaive = 0;
            sumForSlowDc = 0;
            sumForFastDC = 0;
           
            for(int n=0 ; n<10 ; n++)
            {                    
                Point[] allPointsForSlow = new Point[noOfPoints];
                Point[] allPointsForFast = new Point[noOfPoints];
                Point[] closePoints = new Point[2];
                double minDist = 0;
                // assigning the same points to all the algorithm so the accuracy of the answer can be checked
                // various types of distribution
                allPointsForSlow = mixedDistribution(noOfPoints);
                //allPointsForSlow = simpleRandomGeneration(noOfPoints);
                //allPointsForSlow = hexagonalDistribution(noOfPoints);
                for(int a=0 ; a<allPointsForFast.length ; a++)
                {
                    allPointsForFast[a] = new Point();
                    allPointsForFast[a].setLocation(allPointsForSlow[a].getX(), allPointsForSlow[a].getY());
                }
                forNaive.start();
                //System.out.println("For naive algorithm");
                closePoints = naiveBruteForce(allPointsForFast);
                
                /*for(int i=0 ; i<closePoints.length ; i++)
                {
                    System.out.println("point "+(i+1)+" = "+closePoints[i].getLocation()[0]+" , "+closePoints[i].getLocation()[1]);
                }
                System.out.println("Minimum dist is = "+closePoints[0].distance(closePoints[1]));*/
                forNaive.stop();
                timeForNaive[n] = (double)forNaive.getElapsedTime()/1000;
                //System.out.println("sum="+sumForNaive);
                sumForNaive+= (double)forNaive.getElapsedTime()/1000;
                forSlowDC.start();
                // sorting on X to feed the slow DC algorithm
                Arrays.sort(allPointsForSlow,new Comparator<Point>(){
                       public int compare(Point p, Point q) {
                      if (p.getX() < q.getX()) return -1;
                          if (p.getX() > q.getX()) return +1;
                      return 0;
                    }
                 });

               minDist = closePointsDCSlow(allPointsForSlow,0,allPointsForSlow.length-1);        
               /*System.out.println("After slow dc");
                System.out.println("=======================================");
                System.out.println("Minimum dist is = "+minDist);
                System.out.println(bestPointsForSlow[0].getLocation()[0]+" , "+bestPointsForSlow[0].getLocation()[1]+
                        " and "+bestPointsForSlow[1].getLocation()[0]+" , "+bestPointsForSlow[1].getLocation()[1]);  */  
                forSlowDC.stop();
                timeForSlowDC[n] = (double)forSlowDC.getElapsedTime()/1000;
                forFastDC.start();
                // sorting on X to feed the fast DC algorithm
                 Arrays.sort(allPointsForFast,new Comparator<Point>(){
                       public int compare(Point p, Point q) {
                      if (p.getX() < q.getX()) return -1;
                          if (p.getX() > q.getX()) return +1;
                      return 0;
                    }
                });
                
                //System.out.println("=======================================");
                minDist = closePointsDCFast(allPointsForFast,0,allPointsForFast.length-1);        
                /*System.out.println("After Fast dc");
                System.out.println("=======================================");
                System.out.println("Minimum dist is = "+minDist);
                System.out.println(bestPointsForFast[0].getLocation()[0]+" , "+bestPointsForFast[0].getLocation()[1]+
                        " and "+bestPointsForFast[1].getLocation()[0]+" , "+bestPointsForFast[1].getLocation()[1]); */
                forFastDC.stop();
                timeForFastDC[n] = (double)forFastDC.getElapsedTime()/1000;
                
                sumForFastDC+=timeForFastDC[n];
                sumForNaive+=timeForNaive[n];
                sumForSlowDc+=timeForSlowDC[n];
            }
            AvgTimeForFastDC[t] = (double)sumForFastDC/2;
            AvgTimeForNaive[t] = (double)sumForNaive/2;
            AvgTimeForSlowDC[t] = (double)sumForSlowDc/2;
           
            power = power + 1 ;
            noOfPoints = (int)Math.pow(2, power);          
            
        }
        // outputting average execution time for various number of points for all the algorithm.  
        System.out.println("For naive");
        for(int d=0 ; d<AvgTimeForNaive.length ; d++)
        {
            System.out.println(AvgTimeForNaive[d]);
        }
        System.out.println("For slow dc");
        for(int d=0 ; d<AvgTimeForSlowDC.length ; d++)
        {
            System.out.println(AvgTimeForSlowDC[d]);
        }
        System.out.println("For fast");
        for(int d=0 ; d<AvgTimeForFastDC.length ; d++)
        {
            System.out.println(AvgTimeForFastDC[d]);
        }
    }    
    
}

