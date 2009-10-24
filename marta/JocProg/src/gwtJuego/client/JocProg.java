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
import com.google.gwt.user.client.ui.DecoratorPanel;
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
	
	private static final String JSON_URL = "http://localhost/php/main.php?";
	private static final int CODE = 1;
	private static final int COMPILE = 2;
	private static final int EXECUTION = 3;

	private TabPanel mainPanel = new TabPanel();
	private HorizontalSplitPanel correPanel = new HorizontalSplitPanel();
	private VerticalSplitPanel codiPanel = new VerticalSplitPanel();
	private HorizontalPanel consolaPanel = new HorizontalPanel();
	private HorizontalPanel inputPanel = new HorizontalPanel();
	private VerticalPanel rankingVPanel = new VerticalPanel();
	private HorizontalPanel rankingHPanel = new HorizontalPanel();
	private HorizontalPanel ranking2HPanel = new HorizontalPanel();
	private HorizontalPanel multiPanel = new HorizontalPanel();
	private VerticalPanel loginVPanel = new VerticalPanel();
	private DisclosurePanel registerDisclosure = new DisclosurePanel("¿Aún no estás registrado?");
	private VerticalPanel perfilVPanel = new VerticalPanel();
  
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
	private PasswordTextBox regNewPassword = new PasswordTextBox();
	private PasswordTextBox regConfirmPassword = new PasswordTextBox();

	private Button loginButton = new Button("Entrar");
	private Button logoutButton = new Button("Cerrar sesión");
	private Button regButton = new Button("Enviar");
	private Button changeButton = new Button("Modificar datos");
	private Button saveChangeButton = new Button("Guardar cambios");
	private Button playButton = new Button("¡Corre!");
	private Button stopButton = new Button("¡Para!");
	//private Label msgLabel = new Label();
  
	/*private String[] circuitsList;
   	private String[] champsList;
   	private String[] teamsList;*/
	
	private ArrayList circuitsList = new ArrayList();
   	private ArrayList champsList = new ArrayList();
   	private ArrayList teamsList = new ArrayList();
	
	private static String USER = "";
	private static int modeOn = CODE;
	private static String hPositionExec = "75%";
	private static String vPositionExec = "75%";
	private static String hPositionCode = "30%";
	private static String vPositionCode = "95%";
	private static String hPositionComp = "40%";
	private static String vPositionComp = "25%";
   

  /**
   * Entry point method.
   */
  public void onModuleLoad() {

	  createLoginPanel();
	  multiPanel.setSize("100%","100%");
	  if(USER=="") multiPanel.add(loginVPanel);
	  else{
		  createPerfilPanel();
		  multiPanel.add(perfilVPanel);
	  }
	  createCorrePanel();
	  createRankingPanel();
	    
	  loginButton.addClickHandler( 
			  new ClickHandler() {
				  public void onClick(ClickEvent event) {
					  requestLogin();
				  }
			  });
	 logoutButton.addClickHandler( 
			  new ClickHandler() {
				  public void onClick(ClickEvent event) {
					  logout();
				  }
			  });
	  regButton.addClickHandler( 
			  new ClickHandler() {
				  public void onClick(ClickEvent event) {
					  requestRegistration();
				  }
			  });
	  changeButton.addClickHandler( 
			  new ClickHandler() {
				  public void onClick(ClickEvent event) {
					  enableChanges();
				  }
			  });
	  saveChangeButton.addClickHandler( 
			  new ClickHandler() {
				  public void onClick(ClickEvent event) {
					  saveChanges();
				  }	
			  });
	    
	  //Assemble Main panel.
	  //mainPanel.setAnimationEnabled(true);
	  mainPanel.add(multiPanel,"Login/User");
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
				  switch (modeOn){
				  	case CODE:
						  codiPanel.setSplitPosition(hPositionCode);
						  correPanel.setSplitPosition(vPositionCode);
						  break;
				  	case COMPILE:
						  codiPanel.setSplitPosition(hPositionComp);
						  correPanel.setSplitPosition(vPositionComp);
						  break;
				  	case EXECUTION:
						  codiPanel.setSplitPosition(hPositionExec);
						  correPanel.setSplitPosition(vPositionExec);
						  break;
				  }
			  }
			  else if(tabIndex==2){
				  circuitsDropBox.setSelectedIndex(0);  //y borrar tabla...dejar igual al cambiar entre pestañas??
				  if(!USER.equals("")) refreshDropBoxs();  //INEFICIENTE!!y si añaden/borran campeonatos/equipos??
			  }
		  }
	  });
	  
	  //Associate the Main panel with the HTML host page.
	  RootPanel.get("uiJuego").add(mainPanel);
  }
  
  
  private void createLoginPanel(){
		
	  Grid loginLayout = new Grid(4, 2);
	  loginLayout.setCellSpacing(5);
	  CellFormatter loginCellFormatter = loginLayout.getCellFormatter();
	
	  // Add a title to the form
	  Label loginLabel = new Label("Identifícate para acceder a tu cuenta:");
	  loginVPanel.add(loginLabel);
	  loginVPanel.setCellVerticalAlignment(loginLabel,HasVerticalAlignment.ALIGN_BOTTOM);
	  
	  // Add login form
	  loginLayout.setHTML(1, 0, "Usuario: ");
	  loginLayout.setWidget(1, 1, loginUserTextBox);
	  loginLayout.setHTML(2, 0, "Contraseña: ");
	  loginLayout.setWidget(2, 1, loginPassword);
	  loginLayout.setWidget(3, 1, loginButton);
	  loginCellFormatter.setHorizontalAlignment(3,1,HasHorizontalAlignment.ALIGN_RIGHT);
	  loginVPanel.setSize("100%", "100%");
	  loginVPanel.add(loginLayout);
	  loginVPanel.setCellHeight(loginLayout, "135px");
	  loginVPanel.setCellVerticalAlignment(loginLayout,HasVerticalAlignment.ALIGN_TOP);
	  
	  // Create register form
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
	  regOptions.setHTML(5, 0, "Ciudad: ");
	  regOptions.setWidget(5, 1, regCityTextBox);
	  regOptions.setHTML(4, 0, "Email: ");
	  regOptions.setWidget(4, 1, regEmailUserTextBox);
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
	  
	  // Add register form in a disclosure panel
	  registerDisclosure.setAnimationEnabled(true);
	  registerDisclosure.setContent(regOptions);
	  loginVPanel.add(registerDisclosure);
	  loginVPanel.setCellVerticalAlignment(registerDisclosure,HasVerticalAlignment.ALIGN_TOP);
	  loginVPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
  }
  
  private void createPerfilPanel(){
	  
	  String url = JSON_URL;
	  url = URL.encode(url);
	  //Send request to server and catch any errors.
	  RequestBuilder builder = new RequestBuilder(RequestBuilder.POST, url);
	  builder.setHeader("Content-Type","application/x-www-form-urlencoded");

	  try {
		  Request request = builder.sendRequest(URL.encodeComponent("function")+"="+
		  URL.encodeComponent("getUser")+"&"+URL.encodeComponent("nick")+"="+
		  URL.encodeComponent(USER), new RequestCallback() {
			  public void onError(Request request, Throwable exception) {
				  Window.alert("Couldn't retrieve JSON");
			  }
		      public void onResponseReceived(Request request, Response response) {
		    	  if (200 == response.getStatusCode()) {
		    		  UserData res = asUserData(response.getText());
		    		  
		    		  perfilVPanel.add(logoutButton);
		    		    
		    		  Grid perfilInfo = new Grid(9, 4);
		    		  perfilInfo.setCellSpacing(6);
		    		  perfilInfo.setHTML(0, 0, "Usuario: ");
		    		  regNickTextBox.setText(res.getNick());
		    		  regNickTextBox.setEnabled(false);
		    		  perfilInfo.setWidget(0, 1, regNickTextBox);
		    		  perfilInfo.setHTML(1, 0, "Name: ");
		    		  regNameTextBox.setText(res.getName());
		    		  regNameTextBox.setEnabled(false);
		    		  perfilInfo.setWidget(1, 1, regNameTextBox);
		    		  perfilInfo.setHTML(2, 0, "Primer apellido: ");
		    		  regSurname1TextBox.setText(res.getSurname1());
		    		  regSurname1TextBox.setEnabled(false);
		    		  perfilInfo.setWidget(2, 1, regSurname1TextBox);
		    		  perfilInfo.setHTML(2, 2, "Segundo apellido: ");
		    		  regSurname2TextBox.setText(res.getSurname2());
		    		  regSurname2TextBox.setEnabled(false);
		    		  perfilInfo.setWidget(2, 3, regSurname2TextBox);
		    		  perfilInfo.setHTML(3, 0, "Ciudad: ");
		    		  regCityTextBox.setText(res.getCity());
		    		  regCityTextBox.setEnabled(false);
		    		  perfilInfo.setWidget(3, 1, regCityTextBox);
		    		  perfilInfo.setHTML(3, 2, "Email: ");
		    		  regEmailUserTextBox.setText(res.getEmailUser());
		    		  regEmailUserTextBox.setEnabled(false);
		    		  perfilInfo.setWidget(3, 3, regEmailUserTextBox);
		    		  perfilInfo.setHTML(5, 0, "Escuela: ");
		    		  regSchoolTextBox.setText(res.getSchool());
		    		  regSchoolTextBox.setEnabled(false);
		    		  perfilInfo.setWidget(5, 1, regSchoolTextBox);
		    		  perfilInfo.setHTML(5, 2, "Email de la escuela: ");
		    		  regEmailSchoolTextBox.setText(res.getEmailSchool());
		    		  regEmailSchoolTextBox.setEnabled(false);
		    		  perfilInfo.setWidget(5, 3, regEmailSchoolTextBox);
		    		  perfilInfo.setHTML(6, 0, "Contraseña: ");
		    		  regPassword.setText("password");
		    		  regPassword.setEnabled(false);
		    		  perfilInfo.setWidget(6, 1, regPassword);
		    		  perfilInfo.setHTML(7, 0, "Nueva contraseña");
		    		  regNewPassword.setText("");
		    		  regNewPassword.setEnabled(false);
		    		  regNewPassword.setVisible(false);
		    		  perfilInfo.setWidget(7, 1, regNewPassword);
		    		  perfilInfo.setHTML(7, 2, "Confirmar contraseña: ");
		    		  regConfirmPassword.setText("");
		    		  regConfirmPassword.setEnabled(false);
		    		  regConfirmPassword.setVisible(false);
		    		  perfilInfo.setWidget(7, 3, regConfirmPassword);
		    		  perfilInfo.setWidget(8,3, changeButton);
		    		  saveChangeButton.setEnabled(false);
		    		  saveChangeButton.setVisible(false);
		    		  perfilInfo.setWidget(8,3, saveChangeButton);
		    		  CellFormatter perfilInfoCellFormatter = perfilInfo.getCellFormatter();
		    		  perfilInfoCellFormatter.setHorizontalAlignment(8,3,HasHorizontalAlignment.ALIGN_RIGHT);
		    		  perfilVPanel.setSize("100%","100%");
		    		  perfilVPanel.add(perfilInfo);
		    		  perfilVPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);  
		
		    		  	    		  
		          } else {
		        	Window.alert("Couldn't retrieve JSON (" + response.getStatusText()+ ")");
		          }
		      }
		  });
	  } catch (RequestException e) {
		  Window.alert("Couldn't retrieve JSON");
	  }
  }
  
  private void createCorrePanel(){
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
	  VerticalPanel buttonsPanel = new VerticalPanel();
	  buttonsPanel.add(playButton);
	  stopButton.setEnabled(false);
	  buttonsPanel.add(stopButton);
	  //correPanel.setLeftWidget(new Label("CIRCUITO"));
	  correPanel.setLeftWidget(buttonsPanel);
	  correPanel.setRightWidget(consolaPanel);
		  
	  codiPanel.setSize("100%","100%");
	  codiPanel.setSplitPosition("50%");
	  codiPanel.setTopWidget(correPanel);
	  codiPanel.setBottomWidget(inputPanel);
	  
	  playButton.addClickHandler( 
			  new ClickHandler() {
				  public void onClick(ClickEvent event) {
					  if(modeOn == CODE) modeOn = COMPILE;
					  else modeOn = EXECUTION;
					  changeMode();
				  }
			  });
	  stopButton.addClickHandler( 
			  new ClickHandler() {
				  public void onClick(ClickEvent event) {
					  modeOn = CODE;
					  changeMode();
				  }
			  });
  }
  
  private void createRankingPanel(){
	  
	  // Create a panel to align the Widgets
	  rankingHPanel.setSize("100%", "30%");
	  rankingHPanel.setSpacing(10);
	  rankingHPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);

	  requestCircuits();
	  // Add drop boxs with the lists
	  circuitsDropBox.clear();
	  circuitsDropBox.addItem("CIRCUITOS");
	  //for (int i=0; i<circuitsList.length; i++) { circuitsDropBox.addItem(circuitsList[i]); }
	  for (int i=0; i<circuitsList.size(); i++) { circuitsDropBox.addItem((String)circuitsList.get(i)); }
	  champsDropBox.clear();
	  champsDropBox.addItem("CAMPEONATOS");
	  champsDropBox.setEnabled(false);
	  teamsDropBox.clear();
	  teamsDropBox.addItem("EQUIPOS");
	  teamsDropBox.setEnabled(false);
	  rankingHPanel.add(circuitsDropBox);
	  rankingHPanel.add(champsDropBox);
	  rankingHPanel.add(teamsDropBox);
	    
	  // Create table for ranking data.
	  rankingFlexTable.setText(0, 0, "Usuario");
	  rankingFlexTable.setText(0, 1, "Tiempo");
	  ranking2HPanel.setSize("100%", "70%");
	  ranking2HPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
	  ranking2HPanel.add(rankingFlexTable);
	  rankPagesDropBox.clear();
	  rankPagesDropBox.addItem("Página");
	  rankPagesDropBox.setEnabled(false);
	  ranking2HPanel.add(rankPagesDropBox);
	    
	  rankingVPanel.setSize("100%","100%");
	  rankingVPanel.add(rankingHPanel);
	  rankingVPanel.add(ranking2HPanel);
	  
	  // Listen for events on the DropBoxs.
	  circuitsDropBox.addChangeHandler(new ChangeHandler() {
		  public void onChange(ChangeEvent event) {
			  if(circuitsDropBox.getSelectedIndex()>0){
				  requestRanking();
				  champsDropBox.setEnabled(true);
				  teamsDropBox.setEnabled(true);
			  }
			  else{
				  champsDropBox.setSelectedIndex(0);
				  champsDropBox.setEnabled(false);
				  teamsDropBox.setSelectedIndex(0);
				  teamsDropBox.setEnabled(false);	
				  Window.alert("Debes elegir un circuito válido");
			  }
			  rankPagesDropBox.clear();
			  rankPagesDropBox.addItem("Página");
			  rankPagesDropBox.setEnabled(false);
		  }
	  });
	  champsDropBox.addChangeHandler(new ChangeHandler() {
		  public void onChange(ChangeEvent event) {
			  if(champsDropBox.getSelectedIndex()>0) requestRanking();
		  }
	  });
	  teamsDropBox.addChangeHandler(new ChangeHandler() {
		  public void onChange(ChangeEvent event) {
			  if(teamsDropBox.getSelectedIndex()>0) requestRanking();
		  }
	  });
	  rankPagesDropBox.addChangeHandler(new ChangeHandler() {  //tener en cuenta si esta logeado!!algo de paginas?
		  public void onChange(ChangeEvent event) {
			  if(rankPagesDropBox.getSelectedIndex()>0) requestRanking();
		  }
	  });
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
		        	  int res = asInt(response.getText());
		        	  if(res==1) Window.alert("Usuario o contraseña incorrectos");
		        	  else if(res==2) Window.alert("Usuario pendiente de activación");
		        	  else if (res==0){
		        		  USER = loginUserTextBox.getText();
		        		  createPerfilPanel();
		        		  multiPanel.remove(loginVPanel);
		        		  multiPanel.add(perfilVPanel);
		        		  loginUserTextBox.setText("");
		        		  loginPassword.setText("");
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
		    		  circuitsList = asArrayOfString(response.getText());
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
		    		  champsList = asArrayOfString(response.getText());
		    		  champsDropBox.clear();
		    		  champsDropBox.addItem("CAMPEONATOS");
		    		  //for(int i=0;i<champsList.length;i++) { champsDropBox.addItem(champsList[i]); }
		    		  for(int i=0;i<champsList.size();i++) { champsDropBox.addItem((String)champsList.get(i)); }
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
		    		  teamsList = asArrayOfString(response.getText());
		    		  teamsDropBox.clear();
		    		  teamsDropBox.addItem("EQUIPOS");
		    		  //for(int i=0;i<teamsList.length;i++) { teamsDropBox.addItem(teamsList[i]); }
		    		  for(int i=0;i<teamsList.size();i++) { teamsDropBox.addItem((String)teamsList.get(i)); }
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
  
  private void requestRanking(){

	  if(circuitsDropBox.getSelectedIndex()==0){
		  Window.alert("Debes elegir un circuito válido");
	  }
	  else {
		  int sizepage = 50;
		  int page = rankPagesDropBox.getSelectedIndex();
		  //String circuit = circuitsList[circuitsDropBox.getSelectedIndex() - 1];
		  String circuit = (String)circuitsList.get(circuitsDropBox.getSelectedIndex() - 1);
		  String champ;
		  if(champsDropBox.getSelectedIndex()==0) champ = "";
		  //else champ = champsList[champsDropBox.getSelectedIndex() - 1];
		  else champ = (String)champsList.get(champsDropBox.getSelectedIndex() - 1);
		  String team;
		  if(teamsDropBox.getSelectedIndex()==0) team = "";
		  //else team = teamsList[teamsDropBox.getSelectedIndex() - 1];
		  else team = (String)teamsList.get(teamsDropBox.getSelectedIndex() - 1);

		  String url = JSON_URL;
		  url = URL.encode(url);
		  // Send request to server and catch any errors.
		  RequestBuilder builder = new RequestBuilder(RequestBuilder.POST, url);
		  builder.setHeader("Content-Type","application/x-www-form-urlencoded");

		  try {
			  Request request = builder.sendRequest(URL.encodeComponent("function")+"="+
					  URL.encodeComponent("getRankings")+"&"+URL.encodeComponent("circuito")+"="+
					  URL.encodeComponent(circuit)+"&"+URL.encodeComponent("team")+"="+
					  URL.encodeComponent(team)+"&"+URL.encodeComponent("championship")+"="+
					  URL.encodeComponent(champ)+"&"+URL.encodeComponent("page")+"="+
					  URL.encodeComponent(String.valueOf(page))+"&"+URL.encodeComponent("sizepage")+"="+
					  URL.encodeComponent(String.valueOf(sizepage)), new RequestCallback() {
				  public void onError(Request request, Throwable exception) {
					  Window.alert("Couldn't retrieve JSON");
				  }

				  public void onResponseReceived(Request request, Response response) {
					  if (200 == response.getStatusCode()) {
						  RankingData res = asRankingData(response.getText());
						  updateTable(res.getData());
						  int pag = res.getPage();
						  int npag = res.getNumPages();
						  rankPagesDropBox.clear();
						  rankPagesDropBox.addItem("Página");
						  for(int i=1;i<=npag;i++) rankPagesDropBox.addItem(String.valueOf(i));
						  rankPagesDropBox.setSelectedIndex(pag);
						  rankPagesDropBox.setEnabled(true);
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
  
  
  
  

  private void logout(){
	  USER = ""; 
	  
	  multiPanel.remove(perfilVPanel);
	  multiPanel.add(loginVPanel);
	  
	  regNickTextBox.setText("");  regNameTextBox.setText("");
	  regSurname1TextBox.setText("");  regSurname2TextBox.setText("");
	  regEmailUserTextBox.setText("");  regCityTextBox.setText("");
	  regSchoolTextBox.setText("");  regEmailSchoolTextBox.setText("");
	  regPassword.setText("");  regConfirmPassword.setText("");
	  
	  regNickTextBox.setEnabled(true); regNameTextBox.setEnabled(true);
	  regSurname1TextBox.setEnabled(true);  regSurname2TextBox.setEnabled(true);
	  regEmailUserTextBox.setEnabled(true);  regCityTextBox.setEnabled(true);
	  regSchoolTextBox.setEnabled(true);  regEmailSchoolTextBox.setEnabled(true);
	  regPassword.setEnabled(true); regConfirmPassword.setEnabled(true);
	  regConfirmPassword.setVisible(true);
	  
	  circuitsDropBox.setSelectedIndex(0);
	  champsDropBox.clear();
	  champsDropBox.addItem("CAMPEONATOS");
	  champsDropBox.setEnabled(false);
	  teamsDropBox.clear();
	  teamsDropBox.addItem("EQUIPOS");
	  teamsDropBox.setEnabled(false);
	  rankPagesDropBox.clear();
	  rankPagesDropBox.addItem("Página");
	  rankPagesDropBox.setEnabled(false);
  }
  
  
  private void enableChanges(){
	  regNameTextBox.setEnabled(true);
	  regSurname1TextBox.setEnabled(true);  regSurname2TextBox.setEnabled(true);
	  regEmailUserTextBox.setEnabled(true);  regCityTextBox.setEnabled(true);
	  regSchoolTextBox.setEnabled(true);  regEmailSchoolTextBox.setEnabled(true);
	  regPassword.setText("");  
	  regPassword.setEnabled(true); 
	  regNewPassword.setText(""); regConfirmPassword.setText("");
	  regNewPassword.setEnabled(true); regConfirmPassword.setEnabled(true);
	  regNewPassword.setVisible(true); regConfirmPassword.setVisible(true);
	  changeButton.setEnabled(false); changeButton.setVisible(false);
	  saveChangeButton.setEnabled(true); saveChangeButton.setVisible(true);
  }
  
  private void disableChanges(){
	  regNameTextBox.setEnabled(false);
	  regSurname1TextBox.setEnabled(false);  regSurname2TextBox.setEnabled(false);
	  regEmailUserTextBox.setEnabled(false);  regCityTextBox.setEnabled(false);
	  regSchoolTextBox.setEnabled(false);  regEmailSchoolTextBox.setEnabled(false);
	  regPassword.setText("password");  
	  regPassword.setEnabled(false); 
	  regNewPassword.setText(""); regConfirmPassword.setText("");
	  regNewPassword.setEnabled(false); regConfirmPassword.setEnabled(false);
	  regNewPassword.setVisible(false); regConfirmPassword.setVisible(false);
	  changeButton.setEnabled(true); changeButton.setVisible(true);
	  saveChangeButton.setEnabled(false); saveChangeButton.setVisible(false);
  }
  
  private void saveChanges(){
	  
	  if (regNameTextBox.getText().equals("") || regSurname1TextBox.getText().equals("") 
			  || regSurname2TextBox.getText().equals("") || regEmailUserTextBox.getText().equals("") 
			  || regCityTextBox.getText().equals("") || regSchoolTextBox.getText().equals("") 
			  || regEmailSchoolTextBox.getText().equals("")){
				  Window.alert("Debes rellenar todos los campos");
			  }
	  else if (!regNewPassword.getText().equals("") && regPassword.getText().equals("")){
		  Window.alert("Para cambiar la contraseña debes introducir la actual");
	  }
	  else if (!regNewPassword.getText().equals("") && regConfirmPassword.getText().equals("")){
		  Window.alert("Debes introducir la confirmación de la nueva contraseña");
	  }
	  else if(!regNewPassword.getText().equals(regConfirmPassword.getText())){
		  Window.alert("La nueva contraseña y su confirmación no coinciden. Por favor, introdúcelas de nuevo");
		  regPassword.setText("");
		  regNewPassword.setText("");
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
					  URL.encodeComponent("changeUser")+"&"+URL.encodeComponent("name")+"="+
				      URL.encodeComponent(regNameTextBox.getText())+"&"+URL.encodeComponent("surname1")+"="+
				      URL.encodeComponent(regSurname1TextBox.getText())+"&"+URL.encodeComponent("surname2")+"="+
				      URL.encodeComponent(regSurname2TextBox.getText())+"&"+URL.encodeComponent("email_user")+"="+
				      URL.encodeComponent(regEmailUserTextBox.getText())+"&"+URL.encodeComponent("city")+"="+
				      URL.encodeComponent(regCityTextBox.getText())+"&"+URL.encodeComponent("school")+"="+
				      URL.encodeComponent(regSchoolTextBox.getText())+"&"+URL.encodeComponent("email_school")+"="+
				      URL.encodeComponent(regEmailSchoolTextBox.getText())+"&"+URL.encodeComponent("oldpassword")+"="+
				      URL.encodeComponent(regPassword.getText())+"&"+URL.encodeComponent("password")+"="+
				      URL.encodeComponent(regNewPassword.getText()), new RequestCallback() {
				        public void onError(Request request, Throwable exception) {
				        	Window.alert("Couldn't retrieve JSON");
				        }
				        public void onResponseReceived(Request request, Response response) {
				          if (200 == response.getStatusCode()) {
				        	  int res = asInt(response.getText());
				        	  if(res==2){ 
				        		  Window.alert("Contraseña incorrecta");
					        	  regPassword.selectAll();
				        	  }
				        	  else if(res==1) Window.alert("Se ha producido un error");
				        	  else if (res==0){
				        		  Window.alert("¡Los cambios se han realizado con éxito!");
				        		  disableChanges();
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
  
  private void changeMode(){
	  switch (modeOn){
	  	case CODE:
	  		playButton.setEnabled(true);
	  		stopButton.setEnabled(false);
			codiPanel.setSplitPosition(hPositionCode);
			correPanel.setSplitPosition(vPositionCode);
			break;
	  	case COMPILE:
	  		playButton.setEnabled(true);
	  		stopButton.setEnabled(false);
			codiPanel.setSplitPosition(hPositionComp);
			correPanel.setSplitPosition(vPositionComp);
			break;
	  	case EXECUTION:
	  		playButton.setEnabled(false);
	  		stopButton.setEnabled(true);
			codiPanel.setSplitPosition(hPositionExec);
			correPanel.setSplitPosition(vPositionExec);
			break;
	  }
  }
  
  /**
   * Update the "Usuario" and "Tiempo" fields for all rows in the ranking table.
   * @param ranking data for all rows.
   */
  private void updateTable(JsArray<RankingUserData> rankInfo) {
    for (int i=0; i<rankInfo.length(); i++) updateTable(rankInfo.get(i));
  }
  
  /**
   * Update a single row in the ranking table.
   * @param ranking data for a single row.
  */
  private void updateTable(RankingUserData info) { 
     int row = rankingFlexTable.getRowCount();
     // Populate the "Usuario" and "Tiempo" fields with new data.
     rankingFlexTable.setText(row, 0, info.getNick());
     rankingFlexTable.setText(row, 1, info.getTiempo());
  }
  
  
  /**
   * Convert the string of JSON into JavaScript object.
   */  
  private final native RankingData asRankingData(String json) /*-{
	return eval(json);
  }-*/; 
  
  private final native UserData asUserData(String json) /*-{
	return eval(json);
  }-*/;
  
  private final native int asInt(String json) /*-{
  	return eval(json);
  }-*/;
 
  //private final native String[] asArrayOfString(String json) /*-{
  private final native ArrayList asArrayOfString(String json) /*-{
  	return eval(json);
  }-*/;
  
  
  
  /**
   * Create a TextBox example that includes the text box and an optional
   * handler that updates a Label with the currently selected text.
   * 
   * @param textBox the text box to handle
   * @param addSelection add handlers to update label
   * @return the Label that will be updated
   */
  /*private HorizontalPanel createTextBox(final TextBoxBase textBox, String lab) {
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
  }*/
}