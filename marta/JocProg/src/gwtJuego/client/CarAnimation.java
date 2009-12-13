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
     * The amount of time the animation should last for.
     */
    //protected final int animationTime;
    protected int animationTime;
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
        /*this.widget = widget;
        this.panel = (AbsolutePanel) widget.getParent();
        this.targetX = targetX;
        this.targetY = targetY;
        this.animationTime = animationTime;*/
    	for(int i=0; i<=330; i=i+30) {
    		Image c = new Image();
    		c.setUrl("http://localhost/img/car-"+String.valueOf(i)+".gif");
    		carImages.add(c);
    	}
    	getTrace(trace);
    	this.panel = panel;
    	//this.initialX = (int)Float.parseFloat(getNextTraceField());  //esta x es sobre 1000x1000
    	//this.initialY = (int)Float.parseFloat(getNextTraceField());  //esta y es sobre 1000x1000
    	int x = (int)Float.parseFloat(getNextTraceField());
    	int y = (int)Float.parseFloat(getNextTraceField());
    	convertMeasures(x,y,"initial");
    	this.targetAngle = Float.parseFloat(getNextTraceField());
    	//this.widget = ; //calcular con angle q imagen cargar
    	this.widget = carImages.get(0);
    	this.animationTime = 100;
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
        //targetX = (int)Float.parseFloat(getNextTraceField());  //esta x es sobre 1000x1000
        //targetY = (int)Float.parseFloat(getNextTraceField());  //esta y es sobre 1000x1000
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
            	//CALCULAR EL NUEVO WIDGET SEGUN targetAngle!!!!!!!
                panel.setWidgetPosition(widget, targetX, targetY);
                if (trace.size()>0){
                	initialX = targetX;
                	initialY = targetY;
                	//targetX = (int)Float.parseFloat(getNextTraceField());  //esta x es sobre 1000x1000
                	//targetY = (int)Float.parseFloat(getNextTraceField());  //esta y es sobre 1000x1000
                	int x = (int)Float.parseFloat(getNextTraceField());
                	int y = (int)Float.parseFloat(getNextTraceField());
                	convertMeasures(x,y,"target");
                	targetAngle = Float.parseFloat(getNextTraceField());
                	//widget = ;  //poner la imagen correcta según el angulo
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
        	//targetX = (int)Float.parseFloat(getNextTraceField());  //esta x es sobre 1000x1000
        	//targetY = (int)Float.parseFloat(getNextTraceField());  //esta y es sobre 1000x1000
        	int x = (int)Float.parseFloat(getNextTraceField());
        	int y = (int)Float.parseFloat(getNextTraceField());
        	convertMeasures(x,y,"target");
        	targetAngle = Float.parseFloat(getNextTraceField());
        	//widget = ;  //poner la imagen correcta según el angulo
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
    	int j=0;
    	this.trace.add("");
    	for(int i= 0; i < t.length(); ++i){
    		if(t.charAt(i) == ' '){
    			this.trace.add("");
    			++j;
    		}
    		else {
    			String s = this.trace.get(j);
    			s += t.charAt(i);
    			this.trace.remove(j);
    			this.trace.add(s);
    		}
    	}
    }
    
    private String getNextTraceField() {
    	String s = this.trace.get(0);
    	this.trace.remove(0);
    	return s;
    }
    
    private void convertMeasures(int x, int y, String what) {
    	int h = this.panel.getOffsetHeight();
    	int w = this.panel.getOffsetWidth();
    	//Window.alert("h: "+String.valueOf(h)+" w: "+String.valueOf(w));
    	float newX = (float)(x/1000.0)*h;
    	float newY = (float)(y/1000.0)*h;
    	if(w > h) newX = newX + (float)((w-h)/2);
    	if(what.equals("initial")){
    		this.initialX = (int)newX;
    		this.initialY = (int)newY;
    	}
    	else if(what.equals("target")){
    		this.targetX = (int)newX;
    		this.targetY = (int)newY;
    	}
    	//Window.alert("newX: "+String.valueOf(newX)+" newY: "+String.valueOf(newY));
    }
}