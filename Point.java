/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package closestpair;

/**
 *
 * @author Rohan
 */
public class Point 
{
    double x, y;
    Point()
    {
        x = 0;
        y = 0; 
    }
    Point(double a, double b)
    {
        this.setLocation(a, b);
    }
    void setLocation(double a, double b)
    {
        x = a;
        y = b;
    }
    double[] getLocation()
    {
        double[] location = {x,y};
        return location;
    }
    double getX()
    {
        return x;
    }
    double getY()
    {
        return y;
    }
    double distance(Point p)
    {
        double xs = this.getX() - p.getX();
        double ys = this.getY() - p.getY();
        
        return Math.sqrt(Math.pow(xs, 2) + Math.pow(ys, 2));
    }
}
