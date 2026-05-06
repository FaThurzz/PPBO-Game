package main.java.com.Games.engine;

import main.java.com.Games.entity.Player;
import main.java.com.Games.world.Camera;
import main.java.com.Games.world.TileMap;
import javax.swing.JPanel;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {

    public static final int TILE_SIZE    = 16;
    public static final int SCALE        = 3;
    public static final int SCREEN_COLS  = 16;
    public static final int SCREEN_ROWS  = 12;
    public static final int SCREEN_WIDTH  = TILE_SIZE * SCALE * SCREEN_COLS;
    public static final int SCREEN_HEIGHT = TILE_SIZE * SCALE * SCREEN_ROWS;
    private static final int TARGET_FPS  = 60;

    private Thread gameThread;

    // Sistem
    private final KeyHandler       keyHandler = new KeyHandler();
    private final GameStateManager gsm        = new GameStateManager();
    private final TileMap          tileMap    = new TileMap();
    private final Camera           camera     = new Camera();
    private final Player           player;

    // HUD sederhana
    private int fps = 0;
    private int fpsCounter = 0;
    private long fpsTimer = System.nanoTime();

    public GamePanel() {
        player = new Player(keyHandler, tileMap);

        setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        setBackground(Color.BLACK);
        setDoubleBuffered(true);
        addKeyListener(keyHandler);
        setFocusable(true);

        gsm.setState(GameStateManager.GameState.PLAYING);
    }

    public void startGameLoop() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        final double nsPerFrame = 1_000_000_000.0 / TARGET_FPS;
        long lastTime = System.nanoTime();
        double delta = 0;

        while (gameThread != null) {
            long now = System.nanoTime();
            delta += (now - lastTime) / nsPerFrame;
            lastTime = now;

            if (delta >= 1) {
                update();
                repaint();
                delta--;

                // Hitung FPS
                fpsCounter++;
                if (now - fpsTimer >= 1_000_000_000L) {
                    fps = fpsCounter;
                    fpsCounter = 0;
                    fpsTimer = now;
                }
            }
        }
    }

    private void update() {
        if (gsm.is(GameStateManager.GameState.PLAYING)) {
            player.update();
            camera.follow(player, tileMap);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        g2.setRenderingHint(
                RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR
        );

        // Render world
        tileMap.render(g2, camera);

        // Render player
        player.render(g2, camera.x, camera.y);

        // HUD — HP bar
        g2.setColor(new Color(0, 0, 0, 120));
        g2.fillRoundRect(10, 10, 104, 14, 6, 6);
        g2.setColor(new Color(220, 50, 50));
        int hpW = (int)(100.0 * player.hp / player.maxHp);
        g2.fillRoundRect(12, 12, hpW, 10, 4, 4);
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Courier New", Font.BOLD, 9));
        g2.drawString("HP " + player.hp + "/" + player.maxHp, 14, 21);

        // HUD — uang & level
        g2.setColor(new Color(0, 0, 0, 120));
        g2.fillRoundRect(10, 28, 104, 12, 6, 6);
        g2.setColor(new Color(255, 220, 60));
        g2.setFont(new Font("Courier New", Font.PLAIN, 9));
        g2.drawString("Lv." + player.level + "  $" + player.money, 14, 38);

        // FPS counter (pojok kanan atas)
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Courier New", Font.PLAIN, 9));
        g2.drawString("FPS: " + fps, SCREEN_WIDTH - 55, 14);

        g2.dispose();
    }
}