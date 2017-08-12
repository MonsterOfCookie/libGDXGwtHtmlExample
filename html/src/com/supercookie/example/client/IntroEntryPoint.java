package com.supercookie.example.client;

import com.badlogic.gdx.backends.gwt.SuperDevModeIndicator;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.RunAsyncCallback;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style;
import com.google.gwt.user.client.Window;
import com.google.gwt.xhr.client.XMLHttpRequest;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class IntroEntryPoint implements EntryPoint {

    private static final LinkedList<String> toPreload = new LinkedList<String>();

    static Map<String, Float> loadingProgress = new HashMap<String, Float>();
    static int toLoad = 0;
    static int loaded = 0;

    @Override
    public void onModuleLoad() {

        SuperDevModeIndicator superDevModeIndicator = GWT.create(SuperDevModeIndicator.class);
        GWT.log("In Super Dev mode ? " + superDevModeIndicator.isSuperDevMode());
        if (!superDevModeIndicator.isSuperDevMode()) {
            toPreload.add(getUrl(1));
            toLoad = toPreload.size();

            GWT.log("need to load " + toLoad);

            while (!toPreload.isEmpty()) {
                load(toPreload.remove(0));
            }
        } else {
            GWT.log("Launching the game");
            HtmlLauncher htmlLauncher = new HtmlLauncher();
            htmlLauncher.onModuleLoad();
        }
    }

    private String getUrl(int fragment) {
        return GWT.getModuleBaseURL() + "deferredjs/"
                + GWT.getPermutationStrongName() + "/" + fragment + ".cache.js";
    }


    public void handleSideLoadProgress(String path, float progress) {
        Element element = Document.get().getElementById("loading-meter");
        float overall = 0;

        loadingProgress.put(path, progress);

        for (Float f : loadingProgress.values()) {
            overall += ((1 / (float) toLoad) * f) * 100;
        }

        element.getStyle().setWidth(overall, Style.Unit.PCT);
    }

    public void handleSideLoadComplete(String path) {
        GWT.log(toLoad + " " + loaded + " " + path);
        loaded++;
        if (loaded == toLoad) {
            GWT.runAsync(new RunAsyncCallback() {
                public void onFailure(Throwable caught) {
                    Window.alert("Download failed");
                }

                public void onSuccess() {
                    try {
                        GWT.log("Launching the game");
                        HtmlLauncher htmlLauncher = new HtmlLauncher();
                        htmlLauncher.onModuleLoad();
                    } catch (Throwable t) {
                        Window.alert(t.getMessage());
                    }
                }
            });
        }
    }

    public void load(final String url) {
        XMLHttpRequest request = XMLHttpRequest.create();
        request.setOnReadyStateChange(xhr -> {
            if (xhr.getReadyState() == XMLHttpRequest.DONE) {
                if (xhr.getStatus() == 200) {
                    handleSideLoadComplete(url);
                }
            }
        });
        setOnProgress(url, request, this);
        request.open("GET", url);
        request.setResponseType(XMLHttpRequest.ResponseType.ArrayBuffer);
        request.send();
    }

    private native static void setOnProgress(String path, XMLHttpRequest req, IntroEntryPoint point) /*-{
        req.onprogress = $entry(function(evt) {
		    point.@com.supercookie.example.client.IntroEntryPoint::handleSideLoadProgress(Ljava/lang/String;F)(path, evt.loaded / evt.total);
		});
	}-*/;

}
