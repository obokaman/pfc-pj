package gwtJuego.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.http.client.URL;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SourcesTabEvents;
import com.google.gwt.user.client.ui.TabListener;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.HorizontalSplitPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalSplitPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.FlexTable;
import gwtJuego.client.RankingData;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.i18n.client.NumberFormat;

//import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
//import com.google.gwt.user.client.ui.TextBox;



public class JocProg implements EntryPoint {

  private TabPanel mainPanel = new TabPanel();
  private HorizontalSplitPanel correPanel = new HorizontalSplitPanel();
  private VerticalSplitPanel codiPanel = new VerticalSplitPanel();
  //private DecoratorPanel decPanel = new DecoratorPanel();
  private HorizontalPanel consolaPanel = new HorizontalPanel();
  private HorizontalPanel inputPanel = new HorizontalPanel();
  private VerticalPanel rankingVPanel = new VerticalPanel();
  private HorizontalPanel rankingHPanel = new HorizontalPanel();
  private HorizontalPanel ranking2HPanel = new HorizontalPanel();
  
  private TextArea inputTextArea = new TextArea();
  private TextArea consolaTextArea = new TextArea();
  private ListBox circuitDropBox = new ListBox(false);
  private ListBox cityDropBox = new ListBox(false);
  private ListBox schoolDropBox = new ListBox(false);
  private FlexTable rankingFlexTable = new FlexTable();
  /*private TextBox newSymbolTextBox = new TextBox();
  private Button addStockButton = new Button("Add");*/
  private Label msgLabel = new Label();
  
   private String[] circuitList = {"CIRCUIT", "circuit1","circuit2","circuit3"};
   private String[] cityList = {"CIUTAT", "Barcelona","Sabadell","Terrassa"};
   private String[] schoolList = {"CIRCUIT", "circuit1","circuit2","circuit3"};
   
   private static final String JSON_URL = "http://localhost/php/prueba.php";

  /**
   * Entry point method.
   */
  public void onModuleLoad() {
    // TODO Create table for stock data.

//------------------------------------------> tab corre
	  //Text areas
	 consolaPanel.setSize("100%","100%");
	 consolaPanel.setSpacing(5);
	 inputPanel.setSize("100%","100%");
	 inputPanel.setSpacing(5);
	 consolaTextArea.setText("consola de salida");
	 consolaTextArea.setSize("100%","100%");
	 inputTextArea.setText("entrada de código");
	 inputTextArea.setSize("100%","100%");
	 consolaPanel.add(consolaTextArea);
	 inputPanel.add(inputTextArea);
	  
	  //Assemble Split panels.
	  codiPanel.setSize("100%","100%");
	  codiPanel.setSplitPosition("50%");
	  /*codiPanel.setTopWidget(new Label("aaa"));
	  codiPanel.setBottomWidget(new Label("bbb"));*/
	  codiPanel.setTopWidget(consolaPanel);
	  codiPanel.setBottomWidget(inputPanel);
	  
	  correPanel.setSize("100%","100%");
	  correPanel.setSplitPosition("60%");
	  correPanel.setLeftWidget(new Label("ccc"));
	  correPanel.setRightWidget(codiPanel);	
	  
	  //vPanel.setSize("100%","100%");
	  //vPanel.add(correPanel);
	  
//----------------------------------> tab ranking
	    // Create a panel to align the Widgets
	  	rankingHPanel.setSize("100%", "30%");
	    rankingHPanel.setSpacing(10);
	    rankingHPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);

	    // Add drop boxs with the lists
	    for (int i = 0; i < circuitList.length; i++) {
	      circuitDropBox.addItem(circuitList[i]);
	    }
	    for (int i = 0; i < cityList.length; i++) {
		      cityDropBox.addItem(cityList[i]);
		}
	    for (int i = 0; i < schoolList.length; i++) {
		      schoolDropBox.addItem(schoolList[i]);
		}
	    rankingHPanel.add(circuitDropBox);
	    rankingHPanel.add(cityDropBox);
	    rankingHPanel.add(schoolDropBox);
	    
	    // Create table for ranking data.
	    rankingFlexTable.setText(0, 0, "Nom");
	    rankingFlexTable.setText(0, 1, "Temps");
	    rankingFlexTable.setText(0, 2, "Data");
	    /*for (int i = rankingFlexTable.getRowCount(); i < 5; i++) {
	    	rankingFlexTable.setText(i, 0, "aaaa");
	    	rankingFlexTable.setText(i, 1, "bbbb");
	    	rankingFlexTable.setText(i, 2, "cccc");
		}  */
	    ranking2HPanel.setSize("100%", "70%");
	    ranking2HPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
	    msgLabel.setVisible(false);
	    ranking2HPanel.add(msgLabel);
	    ranking2HPanel.add(rankingFlexTable);
	    
	    rankingVPanel.setSize("100%","100%");
	    rankingVPanel.add(rankingHPanel);
	    rankingVPanel.add(ranking2HPanel);
	  

	  //Assemble Main panel.
	  mainPanel.setSize("100%","100%");
	  mainPanel.getDeckPanel().setHeight("100%");
	  mainPanel.addTabListener(new TabListener() {
	        public boolean onBeforeTabSelected(SourcesTabEvents sender, int tabIndex) {
	          return true;
	        } 
	        public void onTabSelected(SourcesTabEvents sender, int tabIndex) {
	        	if(tabIndex==1){
	        		correPanel.setSplitPosition("60%");
	        		codiPanel.setSplitPosition("50%");
	        	}
	        	else if(tabIndex==2){
	        		msgLabel.setText("llamo a refresh");
	        		msgLabel.setVisible(true);
	        	    refreshRankingList();
	        	}
	        }
	  });

	  //mainPanel.setAnimationEnabled(true);
	  mainPanel.add(new HTML("Login/User Tab"),"Login/User");
	  mainPanel.add(correPanel,"Corre");
	  mainPanel.add(rankingVPanel,"Ranking");
	  mainPanel.add(new HTML("Help Tab"),"Help");
	  mainPanel.selectTab(0);
	  


	  //Associate the Main panel with the HTML host page.
	  //RootPanel.get("uiJuego").add(correPanel);
	  RootPanel.get("uiJuego").add(mainPanel);

    // TODO Move cursor focus to the input box.
  }
  
  
  /**
   * Update the ricPe and Change fields for all rows in the ranking table.
   *
   * @param prices Stock data for all rows.
   */
  private void updateTable(JsArray<RankingData> positions) {
    for (int i = 0; i < positions.length(); i++) {
      updateTable(positions.get(i));
    }
  }
  
  /**
   * Update a single row in the ranking table.
   *
   * @param price Stock data for a single row.
  */
  private void updateTable(RankingData position) {

     int row = rankingFlexTable.getRowCount();

      // Populate the Price and Change fields with new data.
      rankingFlexTable.setText(row, 0, position.getName());
      rankingFlexTable.setText(row, 1, position.getSurname1());
      rankingFlexTable.setText(row, 2, position.getPopulation());
  }
  
  /**
   * Convert the string of JSON into JavaScript object.
   */
  private final native JsArray<RankingData> asArrayOfRankingData(String json) /*-{
    return eval(json);
  }-*/;
  
  
  private void refreshRankingList() {

	    String url = JSON_URL;

	    /*// Append watch list stock symbols to query URL.
	    Iterator iter = stocks.iterator();
	    while (iter.hasNext()) {
	      url += iter.next();
	      if (iter.hasNext()) {
	        url += "+";
	      }
	    }*/

	    url = URL.encode(url);

	    // Send request to server and catch any errors.
	    RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, url);

	    try {
	      Request request = builder.sendRequest(null, new RequestCallback() {
	        public void onError(Request request, Throwable exception) {
	          //displayError("Couldn't retrieve JSON");
	        	msgLabel.setText("Couldn't retrieve JSON1");
	        	msgLabel.setVisible(true);
	        }

	        public void onResponseReceived(Request request, Response response) {
	          if (200 == response.getStatusCode()) {
	        	  //msgLabel.setText(response.getText());
	        	  //msgLabel.setVisible(true);
	        	  JsArray<RankingData> arr = asArrayOfRankingData(response.getText());
	        	  updateTable(asArrayOfRankingData(response.getText()));
	          } else {
	            //displayError("Couldn't retrieve JSON (" + response.getStatusText()+ ")");
	        	  msgLabel.setText("Couldn't retrieve JSON (" + response.getStatusText()+ ")2");
	        	  msgLabel.setVisible(true);
	          }
	        }
	      });
	    } catch (RequestException e) {
	      //displayError("Couldn't retrieve JSON");
        	msgLabel.setText("Couldn't retrieve JSON3");
        	msgLabel.setVisible(true);
	    }
	  }

}