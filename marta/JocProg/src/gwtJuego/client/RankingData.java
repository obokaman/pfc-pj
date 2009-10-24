package gwtJuego.client;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;


class RankingData extends JavaScriptObject {
	  // Overlay types always have protected, zero argument constructors.
	  protected RankingData() {}

	  // JSNI methods to get ranking data.	  
	  public final native int getPage() /*-{ return this.page; }-*/;
	  public final native int getNumPages() /*-{ return this.numpages; }-*/;
	  public final native JsArray<RankingUserData> getData() /*-{ return this.data; }-*/;
}
