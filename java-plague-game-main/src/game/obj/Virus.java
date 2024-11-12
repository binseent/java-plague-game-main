package game.obj;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Path2D;
import javax.swing.ImageIcon;
import java.util.Random;

public class Virus extends HpRender {
    private float speed; // Add a speed variable

    public Virus() {
        super(new HP(100, 100));
        Image originalImage = new ImageIcon(getClass().getResource("/game/image/Virus.png")).getImage();
        this.image = resizeImage(originalImage, 50, 50);
        Path2D p = new Path2D.Double();
        p.moveTo(0, VIRUS_SIZE / 2);
        p.lineTo(40, 0); // Increase the hitbox size by adjusting the coordinates
        p.lineTo(VIRUS_SIZE + 40, 0); // Increase the hitbox size by adjusting the coordinates
        p.lineTo(VIRUS_SIZE + 40, VIRUS_SIZE); // Increase the hitbox size by adjusting the coordinates
        p.lineTo(10, VIRUS_SIZE); // Increase the hitbox size by adjusting the coordinates
        p.closePath(); // Close the path to create a closed shape
        v = new Area(p);

        // Generate random speed between 0.1 and 1.0
        Random rand = new Random();
        speed = 0.50f + rand.nextFloat() * 2f;
    }


    private Image resizeImage(Image originalImage, int width, int height) {
        return originalImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
    }

    public static final double VIRUS_SIZE = 40;
    private double x;
    private double y;
    
    private float angle = 0;
    private final Image image;
    private final Area v;

    public void changeLocation(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void update() {
        x += Math.cos(Math.toRadians(angle)) * speed;
        y += Math.sin(Math.toRadians(angle)) * speed;
    }

    public void changeAngle(float angle) {
        if (angle < 0) {
            angle = 359;
        } else if (angle > 359) {
            angle = 0;
        }
        this.angle = angle;
    }

    public void draw(Graphics2D g2) {
        AffineTransform oldTransform = g2.getTransform();
        g2.translate(x, y);
        AffineTransform tran = new AffineTransform();
        tran.rotate(Math.toRadians(angle + 45), VIRUS_SIZE / 2, VIRUS_SIZE / 2);
        g2.drawImage(image, tran, null);
        Shape shap = getShape();
        hpRender(g2, shap, y);
        g2.setTransform(oldTransform);

        //  Test
        // g2.setColor(new Color(36, 214, 63));
        // g2.draw(shap);
        // g2.draw(shap.getBounds2D());
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public float getAngle() {
        return angle;
    }

    public Area getShape() {
        AffineTransform afx = new AffineTransform();
        afx.translate(x, y);
        afx.rotate(Math.toRadians(angle), VIRUS_SIZE / 2, VIRUS_SIZE / 2);
        return new Area(afx.createTransformedShape(v));
    }

    public boolean check(int width, int height) {
        Rectangle size = getShape().getBounds();
        if (x <= -size.getWidth() || y < -size.getHeight() || x > width || y > height) {
            return false;
        } else {
            return true;
        }
    }
}


