package gwtJuego.client;

import com.google.gwt.core.client.JavaScriptObject;

class UserData extends JavaScriptObject {
	  // Overlay types always have protected, zero argument constructors.
	  protected UserData() {}
	  
	  // JSNI methods to get user data.
	  public final native String getNick() /*-{ return this.nick; }-*/;
	  public final native String getName() /*-{ return this.name; }-*/;
	  public final native String getSurname1() /*-{ return this.surname1; }-*/;
	  public final native String getSurname2() /*-{ return this.surname2; }-*/;
	  public final native String getEmailUser() /*-{ return this.email_user; }-*/;
	  public final native String getCity() /*-{ return this.city; }-*/;
	  public final native String getSchool() /*-{ return this.school; }-*/;
	  public final native String getEmailSchool() /*-{ return this.email_school; }-*/;
	  //public final native String getPass() /*-{ return this.pass; }-*/;  
}