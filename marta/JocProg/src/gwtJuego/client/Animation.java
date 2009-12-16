package gwtJuego.client;

import com.google.gwt.user.client.ui.Widget;

/**
 * Interface for animations to be executed by an AnimationEngine instance.
 *
 * @author amoffat Alex Moffat
 */
public interface Animation {

    /**
     * Get the widget that is being animated. This is used by the AnimationEngine to ensure there is only one
     * animation active for a widget at one time.
     *
     * @return The widget that is being animated.
     */
    public Widget getWidget();

    /**
     * Called before animateOneFrame is called for the first time.
     * 
     * @return true if the animation is finished already, false if animateOneFrame should be called at least once.
     */
    public boolean beforeFirstFrame();

    /**
     * Execute one frame of the animation.
     *
     * @return true if the animation is finished, false if it should execute again.
     */
    public boolean animateOneFrame();

    /**
     * Called after animateOneFrame is called for the last time. This is not called if beforeFirstFrame returns true.
     */
    public void afterLastFrame();
}
