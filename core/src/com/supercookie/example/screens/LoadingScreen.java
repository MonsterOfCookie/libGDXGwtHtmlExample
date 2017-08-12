package com.supercookie.example.screens;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.OndemandAssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.supercookie.example.HtmlGame;

import static com.supercookie.example.HtmlGame.VIEWPORT_HEIGHT;
import static com.supercookie.example.HtmlGame.VIEWPORT_WIDTH;
import static com.supercookie.example.screens.GameScreen.LOGO;

public class LoadingScreen extends ScreenAdapter {

    private final HtmlGame game = HtmlGame.getGame();
    private final OndemandAssetManager ondemandAssetManager = game.getOndemandAssetManager();
    private final Stage stage = game.getStage();

    private ShapeRenderer shapeRenderer = new ShapeRenderer();

    @Override
    public void show() {
        ondemandAssetManager.load(LOGO, Texture.class);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        ondemandAssetManager.update();
        if (ondemandAssetManager.isComplete()) {
            game.setScreen(new GameScreen());
        }

        renderProgressBar();
    }

    private void renderProgressBar() {
        shapeRenderer.setProjectionMatrix(stage.getCamera().projection);
        shapeRenderer.setTransformMatrix(stage.getCamera().view);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.WHITE);
        shapeRenderer.rect(VIEWPORT_WIDTH / 2 - VIEWPORT_WIDTH / 4, VIEWPORT_HEIGHT / 2 - VIEWPORT_HEIGHT / 20, VIEWPORT_WIDTH / 2 * ondemandAssetManager.progress(), VIEWPORT_HEIGHT / 10);
        shapeRenderer.end();
    }

}
