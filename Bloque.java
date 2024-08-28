import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class Bloque extends Actor
{
    public Bloque() {
        // Establecer tamaño de la imagen del bloque
        GreenfootImage image = new GreenfootImage("lunovich.png");
        image.fillRect(0, 0, 0, 0);
        setImage(image);
        image.scale(50 , 50);
    }

    public void act()
    {
    }
}
