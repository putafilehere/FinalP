import java.awt.*;
public class Rect extends GameObject {


    private Vec2 size;

    private Color color;

    public Rect(Vec2 size, Color color, Vec2 pos, double angle)
    {
        super(pos, angle);
        this.size = size;
        this.color = color;
    }

    @Override
    public void draw(Graphics g)
    {
        g.setColor(color);
        g.fillRect(getPos().getX(), getPos().getY(), size.getX(), size.getY());
    }

    @Override
    public Vec2 getSize()
    {
        return size;
    }

}
