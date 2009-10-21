package gwtJuego.client;

import java.util.ArrayList;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.http.client.URL;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SourcesTabEvents;
import com.google.gwt.user.client.ui.TabListener;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.HorizontalSplitPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.TextBoxBase;
import com.google.gwt.user.client.ui.VerticalSplitPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTMLTable.CellFormatter;

import gwtJuego.client.RankingData;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.i18n.client.NumberFormat;



public class JocProg implements EntryPoint {

  private TabPanel mainPanel = new TabPanel();
  private HorizontalSplitPanel correPanel = new HorizontalSplitPanel();
  private VerticalSplitPanel codiPanel = new VerticalSplitPanel();
  private HorizontalPanel consolaPanel = new HorizontalPanel();
  private HorizontalPanel inputPanel = new HorizontalPanel();
  private VerticalPanel rankingVPanel = new VerticalPanel();
  private HorizontalPanel rankingHPanel = new HorizontalPanel();
  private HorizontalPanel ranking2HPanel = new HorizontalPanel();
  private VerticalPanel loginVPanel = new VerticalPanel();
  private DisclosurePanel registerDisclosure = new DisclosurePanel("¿Aún no estás registrado?");

  
  private TextArea inputTextArea = new TextArea();
  private TextArea consolaTextArea = new TextArea();
  private ListBox circuitsDropBox = new ListBox(false);
  private ListBox champsDropBox = new ListBox(false);
  private ListBox teamsDropBox = new ListBox(false);
  private ListBox rankPagesDropBox = new ListBox(false);
  private FlexTable rankingFlexTable = new FlexTable();
  private TextBox loginUserTextBox = new TextBox();
  private PasswordTextBox loginPassword = new PasswordTextBox();
  private TextBox regNickTextBox = new TextBox();
  private TextBox regNameTextBox = new TextBox();
  private TextBox regSurname1TextBox = new TextBox();
  private TextBox regSurname2TextBox = new TextBox();
  private TextBox regEmailUserTextBox = new TextBox();
  private TextBox regCityTextBox = new TextBox();
  private TextBox regSchoolTextBox = new TextBox();
  private TextBox regEmailSchoolTextBox = new TextBox();
  private PasswordTextBox regPassword = new PasswordTextBox();
  private PasswordTextBox regConfirmPassword = new PasswordTextBox();

  //private Button showRankingButton = new Button("Show ranking");
  private Button loginButton = new Button("Entrar");
  private Button regButton = new Button("Enviar");
  private Label msgLabel = new Label();
  
  private ArrayList<String> circuitsList = new ArrayList<String>();
  private ArrayList<String> champsList = new ArrayList<String>();
  private ArrayList<String> teamsList = new ArrayList<String>();

   //private String[] circuitsList /*= {"CIRCUITOS", "circuit1","circuit2","circuit3"}*/;
   //private String[] champsList = {"CAMPEONATOS", "champ1","champ2","champ3"};
   //private String[] teamsList = {"EQUIPOS", "team1","team2","team3"};
   private String USER = "";
   
   //private static final String JSON_URL = "http://localhost/php/prueba.php";
   private static final String JSON_URL = "http://localhost/php/main.php?";

  /**
   * Entry point method.
   */
  public void onModuleLoad() {

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
	  correPanel.setSize("100%","100%");
	  correPanel.setSplitPosition("60%");
	  correPanel.setLeftWidget(new Label("CIRCUITO"));
	  correPanel.setRightWidget(consolaPanel);
	  
	  codiPanel.setSize("100%","100%");
	  codiPanel.setSplitPosition("50%");
	  codiPanel.setTopWidget(correPanel);
	  codiPanel.setBottomWidget(inputPanel);

	  
//----------------------------------> tab ranking
	    // Create a panel to align the Widgets
	  	rankingHPanel.setSize("100%", "30%");
	    rankingHPanel.setSpacing(10);
	    rankingHPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);

	    requestCircuits();
	    // Add drop boxs with the lists
	    for (int i=0; i<circuitsList.size(); i++) { circuitsDropBox.addItem(circuitsList.get(i)); }
	    champsList.add("CAMPEONATOS");
	    teamsList.add("EQUIPOS");
	    /*for (int i = 0; i < champsList.length; i++) {
		      champsDropBox.addItem(champsList[i]);
		}
	    for (int i = 0; i < teamsList.length; i++) {
		      teamsDropBox.addItem(teamsList[i]);
		}*/
	    champsDropBox.setEnabled(false);
	    teamsDropBox.setEnabled(false);
	    circuitsDropBox.addItem("CIRCUITOS");
	    rankingHPanel.add(circuitsDropBox);
	    champsDropBox.addItem("CAMPEONATOS");
	    rankingHPanel.add(champsDropBox);
	    teamsDropBox.addItem("EQUIPOS");
	    rankingHPanel.add(teamsDropBox);
		// Listen for mouse events on the Add button.
	    circuitsDropBox.addChangeHandler(new ChangeHandler() {
	    	public void onChange(ChangeEvent event) {
	    		if(circuitsDropBox.getSelectedIndex()>0){
	    			//requestRanking(); aa;
	    			champsDropBox.setEnabled(true);
	    			teamsDropBox.setEnabled(true);

	    		}
	    		else{
	    			champsDropBox.setSelectedIndex(0);
	    			champsDropBox.setEnabled(false);
	    			teamsDropBox.setSelectedIndex(0);
	    			teamsDropBox.setEnabled(false);	    			
	    		}
			}
		});
	    champsDropBox.addChangeHandler(new ChangeHandler() {
	    	public void onChange(ChangeEvent event) {
	    		//if(champsDropBox.getSelectedIndex()>0) requestRanking(); aa;
	    	}
	    });
	    teamsDropBox.addChangeHandler(new ChangeHandler() {
	    	public void onChange(ChangeEvent event) {
	    		//if(teamsDropBox.getSelectedIndex()>0) requestRanking(); aa;
			}
		});
	    
	    // Create table for ranking data.
	    rankingFlexTable.setText(0, 0, "Usuario");
	    rankingFlexTable.setText(0, 1, "Tiempo");
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
	    
//--------------------------------------------------------> tab login
	    Grid loginLayout = new Grid(4, 2);
	    loginLayout.setCellSpacing(5);
	    CellFormatter loginCellFormatter = loginLayout.getCellFormatter();
	    // Add a title to the form
	    Label loginLabel = new Label("Identifícate para acceder a tu cuenta:");
	    loginVPanel.add(loginLabel);
	    loginVPanel.setCellVerticalAlignment(loginLabel,HasVerticalAlignment.ALIGN_BOTTOM);
	    // Add some standard form options
	    loginLayout.setHTML(1, 0, "Usuario: ");
	    loginLayout.setWidget(1, 1, loginUserTextBox);
	    loginLayout.setHTML(2, 0, "Contraseña: ");
	    loginLayout.setWidget(2, 1, loginPassword);
	    loginLayout.setWidget(3, 1, loginButton);
	    loginCellFormatter.setHorizontalAlignment(3,1,HasHorizontalAlignment.ALIGN_RIGHT);
	    loginVPanel.setSize("100%", "100%");
	    loginVPanel.add(loginLayout);
	    msgLabel.setVisible(false);
	    loginVPanel.add(msgLabel);
	    loginVPanel.setCellHeight(loginLayout, "135px");
	    loginVPanel.setCellVerticalAlignment(loginLayout,HasVerticalAlignment.ALIGN_TOP);
	    // Create some advanced options
	    Grid regOptions = new Grid(11, 2);
	    regOptions.setCellSpacing(6);
	    regOptions.setHTML(0, 0, "Usuario: ");
	    regOptions.setWidget(0, 1, regNickTextBox);
	    regOptions.setHTML(1, 0, "Name: ");
	    regOptions.setWidget(1, 1, regNameTextBox);
	    regOptions.setHTML(2, 0, "Primer apellido: ");
	    regOptions.setWidget(2, 1, regSurname1TextBox);
	    regOptions.setHTML(3, 0, "Segundo apellido: ");
	    regOptions.setWidget(3, 1, regSurname2TextBox);
	    regOptions.setHTML(4, 0, "Email: ");
	    regOptions.setWidget(4, 1, regEmailUserTextBox);
	    regOptions.setHTML(5, 0, "Localidad: ");
	    regOptions.setWidget(5, 1, regCityTextBox);
	    regOptions.setHTML(6, 0, "Escuela: ");
	    regOptions.setWidget(6, 1, regSchoolTextBox);
	    regOptions.setHTML(7, 0, "Email de la escuela: ");
	    regOptions.setWidget(7, 1, regEmailSchoolTextBox);
	    regOptions.setHTML(8, 0, "Contraseña: ");
	    regOptions.setWidget(8, 1, regPassword);
	    regOptions.setHTML(9, 0, "Confirmar contraseña: ");
	    regOptions.setWidget(9, 1, regConfirmPassword);
	    regOptions.setWidget(10, 1, regButton);
	    CellFormatter regOpsCellFormatter = regOptions.getCellFormatter();
	    regOpsCellFormatter.setHorizontalAlignment(10,1,HasHorizontalAlignment.ALIGN_RIGHT);
	    // Add advanced options to form in a disclosure panel
	    registerDisclosure.setAnimationEnabled(true);
	    registerDisclosure.setContent(regOptions);
	    loginVPanel.add(registerDisclosure);
	    loginVPanel.setCellVerticalAlignment(registerDisclosure,HasVerticalAlignment.ALIGN_TOP);
	    loginVPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
	    
	    loginButton.addClickHandler( 
	    		new ClickHandler() {
	    			public void onClick(ClickEvent event) {
	    				requestLogin();
	            }
	    });
	    regButton.addClickHandler( 
	    		new ClickHandler() {
	    			public void onClick(ClickEvent event) {
	    				requestRegistration();
	            }
	    });


	    //Assemble Main panel.
	    //mainPanel.setAnimationEnabled(true);
	    mainPanel.add(loginVPanel,"Login/User");
		mainPanel.add(codiPanel,"Corre");
		mainPanel.add(rankingVPanel,"Ranking");
		mainPanel.add(new HTML("Help Tab"),"Help");
		mainPanel.selectTab(0);
		  
		mainPanel.setSize("100%","100%");
		mainPanel.getDeckPanel().setHeight("100%");
		mainPanel.addTabListener(new TabListener() {
	        public boolean onBeforeTabSelected(SourcesTabEvents sender, int tabIndex) {
	          return true;
	        } 
	        public void onTabSelected(SourcesTabEvents sender, int tabIndex) {
	        	if(tabIndex==1){
	        		codiPanel.setSplitPosition("50%");
	        		correPanel.setSplitPosition("60%");
	        	}
	        	else if(tabIndex==2){
	        		circuitsDropBox.setSelectedIndex(0);  //y borrar tabla... deseable o dejar igual al cambiar entre pestañas?? y si añaden/borran campeonatos/equipos??
	        		refreshDropBoxs();
	        	}
	        }
		});

	  
	  //Associate the Main panel with the HTML host page.
	  RootPanel.get("uiJuego").add(mainPanel);
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
  
  private final native int asInt(String json) /*-{
  	return eval(json);
  }-*/;
 
  private final native String[] asArrayOfString(String json) /*-{
  	return eval(json);
  }-*/;
  
  
  private void requestRanking(){

	    String url = JSON_URL;
		String circuit = circuitsList.get(circuitsDropBox.getSelectedIndex());
		String champ = champsList.get(champsDropBox.getSelectedIndex());
		String team = teamsList.get(teamsDropBox.getSelectedIndex());

	    url = URL.encode(url);

	    // Send request to server and catch any errors.
	    //RequestBuilder builder = new RequestBuilder(RequestBuilder.POST, url);
	    //builder.setHeader("Content-Type","application/x-www-form-urlencoded");

	    /*try {
	      Request request = builder.sendRequest(URL.encodeComponent("circuit")+"="+
	      URL.encodeComponent(circuit)+"&"+URL.encodeComponent("city")+"="+
	      URL.encodeComponent(city)+"&"+URL.encodeComponent("school")+"="+
	      URL.encodeComponent(school), new RequestCallback() {
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
	    }*/
	  }
  
  private void requestLogin() {
	  
	  if (loginUserTextBox.getText().equals("") || loginPassword.getText().equals("")){
		  Window.alert("Debes indicar tu nombre de usuario y contraseña");
	  }
	  else{
		  String url = JSON_URL;
		  url = URL.encode(url);
		  //Send request to server and catch any errors.
		  RequestBuilder builder = new RequestBuilder(RequestBuilder.POST, url);
		  builder.setHeader("Content-Type","application/x-www-form-urlencoded");

		  try {
			  Request request = builder.sendRequest(URL.encodeComponent("function")+"="+
		      URL.encodeComponent("login")+"&"+URL.encodeComponent("nick")+"="+
		      URL.encodeComponent(loginUserTextBox.getText())+"&"+URL.encodeComponent("password")+"="+
		      URL.encodeComponent(loginPassword.getText()), new RequestCallback() {
		        public void onError(Request request, Throwable exception) {
		        	Window.alert("Couldn't retrieve JSON");
		        }
		        public void onResponseReceived(Request request, Response response) {
		          if (200 == response.getStatusCode()) {
		        	  msgLabel.setText(response.getText());
		        	  msgLabel.setVisible(true);
		        	  int res = asInt(response.getText());
		        	  if(res==1) Window.alert("Usuario o contraseña incorrectos");
		        	  else if(res==2) Window.alert("Usuario pendiente de activación");
		        	  else if (res==0){
		        		  USER = loginUserTextBox.getText();
		        		  msgLabel.setText("bien!!");
		        		  //pasar a la vista del perfil del usuario
		        	  }
		        	  
		          } else {
		        	Window.alert("Couldn't retrieve JSON (" + response.getStatusText()+ ")");
		          }
		        }
		      });
		    } catch (RequestException e) {
		    	Window.alert("Couldn't retrieve JSON");
		    }
	  }
  }
  
  private void requestRegistration() {  //solo se permiten letras y numeros y..

	  if (regNickTextBox.getText().equals("") || regNameTextBox.getText().equals("")
		|| regSurname1TextBox.getText().equals("") || regSurname2TextBox.getText().equals("")
		|| regEmailUserTextBox.getText().equals("") || regCityTextBox.getText().equals("")
		|| regSchoolTextBox.getText().equals("") || regEmailSchoolTextBox.getText().equals("")
		|| regPassword.getText().equals("") || regConfirmPassword.getText().equals("")){
		  Window.alert("Debes rellenar todos los campos");
	  }
	  else if(!regPassword.getText().equals(regConfirmPassword.getText())){
		  Window.alert("La contraseña y su confirmación no coinciden. Por favor, introdúcelas de nuevo");
		  regPassword.setText("");
		  regConfirmPassword.setText("");
	  }
	  else{
		  String url = JSON_URL;
		  url = URL.encode(url);
		  //Send request to server and catch any errors.
		  RequestBuilder builder = new RequestBuilder(RequestBuilder.POST, url);
		  builder.setHeader("Content-Type","application/x-www-form-urlencoded");

		  try {
			  Request request = builder.sendRequest(URL.encodeComponent("function")+"="+
		      URL.encodeComponent("newUser")+"&"+URL.encodeComponent("nick")+"="+
		      URL.encodeComponent(regNickTextBox.getText())+"&"+URL.encodeComponent("name")+"="+
		      URL.encodeComponent(regNameTextBox.getText())+"&"+URL.encodeComponent("surname1")+"="+
		      URL.encodeComponent(regSurname1TextBox.getText())+"&"+URL.encodeComponent("surname2")+"="+
		      URL.encodeComponent(regSurname2TextBox.getText())+"&"+URL.encodeComponent("email_user")+"="+
		      URL.encodeComponent(regEmailUserTextBox.getText())+"&"+URL.encodeComponent("city")+"="+
		      URL.encodeComponent(regCityTextBox.getText())+"&"+URL.encodeComponent("school")+"="+
		      URL.encodeComponent(regSchoolTextBox.getText())+"&"+URL.encodeComponent("email_school")+"="+
		      URL.encodeComponent(regEmailSchoolTextBox.getText())+"&"+URL.encodeComponent("password")+"="+
		      URL.encodeComponent(regPassword.getText()), new RequestCallback() {
		        public void onError(Request request, Throwable exception) {
		        	Window.alert("Couldn't retrieve JSON");
		        }
		        public void onResponseReceived(Request request, Response response) {
		          if (200 == response.getStatusCode()) {
		        	  int res = asInt(response.getText());
		        	  if(res==1){ 
		        		  Window.alert("El nombre de usuario elegido ya existe");
			        	  regNickTextBox.selectAll();
		        	  }
		        	  else if(res==2) Window.alert("Se ha producido un error");
		        	  else if (res==0){
		        		  Window.alert("¡Te has registrado correctamente! En breve recibirás un correo en tu email de contacto para activar tu usuario");
		        		  regNickTextBox.setText("");  regNameTextBox.setText("");
		        		  regSurname1TextBox.setText("");  regSurname2TextBox.setText("");
		        		  regEmailUserTextBox.setText("");  regCityTextBox.setText("");
		        		  regSchoolTextBox.setText("");  regEmailSchoolTextBox.setText("");
		        		  regPassword.setText("");  regConfirmPassword.setText("");
		        	  }
		          } else {
		        	Window.alert("Couldn't retrieve JSON (" + response.getStatusText()+ ")");
		          }
		        }
		      });
		    } catch (RequestException e) {
		    	Window.alert("Couldn't retrieve JSON");
		    }
	  }
  }
  
  private void requestCircuits() {    //al iniciar la aplicacion una sola vez
	  
	  String url = JSON_URL;
	  url = URL.encode(url);
	  //Send request to server and catch any errors.
	  RequestBuilder builder = new RequestBuilder(RequestBuilder.POST, url);
	  builder.setHeader("Content-Type","application/x-www-form-urlencoded");

	  try {
		  Request request = builder.sendRequest(URL.encodeComponent("function")+"="+
		  URL.encodeComponent("getCircuito"), new RequestCallback() {
			  public void onError(Request request, Throwable exception) {
				  Window.alert("Couldn't retrieve JSON");
			  }
		      public void onResponseReceived(Request request, Response response) {
		    	  if (200 == response.getStatusCode()) {
		    		  String[] circuits = asArrayOfString(response.getText());
		    		  circuitsList.clear();
		    		  circuitsList.add("CIRCUITOS");
		    		  for(int i=0;i<circuits.length;i++) { circuitsList.add(circuits[i]); }
		          } else {
		        	Window.alert("Couldn't retrieve JSON (" + response.getStatusText()+ ")");
		          }
		      }
		  });
	  } catch (RequestException e) {
		  Window.alert("Couldn't retrieve JSON");
	  }
  }

  private void refreshDropBoxs() {   //cuando se elija la pestaña de ranking
	  
	  String url = JSON_URL;
	  url = URL.encode(url);
	  //Send request to server and catch any errors.
	  RequestBuilder builder = new RequestBuilder(RequestBuilder.POST, url);
	  builder.setHeader("Content-Type","application/x-www-form-urlencoded");

	  try {
		  Request request = builder.sendRequest(URL.encodeComponent("function")+"="+
		  URL.encodeComponent("getMyChampionship"), new RequestCallback() {
			  public void onError(Request request, Throwable exception) {
				  Window.alert("Couldn't retrieve JSON");
			  }
		      public void onResponseReceived(Request request, Response response) {
		    	  if (200 == response.getStatusCode()) {
		    		  String[] champs = asArrayOfString(response.getText());
		    		  champsList.clear();
		    		  champsList.add("CAMPEONATOS");
		    		  for(int i=0;i<champs.length;i++) { champsList.add(champs[i]); }
		    		  for(int i=0;i<champsList.size();i++) { champsDropBox.addItem(champsList.get(i)); }
		    		  champsDropBox.setEnabled(false);
		        	  
		          } else {
		        	Window.alert("Couldn't retrieve JSON (" + response.getStatusText()+ ")");
		          }
		      }
		  });
	  } catch (RequestException e) {
		  Window.alert("Couldn't retrieve JSON");
	  }
	  
	  try {
		  Request request = builder.sendRequest(URL.encodeComponent("function")+"="+
		  URL.encodeComponent("getMyTeams"), new RequestCallback() {
			  public void onError(Request request, Throwable exception) {
				  Window.alert("Couldn't retrieve JSON");
			  }
		      public void onResponseReceived(Request request, Response response) {
		    	  if (200 == response.getStatusCode()) {
		    		  String[] teams = asArrayOfString(response.getText());
		    		  teamsList.clear();
		    		  teamsList.add("EQUIPOS");
		    		  for(int i=0;i<teams.length;i++) { teamsList.add(teams[i]); }
		    		  for(int i=0;i<teamsList.size();i++) { teamsDropBox.addItem(teamsList.get(i)); }
		    		  teamsDropBox.setEnabled(false);
		          } else {
		        	Window.alert("Couldn't retrieve JSON (" + response.getStatusText()+ ")");
		          }
		      }
		  });
	  } catch (RequestException e) {
		  Window.alert("Couldn't retrieve JSON");
	  }
  }
  
  
  /**
   * Create a TextBox example that includes the text box and an optional
   * handler that updates a Label with the currently selected text.
   * 
   * @param textBox the text box to handle
   * @param addSelection add handlers to update label
   * @return the Label that will be updated
   */
  private HorizontalPanel createTextBox(final TextBoxBase textBox, String lab) {
    // Add the text box and label to a panel
    HorizontalPanel hPanel = new HorizontalPanel();
    hPanel.setSpacing(4);
    // Create the new label
    final Label label = new Label(lab);
    // Add the label to the box
    hPanel.add(label);
    hPanel.add(textBox);
    // Return the panel
    return hPanel;
  }


}