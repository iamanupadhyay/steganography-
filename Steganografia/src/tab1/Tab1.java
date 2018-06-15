/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tab1;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.*;

class Tab1 extends 	JFrame  
{
	private		JTabbedPane tabbedPane;
	private		JPanel		panel1;
	private		JPanel		panel2;
        
        
        
        //encrypt
        JTextArea  msg;
        BufferedImage sourceImage = null,embeddedImage = null;
        JSplitPane sp = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        JScrollPane originalPane = new JScrollPane(),embeddedPane = new JScrollPane();
        JButton open,embed,save, reset ;
        
        
        
        //decrypt
        JTextArea message;
        JButton open1,decode1,reset1;
        BufferedImage image = null;
        JScrollPane imagePane ;
   
        


	public Tab1()
	{
	
		
        
            
         //frame
        setTitle("STAGANOGRAFIA" );
	this.setBounds(GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds());
	setBackground( Color.gray );
        this.setLayout(new GridLayout());
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        JPanel topPanel = new JPanel();
	topPanel.setLayout( new BorderLayout() );      //toppanel
	getContentPane().add( topPanel );

	// Create the tab pages
	create_encrypt();
        create_decrypt();
	

	// Create a tabbed pane
	tabbedPane = new JTabbedPane();
	tabbedPane.addTab( "ENCRYPT", panel1 );
	tabbedPane.addTab( "DECRYPT", panel2 );
	topPanel.add( tabbedPane, BorderLayout.CENTER );
        this.validate();
        
	}


	public void create_encrypt()
	{
                msg =new JTextArea();
                open = new JButton("Open"); 
                embed = new JButton("Embed");
                save = new JButton("Save into new file"); 
                reset = new JButton("Reset");
            
            
            
                 //panel1
	        panel1 = new JPanel();
		panel1.setLayout( null );
                panel1.setBounds(0, 0, this.getWidth(), this.getHeight());
                
                
                
                
                //p1
		JPanel p1=new JPanel();
                p1.setLayout(new GridLayout(1,1));
                p1.setBounds(0, 0, this.getWidth(), 270);
                p1.setBackground(Color.red);
                p1.add(new JScrollPane(msg));
                msg.setFont(new Font("Arial",Font.BOLD,20));
                p1.setBorder(BorderFactory.createTitledBorder("Message to be embedded"));
                panel1.add(p1);
     
                
                
                //p2
                JPanel p2=new JPanel();
                p2 = new JPanel(new GridLayout(1,1));
                p2.setBounds(0, 290, this.getWidth(), 300);
   
                sp.setLeftComponent(originalPane);
                sp.setRightComponent(embeddedPane);
                sp.setResizeWeight(0.5d);
                originalPane.setBorder(BorderFactory.createTitledBorder("Original Image"));
                embeddedPane.setBorder(BorderFactory.createTitledBorder("Steganographed Image"));
                p2.add(sp, BorderLayout.CENTER);
                panel1.add(p2);
                
                
                
                //p3
                JPanel p3=new JPanel();
                p3.setLayout(new FlowLayout());
                p3.setBounds(0,600, this.getWidth(),120 );
                p3.add(open);
                p3.add(embed);
                p3.add(save);   
                p3.add(reset);
                panel1.add(p3);
                
                
                
 open.addActionListener(new ActionListener() {

                 @Override
                public void actionPerformed(ActionEvent e) {
                    java.io.File f = showFileDialog(true);
                try {   
                       sourceImage = ImageIO.read(f);
                      JLabel l = new JLabel(new ImageIcon(sourceImage));
                      originalPane.getViewport().add(l);
        
                    } catch(Exception ex) { ex.printStackTrace(); }
                }
            });
                       
               
 embed.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                
                String mess = msg.getText();
                embeddedImage = sourceImage.getSubimage(0,0,
                sourceImage.getWidth(),sourceImage.getHeight());
               embedMessage(embeddedImage, mess);
              JLabel l = new JLabel(new ImageIcon(embeddedImage));
              embeddedPane.getViewport().add(l);
    
            }
        });
 
 
 
 save.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if(embeddedImage == null) {
                JOptionPane.showMessageDialog(panel1, "No message has been embedded!", 
           "Nothing to save", JOptionPane.ERROR_MESSAGE);
            return;
        }
         java.io.File f = showFileDialog(false);
         String name = f.getName();
         String ext = name.substring(name.lastIndexOf(".")+1).toLowerCase();
         if(!ext.equals("png") && !ext.equals("bmp") &&   !ext.equals("dib")) {
           ext = "png";
          f = new java.io.File(f.getAbsolutePath()+".png");
           }
        try {
                   if(f.exists()) f.delete();
                    ImageIO.write(embeddedImage, ext.toUpperCase(), f);
        } catch(Exception ex) { ex.printStackTrace(); }
            }
        });
        
     
reset.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                msg.setText("");
              originalPane.getViewport().removeAll();
              embeddedPane.getViewport().removeAll();
               sourceImage = null;
              embeddedImage = null;
              sp.setResizeWeight(0.5d);
               panel1.validate();
                
                
            }
        });

}
        
        
        
 private java.io.File showFileDialog(final boolean open) {
          JFileChooser fc = new JFileChooser("Open an image");
         javax.swing.filechooser.FileFilter ff = new javax.swing.filechooser.FileFilter() {
         public boolean accept(java.io.File f) {
         String name = f.getName().toLowerCase();
         if(open)
              return f.isDirectory() || name.endsWith(".jpg") || name.endsWith(".jpeg") ||
                name.endsWith(".png") || name.endsWith(".gif") || name.endsWith(".tiff") ||
                 name.endsWith(".bmp") || name.endsWith(".dib");
           return f.isDirectory() || name.endsWith(".png") ||    name.endsWith(".bmp");
           }
        public String getDescription() {
           if(open)
              return "Image (*.jpg, *.jpeg, *.png, *.gif, *.tiff, *.bmp, *.dib)";
           return "Image (*.png, *.bmp)";
           }
        };
         fc.setAcceptAllFileFilterUsed(false);
         fc.addChoosableFileFilter(ff);
  
         java.io.File f = null;
         if(open && fc.showOpenDialog(this) == fc.APPROVE_OPTION)
          f = fc.getSelectedFile();
         else if(!open && fc.showSaveDialog(this) == fc.APPROVE_OPTION)
         f = fc.getSelectedFile();
        return f;
 }
  
 private void embedMessage(BufferedImage img, String mess) {
          int messageLength = mess.length();
     
          int imageWidth = img.getWidth(), imageHeight = img.getHeight(),
          imageSize = imageWidth * imageHeight;
          if(messageLength * 8 + 32 > imageSize) {
          JOptionPane.showMessageDialog(panel1, "Message is too long for the chosen image",
           "Message too long!", JOptionPane.ERROR_MESSAGE);
          return;
         }
        embedInteger(img, messageLength, 0, 0);
  
         byte b[] = mess.getBytes();
         for(int i=0; i<b.length; i++)
         embedByte(img, b[i], i*8+32, 0);
     }
  
 private void embedInteger(BufferedImage img, int n, int start, int storageBit) {
         int maxX = img.getWidth(), maxY = img.getHeight(), 
         startX = start/maxY, startY = start - startX*maxY, count=0;
         for(int i=startX; i<maxX && count<32; i++) {
         for(int j=startY; j<maxY && count<32; j++) {
           int rgb = img.getRGB(i, j), bit = getBitValue(n, count);
           rgb = setBitValue(rgb, storageBit, bit);
           img.setRGB(i, j, rgb);
           count++;
           }
        }
     }
  
  private void embedByte(BufferedImage img, byte b, int start, int storageBit) {
           int maxX = img.getWidth(), maxY = img.getHeight(), 
           startX = start/maxY, startY = start - startX*maxY, count=0;
           for(int i=startX; i<maxX && count<8; i++) {
               for(int j=startY; j<maxY && count<8; j++) {
                      int rgb = img.getRGB(i, j), bit = getBitValue(b, count);
                      rgb = setBitValue(rgb, storageBit, bit);
                      img.setRGB(i, j, rgb);
                      count++;
           }
        }
     }
 
  private int getBitValue(int n, int location) {
     int v = n & (int) Math.round(Math.pow(2, location));
     return v==0?0:1;
     }
  
  private int setBitValue(int n, int location, int bit) {
    int toggle = (int) Math.pow(2, location), bv = getBitValue(n, location);
    if(bv == bit)
       return n;
    if(bv == 0 && bit == 1)
       n |= toggle;
    else if(bv == 1 && bit == 0)
        n ^= toggle;
    return n;
    }
  
 
 
 
 
 
 
public void create_decrypt()
	{
            
             image = null;
             imagePane = new JScrollPane();
             message=new  JTextArea();
             open1 = new JButton("Open");
             decode1 = new JButton("Decode");
             reset1 = new JButton("Reset");
             
             
              //panel2 
	      panel2 = new JPanel();
	      panel2.setLayout( null );
              panel2.setBounds(0, 0, this.getWidth(), this.getHeight());
              
              
              
              //p1
               JPanel p1=new JPanel();
               p1.setLayout(null);
               p1.setLayout(new FlowLayout());
               p1.setBounds(0, 0, this.getWidth(), 50);
               p1.add(open1);
               p1.add(decode1);
               p1.add(reset1);
               panel2.add(p1);
               
               
               
                //p2
               JPanel p2 =new JPanel();
               p2.setLayout(new GridLayout(1,1));
               p2.setBounds(0, 50, this.getWidth(),320);
               imagePane.setBorder(BorderFactory.createTitledBorder("Steganographed Image"));
               p2.add(imagePane, BorderLayout.CENTER);
               panel2.add(p2);
     
     
               
               
               JPanel p3=new JPanel();
               p3.setLayout(new GridLayout(1,1));
               p3.setBounds(0, 380, this.getWidth(), 270);
               p3.setBackground(Color.red);
               p3.add(new JScrollPane(message));
               message.setFont(new Font("Arial",Font.BOLD,20));
               p3.setBorder(BorderFactory.createTitledBorder("Decoded message"));
               panel2.add(p3);
     
               
 open1.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                
                java.io.File f = showFileDialog1(true);
            try {   
                   image = ImageIO.read(f);
                   JLabel l = new JLabel(new ImageIcon(image));
                   imagePane.getViewport().add(l);
                   panel2.validate();
              } catch(Exception ex) { ex.printStackTrace(); }
            }
        });

 
 decode1.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                int len = extractInteger(image, 0, 0);
                byte b[] = new byte[len];
                for(int i=0; i<len; i++)
                b[i] = extractByte(image, i*8+32, 0);
                message.setText(new String(b));
            }
        });
          
 reset1.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                message.setText("");
               imagePane.getViewport().removeAll();
               image = null;
                panel2.validate();
            }
        });
        
		
}

private java.io.File showFileDialog1(boolean open) {
         JFileChooser fc = new JFileChooser("Open an image");
         javax.swing.filechooser.FileFilter ff = new javax.swing.filechooser.FileFilter() {
         public boolean accept(java.io.File f) {
           String name = f.getName().toLowerCase();
          return f.isDirectory() ||   name.endsWith(".png") || name.endsWith(".bmp");
           }
         public String getDescription() {
           return "Image (*.png, *.bmp)";
          }
        };
       fc.setAcceptAllFileFilterUsed(false);
       fc.addChoosableFileFilter(ff);
  
         java.io.File f = null;
        if(open && fc.showOpenDialog(this) == fc.APPROVE_OPTION)
           f = fc.getSelectedFile();
        else if(!open && fc.showSaveDialog(this) == fc.APPROVE_OPTION)
           f = fc.getSelectedFile();
        return f;
     }
       

private int extractInteger(BufferedImage img, int start, int storageBit) {
          int maxX = img.getWidth(), maxY = img.getHeight(), 
          startX = start/maxY, startY = start - startX*maxY, count=0;
          int length = 0;
           for(int i=startX; i<maxX && count<32; i++) {
           for(int j=startY; j<maxY && count<32; j++) {
           int rgb = img.getRGB(i, j), bit = getBitValue(rgb, storageBit);
           length = setBitValue(length, count, bit);
           count++;
           }
       }
    return length;
     }
 
private byte extractByte(BufferedImage img, int start, int storageBit) {
        int maxX = img.getWidth(), maxY = img.getHeight(), 
       startX = start/maxY, startY = start - startX*maxY, count=0;
       byte b = 0;
        for(int i=startX; i<maxX && count<8; i++) {
        for(int j=startY; j<maxY && count<8; j++) {
          int rgb = img.getRGB(i, j), bit = getBitValue(rgb, storageBit);
          b = (byte)setBitValue(b, count, bit);
          count++;
          }
       }
     return b;
     }
 
  
        
        
        

    // Main method to get things started
/*public static void main( String args[] )
	{
		// Create an instance of the test application
		Tab1 mainFrame	= new Tab1();
	
	}*/

  
    

    
    
    
    
}


