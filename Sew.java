/**
    ICPC Problem E: Not Sew Difficult
    Mid-Altantic Regionals 2014

    Solution in O(n * k) time where n is
    the number of rectangles and k is the max range
    of any given interval
*/


import java.util.*;

public class Sew{

    private static class Point{
        int x, y;

        public Point(int x, int y){
            this.x = x;
            this.y = y;
        }

        public int x(){return x;}
        public int y(){return y;}

    }

    private static class Rectangle{
        Point tl, tr, bl, br;

        public Rectangle(int x1, int y1, int x2, int y2){
            bl = new Point(x1, y1);
            tr = new Point(x2, y2);
            br = new Point(x2, y1);
            tl = new Point(x1, y2);
        }

        public Point bl(){return bl;}
        public Point tr(){return tr;}
        public Point br(){return br;}
        public Point tl(){return tl;}
        public boolean inRectangle(int x, int y){
            if(bl.x() < x && x < br.x() && bl.y() < y && y < tl.y())
                return true;

            return false;
        }
    }

    public static void main(String[] args){

        Scanner in = new Scanner(System.in);

        int numRect = in.nextInt();

        while(numRect > 0){

            Rectangle[] rectangles = new Rectangle[numRect];
            int[] x_axis = new int[100001];
            int[] y_axis = new int[100001];

            for(int i = 0; i < numRect; i++){

                int x1 = in.nextInt();
                int y1 = in.nextInt();
                int x2 = in.nextInt();
                int y2 = in.nextInt();

                rectangles[i] = new Rectangle(x1,y1,x2,y2);

                for(int j = x1; j < x2; j++){
                    x_axis[j]++;
                }
                for(int j = y1; j < y2; j++){
                    y_axis[j]++;
                }
            }

            int x_max = 1, y_max = 1;
            int x_index = 0, y_index = 0;

            for(int i = 0; i < x_axis.length; i++){
                if(x_axis[i] > x_max){
                    x_max = x_axis[i];
                    x_index = i;
                }
            }

            for(int i = 0; i < y_axis.length; i++){
                if(y_axis[i] > y_max){
                    y_max = y_axis[i];
                    y_index = i;
                }
            }

            int max = x_max > y_max ? y_max : x_max;

            //Final check
            int c = 0;
            for(Rectangle r: rectangles){
                if(r.inRectangle(x_index, y_index))
                    c++;
            }

            int answer = max != c ? c : max;
            answer++;

            System.out.println(answer);

            numRect = in.nextInt();
        }
        in.close();
    }

}
