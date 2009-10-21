package gwtJuego.client;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;


class RankingData extends JavaScriptObject {
	  // Overlay types always have protected, zero argument constructors.
	  protected RankingData() {}

	  // JSNI methods to get ranking data.
	  //public final native int getIdUser() /*-{ return this.id_user; }-*/;
	  //public final native String getNick() /*-{ return this.nick; }-*/;
	  //public final native String getName() /*-{ return this.name; }-*/;
	  //public final native String getSurname1() /*-{ return this.surname1; }-*/;
	  //public final native String getSurname2() /*-{ return this.surname2; }-*/;
	  //public final native String getEmailUser() /*-{ return this.email_user; }-*/;
	  //public final native String getPopulation() /*-{ return this.population; }-*/;
	  //public final native String getSchool() /*-{ return this.school; }-*/;
	  //public final native String getEmailSchool() /*-{ return this.email_school; }-*/;
	  //public final native String getTypeUser() /*-{ return this.type_user; }-*/;
	  //public final native String getPass() /*-{ return this.pass; }-*/;

	  // Non-JSNI method to return change percentage.
	  /*public final double getChangePercent() {
	    return 100.0 * getChange() / getPrice();
	  }*/
	  
	  public final native int getPage() /*-{ return this.page; }-*/;
	  public final native int getNumPages() /*-{ return this.numpages; }-*/;
	  public final native JsArray<RankingUserData> getData() /*-{ return this.data; }-*/;
}
