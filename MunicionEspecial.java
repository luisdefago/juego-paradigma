import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Bombanaos here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class MunicionEspecial extends Actor
{
    private int velocidad = 5;  // Velocidad de la munición especial
    private int player;  // Para identificar quién disparó la munición

    public MunicionEspecial(int player) {
        this.player = player;
        GreenfootImage image;
        if (player == 1) {
            image = new GreenfootImage("manaos derecha.png");  // Imagen para el jugador 1
        } else {
            image = new GreenfootImage("manaos derecha.png");  // Imagen para el jugador 2
        }
        image.scale(85, 69);  // Cambia estos valores para ajustar el tamaño
        setImage(image);
        if (player == 1) {
            setRotation(0);  // Apunta hacia la derecha
        } else {
            setRotation(180);  // Apunta hacia la izquierda
        }
    }
    
    public void act() {
        // Mover la munición especial
        move(velocidad);
        
        if (isAtEdge()) {
            getWorld().removeObject(this);
        }
        
        if (getWorld() == null) return;
        
        // Verificar si la munición especial ha tocado una nave
        Nave nave = (Nave) getOneIntersectingObject(Nave.class);
        if (nave != null && nave.getPlayer() != this.player) {
            // Inmovilizar la nave
            nave.inmovilizar();
            // Mostrar efecto de explosión
            mostrarExplosion();
        }
    }
    
    private void mostrarExplosion() {
        GreenfootImage explosion = new GreenfootImage("explosion_manaos_sin_fondo.png");  // Reemplaza con el nombre de la imagen de la explosión
        setImage(explosion);
        Greenfoot.delay(10);  // Pausa para mostrar la explosión
         // Asegurarse de que la munición todavía está en el mundo antes de eliminarla
        if (getWorld() != null) {
            getWorld().removeObject(this);  // Elimina la munición después de mostrar la explosión
    }
}
}