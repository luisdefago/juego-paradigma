import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class Bala extends Actor
{
    private int player;  // Variable para identificar qué jugador disparó la bala
    private boolean explosionVisible = false;  // Bandera para saber si la explosión está visible
    private int explosionTimer = 0;  // Temporizador para controlar cuánto tiempo mostrar la explosión

    public Bala(int player) {
        this.player = player;
        
        // Redimensionar la imagen de la bala
        GreenfootImage image = getImage();
        image.scale(image.getWidth() / 2, image.getHeight() / 2);  // Achicar la bala al 50%
        setImage(image);
    }

    public void act()
    {
        if (!explosionVisible) {
            // Mover la bala dependiendo del jugador
            if (player == 1) {
                setLocation(getX() + 5, getY());  // Mover hacia la derecha (jugador 1)
            } else if (player == 2) {
                setLocation(getX() - 5, getY());  // Mover hacia la izquierda (jugador 2)
            }

            // Eliminar la bala si llega al borde del mundo
            if (isAtEdge()) {
                getWorld().removeObject(this);
            }

            if (getWorld() == null) return;

            // Verificar colisiones
            verificarColisiones();
        } else {
            // Si la explosión está visible, incrementar el temporizador
            explosionTimer++;
            if (explosionTimer >= 4) {  // Mostrar la explosión durante 30 ciclos de act()
                getWorld().removeObject(this);  // Eliminar la bala después del tiempo de explosión
            }
        }
    }

    private void verificarColisiones() {
        // Verificar si toca un Bloque
        if (isTouching(Bloque.class)) {
            removeTouching(Bloque.class);
            setImage("explosion.png");  // Cambiar la imagen a la explosión
            explosionVisible = true;  // Marcar que la explosión está visible
            explosionTimer = 0;  // Reiniciar el temporizador de la explosión
        }

        if (getWorld() == null) return;

        // Verificar si toca otra Bala
        if (isTouching(Bala.class)) {
            setImage("explosion.png");  // Cambiar la imagen a la explosión
            explosionVisible = true;  // Marcar que la explosión está visible
            explosionTimer = 0;  // Reiniciar el temporizador de la explosión
        }

        if (getWorld() == null) return;

        // Verificar si toca una Nave
        if (isTouching(Nave.class)) {
            setImage("explosion.png");  // Cambiar la imagen a la explosión
            explosionVisible = true;  // Marcar que la explosión está visible
            explosionTimer = 0;  // Reiniciar el temporizador de la explosión
        }
    }
}
