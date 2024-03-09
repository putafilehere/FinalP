import java.awt.*;
public class Rect extends GameObject {

    private Color color;

    public Rect(Vec2 size, Color color, Vec2 pos, double angle, boolean isStatic)
    {
        super(pos, angle, size, isStatic);
        this.color = color;
    }

    @Override
    public void draw(Graphics g)
    {
        g.setColor(color);
        g.fillRect(getPos().getX(), getPos().getY(), getSize().getX(), getSize().getY());
    }

}
