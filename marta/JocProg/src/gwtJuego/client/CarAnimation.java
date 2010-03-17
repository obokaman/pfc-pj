package gwtJuego.client;

import java.util.ArrayList;

import com.google.gwt.core.client.Duration;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArrayNumber;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
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
     * The URL where images are.
     */
    //private final static String IMG_URL = "http://localhost/img/";
    private final static String IMG_URL = "http://gabarro.org/racing/img/";
    /**
     * The next field to read from trace
     */
    private int nextField;
    /**
     * Flag that indicates whether or not the pointer is being dragged.
     */
    private boolean dragging;
    /**
     * The panel containing the widget being moved
     */
    protected final AbsolutePanel panel;
    /**
     * The panel containing the animation progress bar
     */
    protected final AbsolutePanel progressPanel;
    /**
     * The trace the widget should follow.
     */
    protected final ArrayList<String> trace = new ArrayList<String>();
    protected JsArrayNumber trace2; //
    /**
     * The circuit image width.
     */
    protected final int circuitWidth;
    /**
     * The circuit image height.
     */
    protected final int circuitHeight;
    /**
     * The widget being moved.
     */
    protected Widget widget;
    /**
     * The pointer being moved.
     */
    protected Widget pointer;
    /**
     * The widget index.
     */
    protected int indexWidget;
    /**
     * The final X position for the widget.
     */
    private int targetX;
    /**
     * The final Y position for the widget.
     */
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
     * Initial X position for the pointer.
     */
    private int pointerIniX;
    /**
     * The final X position for the pointer.
     */
    private int pointerX;

    /**
     * Create an animation
     *
     * @param widget The widget to move.
     * @param animationTime The amount of time the animation should take to get from the initial to the final position.
     * @param targetX The final X position the widget will end up in.
     * @param targetY The final Y position the widget will end up in.
     */
    public CarAnimation(AbsolutePanel panel, AbsolutePanel progressAbsPanel, int circWidth, int circHeight, String tr) {
    	
    	if (carImages.size() == 0) {
	    	for(int i=0; i<=360-frec; i=i+frec) {
	    		Image c = new Image();
	    		c.setUrl(IMG_URL+"car-"+String.valueOf(i)+".gif");
	    		carImages.add(c);
	    	}
    	}
    	getTrace(tr);
    	this.panel = panel;
    	this.progressPanel = progressAbsPanel;
    	this.circuitWidth = circWidth;
    	this.circuitHeight = circHeight;
    	
    	int x = (int)(getNextTraceField());
    	int y = (int)(getNextTraceField());
    	this.targetAngle = (float)(getNextTraceField());
    	
    	int which = (int)((carImages.size()*((targetAngle + Math.PI)/(2*Math.PI)))+0.5);
    	indexWidget = which%carImages.size();
    	this.widget = carImages.get(indexWidget);
    	this.pointer = this.progressPanel.getWidget(1);
    	pointerIniX = 0;

    	convertMeasures(x,y,"initial");
    	
    	Image point = (Image)this.progressPanel.getWidget(1);
    	point.addMouseDownHandler(
    			new MouseDownHandler(){
    				public void onMouseDown(MouseDownEvent event){
    					dragging = true;
    				}
    			});
    	point.addMouseUpHandler(
    			new MouseUpHandler(){
    				public void onMouseUp(MouseUpEvent event){
    					//coger posicion final
    					pointerIniX = event.getClientX() - progressPanel.getAbsoluteLeft();
    		    		pointerIniX = Math.max(0, pointerIniX);
    		    		pointerIniX = Math.min(pointerIniX, progressPanel.getOffsetWidth()-pointer.getOffsetWidth());
    					double wBar = (progressPanel.getOffsetWidth()/(double)trace2.length());
    					nextField = (int)(pointerIniX/wBar);
    					nextField = (nextField/3)*3+2;
    					nextField = Math.max(nextField, 5);  //primera posicion de trace donde hay coordenada x
    					nextField = Math.min(nextField, trace2.length()-6);
    			    	
//    			    	int x = (int)Float.parseFloat(getNextTraceField());
//    			    	int y = (int)Float.parseFloat(getNextTraceField());
//    			    	targetAngle = Float.parseFloat(getNextTraceField());
    			    	int x = (int)(getNextTraceField());
    			    	int y = (int)(getNextTraceField());
    			    	targetAngle = (float)(getNextTraceField());

    			    	convertMeasures(x,y,"initial");
    			    	
    			    	x = (int)(getNextTraceField());
    			    	y = (int)(getNextTraceField());
    			        targetAngle = (float)(getNextTraceField());
//    			    	x = (int)Float.parseFloat(getNextTraceField());
//    			    	y = (int)Float.parseFloat(getNextTraceField());
//    			        targetAngle = Float.parseFloat(getNextTraceField());

    			    	convertMeasures(x,y,"target");
    			    	
    					dragging = false;
    				}
    			});
    	point.addMouseMoveHandler(
    			new MouseMoveHandler(){
    				public void onMouseMove(MouseMoveEvent event){
    					if(dragging){
    						//coger posicion y poner widget
    						int newX = Math.max(0, event.getClientX() - progressPanel.getAbsoluteLeft());
    						newX = Math.min(newX, progressPanel.getOffsetWidth()-pointer.getOffsetWidth());
    						progressPanel.setWidgetPosition(pointer, newX,0);
    					}
    				}
    			});
    	
    	Image bar = (Image)this.progressPanel.getWidget(0);
    	bar.addMouseUpHandler(
    			new MouseUpHandler(){
    				public void onMouseUp(MouseUpEvent event){
    					//coger posicion final
    					pointerIniX = event.getClientX() - progressPanel.getAbsoluteLeft();
    		    		pointerIniX = Math.max(0, pointerIniX);
    		    		pointerIniX = Math.min(pointerIniX, progressPanel.getOffsetWidth()-pointer.getOffsetWidth());
    					double wBar = (progressPanel.getOffsetWidth()/(double)trace2.length());
    					nextField = (int)(pointerIniX/wBar);
    					nextField = (nextField/3)*3+2;
    					nextField = Math.max(nextField, 5);  //primera posicion de trace donde hay coordenada x
    					nextField = Math.min(nextField, trace2.length()-6);
    			    	
//    			    	int x = (int)Float.parseFloat(getNextTraceField());
 //   			    	int y = (int)Float.parseFloat(getNextTraceField());
//    			    	targetAngle = Float.parseFloat(getNextTraceField());
    			    	int x = (int)(getNextTraceField());
    			    	int y = (int)(getNextTraceField());
    			    	targetAngle = (float)(getNextTraceField());

    			    	convertMeasures(x,y,"initial");
    			    	
//    			    	x = (int)Float.parseFloat(getNextTraceField());
//    			    	y = (int)Float.parseFloat(getNextTraceField());
//    			        targetAngle = Float.parseFloat(getNextTraceField());
    			    	x = (int)(getNextTraceField());
    			    	y = (int)(getNextTraceField());
    			        targetAngle = (float)(getNextTraceField());

    			    	convertMeasures(x,y,"target");
    				}
    			});
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
//    	int x = (int)Float.parseFloat(getNextTraceField());
//    	int y = (int)Float.parseFloat(getNextTraceField());
    	int x = (int)(getNextTraceField());
    	int y = (int)(getNextTraceField());

    	convertMeasures(x,y,"target");
//        targetAngle = Float.parseFloat(getNextTraceField());
        targetAngle = (float)(getNextTraceField());

        panel.add(this.widget, this.initialX, this.initialY);
        
    	progressPanel.setWidgetPosition(pointer, 0,0);
        // Widget is already in correct location.
        //return initialX == targetX && initialY == targetY;
    	startTime = new Duration();
        return false;
    }

    /**
     * Execute one frame of the animation.
     *
     * @return true if the animation is finished, false if it should execute again.
     */
    public boolean animateOneFrame() {
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
    	if(dragging) return false;
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
            	progressPanel.setWidgetPosition(pointer, pointerX,0);
            	
                if (nextField < trace2.length()){
                	initialX = targetX;
                	initialY = targetY;
                	pointerIniX = pointerX;
//                	int x = (int)Float.parseFloat(getNextTraceField());
//                	int y = (int)Float.parseFloat(getNextTraceField());
                	int x = (int)(getNextTraceField());
                	int y = (int)(getNextTraceField());
                	convertMeasures(x,y,"target");
//                	targetAngle = Float.parseFloat(getNextTraceField());
                	targetAngle = (float)(getNextTraceField());
                	startTime = new Duration();
                	return false;
                }
                else return true;
            } else {
                // Move to the correct fractional location.
                int x = Math.round(initialX + ((targetX - initialX) * fraction));
                int y = Math.round(initialY + ((targetY - initialY) * fraction));
                panel.setWidgetPosition(widget, x, y);
                x = Math.round(pointerIniX + ((pointerX - pointerIniX) * fraction));
                progressPanel.setWidgetPosition(pointer, x,0);
                return false;
            }
        }
        else if (trace2.length()>0) {
        	initialX = targetX;
        	initialY = targetY;
        	pointerIniX = pointerX;
//        	int x = (int)Float.parseFloat(getNextTraceField());
//        	int y = (int)Float.parseFloat(getNextTraceField());
        	int x = (int)getNextTraceField();
        	int y = (int)getNextTraceField();

        	convertMeasures(x,y,"target");
//        	targetAngle = Float.parseFloat(getNextTraceField());
        	targetAngle = (float)getNextTraceField();
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
    	progressPanel.setWidgetPosition(pointer, 0,0);
    }
    
    private void getTrace(String t) {
//    	String[] vs = t.split("[ \n]");
//    	trace.clear();
//    	for(int i=0; i<vs.length;++i) {
//    		this.trace.add(vs[i]);
//    	}
    	nextField = 5;
    	
    	trace2 = JavaScriptObject.createArray().cast();
    	int len = t.length();
    	int lasti = 0, val = 0;
    	for (int i=0;i<len;++i) {
    		char c = t.charAt(i);
    		if (c==' ' || c=='\n') {
    			trace2.set(val, Double.parseDouble(t.substring(lasti, i)));
    			lasti = i+1;
    			++val;
    		}
    	}
    }
    
//    private String getNextTraceField() {
    private double getNextTraceField() {
//    	String s = this.trace.get(nextField);
//    	nextField++;
//    	return s;
    	return this.trace2.get(nextField++);
    }
    
    private void convertMeasures(int x, int y, String what) {
    	int h = this.panel.getOffsetHeight();
    	int w = this.panel.getOffsetWidth();
    	int imgWidth = (h*circuitWidth)/circuitHeight;
    	int widgtH = h*60/circuitHeight;  //dividido entre circuitHeight o circuitWidth??
    	
    	widget.setHeight(widgtH + "px");
    	widget.setWidth(widgtH + "px");

    	float newY = (y/(float)circuitHeight)*h;
    	float newX = (x/(float)circuitWidth)*imgWidth;

    	if(w > imgWidth) newX = newX + (float)((w-imgWidth)/2);
    	newX = newX - (widgtH/2);
    	newY = newY - (widgtH/2);
    	
    	if(what.equals("initial")){
    		this.initialX = (int)newX;
    		this.initialY = (int)newY;
    	}
    	else if(what.equals("target")){
    		this.targetX = (int)newX;
    		this.targetY = (int)newY;
    		double wBar = (this.progressPanel.getOffsetWidth()/(double)trace2.length());
    		pointerX = (int)(wBar*nextField);
    		pointerX = Math.max(0, pointerX);
    		pointerX = Math.min(pointerX, progressPanel.getOffsetWidth()-pointer.getOffsetWidth());
    	}
    }
}