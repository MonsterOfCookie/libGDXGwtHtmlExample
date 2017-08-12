package com.supercookie.example.screens;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Align;
import com.supercookie.example.HtmlGame;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

public class GameScreen extends ScreenAdapter {

    public static final String LOGO = "logo.png";

    private final HtmlGame game = HtmlGame.getGame();
    private final AssetManager assetManager = game.getAssetManager();
    private final Stage stage = game.getStage();

    @Override
    public void show() {
        super.show();
        Image image = new Image(assetManager.get(LOGO, Texture.class));
        image.setPosition(HtmlGame.VIEWPORT_WIDTH / 2, HtmlGame.VIEWPORT_HEIGHT / 2, Align.center);
        image.setOrigin(Align.center);
        image.setScale(0.5f);
        image.addAction(forever(sequence(scaleTo(1f, 1f, 0.5f), scaleTo(0.5f, 0.5f, 0.5f))));
        stage.addActor(image);
    }
}
