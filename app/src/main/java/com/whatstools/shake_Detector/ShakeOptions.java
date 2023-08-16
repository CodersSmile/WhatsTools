package com.whatstools.shake_Detector;

public class ShakeOptions {
    private boolean background;
    private int interval;
    private float sensibility;
    private int shakeCounts;

    public ShakeOptions background(Boolean background) {
        this.background = background;
        return this;
    }

    public ShakeOptions shakeCount(int shakeCount) {
        this.shakeCounts = shakeCount;
        return this;
    }

    public ShakeOptions interval(int interval) {
        this.interval = interval;
        return this;
    }

    public ShakeOptions sensibility(float sensibility) {
        this.sensibility = sensibility;
        return this;
    }

    public boolean isBackground() {
        return this.background;
    }

    public void setBackground(boolean background) {
        this.background = background;
    }

    public int getShakeCounts() {
        return this.shakeCounts;
    }

    public void setShakeCounts(int shakeCounts) {
        this.shakeCounts = shakeCounts;
    }

    public int getInterval() {
        return this.interval;
    }

    public float getSensibility() {
        return this.sensibility;
    }
}
