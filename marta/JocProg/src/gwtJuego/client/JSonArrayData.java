package gwtJuego.client;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;


class JSonArrayData extends JavaScriptObject {
	  // Overlay types always have protected, zero argument constructors.
	  protected JSonArrayData() {}

	  // JSNI methods to get ranking data.	  
	  public final native int getInt(String s) /*-{ return this[s] ; }-*/;
	  public final native JsArray<JSonData> getData() /*-{ return this.data; }-*/;
}