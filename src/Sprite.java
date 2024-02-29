import javax.swing.*;
import java.awt.*;
public class Sprite extends GameObject{

    private String sprite;

    private Image ogImg;

    private Image scaledImg;

    public Sprite(Vec2 pos, String sprite, Vec2 size, double angle)
    {
        super(pos, angle, size);
        this.sprite = sprite;
        ImageIcon img = new ImageIcon(sprite);
        ogImg = img.getImage().getScaledInstance(size.getX(), size.getY(), Image.SCALE_DEFAULT);
        scaledImg = RotateHelper.getRotatedInstance(ogImg, angle);
    }

    public void setSprite(String sprite)
    {
        this.sprite = sprite;
    }

    public String getSprite()
    {
        return sprite;
    }

    //it makes a die.
    @Override
    public void draw(Graphics g)
    {
        scaledImg = RotateHelper.getRotatedInstance(ogImg, getAngle());
        g.drawImage(scaledImg, getPos().getX(), getPos().getY(), null);
    }
    //that was a lie
}
