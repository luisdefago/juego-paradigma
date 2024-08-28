import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class MyWorld extends World
{
    private boolean gameEnded = false;  // Flag para saber si el juego ha terminado
    private String nombre1;
    private String nombre2;
    private int cantidadRondas;
    private int rondasJugadas;
    
    private Contador contador;  // Contador para llevar el registro de rondas y partidas ganadas
    
    public MyWorld(String nombre1, String nombre2, int cantidadRondas)
    {    
        // Crear un mundo con un tamaño de 600x400 píxeles y un tamaño de celda de 1x1 píxeles.
        super(600, 400, 1);
        // Cargar la imagen de fondo
        GreenfootImage fondo = new GreenfootImage("./images/espacio.png");
        setBackground(fondo);  // Establecer la imagen como fondo
        this.nombre1 = nombre1;
        this.nombre2 = nombre2;
        this.cantidadRondas = cantidadRondas;
        this.rondasJugadas = 0;
        
        // Crear y añadir el contador al mundo
        contador = new Contador(this);
        addObject(contador, 350, 50);  // Posicionar el contador en el centro superior del mundo

        // Generar los bloques y naves
        iniciarNuevaRonda();
    }

    private void iniciarNuevaRonda() {
        // Resetear el estado del juego
        gameEnded = false;
        generateBlocks();

        // Crear la nave del jugador 1 a la izquierda con un pequeño espacio de los bloques
        Nave nave1 = new Nave(1);
        addObject(nave1, 90, getHeight() / 2);  // Posicionar la nave en el lado izquierdo

        // Crear la nave del jugador 2 a la derecha con un pequeño espacio de los bloques
        Nave nave2 = new Nave(2);
        addObject(nave2, getWidth() - 90, getHeight() / 2);  // Posicionar la nave en el lado derecho
    }

    private void generateBlocks() {
        int blockSize = 40;
        int numBlocks = 10;

        // Generar 10 bloques en el lado izquierdo
        for (int i = 0; i < numBlocks; i++) {
            Bloque bloque = new Bloque();
            addObject(bloque, blockSize / 2, blockSize / 2 + i * blockSize);  // Posicionar en el lado izquierdo
        }

        // Generar 10 bloques en el lado derecho
        for (int i = 0; i < numBlocks; i++) {
            Bloque bloque = new Bloque();
            addObject(bloque, getWidth() - blockSize / 2, blockSize / 2 + i * blockSize);  // Posicionar en el lado derecho
        }
    }

    public void act() {
        if (!gameEnded) {
            verificarEstadoDelJuego();
        }
    }
    
    private void verificarEstadoDelJuego() {
        boolean bloquesIzquierdaVacios = verificarBloquesVacios(0, getWidth() / 2 - 40);  // Verificar si los bloques en el lado izquierdo están vacíos
        boolean bloquesDerechaVacios = verificarBloquesVacios(getWidth() / 2 + 40, getWidth());  // Verificar si los bloques en el lado derecho están vacíos
        
        if (bloquesIzquierdaVacios) {
            contador.aumentarRonda();
            contador.aumentarGanadasJugador2();  // Jugador 2 gana la ronda
            rondasJugadas++;
            finalizarRonda();
        } else if (bloquesDerechaVacios) {
            contador.aumentarRonda();
            contador.aumentarGanadasJugador1();  // Jugador 1 gana la ronda
            rondasJugadas++;
            finalizarRonda();
        }
    }
    
    private boolean verificarBloquesVacios(int minX, int maxX) {
        // Verificar si hay bloques en el rango de x especificado
        for (int x = minX; x <= maxX; x += 40) {
            for (int y = 0; y < getHeight(); y += 40) {
                if (getObjectsAt(x, y, Bloque.class).size() > 0) {
                    return false;  // Hay bloques en esta posición
                }
            }
        }
        return true;  // Todos los bloques en esta posición han sido eliminados
    }
    
    private void finalizarRonda() {
        if (rondasJugadas >= cantidadRondas | contador.getGanadasJugador1() > (cantidadRondas/2) | contador.getGanadasJugador2() > (cantidadRondas/2)) {
            // Mostrar el ganador final cuando se completen todas las rondas
            if (contador.getGanadasJugador1() > contador.getGanadasJugador2()) {
                if(this.nombre1 != ""){
                    mostrarMensaje("Ganador: " + this.nombre1);
                } else {
                    mostrarMensaje("Ganador: jugador 1");
                }
            } else if (contador.getGanadasJugador2() > contador.getGanadasJugador1()) {
                if(this.nombre2 != ""){
                    mostrarMensaje("Ganador: " + this.nombre2);
                } else {
                    mostrarMensaje("Ganador: jugador 2");
                }
            } else {
                mostrarMensaje("Empate");
            }
            gameEnded = true;
        } else {
            // Iniciar una nueva ronda
            limpiarMundo();
            iniciarNuevaRonda();
        }
    }
    
    public int getCantidadRondas(){
        return this.cantidadRondas;
    }
    
    private void limpiarMundo() {
        // Eliminar todos los actores excepto el contador
        removeObjects(getObjects(Actor.class));
        addObject(contador, 350, 50);  // Recolocar el contador en el centro superior
    }
    
    private void mostrarMensaje(String mensaje) {
        GreenfootImage fondo = new GreenfootImage(getWidth(), getHeight());
        fondo.setColor(Color.BLACK);
        fondo.fill();
        
        GreenfootImage texto = new GreenfootImage(mensaje, 48, Color.WHITE, Color.BLACK);
        fondo.drawImage(texto, (getWidth() - texto.getWidth()) / 2, (getHeight() - texto.getHeight()) / 2);
        setBackground(fondo);
        
        limpiarMundo();
        // Detener todos los actores en el mundo
        for (Actor actor : getObjects(Actor.class)) {
            actor.setLocation(-1, -1);  // Esto es solo para que no se sigan moviendo, se puede mejorar
        }
    }
}
