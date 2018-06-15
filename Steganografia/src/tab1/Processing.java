/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tab1;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JWindow;

public class Processing  extends JWindow{
    
JLabel loading,title;
JProgressBar b;
 JPanel p;
   
    public Processing() {
        p=new JPanel(){
            public void paintComponent(Graphics g){
                try{
                    URL imgurl=new URL(this.getClass().getResource("wall28.jpg"),"wall28.jpg");
                    Image img= ImageIO.read(imgurl);
                    g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), this);
                }catch(Exception e){}
            }
        };
        
        this.setLayout(new GridLayout());
        p.setLayout(null);
        this.add(p);
        this.setSize(850, 600);
        
        Dimension dimension= Toolkit.getDefaultToolkit().getScreenSize();
        int x=(int)((dimension.getWidth()-this.getWidth())/2);
        int y=(int)((dimension.getHeight()-this.getHeight())/2);
        this.setLocation(x, y);
        b=new JProgressBar(0,2000);
        b.setBounds(50, 500, 740, 8);
        b.setForeground(Color.red);
        
        loading=new JLabel("loading................");
        loading.setForeground(Color.red);
        loading.setFont(new Font("Times new Roman",Font.ITALIC,19));
        loading.setBounds(50, 455, 80, 60);
        
        title=new JLabel("Steganografia");
        title.setForeground(Color.red);
        title.setFont(new Font("Matura MT Script Capitals",Font.ITALIC,50));
        title.setBounds(480, 170,350, 220);
        
        p.add(loading);
        p.add(title);
        p.add(b);
        b.setValue(0);
        this.add(p);
        
        
        this.setVisible(true);
                }
    public static void main(String[] args) throws InterruptedException 
    {
       Processing s= new Processing();
       int i=0;
       while(i<=2000)
       {
       s.b.setValue(i);
       i=i+25;
       Thread.sleep(50);
       }
       new Tab1().setVisible(true);
       s.dispose();        

    }

}


