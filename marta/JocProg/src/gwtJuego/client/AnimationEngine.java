package gwtJuego.client;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Widget;

/**
 * An engine for executing animations.
 *
 * @author amoffat Alex Moffat
 */
public class AnimationEngine {

    /**
     * The time that should elapse between calls to executeAnimations, in milliseconds.
     */
    private static final int ANIMATION_INTERVAL = 33;

    /**
     * Map from a widget to the animation that is animating it. Only one animation is allowed per widget. A LinkedHashMap
     * is used so that the order in which the animations are added is preserved and used when executing the animations.
     */
    private Map<Widget, Animation> animations = new LinkedHashMap<Widget, Animation>();
    /**
     * A list containing animations that were added while the animation timer was running. Though execution is single
     * threaded in JavaScript we have to worry about ConcurrentModificationExceptions that will happen if while an animation
     * is executing it tries to add another animation to this engine.
     */
    private List<Animation> addedAnimations = new ArrayList<Animation>();
    /**
     * The timer used to execute the animations at regular intervals.
     */
    private AnimationTimer timer = new AnimationTimer();
    /**
     * Flag that indicates whether or not animations are currently being executed.
     */
    private boolean executingAnimations = false;

    /**
     * Add a new animation. Only one animation per widget is supported so this will replace any existing animation for
     * the same widget. Animations are executed in the order in which they are added but a later animation for the
     * same widget as an existing animation replaces the existing animation in the execution order.
     *
     * @param animation The animation to add.
     */
    public void addAnimation(Animation animation) {
        boolean timerIsRunning = !animations.isEmpty();
        if (!animation.beforeFirstFrame()) {
            if (executingAnimations) {
                // executeAnimations is running so the animations map is being iterated over and we can't modify it
                // here. Add the new animation to the addedAnimations list instead.
                addedAnimations.add(animation);
            } else {
                animations.put(animation.getWidget(), animation);
                if (!timerIsRunning) {
                    timer.schedule(ANIMATION_INTERVAL);
                }
            }
        }
    }

    /**
     * Execute all of the animations in the order they were added by addAnimation.
     */
    private void executeAnimations() {
        try {
            executingAnimations = true;
            for (Iterator<Map.Entry<Widget, Animation>> entries = animations.entrySet().iterator(); entries.hasNext();) {
                Animation animation = entries.next().getValue();
                if (animation.animateOneFrame()) {
                    // This animation is completed so remove it.
                    animation.afterLastFrame();
                    entries.remove();
                }
            }
        } finally {
            executingAnimations = false;
        }
    }

    /**
     * The timer that calls executeAnimations on a regular basis.
     */
    private class AnimationTimer extends Timer {

        public void run() {
            // Execute the animations.
            executeAnimations();
            // If any animations were added by the ones we just executed add them to the main collection now.
            int numberOfWaitingAnimations = addedAnimations.size();
            if (numberOfWaitingAnimations > 0) {
                for (int index = 0; index < numberOfWaitingAnimations; index++) {
                    Animation animation = addedAnimations.get(index);
                    animations.put(animation.getWidget(), animation);
                }
                addedAnimations.clear();
            }
            // If there are animations still to execute schedule ourselves to run later.
            if (animations.size() > 0) {
                schedule(ANIMATION_INTERVAL);
            }
        }
    }
}
