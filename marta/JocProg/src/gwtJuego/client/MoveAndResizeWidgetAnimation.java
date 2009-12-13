package gwtJuego.client;

import com.google.gwt.user.client.ui.Widget;

/**
 * An animation that moves a widget, which must be a child of an AbsolutePanel, from its initial position
 * to a specified final position and changes its height as it moves.
 *
 * @author amoffat Alex Moffat
 */
public class MoveAndResizeWidgetAnimation extends MoveWidgetAnimation {

    /**
     * The final height the widget should have.
     */
    private final int targetHeight;

    /**
     * The initial height the widget has.
     */
    private int initialHeight;

    /**
     * Create a new animation.
     *
     * @param widget The widget to animate.
     * @param animationTime How long the animation should last.
     * @param targetX The final x location for the widget.
     * @param targetY The final y location for the widget.
     * @param targetHeight The final ehiht for the animation.
     */
    public MoveAndResizeWidgetAnimation(Widget widget, int animationTime, int targetX, int targetY, int targetHeight) {
        super(widget, animationTime, targetX, targetY);
        this.targetHeight = targetHeight;
    }

    /**
     * Called before animateOneFrame is called for the first time.
     *
     * @return true if the animation is finished already, false if animateOneFrame should be called at least once.
     */
    @Override
    public boolean beforeFirstFrame() {
        boolean finished = super.beforeFirstFrame();
        initialHeight = widget.getOffsetHeight();
        return finished && initialHeight == targetHeight;
    }

    /**
     * Execute one frame of the animation.
     *
     * @param fraction The fraction, between 0 and 1, of the animationTime that has elapsed so far.
     * @return true if the animation is finished, false if it should execute again.
     */
    @Override
    protected boolean animateOneFrame(float fraction) {
        boolean finished = super.animateOneFrame(fraction);
        // Check that we're not already the right height.
        if (initialHeight != targetHeight) {
            if (fraction >= 1.0f) {
                widget.setHeight(targetHeight + "px");
                return finished;
            } else {
                int height = Math.round(initialHeight + (targetHeight - initialHeight) * fraction);
                widget.setHeight(height + "px");
                return false;
            }
        } else {
            return finished;
        }
    }
}
