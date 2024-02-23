import javax.swing.*;
import java.awt.*;
public class Sprite extends GameObject{

    String sprite;

    Vec2 size;

    Image scaledImg;

    public Sprite(Vec2 pos, String sprite, Vec2 size)
    {
        super(pos);
        this.sprite = sprite;
        this.size = size;
        ImageIcon img = new ImageIcon(sprite);
        scaledImg= img.getImage().getScaledInstance(size.getX(), size.getY(), Image.SCALE_DEFAULT);
    }

    public void setSprite(String sprite)
    {
        this.sprite = sprite;
    }

    public String getSprite()
    {
        return sprite;
    }

    public Vec2 getSize()
    {
        return size;
    }
    //it makes a die.
    @Override
    public void draw(Graphics g)
    {
        g.drawImage(scaledImg, getPos().getX(), getPos().getY(), null);
    }
    //that was a lie
}
