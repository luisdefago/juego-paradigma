import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Contador de rondas y partidas ganadas por cada jugador.
 */
public class Contador extends Actor
{
    private int ronda;
    private int ganadasJugador1;
    private int ganadasJugador2;

    // Constructor de la clase Contador
    public Contador() {
        this.ronda = 0;
        this.ganadasJugador1 = 0;
        this.ganadasJugador2 = 0;
        actualizarImagen();
    }

    public void act()
    {
        // Aquí puedes agregar lógica adicional si es necesario
    }

    // Incrementar el número de rondas
    public void aumentarRonda(){
        this.ronda++;
        actualizarImagen();
    }

    // Incrementar el número de partidas ganadas por el jugador 1
    public void aumentarGanadasJugador1(){
        this.ganadasJugador1++;
        actualizarImagen();
    }

    // Incrementar el número de partidas ganadas por el jugador 2
    public void aumentarGanadasJugador2(){
        this.ganadasJugador2++;
        actualizarImagen();
    }
    
    public int getGanadasJugador1(){
        return this.ganadasJugador1;
    }
    
    public int getGanadasJugador2(){
        return this.ganadasJugador2;
    }

    // Actualizar la imagen del contador con los valores actuales
    private void actualizarImagen() {
        // Crear una nueva imagen de tamaño 200x100 para mostrar los datos
        GreenfootImage image = new GreenfootImage(200, 100);
        image.setColor(Color.WHITE);
        image.fill();  // Rellenar el fondo con color blanco

        image.setColor(Color.BLACK);  // Establecer el color del texto en negro
        image.drawString("Ronda: " + ronda, 10, 25);
        image.drawString("Jugador 1: " + ganadasJugador1, 10, 50);
        image.drawString("Jugador 2: " + ganadasJugador2, 10, 75);

        setImage(image);  // Asignar la imagen actualizada al actor
    }
}

