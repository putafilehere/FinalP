import java.awt.*;
public class Rect extends GameObject {

    private Color color;

    public Rect(Vec2 pos, Vec2 size, Color color, double angle, boolean isStatic)
    {
        super(pos, angle, size, isStatic);
        this.color = color;
    }

    @Override
    public void draw(Graphics g)
    {
        g.setColor(color);
        g.fillRect((int)(getPos().getX()), (int)(getPos().getY()), (int)(getSize().getX()), (int)(getSize().getY()));
    }

    public Color getColor()
    {
        return color;
    }

}
