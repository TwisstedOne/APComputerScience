// Main
import java.io.*;
import java.util.*;
// Art
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import javax.swing.*;

class sprite {
    int type; // static, key, enemy
    int state; // on or off
    int map; // texture
    int x, y, z; // position

    void createStprite(int itype, int istate, int imap, int ix, int iy, int iz) { type = itype; state = istate; map = imap; x = ix; y = iy; z = iz; }
}

public class raycastC extends JPanel {
    static String[] keysPressed;

    final int mapX = 16;
    final int mapY = 16;
    final int mapS = 64;
    final int maxDepth = 16;

    static sprite[] sprites;

    int mapW[] = {
        1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
        1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1,
        1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1,
        1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1,
        1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1,
        1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1,
        1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1,
        1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1,
        1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 1,
        1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 1,
        1, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 1,
        1, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 1,
        1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 1,
        1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 1,
        1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1,
        1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
    };

    int mapF[] = {
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
    };

    int mapC[] = {
        1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
        1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
        1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
        1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
        1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
        1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
        1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
        1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
        1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
        1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
        1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
        1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
        1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
        1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
        1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
        1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
    };

    static int[] Wall_Textures;
    static int[] Floor_Textures;
    static int[] Ceiling_Textures;
    static int[] Sprite_Textures;
    static int[] Skybox;

    int[] depth = new int[120];

    static int FOV = 15;

    static double px, py, pdx, pdy, pa;
    static double movementSpeed = .3;
    static double turnSpeed = .32;

    static void sleep(int miliseconds) {
        try   { Thread.sleep(miliseconds); } 
        catch (InterruptedException e) { Thread.currentThread().interrupt(); }
    }

    static int[] textureGetter(String TextureName) {
        int[] texture;

        try {
            Scanner myReader = new Scanner(new File("C:/Users/scott/OneDrive/Desktop/JavaFolder/RaycastingEngine/Textures/" + TextureName + ".txt")); // File Folder: 
            String data = ""; while (myReader.hasNextLine()) { data += myReader.nextLine(); } myReader.close();

            String[] splitdata = data.split(","); texture = new int[splitdata.length];
            for (int i = 0; i < splitdata.length; i++) { texture[i] = Integer.parseInt(splitdata[i]); } return texture;
          } catch (FileNotFoundException e) { System.out.println("An error occurred."); e.printStackTrace(); return new int[0]; }  
    }

    void setColor(Graphics graphics, int r, int g, int b) {
        Color customColor = new Color(r, g, b); graphics.setColor(customColor);
    }

    static double degToRad(double a) { return a * Math.PI / 180.0; }

    double FixAng(double a) { if (a > 359) { a -= 360; } if (a <   0) { a += 360; } return a; }

    void keyboard() {
        for (int i = 0; i < keysPressed.length; i++) {
            String key = keysPressed[i];

            if (key.equals("a")) {
                pa += turnSpeed * fps; pa = FixAng(pa);
                pdx = Math.cos(degToRad(pa)); pdy = -Math.sin(degToRad(pa));
                continue; }

            if (key.equals("d")) {
                pa -= turnSpeed * fps; pa = FixAng(pa);
                pdx = Math.cos(degToRad(pa)); pdy = -Math.sin(degToRad(pa));
                continue; }

                // Collisons
            int xo = 0; if (pdx < 0) { xo = -20; } else { xo = 20; }
            int yo = 0; if (pdy < 0) { yo = -20; } else { yo = 20; }
            int ipx = (int) px / 64, ipx_add_xo = (int) ((px + xo) / 64), ipx_sub_xo = (int) ((px - xo) / 64);
            int ipy = (int) py / 64, ipy_add_yo = (int) ((py + yo) / 64), ipy_sub_yo = (int) ((py - yo) / 64);

            if (key.equals("w")) {
                if (mapW[ipy * mapX + ipx_add_xo] == 0) { px +=  (pdx * movementSpeed * fps); }
                if (mapW[ipy_add_yo * mapX + ipx] == 0) { py +=  (pdy * movementSpeed * fps); }
                continue; }
            if (key.equals("s")) {
                if (mapW[ipy * mapX + ipx_sub_xo] == 0) { px -=  (pdx * movementSpeed * fps); }
                if (mapW[ipy_sub_yo * mapX + ipx] == 0) { py -=  (pdy * movementSpeed * fps); }
                continue; }
        }
    }

    double distance(double ax, double ay, double bx, double by, double ang) { return Math.cos(degToRad(ang)) * (bx - ax) - Math.sin(degToRad(ang)) * (by - ay); }

    void drawRays2D(Graphics2D g) {
        int r, mx, my, mp, dof; double vx, vy, rx, ry, ra, xo = 0, yo = 0, disV, disH; 

        ra = FixAng(pa + 30);

        for(r = 0; r < 120; r++) {
            int vmt = 0, hmt = 0;                                                              //vertical and horizontal map texture number 
            //---Vertical--- 
            dof = 0; disV = 100000;
            double Tan = Math.tan(degToRad(ra));
                if (Math.cos(degToRad(ra)) >  0.001) { rx=(((int) px >> 6) << 6) +     64; ry = (px-rx) * Tan + py; xo =  64; yo = -xo * Tan; }//looking left
            else if(Math.cos(degToRad(ra)) < -0.001) { rx=(((int) px >> 6) << 6) - 0.0001; ry = (px-rx) * Tan + py; xo = -64; yo = -xo * Tan; }//looking right
            else { rx = px; ry = py; dof = maxDepth; }                                                  //looking up or down. no hit  

            while(dof < maxDepth) { 
                mx = (int) (rx) >> 6; my = (int) (ry) >> 6; mp = my * mapX + mx;                     
                if (mp > 0 && mp < mapX * mapY && mapW[mp] > 0){ vmt = mapW[mp] - 1; dof = maxDepth; disV = Math.cos(degToRad(ra)) * (rx - px) - Math.sin(degToRad(ra)) * (ry - py); }//hit         
                else { rx += xo; ry += yo; dof += 1; }                                               //check next horizontal
            } 
            vx = rx; vy = ry;

            //---Horizontal---
            dof = 0; disH = 100000;
            Tan = 1.0/Tan; 
                 if (Math.sin(degToRad(ra)) >  0.001) { ry=(((int) py >> 6) << 6) -0.0001; rx = (py - ry) * Tan + px; yo = -64; xo = -yo * Tan;}//looking up 
            else if (Math.sin(degToRad(ra)) < -0.001) { ry=(((int) py >> 6) << 6)     +64; rx = (py - ry) * Tan + px; yo =  64; xo = -yo * Tan;}//looking down
            else { rx = px; ry = py; dof = maxDepth; }                                                   //looking straight left or right
            
            while(dof < maxDepth) { 
                mx = (int)(rx) >> 6; my = (int)(ry) >> 6; mp = my * mapX + mx;                          
                if (mp > 0 && mp < mapX * mapY && mapW[mp] > 0){ hmt = mapW[mp] - 1; dof = maxDepth; disH = Math.cos(degToRad(ra)) * (rx - px) - Math.sin(degToRad(ra)) * (ry - py); }//hit         
                else { rx += xo; ry += yo; dof += 1; }                                               //check next horizontal
            } 
            
            double shade = 1;
            setColor(g, 204, 204, 0);
            if(disV < disH) { hmt = vmt; shade = 0.5; rx = vx; ry = vy; disH = disV; setColor(g, 153, 153, 0); }//horizontal hit first
                
            int ScrenHeight = 1000;
            int ca = (int) FixAng(pa - ra); disH = disH * Math.cos(degToRad(ca));                            //fix fisheye 
            int lineH = (int) ((mapS * ScrenHeight)/disH); 
            double ty_step= 32.0/lineH; 
            double ty_off = 0; 
            if (lineH > ScrenHeight) { ty_off= (lineH - ScrenHeight)/2.0; lineH = ScrenHeight; }                            //line height and limit
            int lineOff = ScrenHeight/2 - (lineH >> 1);                                               //line offset

            depth[r] = (int) disH;
            //---draw walls---
            int y;
            double ty = ty_off * ty_step; //+hmt*32;
            double tx;
            if (shade == 1) { tx = (int) (rx/2.0) % 32; if (ra > 180) { tx = 31 - tx; }}  
            else            { tx = (int) (ry/2.0) % 32; if (ra > 90 && ra < 270) { tx = 31 - tx; }}
            for (y = 0; y < lineH; y++) {
                int pixel = ((int) ty * 32 + (int) tx) * 3 + (hmt * 32 * 32 * 3);
                int red   = (int) (Wall_Textures[pixel + 0] * shade);
                int green = (int) (Wall_Textures[pixel + 1] * shade);
                int blue  = (int) (Wall_Textures[pixel + 2] * shade);
                g.setStroke(new BasicStroke(FOV)); 
                setColor(g, red, green, blue); 
                g.draw(new Line2D.Double(r * FOV, y + lineOff, r * FOV, y + lineOff));
                ty += ty_step;
            }
            
            //---draw floors---
            for(y = lineOff + lineH; y < ScrenHeight; y++) {
                double dy = y - (ScrenHeight/2.0), deg = degToRad(ra), raFix = Math.cos(degToRad(FixAng(pa - ra)));
                tx = px/2 + Math.cos(deg) * 158 * ScrenHeight/320 * 32/dy/raFix;
                ty = py/2 - Math.sin(deg) * 158 * ScrenHeight/320 * 32/dy/raFix;
                mp = mapF[(int)(ty/32.0)*mapX+(int)(tx/32.0)] * 32 * 32;
                int pixel = (((int) (ty) & 31) * 32 + ((int) (tx) & 31)) * 3 + mp * 3;
                int red   = (int) (Floor_Textures[pixel + 0] * 0.7);
                int green = (int) (Floor_Textures[pixel + 1] * 0.7);
                int blue  = (int) (Floor_Textures[pixel + 2] * 0.7);
                g.setStroke(new BasicStroke(FOV)); 
                setColor(g, red, green, blue); 
                g.draw(new Line2D.Double(r * FOV, y, r * FOV, y));

                //---draw ceiling---
                mp = mapC[(int) (ty/32.0) * mapX + (int) (tx/32.0)] * 32 * 32;
            
                if (mp > 0) {
                    pixel = (((int)(ty) & 31) * 32 + ((int) (tx) & 31)) * 3 + mp * 3 - 3072;

                    red   = Ceiling_Textures[pixel + 0];
                    green = Ceiling_Textures[pixel + 1];
                    blue  = Ceiling_Textures[pixel + 2];
                 
                    g.setStroke(new BasicStroke(FOV)); 
                    setColor(g, red, green, blue); 
                    g.draw(new Line2D.Double(r * FOV, ScrenHeight - y, r * FOV, ScrenHeight - y)); 
                }
            }

            ra = FixAng(ra - 0.5); // go to next ray
        }
    }

    void drawSprites(Graphics2D g) {
        int x, y;
        //if(px < sprites[0].x + 30 && px >sprites[0].x-30 && py < sprites[0].y + 30 && py > sprites[0].y - 30){ sprites[0].state = 0;} //pick up key 	
        //if(px < sprites[3].x + 30 && px >sprites[3].x-30 && py < sprites[3].y + 30 && py > sprites[3].y - 30){ gameState = 4;} //enemy kills

        if (sprites[0].x > px) { sprites[0].x -= 1; }
        if (sprites[0].x < px) { sprites[0].x += 1; }


        double sx = sprites[0].x - px; //temp double variables
        double sy = sprites[0].y - py;
        double sz = sprites[0].z;

        double CS = Math.cos(degToRad(pa)), SN = Math.sin(degToRad(pa)); //rotate around origin
        double a = sy * CS + sx * SN; 
        double b = sx * CS - sy * SN; 
        sx = a; sy = b;

        sx = (sx * 108.0/sy) + (120/2); //convert to screen x,y
        sy = (sz * 108.0/sy) + ( 80/2);

        int scale = (int) (32 * 80/b);   //scale sprite based on distance
        if (scale < 0) { scale = 0; } if (scale > 120) { scale = 120; }  

        //texture
        double t_x = 0, t_y = 31, t_x_step = 31.5/(double) scale, t_y_step = 32.0/(double) scale;

        for (x = (int) (sx-scale/2); x < sx + scale/2; x++) {
            t_y = 31;
            for (y = 0; y < scale; y++) {
                if (sprites[0].state == 1 && x  > 0 && x < 120 && b < depth[x]) {
                    int pixel = ((int) t_y * 32 + (int) t_x) * 3 + (sprites[0].map * 32 * 32 * 3);
                    int red   = Sprite_Textures[pixel+0];
                    int green = Sprite_Textures[pixel+1];
                    int blue  = Sprite_Textures[pixel+2];
                    if (red != 255 && green != 0 && blue != 255) { //dont draw if purple
                        g.setStroke(new BasicStroke(FOV)); setColor(g, red, green, blue); g.draw(new Line2D.Double(x * FOV, sy * FOV - y * FOV, x * FOV, sy * FOV - y * FOV));
                    }
                    t_y -= t_y_step; if (t_y < 0) { t_y = 0; }
                }
            }
            t_x+=t_x_step;
        }
    }

    void drawSky(Graphics2D g) {
        int x, y;
        for (y = 0; y < 40; y++) {
            for (x = 0; x < 120; x++) {
                int xo = (int) pa * 2 - x; if (xo < 0) { xo += 120; } xo = xo % 120;

                int pixel = (y * 120 + xo) * 3;
                int red = Skybox[pixel + 0]; int green = Skybox[pixel + 1]; int blue = Skybox[pixel + 2];
    
                g.setStroke(new BasicStroke(FOV)); setColor(g, red, green, blue); g.draw(new Line2D.Double(x * FOV, y * FOV, x * FOV, y * FOV));
            }
        }
    }

    public void paintComponent(Graphics graphics) {
        final Graphics2D g = (Graphics2D) graphics.create();
        final int width = getSize().width;
        final int height = getSize().height;

        // Set Color
        setColor(g, 110, 104, 103);
        g.fillRect(0, 0, width, height);

        keyboard();
        //drawSky(g);
        drawRays2D(g);
        drawSprites(g);
    }

    public raycastC() {
        KeyListener listener = new KeyListener() {
            public void keyTyped(KeyEvent e) { // Only For Single Used
                // String keyPressed = Character.toString(e.getKeyChar());
                
                // Interact:
                /*if (keyPressed.equals("e")) {
                    int xo = 0; if (pdx < 0) { xo = -30; } else { xo = 30; }
                    int yo = 0; if (pdy < 0) { yo = -30; } else { yo = 30; }
                    int ipx_add_xo = (int) ((px + xo) / 64);
                    int ipy_add_yo = (int) ((py + yo) / 64);
                    if (mapW[ipy_add_yo * mapX + ipx_add_xo] == 4) { mapW[ipy_add_yo * mapX + ipx_add_xo] = 0; }

                }*/
            }

            public void keyPressed(KeyEvent e) {
                String keyPressed = KeyEvent.getKeyText(e.getKeyCode()).toLowerCase();
                
                for (int i = 0; i < keysPressed.length; i++) {
                    if (keysPressed[i].equals("")) {
                        boolean contains = false;
                        for (int j = 0; j < keysPressed.length; j++) { if (keysPressed[j].equals(keyPressed)) { contains = true; break; } }
                        if (!contains) { keysPressed[i] = keyPressed; break; }
                    } 
                }
             }

            public void keyReleased(KeyEvent e) {
                String keyPressed = KeyEvent.getKeyText(e.getKeyCode()).toLowerCase();

                for (int i = 0; i < keysPressed.length; i++) { if (keysPressed[i].equals(keyPressed)) { keysPressed[i] = ""; break; } }
            }
        };
        addKeyListener(listener);
        setFocusable(true);
    }

    static double fps;

    public static void main(String[] args) {
        final raycastC panel = new raycastC();
        final JFrame frame = new JFrame("Raycast");
        
        // Initalize Variables
        px = 150; py = 400; pa = 90; pdx = Math.cos(degToRad(pa)); pdy = -Math.sin(degToRad(pa));

        sprites = new sprite[4];
        for (int i = 0; i < sprites.length; i++) { sprites[i] = new sprite(); }

        sprites[0].createStprite(3, 1, 0, (int) (4.5 * 64), 6 * 64, 0);

        keysPressed = new String[10];
        for (int i = 0; i < keysPressed.length; i++) { keysPressed[i] = ""; }

        // Manual Texture Input:
        Wall_Textures = textureGetter("WallTextures");
        Floor_Textures = textureGetter("FloorTextures");
        Ceiling_Textures = textureGetter("RoofTextures");
        Sprite_Textures = textureGetter("SpriteTextures");
        Skybox = textureGetter("skybox");

        frame.setExtendedState(JFrame.MAXIMIZED_BOTH); frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); frame.getContentPane().add(panel, BorderLayout.CENTER); frame.setVisible(true);

        while (true) {
            panel.repaint();
            fps = 10; // Chnage This yourself smh
        }
    }
}
