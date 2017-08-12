package com.supercookie.example.desktop;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.OndemandAssetManager;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.supercookie.example.HtmlGame;

public class DesktopLauncher {
    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        new LwjglApplication(
                new HtmlGame(
                        new OndemandAssetManager(
                                new AssetManager(),
                                (fileName, type, listener) -> listener.onSuccess(fileName, type)
                        )
                ),
                config
        );
    }
}
