package gwtJuego.client;

import com.google.gwt.core.client.JavaScriptObject;

class RankingUserData extends JavaScriptObject {
	  // Overlay types always have protected, zero argument constructors.
	  protected RankingUserData() {}
	  
	  // JSNI methods to get ranking user data.
	  public final native String getNick() /*-{ return this.nick; }-*/;
	  public final native String getTiempo() /*-{ return this.time_result; }-*/;	  
}