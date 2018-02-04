package ru.romananchugov;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class ImageProxyPattern {
    public static void program(){
        ImageProxyPattern k = new ImageProxyPattern();

    }

    public class ImageProxy implements Icon{

        ImageIcon imageIcon;
        URL imageUrl;
        Thread retrievalThread;
        boolean retrieving = false;

        public ImageProxy(URL url){
            imageUrl = url;
        }

        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {
            if(imageIcon != null){
                imageIcon.paintIcon(c, g, x, y);
            }else{
                g.drawString("Loading...", x + 300, y + 190);
                if(!retrieving){
                    retrieving = true;
                    retrievalThread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                imageIcon = new ImageIcon(imageUrl, "CD Cover");
                                c.repaint();
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                    });
                    retrievalThread.start();
                }
            }
        }

        @Override
        public int getIconWidth() {
            if(imageIcon != null){
                return imageIcon.getIconWidth();
            }else{
                return 800;
            }
        }

        @Override
        public int getIconHeight() {
            if(imageIcon != null){
                return imageIcon.getIconHeight();
            }else{
                return 600;
            }
        }
    }

}
