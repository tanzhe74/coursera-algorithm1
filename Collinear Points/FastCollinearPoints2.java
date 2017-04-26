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


//The second version, didn't use the hashmap
//just use the natural sort and the sort with slopeOrder
//but has some error that the linesegment has some redundant items
//got a 93 score

public class FastCollinearPoints2{
    private Point[] mypoints;
    private LineSegment[] lineSegment;
    private ArrayList<LineSegment> segment;
    private ArrayList<String> segmentString;
    public FastCollinearPoints2(Point[] points) {
        mypoints = new Point[points.length];
        mypoints = Arrays.copyOf(points, points.length);
        segment = new ArrayList<LineSegment>();
        segmentString = new ArrayList<String>();
        Arrays.sort(mypoints);
       
        for(Point p: mypoints) {
            Arrays.sort(mypoints, p.slopeOrder());
            findSegments(mypoints);
        }
        lineSegment = segment.toArray(new LineSegment[segment.size()]);
    }
    
    public int numberOfSegments(){
        return lineSegment.length;
    }
    
    public LineSegment[] segments() {
        return lineSegment;
    }
    
    private void findSegments(Point[] points) {
//        for(Point p: points) {
//            StdOut.print(p.toString()+" ");
//        }
//        StdOut.println();
        Point p = points[0];
        int count = 1;
        double slopeP = p.slopeTo(points[1]);
        int startIndex = 0;
        for(int i = 1; i<points.length; i++) {
            double slope = p.slopeTo(points[i]);
            if(slopeP == slope) {
                count++;
            }
            else {
                if(count >= 4) {
                    Point[] temp = new Point[i-startIndex+1];
                    
                    for(int j = 0; j<temp.length-1; j++) {
                        temp[j] = points[j+startIndex];
                    }
                   
                    temp[temp.length-1] = points[0];
                    Arrays.sort(temp);
                    
                    LineSegment s = new LineSegment(temp[0], temp[temp.length-1]);

                    if(!segmentString.contains(s.toString())) {
                        segment.add(s);  
                        segmentString.add(s.toString());
                    }        
                }
                count = 2;
                slopeP = slope;
                startIndex = i;
            }
        }
        if(count >= 4) {
            Point[] temp = new Point[points.length-startIndex+1];
            for(int j = 0; j<temp.length-1; j++) {
                temp[j] = points[j+startIndex];
            }
            temp[temp.length-1] = points[0];
            Arrays.sort(temp);
            LineSegment s = new LineSegment(temp[0], temp[temp.length-1]);
            if(!segmentString.contains(s.toString())) {
                segment.add(s);  
                segmentString.add(s.toString());
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