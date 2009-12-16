package gwtJuego.client;

import com.google.gwt.core.client.Duration;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * An animation that moves a widget, which must be a child of an AbsolutePanel, from its initial position
 * to a specified final position.
 *
 * @author amoffat Alex Moffat
 */
public class MoveWidgetAnimation implements Animation {

    /**
     * The panel containing the widget being moved
     */
    protected final AbsolutePanel panel;
    /**
     * The widget being moved.
     */
    protected final Widget widget;
    /**
     * The amount of time the animation should last for.
     */
    protected final int animationTime;
    /**
     * The final X position for the widget.
     */
    private final int targetX;
    /**
     * The final Y position for the widget.
     */
    private final int targetY;

    /**
     * When the animation started.
     */
    private Duration startTime;
    /**
     * Initial X position for the widget.
     */
    private int initialX;
    /**
     * Initial Y position for the widget.
     */
    private int initialY;

    /**
     * Create an animation
     *
     * @param widget The widget to move.
     * @param animationTime The amount of time the animation should take to get from the initial to the final poition.
     * @param targetX The final X position the widget will end up in.
     * @param targetY The final Y position the widget will end up in.
     */
    public MoveWidgetAnimation(Widget widget, int animationTime, int targetX, int targetY) {
        this.widget = widget;
        this.panel = (AbsolutePanel) widget.getParent();
        this.targetX = targetX;
        this.targetY = targetY;
        this.animationTime = animationTime;
    }

    /**
     * Get the widget that is being animated. This is used by the AnimationEngine to ensure there is only one
     * animation active for a widget at one time.
     *
     * @return The widget that is being animated.
     */
    public Widget getWidget() {
        return widget;
    }

    /**
     * Called before animateOneFrame is called for the first time.
     *
     * @return true if the animation is finished already, false if animateOneFrame should be called at least once.
     */
    public boolean beforeFirstFrame() {
        startTime = new Duration();
        initialX = panel.getWidgetLeft(widget);
        initialY = panel.getWidgetTop(widget);
        // Widget is already in correct location.
        return initialX == targetX && initialY == targetY;
    }

    /**
     * Execute one frame of the animation.
     *
     * @return true if the animation is finished, false if it should execute again.
     */
    public boolean animateOneFrame() {
        // Check that the widget is still in the panel.
        if (panel.getWidgetIndex(widget) == -1) {
            return true;
        }
        // How much time has elapsed since the start of the animation.
        final int elapsed = startTime.elapsedMillis();
        if (elapsed == 0) {
            return false;
        }
        // What fraction of the total animationTime has elapsed so far?
        float fraction = (float) elapsed / (float) animationTime;
        return animateOneFrame(fraction);
    }

    /**
     * Execute one frame of the animation.
     *
     * @param fraction The fraction, between 0 and 1, of the animationTime that has elapsed so far.
     * @return true if the animation is finished, false if it should execute again.
     */
    protected boolean animateOneFrame(float fraction) {
        // Check whether we actually have to move the widget. This is here in case a subclass calls this method
        // and the widget is already in the correct location.
        if (initialX != targetX || initialY != targetY) {
            if (fraction >= 1.0f) {
                // If all the time has passed then move to final location.
                panel.setWidgetPosition(widget, targetX, targetY);
                return true;
            } else {
                // Move to the correct fractional location.
                int x = Math.round(initialX + ((targetX - initialX) * fraction));
                int y = Math.round(initialY + ((targetY - initialY) * fraction));
                panel.setWidgetPosition(widget, x, y);
                return false;
            }
        } else {
            return true;
        }
    }

    /**
     * Called after animateOneFrame is called for the last time.
     */
    public void afterLastFrame() {
        // Do nothing here.
    }
}
