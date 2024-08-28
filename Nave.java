import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class Nave extends Actor
{
    private int player;  // Variable para identificar si es jugador 1 o 2
    private long lastShotTime;  // Tiempo del último disparo
    private long lastSpecialShotTime;  // Tiempo del último disparo especial
    private boolean inmovilizada = false;  // Bandera para saber si la nave está inmovilizada
    private int tiempoInmovilizada = 0;  // Tiempo que la nave lleva inmovilizada
    private static final int SPECIAL_SHOT_COOLDOWN = 7000;  // Cooldown de 3 segundos para el disparo especial

    public Nave(int player) {
        this.player = player;

        // Redimensionar la imagen de la nave a un tamaño más pequeño
        // GreenfootImage image = getImage();
        // image.scale(image.getWidth() / 3, image.getHeight() / 3);  // Achicar la nave más (25% del tamaño original)
        GreenfootImage image = new GreenfootImage("nave2.png");
        image.scale(60, 60);
        setImage(image);

        lastShotTime = System.currentTimeMillis();  // Inicializar el tiempo del último disparo

        if (player == 1) {
            setRotation(90);  // No rotada (jugador 1)
        } else if (player == 2) {
            setRotation(270);  // Girada 180 grados (jugador 2)
        }
    }
    
    public int getPlayer() {
        return player;
    }

    public void act()
    {
        if (!inmovilizada){
            // Mover la nave solo si no está inmovilizada
            movernave();
        } else{
            // Contar el tiempo de inmovilización
            tiempoInmovilizada++;
            if (tiempoInmovilizada >= 100) {  // Ejemplo: inmovilizada por 100 ciclos
                inmovilizada = false;
                tiempoInmovilizada = 0;
            }
        }
    }
    // Lógica de movimiento según el jugador
    private void movernave(){
        if (player == 1) {
            if (Greenfoot.isKeyDown("w")) {
                setLocation(getX(), getY() - 5);
            }
            if (Greenfoot.isKeyDown("s")) {
                setLocation(getX(), getY() + 5);
            }

            // Disparar una bala con 'space' y ver si ha pasado el tiempo de espera
            if (Greenfoot.isKeyDown("space") && canShoot()) {
                dispararBala();
            }

              // Disparar una munición especial con 'e' y ver si ha pasado el tiempo de espera
            if (Greenfoot.isKeyDown("e") && canShootSpecial()) {
                dispararMunicionEspecial();
            }
        } else if (player == 2) {
            if (Greenfoot.isKeyDown("up")) {
                setLocation(getX(), getY() - 5);
            }
            if (Greenfoot.isKeyDown("down")) {
                setLocation(getX(), getY() + 5);
            }

            // Disparar una bala con 'enter' y ver si ha pasado el tiempo de espera
            if (Greenfoot.isKeyDown("enter") && canShoot()) {
                dispararBala();
            }
            // Disparar una munición especial con 'shift' y ver si ha pasado el tiempo de espera
            if (Greenfoot.isKeyDown("p") && canShootSpecial()) {
                dispararMunicionEspecial();
            }
        }
    }

    private boolean canShoot() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastShotTime >= 300) {  // Esperar 300 milisegundos (0.3 segundos)
            lastShotTime = currentTime;  // Actualizar el tiempo del último disparo
            return true;
        }
        return false;
    }
    
    private boolean canShootSpecial() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastSpecialShotTime >= SPECIAL_SHOT_COOLDOWN) {  // Esperar 3 segundos para disparar la munición especial
            lastSpecialShotTime = currentTime;  // Actualizar el tiempo del último disparo especial
            return true;
        }
        return false;
    }

    private void dispararBala() {
        Bala bala = new Bala(player);  // Crear una nueva bala según el jugador
        if (player == 1) {
            getWorld().addObject(bala, getX() + 95, getY());  // Posicionar la bala frente a la nave del jugador 1
        } else if (player == 2) {
            getWorld().addObject(bala, getX() - 95, getY());  // Posicionar la bala frente a la nave del jugador 2
        }
    }
    
    private void dispararMunicionEspecial() {
        MunicionEspecial municionEspecial = new MunicionEspecial(player);  // Crear una nueva munición especial según el jugador
        if (player == 1) {
            getWorld().addObject(municionEspecial, getX() + 95, getY());  // Posicionar la munición especial frente a la nave del jugador 1
        } else if (player == 2) {
            getWorld().addObject(municionEspecial, getX() - 95, getY());  // Posicionar la munición especial frente a la nave del jugador 2
        }
    }

    public void inmovilizar() {
        inmovilizada = true;
        tiempoInmovilizada = 0;  // Reinicia el contador
    }
}
