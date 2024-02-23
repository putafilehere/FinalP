import java.awt.*;
public class Text extends GameObject {

    String text;
    public Text(Vec2 pos, String text)
    {
        super(pos);
        this.text = text;
    }

    @Override
    public void draw(Graphics g)
    {
        g.drawString(text, getPos().getX(), getPos().getY());
    }
}
