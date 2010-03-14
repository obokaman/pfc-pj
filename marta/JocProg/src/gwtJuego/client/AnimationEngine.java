package gwtJuego.client;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;


public class AnimationEngine {

    /**
     * The time that should elapse between calls to executeAnimations, in milliseconds.
     */
    private static final int ANIMATION_INTERVAL = 33;
    /**
     * Flag that indicates whether or not the current animation is paused.
     */
    private static boolean paused;
    /**
     * Flag that indicates whether or not the current animation should be finished.
     */
    private static boolean stop;
    /**
     * The panel where the buttons to control the animation are.
     */
    private static HorizontalPanel controllersPanel;
    /**
     * The panel where the animation take place.
     */
    private static AbsolutePanel animationPanel;

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

    private static PushButton oldStopButton;
    private static TextArea codeTextArea;
    private static TextBox name;
    private static Button load;
    private static Button save;
    private static Button change;
    
    /**
     * Add a new animation. Only one animation per widget is supported so this will replace any existing animation for
     * the same widget. Animations are executed in the order in which they are added but a later animation for the
     * same widget as an existing animation replaces the existing animation in the execution order.
     *
     * @param animation The animation to add.
     */
    public void addAnimation(Animation animation, HorizontalPanel controllersHPanel, HorizontalPanel buttonsHPanel, AbsolutePanel imgPanel, TextArea inputTextArea ) {
    	
    	controllersPanel = controllersHPanel;
    	animationPanel = imgPanel;
    	codeTextArea = inputTextArea;
    	//final TextBox name = (TextBox)buttonsHPanel.getWidget(0);
    	name = (TextBox)buttonsHPanel.getWidget(0);
    	name.setEnabled(false);
    	//final Button load = (Button)buttonsHPanel.getWidget(1);
    	load = (Button)buttonsHPanel.getWidget(1);
    	load.setEnabled(false);
    	//final Button save = (Button)buttonsHPanel.getWidget(2);
    	save = (Button)buttonsHPanel.getWidget(2);
    	save.setEnabled(false);
    	//final Button change = (Button)buttonsHPanel.getWidget(3);
    	change = (Button)buttonsHPanel.getWidget(3);
    	change.setEnabled(false);
    	
    	paused = false;
    	stop = false;
    	controllersPanel.getWidget(0).setVisible(false);
    	controllersPanel.getWidget(1).setVisible(true);
        PushButton controlPauseButton = (PushButton)controllersPanel.getWidget(1);
        PushButton controlPlayButton = (PushButton)controllersPanel.getWidget(2);
        
        //final PushButton oldStopButton = (PushButton)controllersPanel.getWidget(3);
        oldStopButton = (PushButton)controllersPanel.getWidget(3);
        oldStopButton.setEnabled(true);  
        
        controlPauseButton.addClickHandler( 
  			  new ClickHandler() {
  				  public void onClick(ClickEvent event) {
  					  if(!paused) {
  						controllersPanel.getWidget(1).setVisible(false);
  						controllersPanel.getWidget(2).setVisible(true);
  				        paused = true;
  					  }
  				  }
  			  });
        controlPlayButton.addClickHandler( 
    			  new ClickHandler() {
    				  public void onClick(ClickEvent event) {
    					  if(paused) {
    						controllersPanel.getWidget(2).setVisible(false);
    						controllersPanel.getWidget(1).setVisible(true);
    				        paused = false;
    					  }
    				  }
    			  });
        oldStopButton.addClickHandler( 
    			  new ClickHandler() {
   				  public void onClick(ClickEvent event) {
    					  stop = true;
    			/*		  oldStopButton.setEnabled(false);
    					  controllersPanel.getWidget(1).setVisible(false);
    					  controllersPanel.getWidget(2).setVisible(false);
    	                  controllersPanel.getWidget(0).setVisible(true);
    	                  PushButton oldPlayButton = (PushButton)controllersPanel.getWidget(0);
    	                  oldPlayButton.setEnabled(true);
    	                  //inputTextArea.setEnabled(true);
    	                  codeTextArea.setEnabled(true);
    	                  name.setEnabled(true);
    	                  load.setEnabled(true);
    	                  save.setEnabled(true);
    	                  change.setEnabled(true);
    	                  if (animationPanel.getWidgetCount() == 2) animationPanel.remove(1);  //borrar widget coche
    				*/  	
    				  }
    			  });
    	
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
                /*if (!paused && !stop && animation.animateOneFrame()) {
                    // This animation is completed so remove it.
                	//afterAnimation();
                	animation.afterLastFrame();
                	entries.remove();
                }
                else if(stop) {
                	//afterAnimation();
            		animation.afterLastFrame();
            		entries.remove();
            	}*/
                if (stop || (!paused && !stop && animation.animateOneFrame())) {
                    // This animation is completed so remove it.
                	afterAnimation();
                	animation.afterLastFrame();
                	entries.remove();
                }
            }
        } finally {
            executingAnimations = false;
        }
    }
    
    /**
     * Recover status after finishing the execution of one animation
     */
    private void afterAnimation() {
    	oldStopButton.setEnabled(false);
		controllersPanel.getWidget(1).setVisible(false);
		controllersPanel.getWidget(2).setVisible(false);
        controllersPanel.getWidget(0).setVisible(true);
        PushButton oldPlayButton = (PushButton)controllersPanel.getWidget(0);
        oldPlayButton.setEnabled(true);
        //inputTextArea.setEnabled(true);
        codeTextArea.setEnabled(true);
        name.setEnabled(true);
        load.setEnabled(true);
        save.setEnabled(true);
        change.setEnabled(true);
        if (animationPanel.getWidgetCount() == 2) animationPanel.remove(1);  //borrar widget coche
    }
    
    /**
     * Forces the animation which is being executed to finish.
     */
    public void finishAnimation() {
    	stop = true;
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
