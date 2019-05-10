import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;



public class TuBao {
    public static void main(String[] args) {
        Random random = new Random();
        double i2 = random.nextDouble();
        double x, y;
        Point p = null;
        List<Point> points = new ArrayList<Point>();
        for (int i = 0; i < 30; i++) {
            p = new Point(random.nextInt(100), random.nextInt(100));
            points.add(p);
        }
        JFrame frame = new JFrame("my frame"); //初始化一个窗口
        frame.setSize(300, 300); // 设置窗口大小
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 设置画图结束后的操作：退出画图程序
        frame.setVisible(true); // 显示窗口
        QuickTuBao tubao = new QuickTuBao(points);
        List<Line> lines = new ArrayList<Line>();
        List<Line> linest = tubao.eval();
        for (int i=0;i<linest.size();i++){
            lines.add(linest.get(i));
        }
        JPanel panel = new JPanel() {      // 初始化一个新画布
            private static final long serialVersionUID = 1L; // 不用管，可加可不加
            @Override
            public void paint(Graphics g) {    //重写 pait 方法
                super.paint(g);   //这个要加上，但不加也能正常显示
                g.setColor(Color.BLUE); //设置画笔颜色
                for (int i = 0; i < points.size(); i++) {
                    g.fillOval((int) points.get(i).x, (int) points.get(i).y, 3, 3);
                }
                for (int i = 0; i < lines.size(); i++) {
                    g.drawLine((int) lines.get(i).p1.x, (int) lines.get(i).p1.y, (int) lines.get(i).p2.x, (int) lines.get(i).p2.y);
                }
            }
        };
        frame.setContentPane(panel);    // 将画布添加到窗口中
    }
}
class Line {
    Point p1, p2;
    Line(Point p1, Point p2) {
        this.p1 = p1;
        this.p2 = p2;
    }
    public double getLength() {
        double dx = Math.abs(p1.x - p2.x);
        double dy = Math.abs(p1.y - p2.y);
        return Math.sqrt(dx * dx + dy * dy);
    }
}
class Point{//点
    double x;
    double y;
    public Point(double x,double y){
        this.x=x;
        this.y=y;
    }
}
/*
 *   分治法求凸包
 */
class QuickTuBao  {
    List<Point> pts = null;//给出的点集
    List<Line> lines = new ArrayList<Line>();//点集pts的凸包

    public void setPointList(List<Point> pts) {
        this.pts = pts;
    }

    public QuickTuBao(List<Point> pts){
        this.pts=pts;
    }

    //求凸包，结果存入lines中
    public List<Line> eval() {
        lines.clear();
        if (pts == null || pts.isEmpty()) { return lines; }
        List<Point> ptsLeft = new ArrayList<Point>();//左凸包中的点
        List<Point> ptsRight = new ArrayList<Point>();//右凸包中的点

        //按x坐标对pts排序
        Collections.sort(pts, new Comparator<Point>() {
            public int compare(Point p1, Point p2) {
                if(p1.x-p2.x>0) return 1;
                if(p1.x-p2.x<0) return -1;
                return 0;
            }
        });

        Point p1 = pts.get(0);//最左边的点
        Point p2 = pts.get(pts.size()-1);//最右边的点,用直线p1p2将原凸包分成两个小凸包
        Point p3 = null;
        double area = 0;
        for (int i = 1; i < pts.size(); i++) {//穷举所有的点,
            p3 = pts.get(i);
            area = getArea(p1, p2, p3);//求此三点所成三角形的有向面积
            if (area > 0) {
                ptsLeft.add(p3);//p3属于左
            } else if (area < 0) {
                ptsRight.add(p3);//p3属于右
            }
        }
        d(p1, p2, ptsLeft);//分别求解
        d(p2, p1, ptsRight);
        return lines;
    }

    private void d(Point p1, Point p2, List<Point> s) {
        //s集合为空
        if (s.isEmpty()) {
            lines.add(new Line(p1, p2));
            return;
        }
        //s集合不为空，寻找Pmax
        double area = 0;
        double maxArea = 0;
        Point pMax = null;
        for (int i = 0; i < s.size(); i++) {
            area = getArea(p1, p2, s.get(i));//最大面积对应的点就是Pmax
            if (area > maxArea) {
                pMax = s.get(i);
                maxArea = area;
            }
        }
        //找出位于(p1, pMax)直线左边的点集s1
        //找出位于(pMax, p2)直线左边的点集s2
        List<Point> s1 = new ArrayList<Point>();
        List<Point> s2 = new ArrayList<Point>();
        Point p3 = null;
        for (int i = 0; i < s.size(); i++) {
            p3 = s.get(i);
            if (getArea(p1, pMax, p3) > 0) {
                s1.add(p3);
            } else if (getArea(pMax, p2, p3) > 0) {
                s2.add(p3);
            }
        }
        //递归
        d(p1, pMax, s1);
        d(pMax, p2, s2);
    }
    // 三角形的面积等于返回值绝对值的二分之一
    // 当且仅当点p3位于直线(p1, p2)左侧时，表达式的符号为正
    //向量积
    private double getArea(Point p1, Point p2, Point p3) {
        return p1.x * p2.y + p3.x * p1.y + p2.x * p3.y -
                p3.x * p2.y - p2.x * p1.y - p1.x * p3.y;
    }
}