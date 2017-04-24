import edu.princeton.cs.algs4.*;
import java.util.ArrayList;
import java.util.Arrays;


/*
 * author :zhe tan
 * date: 04/23/2017
 * public class BruteCollinearPoints{
 *     public BruteCollinearPoints(Point[] points) // finds all line segments containing 4 points
 *     public int numberOfSegments() // the number of line segments
 *     public LineSegment[] segments()// the line segments
 * }
 */


public class BruteCollinearPoints {
    private Point[] mypoints;
    private LineSegment[] mysegments;
    
    public BruteCollinearPoints(Point[] points){
        checkPoints(points);
        mypoints = new Point[points.length];
        ArrayList<LineSegment> collinear = new ArrayList<LineSegment>();
        for(int i = 0; i<points.length; i++) {
            mypoints[i] = points[i];
        }
        Arrays.sort(mypoints);
        for(int p = 0; p < mypoints.length-3; p++) {
            for(int q = p+1; q < mypoints.length-2; q++) {
                for(int r = q+1; r < mypoints.length-1; r++) {
                    for( int s = r+1; s < mypoints.length; s++) {
                        if(mypoints[p].slopeTo(mypoints[q]) == mypoints[p].slopeTo(mypoints[r]) &&
                           mypoints[p].slopeTo(mypoints[q]) == mypoints[p].slopeTo(mypoints[s])) {
                            collinear.add(new LineSegment(mypoints[p], mypoints[s]));
                        }
                    }
                }
            }
        }
        mysegments = collinear.toArray(new LineSegment[collinear.size()]);
    }
    
    public int numberOfSegments(){
        return mysegments.length;
    }
    
    public LineSegment[] segments(){
        return Arrays.copyOf(mysegments, numberOfSegments());
    }
    
    private void checkPoints(Point[] points) {
        for(int i = 0; i<points.length-1; i++) {
            if(points[i] == null) {
                throw new NullPointerException();
            }
            for( int j = i+1; j<points.length; j++) {
                if(points[i].compareTo(points[j]) == 0) {
                    throw new IllegalArgumentException();
                }
            }
        }
    }
    
    
    public static void main(String[] args) {
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

       
        
        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        StdOut.println(collinear.segments());
        StdOut.println(collinear.numberOfSegments());
        StdOut.println(collinear.segments());
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}