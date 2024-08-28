import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class Nave extends Actor
{
    private int player;  // Variable para identificar si es jugador 1 o 2
    private long lastShotTime;  // Tiempo del último disparo
    private long lastSpecialShotTime;  // Tiempo del último disparo especial
    private boolean inmovilizada = false;  // Bandera para saber si la nave está inmovilizada
    private int tiempoInmovilizada = 0;  // Tiempo que la nave lleva inmovilizada
    private static final int SPECIAL_SHOT_COOLDOWN = 7000;  // Cooldown de 7 segundos para el disparo especial
    private TemporizadorMunicionEspecial temporizador;

    public Nave(int player) {
        this.player = player;

        // Redimensionar la imagen de la nave a un tamaño más pequeño
        GreenfootImage image = new GreenfootImage("nave2.png");
        image.scale(60, 60);
        setImage(image);

        lastShotTime = System.currentTimeMillis();  // Inicializar el tiempo del último disparo
        
        // Crear el temporizador, pero no lo añadimos aún al mundo
        temporizador = new TemporizadorMunicionEspecial(player);
        
        // Configurar la rotación de la nave según el jugador
        if (player == 1) {
            setRotation(90);
        } else if (player == 2) {
            setRotation(270);
        }
    }
    
    @Override
    protected void addedToWorld(World world) {
        super.addedToWorld(world);
        
        // Añadir el temporizador al mundo después de que la nave haya sido añadida
    if (player == 1) {
        getWorld().addObject(temporizador, getWorld().getWidth() / 2 - 150, 20);
    } else if (player == 2) {
        getWorld().addObject(temporizador, getWorld().getWidth() / 2 + 150, 20);
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
        } else {
            // Contar el tiempo de inmovilización
            tiempoInmovilizada++;
            if (tiempoInmovilizada >= 100) {  // Ejemplo: inmovilizada por 100 ciclos
                inmovilizada = false;
                tiempoInmovilizada = 0;
            }
        }
    }

    private void movernave() {
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
                temporizador.resetCooldown();
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
            // Disparar una munición especial con 'p' y ver si ha pasado el tiempo de espera
            if (Greenfoot.isKeyDown("p") && canShootSpecial()) {
                dispararMunicionEspecial();
                temporizador.resetCooldown();
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
        if (currentTime - lastSpecialShotTime >= SPECIAL_SHOT_COOLDOWN) {  // Esperar 7 segundos para disparar la munición especial
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

