package com.example.temp_project;

import static com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection.DEFAULT_MAX_DURATION_FOR_QUALITY_DECREASE_MS;
import static com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection.DEFAULT_MIN_DURATION_FOR_QUALITY_INCREASE_MS;
import static com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection.DEFAULT_MIN_DURATION_TO_RETAIN_AFTER_DISCARD_MS;

import android.content.Context;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceView;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.MediaMetadata;
import com.google.android.exoplayer2.PlaybackException;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.source.DefaultMediaSourceFactory;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.util.MimeTypes;

import org.jetbrains.annotations.NotNull;

import io.flutter.plugin.common.MethodChannel;
import io.flutter.view.TextureRegistry;

public class ExoPlayerFlutter {

    ExoPlayer player;
    PlayerView playerView;
    DefaultTrackSelector defaultTrackSelector;
    MethodChannel channel;
    Context context;
    Surface surface;
    TextureRegistry.SurfaceTextureEntry surfaceTextureEntry;

    ExoPlayerFlutter(@NotNull Context context, @NotNull MethodChannel channel, TextureRegistry textureRegistry, MethodChannel.Result result) {
        this.channel = channel;
        this.context = context;
        playerView = new PlayerView(context);
        surfaceTextureEntry = textureRegistry.createSurfaceTexture();
        initializePlayer(context);
        play(null, result);
    }


    private void initializePlayer(Context context) {
        Log.d("Plugging", "initializePlayer: initializePlayer");
        /*TrackSelection.Factory*/
        AdaptiveTrackSelection.Factory adaptiveTrackSelection = new AdaptiveTrackSelection.Factory(DEFAULT_MIN_DURATION_FOR_QUALITY_INCREASE_MS,
                DEFAULT_MAX_DURATION_FOR_QUALITY_DECREASE_MS, DEFAULT_MIN_DURATION_TO_RETAIN_AFTER_DISCARD_MS, 2.3f);
        //if (AppConfig.getInstance().isExoPlayerDefaultConfig()) {
        defaultTrackSelector = new DefaultTrackSelector(context, adaptiveTrackSelection);

//        player = new ExoPlayer.Builder(context)
//                .setTrackSelector(defaultTrackSelector)
//                .setMediaSourceFactory(new DefaultMediaSourceFactory(DownloadTracker.getDataSourceFactory(context, userToken)))
//                .build();
//        playerView = new StyledPlayerView(context);
//        DownloadTracker.getDownloadManager(context).addListener(this);
//        makeStreamOption();
//        setQualities();
    }

    public static final String MIME_TYPE_DASH = MimeTypes.APPLICATION_MPD;


    private void play(MediaItem mediaItem, MethodChannel.Result result){
        printD("thread "+Thread.currentThread().getName());
        player = new ExoPlayer.Builder(context)
                .setTrackSelector(defaultTrackSelector)
//                .setMediaSourceFactory(new DefaultMediaSourceFactory(DownloadTracker.getDataSourceFactory(context, userToken)))
                .build();
        MediaItem item = new MediaItem.Builder()
                .setUri(
                        "https://storage.googleapis.com/wvmedia/cenc/h264/tears/tears.mpd")
//                        "https://storage.googleapis.com/wvmedia/clear/h264/tears/tears_uhd.mpd")
                .setMediaMetadata(
                        new MediaMetadata.Builder().setTitle("Widevine DASH cenc: Tears").build())
                .setMimeType(MIME_TYPE_DASH)
                .setDrmConfiguration(
                        new MediaItem.DrmConfiguration.Builder(C.WIDEVINE_UUID)
                                .setLicenseUri("https://proxy.uat.widevine.com/proxy?provider=widevine_test")
                                .build())
                .build();

        surface = new Surface(surfaceTextureEntry.surfaceTexture());
        player.setVideoSurface(surface);

        player.setMediaItem(item);
        player.prepare();
        player.setPlayWhenReady(true);
        player.play();
        player.addListener(new Player.Listener() {
            @Override
            public void onPlayerError(PlaybackException error) {
                printD("onPlayerError: " + error.getMessage());
                Player.Listener.super.onPlayerError(error);
            }

            @Override
            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                Player.Listener.super.onPlayerStateChanged(playWhenReady, playbackState);
                printD("onPlayerError: " + playbackState);
            }
        });

        long textureId = surfaceTextureEntry.id();

        result.success(textureId);
    }

    private void printD(String message){
        Log.d("Plugin: ", message);
    }
}
