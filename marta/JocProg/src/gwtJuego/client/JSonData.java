package gwtJuego.client;

import com.google.gwt.core.client.JavaScriptObject;

class JSonData extends JavaScriptObject {
	  // Overlay types always have protected, zero argument constructors.
	  protected JSonData() {}
	  
	  // JSNI methods to get JSon data. 
	  public final native String get(String s) /*-{ return this[s] ; }-*/; 
	  public final native int getInt(String s) /*-{ return this[s] ; }-*/;
}