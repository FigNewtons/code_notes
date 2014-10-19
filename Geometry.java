/**
		Algorithms (MIT - CLRS)
		Section 33.1 (p. 1015 - 1019)

		Using the cross product and other
		techniques to determine if line segments
		intersect, clockwise orientation, etc.

	*/

public class Geometry{

	private static class Point{

		private double x;
		private double y;

		public Point(double x, double y){
			this.x = x;
			this.y = y;

		}

		public double x(){ return x; }
		public double y(){ return y; }
		public String toString(){ return "(" + x + "," + y + ")"; }

	}

	private static class LineSegment{

		private Point start;
		private Point end;

		public LineSegment(Point s, Point e){
			start = s;
			end = e;
		}

		public Point start(){ return start;}
		public Point end(){ return end; }
		public LineSegment v() {return vector(this);}

	}

	public static LineSegment vector(LineSegment seg){
		Point temp = new Point(seg.start().x() - seg.end().x(), seg.start().y() - seg.end().y());
		return new LineSegment(temp, new Point(0,0));
	}

	// Cross-product (determinant) of two points / vectors
	public static double cross(Point p1, Point p2){
		return p1.x() * p2.y() - p1.y() * p2.x();
	}

	// Returns true if vector p1 is clockwise to vector p2
	public static boolean isClockwise(Point p1, Point p2){
		return cross(p1, p2) > 0;
	}

	// Assumes a common endpoint for L1 and L2
	public static boolean isClockwise(LineSegment l1, LineSegment l2){
		return cross( vector(l1).start() , vector(l2).start()) > 0 ;
	}

	// Helper methods for intersect
	private static double Direction(Point pi, Point pj, Point pk){
		return cross(vector(new LineSegment(pk, pi)).start() , vector(new LineSegment(pj, pi)).start());
	}

	private static double max(double a, double b){
		return a > b? a : b;
	}

	private static double min(double a, double b){
		return a < b? a: b;
	}

	// Returns true if Point pk is on the line segment pi-pj
	private static boolean onSegment(Point pi, Point pj, Point pk){
		double minx = min(pi.x(), pj.x());
		double miny = min(pi.y(), pj.y());
		double maxx = max(pi.x(), pj.x());
		double maxy = max(pi.y(), pj.y());

		if(((minx <= pk.x()) && (pk.x() <= maxx)) &&  ( (miny <= pk.y()) && (pk.y() <= maxy)))
			return true;
		else return false;
	}

	// Returns true if L1 and L2 intersect
	public static boolean intersect(LineSegment l1, LineSegment l2){

		Point p1 = l1.start();
		Point p2 = l1.end();
		Point p3 = l2.start();
		Point p4 = l2.end();

		double d1 = Direction(p3, p4, p1);
		double d2 = Direction(p3, p4, p2);
		double d3 = Direction(p1, p2, p3);
		double d4 = Direction(p1, p2, p4);

		if(((d1 > 0 && d2 < 0 ) || (d1 < 0 && d2 > 0)) && ((d3 > 0 && d4 < 0) || (d3 < 0 && d4 > 0))) return true;
		else if (d1 == 0 && onSegment(p3, p4, p1)) return true;
		else if (d2 == 0 && onSegment(p3, p4, p2)) return true;
		else if (d3 == 0 && onSegment(p1, p2, p3)) return true;
		else if (d4 == 0 && onSegment(p1, p2, p4)) return true;
		else return false;

	}

	public static void main(String[] args){
		LineSegment l1 = new LineSegment( new Point(4,3), new Point(1,1));
		LineSegment l2 = new LineSegment( new Point(-2, 5), new Point(1,1));
		System.out.println( isClockwise(l1, l2));
		System.out.println(intersect(l1, l2));
	}

}
