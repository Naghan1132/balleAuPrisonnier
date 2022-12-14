package fr.icom.info.m1.balleauprisonnier_mvn.Model;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class Sprite extends ImageView {
    private final Rectangle2D[] walkClips;
    private final Rectangle2D[] shootClips;
    private final Timeline walkTimeline;
    private final IntegerProperty frameCounter = new SimpleIntegerProperty(0);
    private final Timeline shootTimeline;
    public boolean isRunning;
    public double hauteurImage;
    public double tailleImage;
    private final int numCells;
    private final int numCellsWalk;
    private final int numCellsShoot;
    private Timeline timeline;


    public Sprite(Image animationImage, int numCells, int numRows, Duration frameTime, String side) {
        this.numCells = numCells;

        double cellWidth = 64;//animationImage.getWidth() / numCells; //64x64
        double cellHeight = 64;//animationImage.getHeight() / numRows;
        this.hauteurImage = animationImage.getHeight();
        this.tailleImage = animationImage.getWidth();

        numCellsWalk = 9;

        int lineNumber = 8;
        if (side == "top") {
            lineNumber += 2;
        }

        walkClips = new Rectangle2D[numCellsWalk];
        for (int i = 0; i < numCellsWalk; i++) {
            walkClips[i] = new Rectangle2D(
                    i * cellWidth, cellHeight * lineNumber,
                    cellWidth, cellHeight
            );
        }

        setImage(animationImage);
        setViewport(walkClips[0]);

        walkTimeline = new Timeline(
                new KeyFrame(frameTime, event -> {
                    frameCounter.set((frameCounter.get() + 1) % numCellsWalk);
                    setViewport(walkClips[frameCounter.get()]);
                })
        );

        numCellsShoot = 13;
        lineNumber += 8;

        shootClips = new Rectangle2D[numCellsShoot];
        for (int i = 0; i < numCellsShoot; i++) {
            shootClips[i] = new Rectangle2D(
                    i * cellWidth, cellHeight * lineNumber,
                    cellWidth, cellHeight
            );
        }

        shootTimeline = new Timeline(
                new KeyFrame(frameTime, event -> {
                    frameCounter.set((frameCounter.get() + 1) % numCellsShoot);
                    setViewport(shootClips[frameCounter.get()]);
                }));

        timeline = walkTimeline;
        isRunning = false;
    }

    public void playContinuously() {
        isRunning = true;
        frameCounter.set(0);
        timeline = walkTimeline;
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.stop();
        timeline.playFromStart();
    }

    public void playShoot() {
        frameCounter.set(0);
        timeline.stop();
        timeline = shootTimeline;
        timeline.setCycleCount(numCellsShoot);
        timeline.setOnFinished(e -> playContinuously());
        timeline.playFromStart();
    }

    public void stop() {
        frameCounter.set(0);
        setViewport(walkClips[frameCounter.get()]);
        walkTimeline.stop();
    }

    public double getHauteurImage() {
        return hauteurImage;
    }

    public double getTailleImage() {
        return tailleImage;
    }

    public void removeImage() {
        setImage(null);
        setViewport(null);
    }
}
