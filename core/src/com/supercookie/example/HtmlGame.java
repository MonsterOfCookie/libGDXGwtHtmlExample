package com.supercookie.example;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.OndemandAssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.supercookie.example.screens.LoadingScreen;

import static com.badlogic.gdx.graphics.Color.BLACK;

public class HtmlGame extends Game {

    public static final int VIEWPORT_WIDTH = 640;
    public static final int VIEWPORT_HEIGHT = 480;

    private final OndemandAssetManager ondemandAssetManager;
    private final AssetManager assetManager;

    private Stage stage;

    public HtmlGame(OndemandAssetManager ondemandAssetManager) {
        this.ondemandAssetManager = ondemandAssetManager;
        this.assetManager = ondemandAssetManager.getAssetManager();
    }

    @Override
    public void create() {
        stage = new Stage(new FitViewport(VIEWPORT_WIDTH, VIEWPORT_HEIGHT));
        setScreen(new LoadingScreen());
    }

    @Override
    public void setScreen(Screen screen) {
        stage.clear();
        super.setScreen(screen);
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        stage.getViewport().update(width, height);
    }

    @Override
    public void render() {
        clearScreen();
        super.render();
        stage.act(1 / 60f);
        stage.draw();
    }

    private void clearScreen() {
        Gdx.gl.glClearColor(BLACK.r, BLACK.g, BLACK.b, BLACK.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    public static HtmlGame getGame() {
        return (HtmlGame) Gdx.app.getApplicationListener();
    }

    public OndemandAssetManager getOndemandAssetManager() {
        return ondemandAssetManager;
    }

    public AssetManager getAssetManager() {
        return assetManager;
    }

    public Stage getStage() {
        return stage;
    }
}
