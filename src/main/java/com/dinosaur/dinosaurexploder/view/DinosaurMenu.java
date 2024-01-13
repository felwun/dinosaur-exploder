package com.dinosaur.dinosaurexploder.view;

import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.MenuType;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.ui.FontType;
import com.dinosaur.dinosaurexploder.model.GameConstants;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Button;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Screen;

import static com.almasb.fxgl.dsl.FXGL.getGameScene;
import static com.almasb.fxgl.dsl.FXGL.getPrimaryStage;

public class DinosaurMenu extends FXGLMenu {
    private static final double HEIGHTMUL = 0.47;

    public DinosaurMenu() {
        super(MenuType.MAIN_MENU);

        try {
            initWindowSize();
        }
        catch (Exception e) {
            System.out.println("Error: failed to initialize window size.");
            e.printStackTrace();
        }

        Media media = new Media(getClass().getResource(GameConstants.MAINMENU_SOUND).toExternalForm());
        MediaPlayer mainMenuSound = new MediaPlayer(media);
        mainMenuSound.play();
        mainMenuSound.setCycleCount(MediaPlayer.INDEFINITE);

        var bg = new Rectangle(getAppWidth(), getAppHeight(), Color.BLACK);

        var title = FXGL.getUIFactoryService().newText(GameConstants.GAME_NAME, Color.LIME, FontType.MONO, 35);
        var startButton = new Button("Start Game");
        var quitButton = new Button("Quit");

        startButton.setMinSize(200, 100);
        quitButton.setMinSize(200, 100);

        title.setTranslateY(100);
        title.setTranslateX(getAppWidth() / 2 - 175);

        startButton.setTranslateY(350);
        startButton.setTranslateX(getAppWidth() / 2 - 100);
        startButton.setStyle("-fx-font-size:20");

        quitButton.setTranslateY(490);
        quitButton.setTranslateX(getAppWidth() / 2 - 100);
        quitButton.setStyle("-fx-font-size:20");

        startButton.setOnAction(event -> {
            fireNewGame();
            mainMenuSound.stop();
        });
        quitButton.setOnAction(event -> fireExit());

        getContentRoot().getChildren().addAll(
                bg, title, startButton, quitButton
        );
    }

    /**
     * Summary :
     *      Initialises the window to 0.47 (default value, change HEIGHTMUL above if you want a differnt value) the height of the screen and maintains the aspect ratio defined in DinosaurGUI.java.
     *      To update the base resolution and aspect ratio of the game window, edit the WIDTH and HEIGHT variables in DinosaurGUI.java.
     *      Does not change the game resolution, it only scales the window.
     *      Also adds listeners to maintain the correct aspect ratio when the window is resized.
     */
    private void initWindowSize() {
        Rectangle2D bounds = Screen.getPrimary().getBounds();

        //set window height and scale the width accordingly.
        double newHeight = bounds.getHeight() * HEIGHTMUL;
        double aspectRatio = getPrimaryStage().getWidth()/getPrimaryStage().getHeight();

        getPrimaryStage().setHeight((int)newHeight);
        getPrimaryStage().setWidth((int)(newHeight * aspectRatio));
        getPrimaryStage().centerOnScreen();

        //listen for changes in window size and maintain the aspect ratio accordingly
        getPrimaryStage().heightProperty().addListener((obs, oldVal, newVal) -> {
            getPrimaryStage().setWidth((int)((double)newVal * aspectRatio));
        });

        getPrimaryStage().widthProperty().addListener((obs, oldVal, newVal) -> {
            getPrimaryStage().setWidth((int)(getPrimaryStage().getHeight() * aspectRatio));
        });
    }
}
