package gwtJuego.client;

import java.util.ArrayList;

import com.google.gwt.core.client.Duration;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;

/**
 * An animation that moves a widget, which must be a child of an AbsolutePanel, from its initial position
 * to a specified final position.
 */
public class CarAnimation implements Animation {

    /**
     * The panel containing the widget being moved
     */
    protected final static ArrayList<Widget> carImages = new ArrayList<Widget>();
    /**
     * The frequency for the widget being moved
     */
    private final static int frec = 10;
    /**
     * The amount of time the animation should last for.
     */
    protected final static int animationTime = 100;
    /**
     * The next field to read from trace
     */
    private static int nextField;
    /**
     * The panel containing the widget being moved
     */
    protected final AbsolutePanel panel;
    /**
     * The trace the widget should follow.
     */
    protected final ArrayList<String> trace = new ArrayList<String>();
    /**
     * The widget being moved.
     */
    //protected final Widget widget;
    protected Widget widget;
    /**
     * The widget index.
     */
    protected int indexWidget;
    /**
     * The final X position for the widget.
     */
    //private final int targetX;
    private int targetX;
    /**
     * The final Y position for the widget.
     */
    //private final int targetY;
    private int targetY;
    /**
     * The final degrees for the widget.
     */
    private float targetAngle;
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
     * @param animationTime The amount of time the animation should take to get from the initial to the final position.
     * @param targetX The final X position the widget will end up in.
     * @param targetY The final Y position the widget will end up in.
     */
    public CarAnimation(AbsolutePanel panel, String trace) {
    	
    	if (carImages.size() == 0) {
	    	for(int i=0; i<=360-frec; i=i+frec) {
	    		Image c = new Image();
	    		c.setUrl("http://localhost/img/car-"+String.valueOf(i)+".gif");
	    		carImages.add(c);
	    	}
    	}
    	getTrace(trace);
    	this.panel = panel;
    	int x = (int)Float.parseFloat(getNextTraceField());
    	int y = (int)Float.parseFloat(getNextTraceField());
    	this.targetAngle = Float.parseFloat(getNextTraceField());
    	
    	int which = (int)((carImages.size()*((targetAngle + Math.PI)/(2*Math.PI)))+0.5);
    	indexWidget = which%carImages.size();
    	this.widget = carImages.get(indexWidget);

    	convertMeasures(x,y,"initial");
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
    	int x = (int)Float.parseFloat(getNextTraceField());
    	int y = (int)Float.parseFloat(getNextTraceField());
    	convertMeasures(x,y,"target");
        targetAngle = Float.parseFloat(getNextTraceField());
        panel.add(this.widget, this.initialX, this.initialY);
        // Widget is already in correct location.
        //return initialX == targetX && initialY == targetY;
        return false;
    }

    /**
     * Execute one frame of the animation.
     *
     * @return true if the animation is finished, false if it should execute again.
     */
    public boolean animateOneFrame() {
        // Check that the widget is still in the panel.
        /*if (panel.getWidgetIndex(widget) == -1) {
            return true;
        }*/
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
            	int index = (int)((carImages.size()*((targetAngle + Math.PI)/(2*Math.PI)))+0.5);
            	index = index%carImages.size();
            	if(index != indexWidget){
            		panel.remove(widget);
            		indexWidget = index;
                	this.widget = carImages.get(indexWidget);
                    panel.add(widget, targetX, targetY);
            	}
            	else panel.setWidgetPosition(widget, targetX, targetY);
            	
                if (nextField < trace.size()){
                	initialX = targetX;
                	initialY = targetY;
                	int x = (int)Float.parseFloat(getNextTraceField());
                	int y = (int)Float.parseFloat(getNextTraceField());
                	convertMeasures(x,y,"target");
                	targetAngle = Float.parseFloat(getNextTraceField());
                    startTime = new Duration();
                	return false;
                }
                else return true;
            } else {
                // Move to the correct fractional location.
                int x = Math.round(initialX + ((targetX - initialX) * fraction));
                int y = Math.round(initialY + ((targetY - initialY) * fraction));
                panel.setWidgetPosition(widget, x, y);
                return false;
            }
        }
        else if (trace.size()>0) {
        	initialX = targetX;
        	initialY = targetY;
        	int x = (int)Float.parseFloat(getNextTraceField());
        	int y = (int)Float.parseFloat(getNextTraceField());
        	convertMeasures(x,y,"target");
        	targetAngle = Float.parseFloat(getNextTraceField());
            startTime = new Duration();
            return false;
        }
        else {
            return true;
        }
    }

    /**
     * Called after animateOneFrame is called for the last time.
     */
    public void afterLastFrame() {
        // Do nothing here.
    }
    
    private void getTrace(String t) {
    	String[] vs = t.split("[ \n]");
    	trace.clear();
    	for(int i=0; i<vs.length;++i) {
    		this.trace.add(vs[i]);
    	}
    	nextField = 5;
    }
    
    private String getNextTraceField() {
    	String s = this.trace.get(nextField);
    	nextField++;
    	return s;
    }
    
    private void convertMeasures(int x, int y, String what) {
    	int h = this.panel.getOffsetHeight();
    	int w = this.panel.getOffsetWidth();
    	int widgtH = h*80/1000;
    	
    	widget.setHeight(widgtH + "px");
    	
    	float newX = (float)(x/1000.0)*h;
    	float newY = (float)(y/1000.0)*h;
    	if(w > h) newX = newX + (float)((w-h)/2);
    	newX = newX - (widgtH/2);
    	newY = newY - (widgtH/2);
    	if(what.equals("initial")){
    		this.initialX = (int)newX;
    		this.initialY = (int)newY;
    	}
    	else if(what.equals("target")){
    		this.targetX = (int)newX;
    		this.targetY = (int)newY;
    	}
    }
}