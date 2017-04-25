import edu.princeton.cs.algs4.*;
import java.util.*;
/*
 * author :zhe tan
 * date: 04/25/2017
 * public class FastCollinearPoints{
 *     public FastCollinearPoints(Point[] points) // finds all line segments containing 4 (or more) points
 *     public int numberOfSegments() // the number of line segments
 *     public LineSegment[] segments()// the line segments
 * }
 */
//the final version of this project get 100/100
//first do the natural sort
//then do the sort based on the slopeOrder


//for every point p, compute the slope other points make with p
//sort the slopes
//check if 3 adjacent points have equal slopes

public class FastCollinearPoints{
    private List<LineSegment> lineSegments = new ArrayList<>();
    public FastCollinearPoints(Point[] points) {
        checkPoints(points);
        Point[] sortedPoints = Arrays.copyOf(points, points.length);
        Arrays.sort(sortedPoints);
        findLineSegments(sortedPoints);
    }
    
    public int numberOfSegments(){
        return lineSegments.size();
    }
    
    public LineSegment[] segments() {
        return lineSegments.toArray(new LineSegment[lineSegments.size()]);
    }
    
    private void findLineSegments(Point[] points) { 
        for( int i = 0; i<points.length && !interrupted(); i++) {
            Comparator<Point> slopeOrder = points[i].slopeOrder();
            Arrays.sort(points, slopeOrder);
            int j = 2;
            while(j < points.length) {
                int start = j-1;
                while( j< points.length && slopeOrder.compare(points[j-1], points[j]) == 0) {
                    j++;
                }
                if(j - start >= 3 && points[0].compareTo(points[start]) < 0) {
                    lineSegments.add(new LineSegment(points[0], points[j-1]));
                }
                j++;
            }
            Arrays.sort(points);
        }
    }
    
    private boolean interrupted() {
        return Thread.currentThread().isInterrupted();       
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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();

    }
}