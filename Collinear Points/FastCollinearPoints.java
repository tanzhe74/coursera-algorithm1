import edu.princeton.cs.algs4.*;
import java.util.*;
/*
 * author :zhe tan
 * date: 04/23/2017
 * public class FastCollinearPoints{
 *     public FastCollinearPoints(Point[] points) // finds all line segments containing 4 (or more) points
 *     public int numberOfSegments() // the number of line segments
 *     public LineSegment[] segments()// the line segments
 * }
 */

//for every point p, compute the slope other points make with p
//sort the slopes
//check if 3 adjacent points have equal slopes

public class FastCollinearPoints{
    private Point[] mypoints;
    private LineSegment[] lineSegment;
    private HashMap<Double, List<Point>> map;
    private List<LineSegment> segment;
    public FastCollinearPoints(Point[] points) {
        checkPoints(points);
        mypoints = new Point[points.length];

        mypoints = Arrays.copyOf(points, points.length);
        segment = new ArrayList<LineSegment>();
        map = new HashMap<>();
        
       
        for(int j = 0; j<points.length; j++) {
            Point p = points[j];
           
            Arrays.sort(mypoints, p.slopeOrder());
            
            
            List<Point> slopePoint = new ArrayList<>();
            double slope = 0;
            double slopeP = Double.NEGATIVE_INFINITY;
            
            for(int i = 1; i<mypoints.length; i++) {
                slope = p.slopeTo(mypoints[i]);
                if(slope == slopeP) {
                    slopePoint.add(mypoints[i]);
                }
                else {
                    if(slopePoint.size() >=3) {
                        slopePoint.add(p);
                        findSegments(slopePoint, slopeP);
                        
                    }
                    slopePoint.clear();
                    slopePoint.add(mypoints[i]);
                }
                slopeP = slope;
            }
            if(slopePoint.size() >=3) {
                slopePoint.add(p);
                findSegments(slopePoint, slope);
            }
        }
   
        lineSegment = segment.toArray(new LineSegment[segment.size()]);
    }
    
    public int numberOfSegments(){
        return segment.size();
    }
    
    public LineSegment[] segments() {
        return Arrays.copyOf(lineSegment, numberOfSegments());
    }
    
    private void findSegments(List<Point> slopePoint, double slope) { 
        List<Point> already = map.get(slope);
        Collections.sort(slopePoint);
        Point start = slopePoint.get(0);
        Point end = slopePoint.get(slopePoint.size()-1);
        
        if(already == null) {
            already = new ArrayList<>();
            already.add(end);
            map.put(slope, already);
            segment.add(new LineSegment(start, end));
        }
        else {
            for(Point current: already) {
                if(end.compareTo(current) == 0) {
                    return;
                }
            }
            already.add(end);
            segment.add(new LineSegment(start, end));
        }
              
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