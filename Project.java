/*
* Class Section: TC1V/TT4V
* Trimester 2 2020/21
* Lee Xin Chen 1181203295
*/

package project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import static java.lang.Math.sqrt;
import javax.swing.border.*;

public class Project extends JFrame implements ActionListener
{
    JLabel inter, c1, c2, c1x, c2x, c1y, c2y, c1r, c2r, yn;
    JTextField tc1x, tc2x, tc1y, tc2y, tc1r, tc2r;
    JPanel top, cir1, cir2, cir1t, cir2t, cir1all, cir2all,left, right, info, but, mid;
    JButton redraw;
    int nc1x=130, nc1y=200, nc1r=50, nc2x=350, nc2y=200, nc2r=50; //enter by user
    int ac1x, ac1y, ac1d, ac2x, ac2y, ac2d; //actual points and diameter of circles
    int distance, radiusSum;
    double dis;
    
    MouseHandler mouse;
    int px, py, mousex, mousey; //press by user
    int dx, dy; //drag by user
    boolean c1press=false, c2press=false;
    
    public static void main(String[] args)
    {
        Project frame = new Project();
        frame.setSize(500, 600);
        frame.setVisible(true);
        frame.setTitle("Project");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    public Project()
    {
        Border blackline;
        blackline = BorderFactory.createLineBorder(Color.black);
        
        //top
        inter=new JLabel("Two circles intersect?");
        yn= new JLabel("");
        yn.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        top=new JPanel();
        top.add(inter); top.add(yn);
        
        //info of circles
        c1=new JLabel("Enter circle 1 info (red):");
        cir1t=new JPanel();
        cir1t.add(c1);
        c1x=new JLabel("Center x:");
        tc1x=new JTextField(5);
        tc1x.setText(String.valueOf(nc1x));
        c1y=new JLabel("Center y:");
        tc1y=new JTextField(5);
        tc1y.setText(String.valueOf(nc1y));
        c1r=new JLabel("Radius:");
        tc1r=new JTextField(5);
        tc1r.setText(String.valueOf(nc1r));
        cir1=new JPanel(new GridLayout(3,2));
        cir1.add(c1x);cir1.add(tc1x);
        cir1.add(c1y);cir1.add(tc1y);
        cir1.add(c1r);cir1.add(tc1r);
        cir1all=new JPanel(new GridLayout(2,1));
        cir1all.add(cir1t);cir1all.add(cir1);
        left=new JPanel();
        left.setBorder(blackline);
        left.add(cir1all);
        
        c2=new JLabel("Enter circle 2 info (blue):");
        cir2t=new JPanel();
        cir2t.add(c2);
        c2x=new JLabel("Center x:");
        tc2x=new JTextField(5);
        tc2x.setText(String.valueOf(nc2x));
        c2y=new JLabel("Center y:");
        tc2y=new JTextField(5);
        tc2y.setText(String.valueOf(nc2y));
        c2r=new JLabel("Radius:");
        tc2r=new JTextField(5);
        tc2r.setText(String.valueOf(nc2r));
        cir2=new JPanel(new GridLayout(3,2));
        cir2.add(c2x);cir2.add(tc2x);
        cir2.add(c2y);cir2.add(tc2y);
        cir2.add(c2r);cir2.add(tc2r);
        cir2all=new JPanel(new GridLayout(2,1));
        cir2all.add(cir2t);cir2all.add(cir2);
        right=new JPanel();
        right.setBorder(blackline);
        right.add(cir2all);
        
        info=new JPanel(new GridLayout(1, 2));
        info.add(left);info.add(right);
        
        
        redraw=new JButton("Redraw Circles");
        but=new JPanel();
        but.add(redraw);
        
        mid=new JPanel(new GridLayout(2,1));
        mid.add(info);
        mid.add(but);
        
        
        //add to layout
        setLayout(new BorderLayout());
        add(top, BorderLayout.NORTH);
        add(mid, BorderLayout.SOUTH);
        
        redraw.addActionListener(this);
        
        mouse = new MouseHandler();
        addMouseListener(mouse);
        addMouseMotionListener(mouse);
    }
    
    public void paint(Graphics g)
    {
       super.paint(g);
       
       //circle 1
       ac1x=(nc1x*2)-(nc1x+nc1r);
       ac1y=(nc1y*2)-(nc1y+nc1r);
       ac1d=nc1r*2;
       
       g.setColor(Color.red);
       g.drawOval(ac1x, ac1y, ac1d, ac1d);
       
       //circle 2
       ac2x=(nc2x*2)-(nc2x+nc2r);
       ac2y=(nc2y*2)-(nc2y+nc2r);
       ac2d=nc2r*2;
       
       g.setColor(Color.blue);
       g.drawOval(ac2x, ac2y, ac2d, ac2d);
       
       checkIntersect();
    }

    public void checkIntersect()
    {
        //calculate distance between midpoint of circle 1 & circle 2
        distance=((nc1x-nc2x)*(nc1x-nc2x))+((nc1y-nc2y)*(nc1y-nc2y));
        dis=sqrt(distance);
        //calculate the sum of distance of two circle's radius
        radiusSum=nc1r+nc2r;
        
        if(dis<=radiusSum)
            yn.setText(String.valueOf("Yes"));
        else
            yn.setText(String.valueOf("No"));
    }
    
    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource()== redraw)
        {
            //circle 1
            nc1x=Integer.parseInt(tc1x.getText());
            nc1y=Integer.parseInt(tc1y.getText());
            nc1r=Integer.parseInt(tc1r.getText());

            //circle2
            nc2x=Integer.parseInt(tc2x.getText());
            nc2y=Integer.parseInt(tc2y.getText());
            nc2r=Integer.parseInt(tc2r.getText());
            
            repaint();
        }
    }
    
    private class MouseHandler extends MouseAdapter implements MouseMotionListener 
    {
        public void mousePressed(MouseEvent me1)
        {
            //coordinate of mouse the moment clicked
            px=me1.getX();
            py=me1.getY();
            
            if(px<=(ac1x+ac1d) && px>=ac1x && py<=(ac1y+ac1d) && py>=ac1y) //if when mousePressed is inside the circle
            {
                c1press=true;
                c2press=false;
                
                mousex=px-ac1x;
                mousey=py-ac1y;
            }
            
            if(px>=ac2x && px<=(ac2x+ac2d) && py>=ac2y && py<=(ac2y+ac2d)) //if when mousePressed is inside the circle
            {
                c1press=false;
                c2press=true;
                
                mousex=px-ac2x;
                mousey=py-ac2y;
            }
        }
        
        public void mouseDragged(MouseEvent me2) 
        {
            //current coordinate of mouse after drag
            dx=me2.getX();
            dy=me2.getY();
            
            //circle 1
            if(c1press==true)
            {
                //let the mouse stay on its position while dragging the circle
                ac1x=dx-mousex;
                ac1y=dy-mousey;
                
                //get midpoint of cirlce
                nc1x=(ac1x+(ac1x+ac1d))/2;
                nc1y=(ac1y+(ac1y+ac1d))/2;
                
                //renew circle data on screen
                tc1x.setText(String.valueOf(nc1x));
                tc1y.setText(String.valueOf(nc1y));
            }
            
            //circle 2
            if(c2press==true)
            {
                //let the mouse stay on its position while dragging the circle
                ac2x=dx-mousex;
                ac2y=dy-mousey;
                
                //get midpoint of cirlce
                nc2x=(ac2x+(ac2x+ac2d))/2;
                nc2y=(ac2y+(ac2y+ac2d))/2;
                
                //renew circle data on screen
                tc2x.setText(String.valueOf(nc2x));
                tc2y.setText(String.valueOf(nc2y));
            }
            
            repaint();
        }
        
        public void mouseReleased(MouseEvent me3)
        {
            c1press=false;
            c2press=false;
        }
    }
}