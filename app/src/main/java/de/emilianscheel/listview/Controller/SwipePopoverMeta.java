package de.emilianscheel.listview.Controller;

public class SwipePopoverMeta {

    int baseLayoutYPosition = 0;
    int baseLayoutXPosition = 0;
    int yPosition = 0;
    int xPosition = 0;
    int yFingerPosition = 0;
    int xFingerPosition = 0;
    int previousYFingerPosition = 0;
    int previousXFingerPosition = 0;
    int defaultViewHeight = -1;
    int defaultViewWidth = -1;

    boolean isClosing = false;
    boolean isScrollingUp = false;
    boolean isScrollingDown = false;

    public int getPreviousYFingerPosition() {
        return previousYFingerPosition;
    }

    public void setPreviousYFingerPosition(int previousYFingerPosition) {
        this.previousYFingerPosition = previousYFingerPosition;
    }

    public int getPreviousXFingerPosition() {
        return previousXFingerPosition;
    }

    public void setPreviousXFingerPosition(int previousXFingerPosition) {
        this.previousXFingerPosition = previousXFingerPosition;
    }

    public int getBaseLayoutXPosition() {
        return baseLayoutXPosition;
    }

    public void setBaseLayoutXPosition(int baseLayoutXPosition) {
        this.baseLayoutXPosition = baseLayoutXPosition;
    }

    public int getBaseLayoutYPosition() {
        return baseLayoutYPosition;
    }

    public void setBaseLayoutYPosition(int baseLayoutYPosition) {
        this.baseLayoutYPosition = baseLayoutYPosition;
    }

    public int getDefaultViewHeight() {
        return defaultViewHeight;
    }

    public void setDefaultViewHeight(int defaultViewHeight) {
        this.defaultViewHeight = defaultViewHeight;
    }

    public boolean isClosing() {
        return isClosing;
    }

    public boolean isNotClosing() {
        return !isClosing();
    }

    public void setClosing(boolean closing) {
        isClosing = closing;
    }

    public boolean isScrollingUp() {
        return isScrollingUp;
    }

    public boolean isNotScrollingUp() {
        return !isScrollingUp();
    }

    public void setScrollingUp(boolean scrollingUp) {
        isScrollingUp = scrollingUp;
    }

    public boolean isScrollingDown() {
        return isScrollingDown;
    }

    public boolean isNotScrollingDown() {
        return !isScrollingDown();
    }

    public void setScrollingDown(boolean scrollingDown) {
        isScrollingDown = scrollingDown;
    }

    public int getyPosition() {
        return yPosition;
    }

    public void setyPosition(int yPosition) {
        this.yPosition = yPosition;
    }

    public int getxPosition() {
        return xPosition;
    }

    public void setxPosition(int xPosition) {
        this.xPosition = xPosition;
    }

    public int getDefaultViewWidth() {
        return defaultViewWidth;
    }

    public void setDefaultViewWidth(int defaultViewWidth) {
        this.defaultViewWidth = defaultViewWidth;
    }

    public int getyFingerPosition() {
        return yFingerPosition;
    }

    public void setyFingerPosition(int yFingerPosition) {
        this.yFingerPosition = yFingerPosition;
    }

    public int getxFingerPosition() {
        return xFingerPosition;
    }

    public void setxFingerPosition(int xFingerPosition) {
        this.xFingerPosition = xFingerPosition;
    }
}
