import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

/*https://github.com/AlexJoz/Algorithms--Java-/blob/master/Week3-Collinear%20Points/FastCollinearPoints.java*/

public class FastCollinearPoints {

    private ArrayList<LineSegment> segments = new ArrayList<>();

    public FastCollinearPoints(Point[] points) {
        
        if (points == null) {
            throw new IllegalArgumentException("argument is null");
        }

        Point[] internalPoints = Arrays.copyOf(points, points.length);
        int length = 0;
        
        for(Point point : internalPoints){
            if(point == null)
                throw new IllegalArgumentException("point is null");
        }

        //check whether has same point
        Arrays.sort(internalPoints, new Comparator<Point>() {
            @Override
            public int compare(Point p1, Point p2) {
                int res = p1.compareTo(p2);
                if (res == 0) {
                    //StdOut.println("p1 = " + p1 + ", p2 = " + p2);
                    throw new IllegalArgumentException("same point"); 
                }
                return res;
            }
        });


        for (int i = 0; i < points.length - 3; i++) {
            
            Arrays.sort(internalPoints);
            
            Arrays.sort(internalPoints, internalPoints[i].slopeOrder());
            
            //for(Point point : internalPoints){
                //StdOut.println("point = " + point);
            //}
            
            for (int p = 0, first = 1, last = 2; last < internalPoints.length; last++) {
                
                //StdOut.println("=============================================");
                //StdOut.println("i = " + i +", p = " + p + ", first =" +first + ", last = " + last);
                
                // find last collinear to p point
                while (last < internalPoints.length
                        && internalPoints[p].slopeTo(internalPoints[first]) == internalPoints[p].slopeTo(internalPoints[last])) {
                    //StdOut.println("internalPoints[p] = " + internalPoints[p]);
                    //StdOut.println("internalPoints[first] = " + internalPoints[first]);
                    //StdOut.println("internalPoints[last] = " + internalPoints[last]);
                    //StdOut.println("internalPoints[p].slopeTo(internalPoints[first]) = " + internalPoints[p].slopeTo(internalPoints[first]) + ", internalPoints[p].slopeTo(internalPoints[last]) = " + internalPoints[p].slopeTo(internalPoints[last]));
                    
                    last++;
                    
                    //StdOut.println("p = " + p + ", last = " + last);
                }
                // if found at least 3 elements, make segment if it's unique
                if (last - first >= 3 && internalPoints[p].compareTo(internalPoints[first]) < 0) {
                    segments.add(new LineSegment(internalPoints[p], internalPoints[last - 1]));
                    //StdOut.println("add segment, p = " + p + ", last = " + last + ", " + internalPoints[p] + ", " + internalPoints[last - 1]);
                }
                // Try to find next
                first = last;
            }
        }
        



    }

    public int numberOfSegments() {
        return segments.size();
    }

    public LineSegment[] segments() {
        return segments.toArray(new LineSegment[0]);
    }

    public static void main(String[] args) {

        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();


    }
}
