import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;

/*可参考https://github.com/AlexJoz/Algorithms--Java-/blob/master/Week3-Collinear%20Points/BruteCollinearPoints.java*/

public class BruteCollinearPoints {

    //LineSegment[] segs;
    private List<LineSegment> segs;

    public BruteCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException("argument is null");
        }
        
        Point[] jCopy = points.clone();
        
        //if (hasDuplicate(points)) {
            //throw new IllegalArgumentException("U have duplicate points");
        //}
        
        for(Point point : jCopy){
            if(point == null)
                throw new IllegalArgumentException("point is null");
        }
        
        //check whether has same point
        Arrays.sort(jCopy, new Comparator<Point>() {
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

        segs = new ArrayList<LineSegment>();
        
        Arrays.sort(jCopy);

        for(int i = 0; i < jCopy.length - 3; i++){
            for(int j = i + 1; j < jCopy.length - 2; j++) {
                double slopeFS = jCopy[i].slopeTo(jCopy[j]);
                for(int k = j + 1; k < jCopy.length - 1; k++) {
                    //StdOut.println("k = " + k);
                    double slopeFT = jCopy[i].slopeTo(jCopy[k]);
                    if(slopeFS == slopeFT) {
                    
                        for(int t = k + 1; t < jCopy.length; t++) {
                            
                            double slopeFF = jCopy[i].slopeTo(jCopy[t]);
                            
                            if(slopeFS == slopeFF){
                                segs.add(new LineSegment(jCopy[i], jCopy[t]));
                            }
                            
                        }
                    }
                }
            }
        }

    }
    
    private boolean hasDuplicate(Point[] points) {
        for (int i = 0; i < points.length - 1; i++) {
            if (points[i].compareTo(points[i + 1]) == 0) {
                return true;
            }
        }
        return false;

    }

    public int numberOfSegments() {
        return segs.size();
    }

    public LineSegment[] segments() {
        //return segs;
        return segs.toArray(new LineSegment[segs.size()]);
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
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();


    }
}
