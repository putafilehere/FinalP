public class Sprite extends GameObject{

    String sprite;

    Vec2 size;

    public Sprite(Vec2 pos, boolean grav, String sprite, Vec2 size)
    {
        super(pos, grav);
        this.sprite = sprite;
        this.size = size;
    }

    public void setSprite(String sprite)
    {
        this.sprite = sprite;
    }

    public void setSize(Vec2 size)
    {
        this.size = size;
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

    //that was a lie
}
