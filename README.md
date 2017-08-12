# Dynamic Asset Loading with libGDX and GWT
This example project shows how you can break out your html5 export to enable a more dynamic loading experience for the player

Firstly this uses a build of libGDX from my fork

https://github.com/MonsterOfCookie/libgdx

You will need to check this out and build it - as per the [libGDX wiki](https://github.com/libgdx/libgdx/wiki/Building-libgdx-from-source)

Then you can just run the superDev task on this project to see it in action

`gradlew html:superDev`

The codeserver for Gwt is still on port 9876, however the static assets now load from port 9090 (this is configurable in the gradle file)

### What's going on?

There are many things happening here, firstly the we utilise GWT [entry points](http://www.gwtproject.org/doc/latest/DevGuideCodingBasicsClient.html), this allows for us to have
a smaller initial download - a bootstrap if you will. Once the bootstrap JS is downloaded it will allow us to have the page loaded and show a progress bar for the rest of the code. The reason for
this is that if you had to wait for large JS file to download before the browser has deemed the page as loaded, this is one way to allow feedback to player that something is going on.
Once that loads we can enable the canvas element and start the game!

In the case of the example it goes to the LoadingScreen class that uses an instance OnDemandAssetLoader which essentially
queues up assets for that require downloading to be downloaded, once downloaded they are loaded into memory.

This allows for you break the downloads up, perhaps you don't want to download all your assets at once, say you have an
"arcade" style app which has lots of mini-games in, you can then download those assets when the player wants to play that game

If you open the browser console and load the page your will see the downloading etc in action.

### Other Things
*This is using JDK 8+
*This is using GWT 2.8.1
*This is using Gradle 4.1, because of this the Jetty plugin as removed and deprecated so we switched over to using the built in SimpleHttpFileServer instead
*You can add assets to be downloaded during the EntryPoint phase, if required, this will of course increase the time on the first progress bar download.
*This, like most projects, has it's own caveats, the main one that springs to mind is that you need to explicitly list the assets to load that are otherwise
taken implicitly. For example loading a texture atlas, with the AssetManager class it uses the loader to pick up on the texture used, this needs to be explicitly
stated with the OnDemandAssetLoader