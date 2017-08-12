package com.supercookie.example.client;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.ApplicationLogger;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.OndemandAssetManager;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;
import com.badlogic.gdx.backends.gwt.SuperDevModeIndicator;
import com.badlogic.gdx.backends.gwt.preloader.OnDemandAssetLoader;
import com.badlogic.gdx.backends.gwt.preloader.PreloadedAssetManager;
import com.badlogic.gdx.backends.gwt.preloader.Preloader;
import com.badlogic.gdx.math.MathUtils;
import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.CanvasElement;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Style;
import com.supercookie.example.HtmlGame;

import static com.supercookie.example.HtmlGame.VIEWPORT_HEIGHT;
import static com.supercookie.example.HtmlGame.VIEWPORT_WIDTH;

public class HtmlLauncher extends GwtApplication {

        private static final String PRELOADER_ID = "initial-preloader";

        private OnDemandAssetLoader onDemandAssetLoader;

        @Override
        public GwtApplicationConfiguration getConfig() {
                GwtApplicationConfiguration configuration = new GwtApplicationConfiguration(VIEWPORT_WIDTH, VIEWPORT_HEIGHT);
                configuration.canvasId = "myCanvas";
                configuration.autoMuteOnPause = true;
                return configuration;
        }

        @Override
        public Preloader createPreloader() {
                return onDemandAssetLoader;
        }

        @Override
        public PreloadedAssetManager getPreloadedAssetManager() {
                return onDemandAssetLoader.getPreloadedAssetManager();
        }

        @Override
        public ApplicationListener createApplicationListener() {
                SuperDevModeIndicator superDevModeIndicator = GWT.create(SuperDevModeIndicator.class);
                setLogLevel(superDevModeIndicator.isSuperDevMode() ? LOG_DEBUG : LOG_NONE);
                HtmlGame game = new HtmlGame(
                        new OndemandAssetManager(
                                new AssetManager(),
                                (fileName, type, listener) -> onDemandAssetLoader.load(new AssetDescriptor(fileName, type), listener)
                        ));


                onDemandAssetLoader = new OnDemandAssetLoader(getPreloaderBaseURL());

                Document.get().getElementById(PRELOADER_ID).removeFromParent();
                Document.get().getElementById("canvasDiv").getStyle().setDisplay(Style.Display.INLINE);

                return game;
        }

        @Override
        public void setApplicationLogger(ApplicationLogger applicationLogger) {

        }

        @Override
        public ApplicationLogger getApplicationLogger() {
                return null;
        }
}