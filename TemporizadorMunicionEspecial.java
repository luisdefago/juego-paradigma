import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class TemporizadorMunicionEspecial extends Actor {
    private int player;
    private int time; // Tiempo transcurrido en milisegundos
    private int cooldown = 7000; // Cooldown de 7 segundos (7000 ms)
    private int squareSize = 15; // Tamaño de cada cuadrado
    private int squaresToShow = 7; // Número de cuadrados que se deben mostrar
    private long lastUpdateTime;

    public TemporizadorMunicionEspecial(int player) {
        this.player = player;
        this.time = 0;
        this.lastUpdateTime = System.currentTimeMillis();
        actualizarImagen();
    }

    public void act() {
        // Calcular el tiempo transcurrido desde la última actualización
        long currentTime = System.currentTimeMillis();
        int elapsedTime = (int) (currentTime - lastUpdateTime);

        if (elapsedTime >= 1000 && squaresToShow < 7) {
            time += elapsedTime;
            squaresToShow++;
            lastUpdateTime = currentTime; // Actualizar el último tiempo de actualización
            actualizarImagen();
        }
    }

    private void actualizarImagen() {
        GreenfootImage image = new GreenfootImage(105, 15);
        image.setColor(Color.WHITE); // Fondo blanco
        image.fillRect(0, 0, 105, 15); // Dibujar rectángulo blanco

        image.setColor(Color.BLACK); // Borde negro
        image.drawRect(0, 0, 105, 15); // Dibujar borde

        // Dibujar cuadrados amarillos de forma horizontal
        for (int i = 0; i < squaresToShow; i++) {
            image.setColor(Color.YELLOW);
            image.fillRect(15 * i, 0, squareSize, squareSize);
            image.setColor(Color.BLACK); // Borde negro alrededor de cada cuadrado
            image.drawRect(15 * i, 0, squareSize, squareSize);
        }

        setImage(image);
    }

    public boolean canShootSpecial() {
        return squaresToShow == 7;
    }

    public void resetCooldown() {
        squaresToShow = 0;
        time = 0;
        lastUpdateTime = System.currentTimeMillis(); // Reiniciar el tiempo de actualización
        actualizarImagen();
    }
}


