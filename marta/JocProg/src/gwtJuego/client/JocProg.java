package gwtJuego.client;

import java.util.ArrayList;
import java.util.Date;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.client.JsArrayString;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.dom.client.KeyCodes.*; 
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.http.client.URL;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.DecoratedStackPanel;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.KeyboardListener;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.MouseListener;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SourcesTabEvents;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.TabListener;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.HorizontalSplitPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.TreeListener;
import com.google.gwt.user.client.ui.VerticalSplitPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTMLTable.CellFormatter;
import com.google.gwt.user.datepicker.client.DateBox;


import gwtJuego.client.RankingData;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.i18n.client.DateTimeFormat;


public class JocProg implements EntryPoint {
	
	//private static final String LOCAL_URL = "http://localhost/";
	//private static final String JSON_URL = "http://localhost/php/main.php?";
	//private static final String IMG_URL = "http://localhost/img/";
	private static final String LOCAL_URL = "http://gabarro.org/racing/";
	private static final String JSON_URL = "http://gabarro.org/racing/php/main.php?";
	private static final String IMG_URL = "http://gabarro.org/racing/img/";
	private static final int CODE = 1;
	private static final int DEBUG = 2;
	private static final int EXECUTION = 3;
	private static final int TRAIN = 1;
	private static final int CHAMP = 2;
	private static final int NONE = 0;
	
	private AnimationEngine engine = new AnimationEngine();
	private static RequestBuilder builder;

	private TabPanel mainPanel = new TabPanel();
	private HorizontalSplitPanel correPanel = new HorizontalSplitPanel();
	private VerticalSplitPanel codiPanel = new VerticalSplitPanel();
	private AbsolutePanel imagePanel = new AbsolutePanel();
	private AbsolutePanel progressAbsPanel = new AbsolutePanel();  //se podria (pasando parametros)
	private HTMLPanel centerImagePanel;
	private DialogBox modeDialogBox = new DialogBox();
	private VerticalPanel rankingVPanel = new VerticalPanel();  //se podria (return)
	private HorizontalPanel multiPanel = new HorizontalPanel();
	private VerticalPanel loginVPanel = new VerticalPanel();
	private VerticalPanel perfilVPanel = new VerticalPanel();
	private HorizontalPanel adminHPanel = new HorizontalPanel();
	private DecoratedStackPanel adminStckPanel = new DecoratedStackPanel();
	
	private TextArea inputTextArea = new TextArea();
	private TextArea consolaTextArea = new TextArea();
	private ListBox modeChampListBox = new ListBox(false);
	private ListBox circuitsDropBox = new ListBox(false);
	private ListBox champsDropBox = new ListBox(false);
	private ListBox teamsDropBox = new ListBox(false);
	private ListBox rankPagesDropBox = new ListBox(false);
	private ListBox sizePagesDropBox = new ListBox(false);
	private ListBox circuitsMultiBox = new ListBox(true);
	private ListBox selectedMultiBox = new ListBox(true);
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
	private Label newPswdLabel = new Label("Nueva contraseña");
	private Label confirmPswdLabel = new Label("Confirmar contraseña");
	
	private TextBox champNameTextBox = new TextBox(); //
	private TextBox teamNameTextBox = new TextBox(); //
	private DateBox champDateBox = new DateBox(); //
	
	private TreeItem ItemChampInvitations = new TreeItem();
	private TreeItem ItemTeamInvitations = new TreeItem();
	private SuggestBox suggestNickBox;
	private ListBox addPlayersDropBox = new ListBox(false);
	private Label addPlayersLabel = new Label();
	private FlexTable invitationsFlexTable = new FlexTable();

	private Button loginButton = new Button("Entrar");
	private Button logoutButton = new Button("Cerrar sesión");
	private Button regButton = new Button("Enviar");
	private Button changeButton = new Button("Modificar datos");
	private Button saveChangeButton = new Button("Guardar cambios");
	private PushButton playButton;
	private PushButton stopButton;
	
	
	private ArrayList<CircuitInfo> circuitsList = new ArrayList<CircuitInfo>();
	private ArrayList<CircuitInfo> champCircuitsList = new ArrayList<CircuitInfo>();
   	private ArrayList<String> champsList = new ArrayList<String>(); //??
   	private ArrayList<String> teamsList = new ArrayList<String>(); //??
   	private ArrayList<String> invitationsList = new ArrayList<String>(); //??
   	//private String[] pageSizes = {"Tamaño","10","25","50","75","100"};
   	private String[] pageSizes = {"Tamaño","1","2","3","4","5","6","7","8","9","10"};
   	private MultiWordSuggestOracle oracle = new MultiWordSuggestOracle();
   	private static boolean whileReqOn = false;
	
	private static String USER = "";
	private static int modeOn = CODE;
	private static final int hPositionExec = 75;
	private static final int vPositionExec = 75;
	private static final int hPositionCode = 30;
	private static final int vPositionCode = 95;
	private static final int hPositionComp = 40;
	private static final int vPositionComp = 25;
   
	
	private int playMode = NONE;
	private String champOn = "";
	private String circuitOn = "Montmelo";   //que poner en default??
	private String circuitURL = IMG_URL+"basic.png";
	private int circuitWidth = 0;
	private int circuitHeight = 0;
	private int indexToShow = 0;
	
	private Image firstCirc = new Image();
	private Image secondCirc = new Image();
	private Image thirdCirc = new Image();
	private PushButton leftPushButton;
	private PushButton rightPushButton;
	private RadioButton firstRadioButton = new RadioButton("circuits", "");
	private RadioButton secondRadioButton = new RadioButton("circuits", "");
	private RadioButton thirdRadioButton = new RadioButton("circuits", "");
	

   /**
   * Entry point method.
   */
  public void onModuleLoad() {
	  
	  String url = JSON_URL;
	  url = URL.encode(url);
	  //Send request to server and catch any errors.
	  builder = new RequestBuilder(RequestBuilder.POST, url);
	  builder.setHeader("Content-Type","application/x-www-form-urlencoded");

	  requestCircuits();
	  createLoginPanel();
	  createAdminPanel();
	  multiPanel.setSize("100%","100%");
	  if(USER.equals("")) multiPanel.add(loginVPanel);
	  else{
		  createPerfilPanel();
		  multiPanel.add(adminHPanel);
	  }
	  createRunPanel();
	  createRankingPanel();
	  createPopupPanel();

	    
	  loginButton.addClickHandler( 
			  new ClickHandler() {
				  public void onClick(ClickEvent event) {
					  if (!whileReqOn){
						  whileReqOn = true;
						  requestLogin();
					  }
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
	  mainPanel.add(multiPanel,"Inicio");
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
			  if(tabIndex==0 && !USER.equals("")){ 
				  requestNInvitations();
			  }
			  if(tabIndex==1){
				  switch (modeOn){
				  	case CODE:
						  codiPanel.setSplitPosition(hPositionCode +"%");
						  correPanel.setSplitPosition(vPositionCode +"%");
						  break;
				  	case DEBUG:
						  codiPanel.setSplitPosition(hPositionComp +"%");
						  correPanel.setSplitPosition(vPositionComp +"%");
						  break;
				  	case EXECUTION:
						  codiPanel.setSplitPosition(hPositionExec +"%");
						  correPanel.setSplitPosition(vPositionExec +"%");
						  break;
				  }
				  if(playMode == NONE){
					  indexToShow = 0;
					  displayCircuitsImages(circuitsList);
					  if(!USER.equals("")) refreshListBox(modeChampListBox);
					  modeDialogBox.center();
					  modeDialogBox.show();
				  }
			  }
			  else if(tabIndex==2){
				  circuitsDropBox.setSelectedIndex(0);
				  champsDropBox.setSelectedIndex(0);
				  teamsDropBox.setSelectedIndex(0);
				  rankPagesDropBox.setSelectedIndex(0);
				  sizePagesDropBox.setSelectedIndex(0);
				  champsDropBox.setEnabled(false);
				  teamsDropBox.setEnabled(false);
				  rankPagesDropBox.setEnabled(false);
				  sizePagesDropBox.setEnabled(false);
				  int rows = rankingFlexTable.getRowCount();
				  for (int i=1; i<rows; i++) rankingFlexTable.removeRow(1); 
			  }
		  }
	  });
	  
	  //Associate the Main panel with the HTML host page.
	  RootPanel.get("uiJuego").add(mainPanel);
  }
  
  
  private void createLoginPanel(){
			
	  // Add a title to the form
	  loginVPanel.clear();
	  loginVPanel.setSize("100%", "100%");
	  loginVPanel.setSpacing(10);
	  
	  VerticalPanel loginFormPanel = new VerticalPanel();
	  loginFormPanel.setSpacing(15);
	  Label loginLabel = new Label("Identifícate para acceder a tu cuenta:");
	  Grid loginLayout = new Grid(2, 2);
	  loginLayout.setCellSpacing(10);
	  // Add login form
	  loginLayout.setHTML(0, 0, "Usuario: ");
	  loginLayout.setWidget(0, 1, loginUserTextBox);
	  loginLayout.setHTML(1, 0, "Contraseña: ");
	  loginLayout.setWidget(1, 1, loginPassword);
	  loginFormPanel.add(loginLabel);
	  loginFormPanel.add(loginLayout);
	  loginFormPanel.add(loginButton);
	  loginFormPanel.setCellHorizontalAlignment(loginButton, HasHorizontalAlignment.ALIGN_CENTER);
	  loginFormPanel.addStyleName("inputForm");
	  
	  // Create register form
	  Grid regOptions = new Grid(6, 3);
	  regOptions.setWidget(0, 0, addInVerticalPanel(new Label("Usuario:"), regNickTextBox));
	  regOptions.setWidget(1, 0, addInVerticalPanel(new Label("Nombre:"), regNameTextBox));
	  regOptions.setWidget(1, 1, addInVerticalPanel(new Label("Primer apellido:"), regSurname1TextBox));
	  regOptions.setWidget(1, 2, addInVerticalPanel(new Label("Segundo apellido:"), regSurname2TextBox));
	  regOptions.setWidget(2, 0, addInVerticalPanel(new Label("Email:"), regEmailUserTextBox));
	  regOptions.setWidget(2, 1, addInVerticalPanel(new Label("Ciudad:"), regCityTextBox));
	  regOptions.setWidget(3, 0, addInVerticalPanel(new Label("Escuela:"), regSchoolTextBox));
	  regOptions.setWidget(3, 1, addInVerticalPanel(new Label("Email de la escuela:"), regEmailSchoolTextBox));
	  regOptions.setWidget(4, 0, addInVerticalPanel(new Label("Contraseña:"), regPassword));
	  regOptions.setWidget(4, 1, addInVerticalPanel(new Label("Confirmar contraseña:"), regConfirmPassword));
	  regOptions.setWidget(5, 1, regButton);
	  CellFormatter regOpsCellFormatter = regOptions.getCellFormatter();
	  regOpsCellFormatter.setHeight(5, 1,"40px");
	  regOpsCellFormatter.setVerticalAlignment(5,1,HasVerticalAlignment.ALIGN_BOTTOM);
	  regOpsCellFormatter.setHorizontalAlignment(5,1,HasHorizontalAlignment.ALIGN_CENTER);
	 
	  // Add register form in a disclosure panel
	  DisclosurePanel registerDisclosure = new DisclosurePanel("¿Aún no estás registrado?");
	  registerDisclosure.setAnimationEnabled(true);
	  registerDisclosure.setContent(regOptions);
	  
	  loginVPanel.add(registerDisclosure);
	  loginVPanel.add(loginFormPanel);

	  loginVPanel.setCellVerticalAlignment(loginFormPanel,HasVerticalAlignment.ALIGN_TOP);
	  loginVPanel.setCellVerticalAlignment(registerDisclosure,HasVerticalAlignment.ALIGN_TOP);
	  loginVPanel.setCellHorizontalAlignment(loginFormPanel,HasHorizontalAlignment.ALIGN_CENTER);
	  
	  loginPassword.addKeyDownHandler(
			  new KeyDownHandler(){
				  public void onKeyDown(KeyDownEvent event){
					  if(event.getNativeKeyCode() == KeyCodes.KEY_ENTER && !whileReqOn){
						  whileReqOn = true;
						  Window.alert("enter key pressed. user value: "+USER);	  
						  requestLogin();
					  }
				  }
			  });
  }
  
  private void createPerfilPanel(){

	  try {
		  String requestStr = encodeParam("function", "getUser")+"&"+
		  	encodeParam("nick", USER);

		  Request request = builder.sendRequest(requestStr, new RequestCallback() {	
			  public void onError(Request request, Throwable exception) {
				  Window.alert("Couldn't retrieve JSON");
			  }
		      public void onResponseReceived(Request request, Response response) {
		    	  if (200 == response.getStatusCode()) {
		    		  UserData res = asUserData(response.getText());
		    		  
		    		  perfilVPanel.clear();

		    		  Grid perfilInfo = new Grid(5,3);
		    		  perfilInfo.setCellSpacing(5);
		    		  
		    		  regNickTextBox.setText(res.getNick());
		    		  regNickTextBox.setEnabled(false);
		    		  perfilInfo.setWidget(0, 0, addInVerticalPanel(new Label("Usuario:"), regNickTextBox));
		    		  
		    		  regNameTextBox.setText(res.getName());
		    		  regNameTextBox.setEnabled(false);
		    		  perfilInfo.setWidget(1, 0, addInVerticalPanel(new Label("Nombre:"), regNameTextBox));
		    		  
		    		  regSurname1TextBox.setText(res.getSurname1());
		    		  regSurname1TextBox.setEnabled(false);
		    		  perfilInfo.setWidget(1, 1, addInVerticalPanel(new Label("Primer apellido:"), regSurname1TextBox));

		    		  regSurname2TextBox.setText(res.getSurname2());
		    		  regSurname2TextBox.setEnabled(false);
		    		  perfilInfo.setWidget(1, 2, addInVerticalPanel(new Label("Segundo apellido:"), regSurname2TextBox));
		    		  
		    		  regEmailUserTextBox.setText(res.getEmailUser());
		    		  regEmailUserTextBox.setEnabled(false);
		    		  perfilInfo.setWidget(2, 0, addInVerticalPanel(new Label("Email:"), regEmailUserTextBox));

		    		  regCityTextBox.setText(res.getCity());
		    		  regCityTextBox.setEnabled(false);
		    		  perfilInfo.setWidget(2, 1, addInVerticalPanel(new Label("Ciudad:"), regCityTextBox));
		    		  
		    		  regSchoolTextBox.setText(res.getSchool());
		    		  regSchoolTextBox.setEnabled(false);
		    		  perfilInfo.setWidget(3, 0, addInVerticalPanel(new Label("Escuela:"), regSchoolTextBox));
		    		  
		    		  regEmailSchoolTextBox.setText(res.getEmailSchool());
		    		  regEmailSchoolTextBox.setEnabled(false);
		    		  perfilInfo.setWidget(3, 1, addInVerticalPanel(new Label("Email de la escuela:"), regEmailSchoolTextBox));
		    		  
		    		  regPassword.setText("password");
		    		  regPassword.setEnabled(false);
		    		  perfilInfo.setWidget(4, 0, addInVerticalPanel(new Label("Contraseña:"), regPassword));
		    		  
		    		  newPswdLabel.setVisible(false);
		    		  regNewPassword.setText("");
		    		  regNewPassword.setEnabled(false);
		    		  regNewPassword.setVisible(false);
		    		  perfilInfo.setWidget(4, 1, addInVerticalPanel(newPswdLabel, regNewPassword));
		    		  
		    		  confirmPswdLabel.setVisible(false);
		    		  regConfirmPassword.setText("");
		    		  regConfirmPassword.setEnabled(false);
		    		  regConfirmPassword.setVisible(false);
		    		  perfilInfo.setWidget(4, 2, addInVerticalPanel(confirmPswdLabel, regConfirmPassword));
		    		  
		    		  VerticalPanel formVPanel = new VerticalPanel();
		    		  formVPanel.setSpacing(10);
		    		  formVPanel.add(perfilInfo);
		    		  formVPanel.add(changeButton);
		    		  saveChangeButton.setEnabled(false);
		    		  saveChangeButton.setVisible(false);
		    		  formVPanel.add(saveChangeButton);
		    		  formVPanel.setCellHorizontalAlignment(changeButton,HasHorizontalAlignment.ALIGN_CENTER);
		    		  formVPanel.setCellHorizontalAlignment(saveChangeButton,HasHorizontalAlignment.ALIGN_CENTER);
		    		  formVPanel.addStyleName("inputForm");
		    		  
		    		  perfilVPanel.setSize("100%","100%");
		    		  perfilVPanel.add(formVPanel);
		    		  perfilVPanel.setCellHorizontalAlignment(formVPanel,HasHorizontalAlignment.ALIGN_CENTER);
	    		  
		          } else {
		        	Window.alert("Couldn't retrieve JSON (" + response.getStatusText()+ ")");
		          }
		      }
		  });
	  } catch (RequestException e) {
		  Window.alert("Couldn't retrieve JSON");
	  }
  }
  
  private VerticalPanel addInVerticalPanel(Label l, TextBox box){  
	  VerticalPanel newVPanel = new VerticalPanel();
	  newVPanel.setSpacing(5);
	  newVPanel.add(l);
	  newVPanel.add(box);
	  return newVPanel;  
  }
 
  private void createRunPanel(){

	  //Text areas
	  HorizontalPanel consolaPanel = new HorizontalPanel();
	  consolaTextArea.setText("consola de salida");
	  consolaTextArea.setSize("100%","100%");
	  consolaPanel.setSize("100%","100%");
	  consolaPanel.setSpacing(5);
	  consolaPanel.add(consolaTextArea);
	  
	  imagePanel.setSize("100%","100%");
	  centerImagePanel = new HTMLPanel("<div align='center' style='background-color:#80FF80'><img src='"+circuitURL+"' height='100%'></div>");
	  imagePanel.add(centerImagePanel);
	  
	  //Assemble Split Panels
	  correPanel.setSize("100%","100%");
	  correPanel.setSplitPosition("60%");
	  correPanel.setLeftWidget(imagePanel);
	  correPanel.setRightWidget(consolaPanel);
	  
	  Image playButtonImg = new Image();
	  playButtonImg.setUrl(IMG_URL+"play.png");
	  playButtonImg.setHeight("21px");
	  Image stopButtonImg = new Image();
	  stopButtonImg.setUrl(IMG_URL+"stop.png");
	  stopButtonImg.setHeight("21px");
	  Image pauseButtonImg = new Image();
	  pauseButtonImg.setUrl(IMG_URL+"pause.png");
	  pauseButtonImg.setHeight("21px");
	  Image play2ButtonImg = new Image();
	  play2ButtonImg.setUrl(IMG_URL+"play.png");
	  play2ButtonImg.setHeight("21px");
	  
	  playButton = new PushButton(playButtonImg);
	  stopButton = new PushButton(stopButtonImg);     
	  stopButton.setEnabled(false);
	  final PushButton controlPauseButton = new PushButton(pauseButtonImg);
	  controlPauseButton.setVisible(false);
	  final PushButton controlPlayButton = new PushButton(play2ButtonImg);
	  controlPlayButton.setVisible(false);
	  
	  Image progressImg = new Image();
	  Image leftProgressImg = new Image();
	  Image rightProgressImg = new Image();
	  leftProgressImg.setUrl(IMG_URL+"barra1.png");
	  progressImg.setUrl(IMG_URL+"barra2.png");
	  rightProgressImg.setUrl(IMG_URL+"barra3.png");
	  Image pointerImg = new Image();
	  pointerImg.setUrl(IMG_URL+"pointer.png");
	  progressAbsPanel.setSize("100%", "100%");
	  progressAbsPanel.add(progressImg);
	  progressAbsPanel.add(pointerImg,0,0);
	  
	  final HorizontalPanel progressPanel = new HorizontalPanel();
	  progressPanel.add(playButton); //0
	  progressPanel.add(controlPauseButton); //1
	  progressPanel.add(controlPlayButton); //2
	  progressPanel.add(stopButton);  //3
	  
	  
	  HorizontalPanel barPanel = new HorizontalPanel();
	  barPanel.add(leftProgressImg);
	  barPanel.add(progressAbsPanel);
	  barPanel.add(rightProgressImg);
	  
	  final HorizontalPanel buttonsPanel = new HorizontalPanel();
	  buttonsPanel.setSpacing(5);
	  final TextBox codeNameTextBox = new TextBox();
	  buttonsPanel.add(codeNameTextBox);
	  Button saveCodeButton = new Button("Guardar");
	  buttonsPanel.add(saveCodeButton);
	  Button loadCodeButton = new Button("Cargar");
	  buttonsPanel.add(loadCodeButton);
	  Button changePlayModeButton = new Button("Cambiar juego");
	  buttonsPanel.add(changePlayModeButton);
	  
	  HorizontalPanel controlHPanel = new HorizontalPanel();
	  controlHPanel.add(progressPanel);
	  controlHPanel.add(barPanel);
	  controlHPanel.setCellWidth(barPanel,"100%");
	  controlHPanel.setCellHorizontalAlignment(barPanel, HasHorizontalAlignment.ALIGN_LEFT);
	  controlHPanel.add(buttonsPanel);
	  controlHPanel.setCellWidth(buttonsPanel,"100%");
	  controlHPanel.setCellHorizontalAlignment(buttonsPanel, HasHorizontalAlignment.ALIGN_RIGHT);
	  
	  inputTextArea.setText("Escribe aquí tu código");
	  inputTextArea.setSize("100%","100%");
	  VerticalPanel inputPanel = new VerticalPanel();
	  inputPanel.setSize("100%","100%");
	  inputPanel.setSpacing(5);
	  inputPanel.add(controlHPanel);
	  inputPanel.add(inputTextArea);
	  inputPanel.setCellHeight(inputTextArea,"100%");
	  inputPanel.setCellVerticalAlignment(inputTextArea, HasVerticalAlignment.ALIGN_TOP);
		  
	  codiPanel.setSize("100%","100%");
	  codiPanel.setSplitPosition("50%");
	  codiPanel.setTopWidget(correPanel);
	  codiPanel.setBottomWidget(inputPanel);
	  
	  playButton.addClickHandler( 
			  new ClickHandler() {
				  public void onClick(ClickEvent event) {
					  if(modeOn == CODE || modeOn == DEBUG){
						  playButton.setEnabled(false);
						  requestRun(inputTextArea.getText(), circuitOn, champOn, progressPanel,buttonsPanel);
					  }
				  }
			  });
	  
	  inputTextArea.addClickHandler( 
			  new ClickHandler() {
				  public void onClick(ClickEvent event) {
					  if(modeOn == EXECUTION && playButton.isEnabled() && playButton.isVisible()){
						  modeOn = CODE;
						  changeMode();
					  }
				  }
			  });
	  saveCodeButton.addClickHandler( 
			  new ClickHandler() {
				  public void onClick(ClickEvent event) {
					  if (USER.equals("")) Window.alert("Para guardar tu código debes ser un usuario registrado");
					  else if(codeNameTextBox.getText().equals("")) Window.alert("Debes introducir un nombre para el archivo de guardado");
					  else {
						  //requestSaveCode(codeNameTextBox.getText(),inputTextArea.getText());
						  requestExistCode(codeNameTextBox.getText(),inputTextArea.getText());
						  codeNameTextBox.setText("");
					  }
				  }
			  });
	  loadCodeButton.addClickHandler( 
			  new ClickHandler() {
				  public void onClick(ClickEvent event) {
					  if (USER.equals("")) Window.alert("Para cargar tus archivos guardados debes ser un usuario registrado");
					  else requestGetSavedCodes();
				  }
			  });
	  changePlayModeButton.addClickHandler( 
			  new ClickHandler() {
				  public void onClick(ClickEvent event) {
					  modeDialogBox.center();
					  modeDialogBox.show();
				  }
			  });
  }
  
  private void createPopupPanel(){
	  
	  HTML infoLabel = new HTML("Elige el modo de juego: ");
	  
	  Label modeLabel = new Label("Modo de juego: ");
	  final ListBox modeListBox = new ListBox(false);
	  modeListBox.addItem("MODO");
	  modeListBox.addItem("Entrenamiento");
	  modeListBox.addItem("Campeonato");
	  HorizontalPanel modeHPanel = new HorizontalPanel();
	  modeHPanel.setSpacing(5);
	  modeHPanel.add(modeLabel);
	  modeHPanel.add(modeListBox);
	  
	  Label champLabel = new Label("Campeonato: ");
	  modeChampListBox.setEnabled(false);
	  modeChampListBox.addItem("CAMPEONATOS");
	  HorizontalPanel champHPanel = new HorizontalPanel();
	  champHPanel.setSpacing(5);
	  champHPanel.add(champLabel);
	  champHPanel.add(modeChampListBox);
	  
	  VerticalPanel leftVPanel = new VerticalPanel();
	  VerticalPanel firstImgVPanel = new VerticalPanel();
	  VerticalPanel secondImgVPanel = new VerticalPanel();
	  VerticalPanel thirdImgVPanel = new VerticalPanel();
	  VerticalPanel rightVPanel = new VerticalPanel();
	  Image moveLeft = new Image();
	  Image moveRight = new Image();
	  
	  moveLeft.setUrl(IMG_URL+"next_left.png");
	  moveRight.setUrl(IMG_URL+"next_right.png");
	  firstCirc.setSize("150px", "150px");
	  secondCirc.setSize("150px", "150px");
	  thirdCirc.setSize("150px", "150px");
	  
	  leftPushButton = new PushButton(moveLeft);
	  rightPushButton = new PushButton(moveRight);
	  leftPushButton.setEnabled(false);
	  rightPushButton.setEnabled(false);

	  firstRadioButton.setEnabled(false);
	  secondRadioButton.setEnabled(false);
	  thirdRadioButton.setEnabled(false);
	  
	  leftVPanel.add(leftPushButton);
	  firstImgVPanel.add(firstCirc);
	  firstImgVPanel.add(firstRadioButton);
	  firstImgVPanel.setCellHorizontalAlignment(firstRadioButton, HasHorizontalAlignment.ALIGN_CENTER);
	  secondImgVPanel.add(secondCirc);
	  secondImgVPanel.add(secondRadioButton);
	  secondImgVPanel.setCellHorizontalAlignment(secondRadioButton, HasHorizontalAlignment.ALIGN_CENTER);
	  thirdImgVPanel.add(thirdCirc);
	  thirdImgVPanel.add(thirdRadioButton);
	  thirdImgVPanel.setCellHorizontalAlignment(thirdRadioButton, HasHorizontalAlignment.ALIGN_CENTER);
	  rightVPanel.add(rightPushButton);
	  HorizontalPanel circuitsHPanel = new HorizontalPanel();
	  circuitsHPanel.setSpacing(10);
	  circuitsHPanel.add(leftVPanel);
	  circuitsHPanel.add(firstImgVPanel);
	  circuitsHPanel.add(secondImgVPanel);
	  circuitsHPanel.add(thirdImgVPanel);
	  circuitsHPanel.add(rightVPanel);
	  circuitsHPanel.setCellVerticalAlignment(leftVPanel, HasVerticalAlignment.ALIGN_MIDDLE);
	  circuitsHPanel.setCellVerticalAlignment(rightVPanel, HasVerticalAlignment.ALIGN_MIDDLE);
	  
	  HorizontalPanel buttonsHPanel = new HorizontalPanel();
	  buttonsHPanel.setSpacing(5);
	  Button okModeButton = new Button("Aceptar");
	  Button cancelModeButton = new Button("Cancelar");
	  buttonsHPanel.add(okModeButton);
	  buttonsHPanel.add(cancelModeButton);
	  
	  VerticalPanel popupVPanel = new VerticalPanel();
	  popupVPanel.setSpacing(15);
	  popupVPanel.add(infoLabel);
	  popupVPanel.add(modeHPanel);
	  popupVPanel.add(champHPanel);
	  popupVPanel.add(circuitsHPanel);  
	  popupVPanel.add(buttonsHPanel);
	  popupVPanel.setCellHorizontalAlignment(buttonsHPanel, HasHorizontalAlignment.ALIGN_RIGHT);
	  
	  modeDialogBox.setAnimationEnabled(true);
	  modeDialogBox.add(popupVPanel);
	  modeListBox.addChangeHandler(new ChangeHandler() {
		  public void onChange(ChangeEvent event) {
			  if(modeListBox.getSelectedIndex() == 0 || modeListBox.getSelectedIndex() == 2) {  //MODO || Campeonato
				  modeChampListBox.setEnabled(false);
				  modeChampListBox.setSelectedIndex(0);
				  if (modeListBox.getSelectedIndex() == 2 && !USER.equals("")) modeChampListBox.setEnabled(true);  //Campeonato
				  else if (modeListBox.getSelectedIndex() == 2 && USER.equals("")) Window.alert("Para poder jugar en modo 'Campeonato' debes estar identificado como usuario");
				  leftPushButton.setEnabled(false);
				  rightPushButton.setEnabled(false);
				  firstRadioButton.setEnabled(false);
				  secondRadioButton.setEnabled(false);
				  thirdRadioButton.setEnabled(false);
				  firstRadioButton.setValue(false);
				  secondRadioButton.setValue(false);
				  thirdRadioButton.setValue(false);
			  }
			  else {  //Entrenamiento
				  modeChampListBox.setEnabled(false);
				  modeChampListBox.setSelectedIndex(0);
				  indexToShow = 0;
				  displayCircuitsImages(circuitsList);			  
				  
				  firstRadioButton.setEnabled(true);
				  secondRadioButton.setEnabled(true);
				  thirdRadioButton.setEnabled(true);
			  }
		  }
	  });
	  
	  modeChampListBox.addChangeHandler(new ChangeHandler() {
		  public void onChange(ChangeEvent event) {
			  firstRadioButton.setValue(false);
			  secondRadioButton.setValue(false);
			  thirdRadioButton.setValue(false);
			  if(modeChampListBox.getSelectedIndex() == 0) { 
				  leftPushButton.setEnabled(false);
				  rightPushButton.setEnabled(false);
				  firstRadioButton.setEnabled(false);
				  secondRadioButton.setEnabled(false);
				  thirdRadioButton.setEnabled(false);
			  }
			  else {
				  String selectedChamp = modeChampListBox.getItemText(modeChampListBox.getSelectedIndex());
				  requestChampionshipCircuits(selectedChamp);
				  firstRadioButton.setEnabled(true);
				  secondRadioButton.setEnabled(true);
				  thirdRadioButton.setEnabled(true);
			  }  
		  }
	  });
	  
	  leftPushButton.addClickHandler( 
			  new ClickHandler() {
				  public void onClick(ClickEvent event) {
					  if (indexToShow-6 < 0) indexToShow=0;
					  else indexToShow=indexToShow-6;
					  if(modeListBox.getSelectedIndex() == 1) displayCircuitsImages(circuitsList);  //entrenamiento
					  else if(modeListBox.getSelectedIndex() == 2) displayCircuitsImages(champCircuitsList);  //campeonato
				  }
			  });
	  rightPushButton.addClickHandler( 
			  new ClickHandler() {
				  public void onClick(ClickEvent event) {
					  if(modeListBox.getSelectedIndex() == 1) displayCircuitsImages(circuitsList);  //entrenamiento
					  else if(modeListBox.getSelectedIndex() == 2) displayCircuitsImages(champCircuitsList);  //campeonato
				  }
			  });
	  
	  okModeButton.addClickHandler( 
			  new ClickHandler() {
				  public void onClick(ClickEvent event) {
					  if(modeListBox.getSelectedIndex()==0) Window.alert("Debes elegir un modo de juego");
					  else if (modeChampListBox.isEnabled() && modeChampListBox.getSelectedIndex() == 0) Window.alert("Para jugar en modo 'Campeonato' debes elegir un campeonato en el que competir");
					  else if (!firstRadioButton.getValue() && !secondRadioButton.getValue() && !thirdRadioButton.getValue()) Window.alert("Debes elegir un circuito");
					  else {
						  playMode = modeListBox.getSelectedIndex();
						  if (!modeChampListBox.isEnabled()) champOn = "";
						  else champOn = modeChampListBox.getItemText(modeChampListBox.getSelectedIndex());
						  if(firstRadioButton.getValue()) circuitOn = firstRadioButton.getText();
						  else if(secondRadioButton.getValue()) circuitOn = secondRadioButton.getText();
						  else if(thirdRadioButton.getValue()) circuitOn = thirdRadioButton.getText();
						  
						  boolean b = false;
						  for(int i=0;i<circuitsList.size() && !b;i++){
							  if(circuitsList.get(i).name.equals(circuitOn)){
								  b=true;
								  circuitURL = circuitsList.get(i).url;
								  circuitWidth = circuitsList.get(i).width;
								  circuitHeight = circuitsList.get(i).height;
							  }
						  }

						  imagePanel.remove(centerImagePanel);
						  centerImagePanel = new HTMLPanel("<div align='center' style='background-color:#80FF80'><img src='"+LOCAL_URL+circuitURL+"' height='100%'></div>");
						  imagePanel.add(centerImagePanel);
						  modeDialogBox.hide();
						  modeListBox.setSelectedIndex(0);
						  modeChampListBox.setEnabled(false);
						  modeChampListBox.setSelectedIndex(0);
						  leftPushButton.setEnabled(false);
						  rightPushButton.setEnabled(false);
						  firstRadioButton.setEnabled(false);
						  secondRadioButton.setEnabled(false);
						  thirdRadioButton.setEnabled(false);
						  firstRadioButton.setValue(false);
						  secondRadioButton.setValue(false);
						  thirdRadioButton.setValue(false);
						  indexToShow = 0;
						  displayCircuitsImages(circuitsList);
					  }
				  }
			  });
	  
	  cancelModeButton.addClickHandler( 
			  new ClickHandler() {
				  public void onClick(ClickEvent event) {
					  if(playMode == NONE){
						  playMode = TRAIN;
						  champOn = "";
						  boolean b = false;
						  for(int i=0;i<circuitsList.size() && !b;i++){
							  if(circuitsList.get(i).name.equals(circuitOn)){
								  b=true;
								  circuitURL = circuitsList.get(i).url;
								  circuitWidth = circuitsList.get(i).width;
								  circuitHeight = circuitsList.get(i).height;
							  }
						  }
					  }
					  modeDialogBox.hide();
					  modeListBox.setSelectedIndex(0);
					  modeChampListBox.setEnabled(false);
					  modeChampListBox.setSelectedIndex(0);
					  leftPushButton.setEnabled(false);
					  rightPushButton.setEnabled(false);
					  firstRadioButton.setEnabled(false);
					  secondRadioButton.setEnabled(false);
					  thirdRadioButton.setEnabled(false);
					  firstRadioButton.setValue(false);
					  secondRadioButton.setValue(false);
					  thirdRadioButton.setValue(false);
					  indexToShow = 0;
					  displayCircuitsImages(circuitsList);
				  }
			  });
  }
  
  private void displayCircuitsImages(ArrayList<CircuitInfo> list) {
		
		firstCirc.setVisible(false);
		secondCirc.setVisible(false);
		thirdCirc.setVisible(false);
		firstRadioButton.setValue(false);
		secondRadioButton.setValue(false);
		thirdRadioButton.setValue(false);
		firstRadioButton.setVisible(false);
		secondRadioButton.setVisible(false);
		thirdRadioButton.setVisible(false);
	  
		if (indexToShow < list.size()){
			firstCirc.setUrl(LOCAL_URL+list.get(indexToShow).url);
			firstCirc.setVisible(true);
			firstRadioButton.setText(list.get(indexToShow).name);
			firstRadioButton.setVisible(true);
		}
		if (indexToShow+1 < list.size()){
			secondCirc.setUrl(LOCAL_URL+list.get(indexToShow+1).url);
			secondCirc.setVisible(true);
			secondRadioButton.setText(list.get(indexToShow+1).name);
			secondRadioButton.setVisible(true);
		}
		if (indexToShow+2 < list.size()){
			thirdCirc.setUrl(LOCAL_URL+list.get(indexToShow+2).url);
			thirdCirc.setVisible(true);
			thirdRadioButton.setText(list.get(indexToShow+2).name);
			thirdRadioButton.setVisible(true);
		}
				  
		if(indexToShow == 0)leftPushButton.setEnabled(false);
		else leftPushButton.setEnabled(true);
		
		indexToShow=indexToShow+3;
		
		if(indexToShow < list.size())rightPushButton.setEnabled(true);
		else rightPushButton.setEnabled(false);
  }
  
  private void createRankingPanel(){

	  // Create a panel to align the Widgets
	  HorizontalPanel boxesHPanel = new HorizontalPanel();
	  boxesHPanel.setSpacing(20);
	  // Add drop boxs with the lists
	  if(circuitsDropBox.getItemCount()==0) circuitsDropBox.addItem("CIRCUITOS");
	  champsDropBox.clear();
	  champsDropBox.addItem("CAMPEONATOS");
	  champsDropBox.setEnabled(false);
	  teamsDropBox.clear();
	  teamsDropBox.addItem("EQUIPOS");
	  teamsDropBox.setEnabled(false);
	  boxesHPanel.add(circuitsDropBox);
	  boxesHPanel.add(champsDropBox);
	  boxesHPanel.add(teamsDropBox);
	  
	  VerticalPanel boxesVPanel = new VerticalPanel();
	  //boxesVPanel.setHeight("30%");
	  boxesVPanel.addStyleName("inputForm");
	  boxesVPanel.setSpacing(5);
	  boxesVPanel.add(new Label("Filtrar ranking según:"));
	  boxesVPanel.add(boxesHPanel);
	    
	  // Create table for ranking data.
	  rankingFlexTable.setText(0, 0, "Usuario");
	  rankingFlexTable.setText(0, 1, "Tiempo (MM:ss.mmm)");
	  // Add styles to elements in the ranking list table.
	  rankingFlexTable.getRowFormatter().addStyleName(0, "flexTableHeader");
	  rankingFlexTable.addStyleName("flexTable");
	  rankingFlexTable.getCellFormatter().addStyleName(0, 0, "rankingListColumn");
	  rankingFlexTable.getCellFormatter().addStyleName(0, 1, "rankingListColumn");
	  
	  sizePagesDropBox.clear();
	  for(int i=0;i<pageSizes.length;i++) { sizePagesDropBox.addItem(pageSizes[i]); }
	  sizePagesDropBox.setEnabled(false);
	  rankPagesDropBox.clear();
	  rankPagesDropBox.addItem("Página");
	  rankPagesDropBox.setEnabled(false);
	  
	  Grid rankPagesGrid = new Grid (1,2);
	  rankPagesGrid.setCellSpacing(5);
	  rankPagesGrid.setWidget(0,0,new Label("Ir a página: "));
	  rankPagesGrid.setWidget(0,1,rankPagesDropBox);
	  Grid sizePagesGrid = new Grid (1,2);
	  sizePagesGrid.setCellSpacing(5);
	  sizePagesGrid.setWidget(0,0,new Label("Tiempos mostrados por página: "));
	  sizePagesGrid.setWidget(0,1,sizePagesDropBox);
	  
	  HorizontalPanel dropBoxes2 = new HorizontalPanel();
	  //dropBoxes2.addStyleName("inputForm");
	  dropBoxes2.setWidth("100%");
	  dropBoxes2.add(rankPagesGrid);
	  dropBoxes2.add(sizePagesGrid);
	  /*dropBoxes2.add(new Label("Ir a página: "));
	  dropBoxes2.add(rankPagesDropBox);
	  dropBoxes2.add(new Label("Tiempos mostrados por página: "));
	  dropBoxes2.add(sizePagesDropBox);*/
	  //dropBoxes2.addStyleName("inputForm");
	  
	 // HorizontalPanel rankingHPanel = new HorizontalPanel();
	  VerticalPanel rankingHPanel = new VerticalPanel();
	  //rankingHPanel.addStyleName("inputForm");
	  //rankingHPanel.setHeight("100%");
	  //rankingHPanel.setWidth("100%");
	  rankingHPanel.setSpacing(10);
	  rankingHPanel.add(rankingFlexTable);
	  rankingHPanel.add(dropBoxes2);

	  //rankingHPanel.setCellHorizontalAlignment(rankingFlexTable,HasHorizontalAlignment.ALIGN_CENTER);
	  //rankingHPanel.setCellVerticalAlignment(rankingFlexTable,HasVerticalAlignment.ALIGN_TOP);
	  VerticalPanel vLayout = new VerticalPanel();
	  vLayout.setSpacing(30);
	  vLayout.add(boxesVPanel);
	  vLayout.add(rankingHPanel);
	  //vLayout.addStyleName("inputForm");
	  vLayout.setCellHorizontalAlignment(boxesVPanel,HasHorizontalAlignment.ALIGN_CENTER);
	  
	  //rankingVPanel.addStyleName("inputForm");
	  rankingVPanel.setWidth("100%");
	  //rankingVPanel.setSize("100%","100%");
	  rankingVPanel.setSpacing(5);
	  //rankingVPanel.add(boxesVPanel);
	  //rankingVPanel.add(rankingHPanel);
	  rankingVPanel.add(vLayout);
	  rankingVPanel.setCellHorizontalAlignment(vLayout,HasHorizontalAlignment.ALIGN_CENTER);
	  //rankingVPanel.setCellHorizontalAlignment(rankingHPanel,HasHorizontalAlignment.ALIGN_CENTER);
	  //rankingVPanel.setCellVerticalAlignment(rankingHPanel,HasVerticalAlignment.ALIGN_TOP);
	  
	  // Listen for events on the DropBoxs.
	  circuitsDropBox.addChangeHandler(new ChangeHandler() {
		  public void onChange(ChangeEvent event) {
			  if(circuitsDropBox.getSelectedIndex()>0){
				  if(!USER.equals("")){
					  refreshDropBoxs();
					  champsDropBox.setEnabled(true);
					  teamsDropBox.setEnabled(true);
				  }
				  requestRanking();
				  sizePagesDropBox.setEnabled(true);
			  }
			  else{
				  champsDropBox.setSelectedIndex(0);
				  champsDropBox.setEnabled(false);
				  teamsDropBox.setSelectedIndex(0);
				  teamsDropBox.setEnabled(false);	
				  rankPagesDropBox.setSelectedIndex(0);
				  rankPagesDropBox.setEnabled(false);
				  sizePagesDropBox.setSelectedIndex(0);
				  sizePagesDropBox.setEnabled(false);
				  sizePagesDropBox.setSelectedIndex(0);
				  sizePagesDropBox.setEnabled(false);
				  Window.alert("Debes elegir un circuito válido");
			  }
			  /*rankPagesDropBox.clear();
			  rankPagesDropBox.addItem("Página");
			  rankPagesDropBox.setEnabled(false);*/
		  }
	  });
	  champsDropBox.addChangeHandler(new ChangeHandler() {
		  public void onChange(ChangeEvent event) {
			  //if(champsDropBox.getSelectedIndex()>0) requestRanking();
			  requestRanking();
		  }
	  });
	  teamsDropBox.addChangeHandler(new ChangeHandler() {
		  public void onChange(ChangeEvent event) {
			  //if(teamsDropBox.getSelectedIndex()>0) requestRanking();
			  requestRanking();
		  }
	  });
	  rankPagesDropBox.addChangeHandler(new ChangeHandler() {  //tener en cuenta si esta logeado!!algo de paginas?
		  public void onChange(ChangeEvent event) {
			  //if(rankPagesDropBox.getSelectedIndex()>0) requestRanking();
			  requestRanking();
		  }
	  });
	  sizePagesDropBox.addChangeHandler(new ChangeHandler() {
		  public void onChange(ChangeEvent event) {
			  //if(sizePagesDropBox.getSelectedIndex()>0) requestRanking();
			  requestRanking();
		  }
	  });
  }
  
  private void createAdminPanel(){

	  final VerticalPanel newChampVPanel = createNewChampPanel();
	  final VerticalPanel newTeamVPanel = createNewTeamPanel();
	  final VerticalPanel addPlayersVPanel = createAddPlayersPanel();
	  final VerticalPanel invitationsVPanel = createInvitationsPanel();
	  
	  final VerticalPanel adminVPanel = new VerticalPanel();
	  adminVPanel.setSpacing(5);
	  adminVPanel.setWidth("100%");
	  adminVPanel.add(logoutButton);
	  adminVPanel.setCellHeight(logoutButton, "50px");
	  adminVPanel.setCellVerticalAlignment(logoutButton, HasVerticalAlignment.ALIGN_TOP);
	  adminVPanel.setCellHorizontalAlignment(logoutButton, HasHorizontalAlignment.ALIGN_RIGHT);
  
	  adminHPanel.setSize("100%","100%");
	  adminHPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
	  adminStckPanel.setSize("205px","50%");

	  final Tree UserTree = new Tree();
	  final TreeItem ItemPerfil = UserTree.addItem("Información de perfil");
	  UserTree.addSelectionHandler(new SelectionHandler<TreeItem>(){
		  public void onSelection(SelectionEvent<TreeItem> event){
			  TreeItem it = UserTree.getSelectedItem();
			  if(it.equals(ItemPerfil)){
				  int n = adminVPanel.getWidgetCount();
				  if (n == 2) adminVPanel.remove(n-1);
				  adminVPanel.add(perfilVPanel);
			  }
		  }
	  });
	  
	  final Tree ChampsTree = new Tree();
	  final TreeItem ItemNewChamp = ChampsTree.addItem("Crear nuevo");
	  final TreeItem ItemAddChamp = ChampsTree.addItem("Invitar a jugadores");
	  ChampsTree.addSelectionHandler(new SelectionHandler<TreeItem>(){
		  public void onSelection(SelectionEvent<TreeItem> event){
			  TreeItem it = ChampsTree.getSelectedItem();
			  if(it.equals(ItemNewChamp)){
				  int n = adminVPanel.getWidgetCount();
				  if (n == 2) adminVPanel.remove(n-1);
				  adminVPanel.add(newChampVPanel);
			  }
			  else if(it.equals(ItemAddChamp)){
				  int n = adminVPanel.getWidgetCount();
				  if (n == 2) adminVPanel.remove(n-1);
				  addPlayersLabel.setText("¡ Invita a tus amigos a participar en tus campeonatos !");
				  refreshAddPlayersDropBox(1);
				  requestAllNicks();
				  adminVPanel.add(addPlayersVPanel);
			  }
		  }
	  });
	
	  final Tree TeamsTree = new Tree();
	  final TreeItem ItemNewTeam = TeamsTree.addItem("Crear nuevo");
	  final TreeItem ItemAddTeam = TeamsTree.addItem("Invitar a jugadores");
	  TeamsTree.addSelectionHandler(new SelectionHandler<TreeItem>(){
		  public void onSelection(SelectionEvent<TreeItem> event){
			  TreeItem it = TeamsTree.getSelectedItem();
			  if(it.equals(ItemNewTeam)){
				  int n = adminVPanel.getWidgetCount();
				  if (n == 2) adminVPanel.remove(n-1);
				  adminVPanel.add(newTeamVPanel);
			  }
			  else if(it.equals(ItemAddTeam)){
				  int n = adminVPanel.getWidgetCount();
				  if (n == 2) adminVPanel.remove(n-1);
				  addPlayersLabel.setText("¡ Invita a tus amigos a unirse a tus equipos !");
				  refreshAddPlayersDropBox(2);
				  requestAllNicks();
				  adminVPanel.add(addPlayersVPanel);
			  }
		  }
	  });
	
	  final Tree InvitationsTree = new Tree();
	  ItemChampInvitations = InvitationsTree.addItem("A campeonatos");
	  ItemTeamInvitations = InvitationsTree.addItem("A equipos");
	  InvitationsTree.addSelectionHandler(new SelectionHandler<TreeItem>(){
		  public void onSelection(SelectionEvent<TreeItem> event){
			  TreeItem it = InvitationsTree.getSelectedItem();
			  if(it.equals(ItemChampInvitations)){
				  int n = adminVPanel.getWidgetCount();
				  if (n == 2) adminVPanel.remove(n-1);
				  refreshInvitationsTable("champs");
				  requestNInvitations();
				  adminVPanel.add(invitationsVPanel);
			  }
			  else if(it.equals(ItemTeamInvitations)){
				  int n = adminVPanel.getWidgetCount();
				  if (n == 2) adminVPanel.remove(n-1);
				  refreshInvitationsTable("teams");
				  requestNInvitations();
				  adminVPanel.add(invitationsVPanel);
			  }
		  }
	  });
	
	  adminStckPanel.add(UserTree, "Usuario");
	  adminStckPanel.add(ChampsTree, "Campeonatos");
	  adminStckPanel.add(TeamsTree, "Equipos");
	  adminStckPanel.add(InvitationsTree, "Invitaciones");
	  adminHPanel.add(adminStckPanel);
	  adminHPanel.add(adminVPanel);
  }
  
  private VerticalPanel createNewChampPanel(){
	  
	  VerticalPanel champNameVPanel = new VerticalPanel();
	  
	  Grid champNameGrid = new Grid(2,2);
	  champNameGrid.setCellSpacing(10);
	  champNameGrid.setWidget(0,0,new Label("Nombre del campeonato:"));
	  champNameGrid.setWidget(0,1,champNameTextBox);
	  champNameGrid.setWidget(1,0,new Label("Fecha límite inscripciones:"));
	  champDateBox.setFormat(new DateBox.DefaultFormat(DateTimeFormat.getFormat("dd/MM/yyyy")));
	  champNameGrid.setWidget(1,1,champDateBox);  
	  champNameVPanel.add(champNameGrid);
	  
	  VerticalPanel champChooseVPanel = new VerticalPanel();
	  champChooseVPanel.setSpacing(10);
	  champChooseVPanel.add(new Label("Circuitos disponibles:"));
	  Grid dispCircuitsGrid = new Grid(1,2);
	  circuitsMultiBox.setSize("130px","200px");
	  dispCircuitsGrid.setWidget(0,0,circuitsMultiBox);
	  Button addCircuitsButton = new Button("Añadir circuito ->");
	  dispCircuitsGrid.setWidget(0,1,addCircuitsButton);
	  CellFormatter dispCircsCellFormatter = dispCircuitsGrid.getCellFormatter();
	  dispCircsCellFormatter.setVerticalAlignment(0,1,HasVerticalAlignment.ALIGN_TOP);
	  champChooseVPanel.add(dispCircuitsGrid);
	  champChooseVPanel.add(new Label("(para selección múltiple mantén pulsado 'Ctrl')"));
	  
	  VerticalPanel champAddVPanel = new VerticalPanel();
	  champAddVPanel.setSpacing(10);
	  champAddVPanel.add(new HTML("Circuitos seleccionados:"));
	  Grid selectedCircuitsGrid = new Grid(1,2);
	  selectedMultiBox.setSize("130px","200px");
	  selectedCircuitsGrid.setWidget(0,0,selectedMultiBox);
	  Button deleteCircuitsButton = new Button("Eliminar");
	  selectedCircuitsGrid.setWidget(0,1,deleteCircuitsButton);
	  CellFormatter selectedCircsCellFormatter = selectedCircuitsGrid.getCellFormatter();
	  selectedCircsCellFormatter.setVerticalAlignment(0,1,HasVerticalAlignment.ALIGN_TOP);
	  champAddVPanel.add(selectedCircuitsGrid);
	  
	  HorizontalPanel newChampHPanel = new HorizontalPanel();
	  newChampHPanel.setSize("100%","100%");
	  newChampHPanel.add(champChooseVPanel);
	  newChampHPanel.add(champAddVPanel);

	  VerticalPanel newChampVPanel = new VerticalPanel();
	  newChampVPanel.setSize("100%","100%");
	  newChampVPanel.setSpacing(10);
	  newChampVPanel.add(champNameVPanel);
	  newChampVPanel.add(newChampHPanel);
	  Button createChampButton = new Button("Crear campeonato");
	  newChampVPanel.add(createChampButton);
	  newChampVPanel.setCellHorizontalAlignment(champNameVPanel, HasHorizontalAlignment.ALIGN_LEFT);
	  newChampVPanel.setCellHeight(createChampButton,"50px");
	  newChampVPanel.setCellHorizontalAlignment(createChampButton, HasHorizontalAlignment.ALIGN_CENTER);

	  
	  addCircuitsButton.addClickHandler( 
			  new ClickHandler() {
				  public void onClick(ClickEvent event) {
					  for(int i=0;i<circuitsMultiBox.getItemCount();i++){
						  if(circuitsMultiBox.isItemSelected(i)){
							  selectedMultiBox.addItem(circuitsMultiBox.getItemText(i));
							  circuitsMultiBox.removeItem(i);
							  i--;
						  }
					  }
				  }
			  });
	  deleteCircuitsButton.addClickHandler( 
			  new ClickHandler() {
				  public void onClick(ClickEvent event) {
					  for(int i=0;i<selectedMultiBox.getItemCount();i++){
						  if(selectedMultiBox.isItemSelected(i)){
							  circuitsMultiBox.addItem(selectedMultiBox.getItemText(i));
							  selectedMultiBox.removeItem(i);
							  i--;
						  }
					  }
				  }
			  });
	  createChampButton.addClickHandler( 
			  new ClickHandler() {
				  public void onClick(ClickEvent event) {
					  Date today = new Date(); 
					  if (champNameTextBox.getText().equals("")){
						  Window.alert("Debes indicar un nombre para el circuito");
					  } 
					 else if (champDateBox.getValue() == null){
						  Window.alert("Debes indicar una fecha correcta");
					  }
					  else if (champDateBox.getValue().before(today)){
						  Window.alert("La fecha límite para las inscripciones debe ser posterior al día de creación");
					  }
					  else if (selectedMultiBox.getItemCount()==0){
						  Window.alert("El nuevo campeonato debe constar de al menos un circuito");
						  
					  }
					  else requestNewChampionship();
				  }
			  });
	  
	  return newChampVPanel;
  }
  
  private VerticalPanel createNewTeamPanel(){
	  
	  Grid newTeam = new Grid(1,2);
	  newTeam.setWidget(0,0,new HTML("Nombre: "));
	  newTeam.setWidget(0,1,teamNameTextBox);
	  
	  Button createTeamButton = new Button("Crear equipo");

	  VerticalPanel newTeamVPanel = new VerticalPanel();
	  newTeamVPanel.setSpacing(20);
	  newTeamVPanel.add(new HTML("¡Crea un nuevo equipo del que serás propietario!"));
	  newTeamVPanel.add(newTeam);
	  newTeamVPanel.add(createTeamButton);
	  
	  createTeamButton.addClickHandler( 
			  new ClickHandler() {
				  public void onClick(ClickEvent event) {
					  if (teamNameTextBox.getText().equals("")){
						  Window.alert("Debes indicar un nombre para el equipo");
					  }
					  else requestNewTeam();
				  }
			  });
	  return newTeamVPanel;  
  }
  
  
  private VerticalPanel createAddPlayersPanel(){
	  
	  requestAllNicks();
	  suggestNickBox = new SuggestBox(oracle);
	  Button addPlayersButton = new Button("Invitar");
	  
	  HorizontalPanel addPlayersHPanel = new HorizontalPanel();
	  addPlayersHPanel.setSpacing(5);
	  addPlayersHPanel.add(addPlayersDropBox);
	  addPlayersHPanel.add(suggestNickBox);
	  addPlayersHPanel.add(addPlayersButton);
	  
	  VerticalPanel addPlayersVPanel = new VerticalPanel();
	  addPlayersVPanel.setSpacing(20);
	  addPlayersVPanel.add(addPlayersLabel);
	  addPlayersVPanel.add(addPlayersHPanel);
	  
	  addPlayersButton.addClickHandler( 
			  new ClickHandler() {
				  public void onClick(ClickEvent event) {
					  if(addPlayersDropBox.getValue(0).equals("CAMPEONATOS")){
						  if (addPlayersDropBox.getSelectedIndex() == 0){
							  Window.alert("Debes elegir un campeonato");
						  }
						  else if (suggestNickBox.getText().equals("")){
							  Window.alert("Debes introducir el nombre del usuario al que quieres invitar");
						  }
						  else if (suggestNickBox.getText().equals(USER)){
							  Window.alert("Debes invitar a un usuario válido");
						  }
						  else requestAddPlayerToChamp();
					  }
					  else if(addPlayersDropBox.getValue(0).equals("EQUIPOS")){
						  if (addPlayersDropBox.getSelectedIndex() == 0){
							  Window.alert("Debes elegir un equipo");
						  }
						  else if (suggestNickBox.getText().equals("")){
							  Window.alert("Debes introducir el nombre del usuario al que quieres invitar");
						  }
						  else if (suggestNickBox.getText().equals(USER)){
							  Window.alert("Debes invitar a un usuario válido");
						  }
						  else requestAddPlayerToTeam();
					  }
				  }
			  });  
	  return addPlayersVPanel;
  }
  
  private VerticalPanel createInvitationsPanel() {
	  
	  VerticalPanel invitationsVPanel = new VerticalPanel();
	  invitationsVPanel.setSize("100%", "100%");
	  invitationsVPanel.setSpacing(20);
	  
	  invitationsFlexTable.setText(0, 0, "Invitación");
	  invitationsFlexTable.setText(0, 1, "Respuesta");
	  // Add styles to elements in the invitations list table.
	  invitationsFlexTable.getRowFormatter().addStyleName(0, "flexTableHeader");
	  invitationsFlexTable.addStyleName("flexTable");
	  invitationsFlexTable.getCellFormatter().addStyleName(0, 0, "invitationListFirstColumn");
	  invitationsFlexTable.getCellFormatter().addStyleName(0, 1, "invitationListSecondColumn");
	  invitationsVPanel.add(invitationsFlexTable);
	  
	  return invitationsVPanel;
  }
  
  
  private void requestLogin() {

	  if (loginUserTextBox.getText().equals("") || loginPassword.getText().equals("")){
		  Window.alert("Debes indicar tu nombre de usuario y contraseña");
		  whileReqOn = false;
	  }
	  else {
		  try {
			  String requestStr = encodeParam("function", "login")+"&"+
			  	encodeParam("nick", loginUserTextBox.getText())+"&"+
				encodeParam("password", loginPassword.getText());

			  Request request = builder.sendRequest(requestStr, new RequestCallback() {	
			        	public void onError(Request request, Throwable exception) {
			        		Window.alert("Couldn't retrieve JSON");
			        		whileReqOn = false;
			        	}
			        	public void onResponseReceived(Request request, Response response) {
			        		if (200 == response.getStatusCode()) {
			        			int res = asInt(response.getText());
			        			if(res==1){
			        				Window.alert("Usuario o contraseña incorrectos");
			        				loginPassword.setText("");
			        			}
			        			else if(res==2){
			        				Window.alert("Usuario pendiente de activación");
			        				loginPassword.setText("");
			        			}
			        			else if (res==0){
			        				USER = loginUserTextBox.getText();
			        				createPerfilPanel();
			        				requestNInvitations();
			        				multiPanel.remove(loginVPanel);
			        				multiPanel.add(adminHPanel);
			        				mainPanel.insert(multiPanel,"Administración", 0);
			        				mainPanel.selectTab(0);
			        				loginUserTextBox.setText("");
			        				loginPassword.setText("");
			        				resetAll();
			        			}		        	  
			        		} else {
			        			Window.alert("Couldn't retrieve JSON (" + response.getStatusText()+ ")");
			        		}
			        		whileReqOn = false;
			        	}
			  });
		  } catch (RequestException e) {
			  Window.alert("Couldn't retrieve JSON");
			  whileReqOn = false;
		  }
	  }
  }
  
  private void requestRegistration() {

	  if (regNickTextBox.getText().equals("") || regNameTextBox.getText().equals("")
		|| regSurname1TextBox.getText().equals("") || regSurname2TextBox.getText().equals("")
		|| regEmailUserTextBox.getText().equals("") || regCityTextBox.getText().equals("")
		|| regSchoolTextBox.getText().equals("") || regEmailSchoolTextBox.getText().equals("")
		|| regPassword.getText().equals("") || regConfirmPassword.getText().equals("")){
		  Window.alert("Debes rellenar todos los campos");
	  }
	  else if(!checkEmail(regEmailUserTextBox.getText())) {
		  Window.alert("El email de usuario debe ser una cuenta de correo válida");
	  }
	  else if(!checkEmail(regEmailSchoolTextBox.getText())){
		  Window.alert("El email de la escuela debe ser una cuenta de correo válida");
	  }
	  else if(!regPassword.getText().equals(regConfirmPassword.getText())){
		  Window.alert("La contraseña y su confirmación no coinciden. Por favor, introdúcelas de nuevo");
		  regPassword.setText("");
		  regConfirmPassword.setText("");
	  }
	  else{
		  try {
			  String requestStr = encodeParam("function", "newUser")+"&"+
				encodeParam("nick", regNickTextBox.getText())+"&"+
				encodeParam("name", regNameTextBox.getText())+"&"+
				encodeParam("surname1", regSurname1TextBox.getText())+"&"+
				encodeParam("surname2", regSurname2TextBox.getText())+"&"+
				encodeParam("email_user", regEmailUserTextBox.getText())+"&"+
				encodeParam("city", regCityTextBox.getText())+"&"+
				encodeParam("school", regSchoolTextBox.getText())+"&"+
				encodeParam("email_school", regEmailSchoolTextBox.getText())+"&"+
				encodeParam("password", regPassword.getText());

			  Request request = builder.sendRequest(requestStr, new RequestCallback() {	
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
  
  private boolean checkEmail(String email){
	  boolean arr = false;
	  boolean dot = false;
	  for(int i=0;i<email.length();i++){
		  if(email.charAt(i)=='@'&& i==0) return false;
		  else if(email.charAt(i)=='.'&& i==(email.length()-1)) return false;
		  else if(arr && email.charAt(i)=='@') return false;
		  else if(!arr && email.charAt(i)=='@') arr = true;
		  else if(arr && !dot && email.charAt(i)=='.' && !(email.charAt(i-1)=='@')) dot = true;
		  else if(arr && dot && email.charAt(i)=='.') return false;
	  }
	  return dot;
  }
  
  private void requestCircuitInfo(String circuit, final int index) {
	  try {
		  String requestStr = encodeParam("function", "getCircuitInfo")+"&"+
			encodeParam("name", String.valueOf(circuit));

		  Request request = builder.sendRequest(requestStr, new RequestCallback() {	
			  public void onError(Request request, Throwable exception) {
				  Window.alert("Couldn't retrieve JSON");
			  }
			  public void onResponseReceived(Request request, Response response) {
				  if (200 == response.getStatusCode()) {
					  JSonData res = asJSonData(response.getText());
					  circuitsList.get(index).url = res.get("url");
					  circuitsList.get(index).width = res.getInt("width");
					  circuitsList.get(index).height = res.getInt("height");
					  circuitsList.get(index).level = res.getInt("level");
					  circuitsList.get(index).n_laps = res.getInt("n_laps");
					  
				  } else {
		        	Window.alert("Couldn't retrieve JSON (" + response.getStatusText()+ ")");
		          }
			  }
		  });
	  } catch (RequestException e) {
		  Window.alert("Couldn't retrieve JSON");
	  } 
  }
  
  private void requestRun(String code, String circuit, String championship, final HorizontalPanel animationControllersPanel,final HorizontalPanel buttonsPanel) {
	  
	  try {
		  String requestStr = encodeParam("function", "run")+"&"+
			encodeParam("code", code)+"&"+
			encodeParam("circuit", circuit)+"&"+
			encodeParam("championship", championship);

		  Request request = builder.sendRequest(requestStr, new RequestCallback() {	
			  public void onError(Request request, Throwable exception) {
				  Window.alert("Couldn't retrieve JSON");
			  }
		      public void onResponseReceived(Request request, Response response) {
		    	  if (200 == response.getStatusCode()) {
		    		  JSonData res = asJSonData(response.getText());
		    		 
		    		  int cod = res.getInt("code");
		    		  String msg = res.get("message");
		    		  int time = res.getInt("time");   		     

		        	  if(cod < 0) {
		        		  Window.alert("Error de compilacion");
		        		  consolaTextArea.setText(msg);
						  modeOn = DEBUG;
						  changeMode();
		        	  }
		        	  else if (cod == 0){
		        		  String t = formatTime(time);
		        		  Window.alert("¡Compilación correcta!\nTiempo conseguido: "+t);
			    		  int id_game = res.getInt("id_game");
		        		  modeOn = EXECUTION;
						  changeMode();
						  consolaTextArea.setText("-- COMPILACIÓN CORRECTA --\nTiempo conseguido: "+t);
						  requestTrace(id_game, animationControllersPanel, buttonsPanel);
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
  
  private void requestTrace(int id_game,final HorizontalPanel animationControllersPanel, final HorizontalPanel buttonsPanel) {
	  
	  try {
		  String requestStr = encodeParam("function", "getFullTrace")+"&"+
			encodeParam("id_game", String.valueOf(id_game));

		  Request request = builder.sendRequest(requestStr, new RequestCallback() {	
			  public void onError(Request request, Throwable exception) {
				  Window.alert("Couldn't retrieve JSON");
			  }
			  public void onResponseReceived(Request request, Response response) {
				  if (200 == response.getStatusCode()) {
					  JSonData res = asJSonData(response.getText());
					  //int nbytes = res.getInt("read_bytes");
					  String dat = res.get("data");
					  engine.addAnimation(new CarAnimation(imagePanel,progressAbsPanel,circuitWidth,circuitHeight,dat), animationControllersPanel, buttonsPanel, imagePanel, inputTextArea);
				  } else {
		        	Window.alert("Couldn't retrieve JSON (" + response.getStatusText()+ ")");
		          }
			  }
		  });
	  } catch (RequestException e) {
		  Window.alert("Couldn't retrieve JSON");
	  }
  }
  
  private void requestExistCode(final String name,final String code) {

	  try {
		  String requestStr = encodeParam("function", "existCode")+"&"+
			encodeParam("name", name);

		  Request request = builder.sendRequest(requestStr, new RequestCallback() {	
			  
			  public void onError(Request request, Throwable exception) {
				  Window.alert("Couldn't retrieve JSON");
			  }
		      public void onResponseReceived(Request request, Response response) {
		    	  if (200 == response.getStatusCode()) {
		    		  int res = asInt(response.getText());
		    		  if(res==2) Window.alert("Se ha producido un error. Inténtalo de nuevo más tarde");
		    		  else if(res==0) requestSaveCode(name,code);
		        	  else if (res==1){
		        		  boolean answer = Window.confirm("El nombre de archivo ya existe.\n¿Quieres sobreescribirlo?");
		        		  if(answer) requestSaveCode(name,code);
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
  
  private void requestSaveCode(String name, String code) {

	  try {
		  String requestStr = encodeParam("function", "saveCode")+"&"+
			encodeParam("code", code)+"&"+
			encodeParam("name", name);

		  Request request = builder.sendRequest(requestStr, new RequestCallback() {	
			  public void onError(Request request, Throwable exception) {
				  Window.alert("Couldn't retrieve JSON");
			  }
		      public void onResponseReceived(Request request, Response response) {
		    	  if (200 == response.getStatusCode()) {
		    		  int res = asInt(response.getText());
		        	  if(res==1) Window.alert("Se ha producido un error. Inténtalo de nuevo más tarde");
		        	  else if (res==0){
		        		  Window.alert("Archivo guardado con éxito");
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
  
  private void requestGetSavedCodes() {
	  
	  try {
		  String requestStr = encodeParam("function", "getSavedCodes");

		  Request request = builder.sendRequest(requestStr, new RequestCallback() {	
			  public void onError(Request request, Throwable exception) {
				  Window.alert("Couldn't retrieve JSON");
			  }
		      public void onResponseReceived(Request request, Response response) {
		    	  if (200 == response.getStatusCode()) {
					  JsArray<JSonData> res =  asJsArrayJSonData(response.getText());
					  
					  //final DialogBox dialogBox = createDialogBox();
					  final DialogBox dialogBox = new DialogBox();
					  dialogBox.setAnimationEnabled(true);
					  
					  // Create a table to layout the content
					  VerticalPanel dialogContents = new VerticalPanel();
					  dialogContents.setSize("100%","100%");
					  dialogContents.setSpacing(5);
					  dialogBox.setWidget(dialogContents);

					  // Add some text to the top of the dialog
					  dialogContents.add(new HTML("Elige el archivo que quieres cargar: "));
					  
					  final ListBox savedCodesMultiBox = new ListBox(true);
					  savedCodesMultiBox.setSize("200px","230px");
					  
					  for (int i=0; i<res.length(); i++){
						  JSonData info = res.get(i);
						  String name = info.get("name");
						  String date = info.get("date");
						  savedCodesMultiBox.addItem(name +"  ("+date+")");
					  }
					  
					  dialogContents.add(savedCodesMultiBox);
					  dialogContents.setCellHorizontalAlignment(savedCodesMultiBox, HasHorizontalAlignment.ALIGN_CENTER);
					  dialogContents.setCellVerticalAlignment(savedCodesMultiBox, HasVerticalAlignment.ALIGN_MIDDLE);
					  
					  HorizontalPanel buttonsContents = new HorizontalPanel();
					  buttonsContents.setSpacing(5);
					  // Add a load button at the bottom of the dialog
					  Button loadButton = new Button("Cargar",
							  new ClickHandler() {
						  		public void onClick(ClickEvent event) {
						  			if(savedCodesMultiBox.getSelectedIndex() == -1) Window.alert("Debes elegir el archivo que deseas cargar");
						  			else {
							  			String str = savedCodesMultiBox.getItemText(savedCodesMultiBox.getSelectedIndex());
							  			String[] file = str.split("[ ]");
							  			requestLoadCode(file[0]);
							  			dialogBox.hide();
						  			}
						  		}
					  });
					  buttonsContents.add(loadButton);

					  // Add a delete button at the bottom of the dialog
					  Button deleteButton = new Button("Eliminar",
							  new ClickHandler() {
						  		public void onClick(ClickEvent event) {
						  			if(savedCodesMultiBox.getSelectedIndex() == -1) Window.alert("Debes elegir el archivo que deseas eliminar");
						  			else {
						  				String str = savedCodesMultiBox.getItemText(savedCodesMultiBox.getSelectedIndex());
							  			String[] file = str.split("[ ]");
						  				boolean answer = Window.confirm("El archivo \""+file[0]+"\" va a ser eliminado de manera permanente.\n¿Deseas continuar?");
						        		if(answer) {
						        			requestDeleteCode(file[0]);
						        			savedCodesMultiBox.removeItem(savedCodesMultiBox.getSelectedIndex());
						        		}
						  			}
						  		}
					  });
					  buttonsContents.add(deleteButton);
					  
					  // Add a cancel button at the bottom of the dialog
					  Button closeButton = new Button("Cancelar",
							  new ClickHandler() {
						  		public void onClick(ClickEvent event) {
						  			dialogBox.hide();
						  		}
					  });
					  buttonsContents.add(closeButton);
					  dialogContents.add(buttonsContents);
					  dialogContents.setCellHorizontalAlignment(buttonsContents, HasHorizontalAlignment.ALIGN_RIGHT);
					  
					  dialogBox.center();
					  dialogBox.show();
					  
		          } else {
		        	Window.alert("Couldn't retrieve JSON (" + response.getStatusText()+ ")");
		          }
		      }
		  });
	  } catch (RequestException e) {
		  Window.alert("Couldn't retrieve JSON");
	  }
  }
  
  private void requestLoadCode(String name) {

	  try {
		  String requestStr = encodeParam("function", "loadCode")+"&"+
			encodeParam("name", name);

		  Request request = builder.sendRequest(requestStr, new RequestCallback() {	
			  public void onError(Request request, Throwable exception) {
				  Window.alert("Couldn't retrieve JSON");
			  }
		      public void onResponseReceived(Request request, Response response) {
		    	  if (200 == response.getStatusCode()) {
		    		  String res = asString(response.getText());
		    		  inputTextArea.setText(res);
		          } else {
		        	Window.alert("Couldn't retrieve JSON (" + response.getStatusText()+ ")");
		          }
		      }
		  });
	  } catch (RequestException e) {
		  Window.alert("Couldn't retrieve JSON");
	  }
  }

  private void requestDeleteCode(String name) {

	  try {
		  String requestStr = encodeParam("function", "deleteCode")+"&"+
			encodeParam("name", name);

		  Request request = builder.sendRequest(requestStr, new RequestCallback() {	
			  public void onError(Request request, Throwable exception) {
				  Window.alert("Couldn't retrieve JSON");
			  }
		      public void onResponseReceived(Request request, Response response) {
		    	  if (200 == response.getStatusCode()) {
		    		  Window.alert("El archivo ha sido eliminado con éxito");
		          } else {
		        	Window.alert("Couldn't retrieve JSON (" + response.getStatusText()+ ")");
		          }
		      }
		  });
	  } catch (RequestException e) {
		  Window.alert("Couldn't retrieve JSON");
	  }
  }
  
  private void requestCircuits() {

	  try {
		  String requestStr = encodeParam("function", "getCircuits");

		  Request request = builder.sendRequest(requestStr, new RequestCallback() {	
			  public void onError(Request request, Throwable exception) {
				  Window.alert("Couldn't retrieve JSON");
			  }
		      public void onResponseReceived(Request request, Response response) {
		    	  if (200 == response.getStatusCode()) {
		    		  JsArrayString circs = asJsArrayString(response.getText());
		    		  circuitsList.clear();
		    		  circuitsDropBox.clear();
		    		  circuitsDropBox.addItem("CIRCUITOS");
		    		  for(int i=0;i<circs.length();i++) { 
		    			  CircuitInfo cInfo = new CircuitInfo();
		    			  cInfo.name = circs.get(i);
		    			  circuitsList.add(cInfo); 
		    			  requestCircuitInfo(circs.get(i),i);
		    		  }
		    		  for (int i=0; i<circuitsList.size(); i++) { 
		    			  circuitsDropBox.addItem(circuitsList.get(i).name);
		    			  circuitsMultiBox.addItem(circuitsList.get(i).name);
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
  
  private static String encodeParam(String param, String value) {
	  return URL.encodeComponent(param)+"="+URL.encodeComponent(value);
  }
  
  private void refreshListBox(final ListBox listBox) {

	  try{
		  String requestStr = encodeParam("function", "getMyChampionships")+"&"+
		  					encodeParam("circuit", "");

		  Request request = builder.sendRequest(requestStr, new RequestCallback() {
			  public void onError(Request request, Throwable exception) {
				  Window.alert("Couldn't retrieve JSON");
			  }
		      public void onResponseReceived(Request request, Response response) {
		    	  if (200 == response.getStatusCode()) {
		    		  JsArrayString res = asJsArrayString(response.getText());
		    		  listBox.clear();
		    		  listBox.addItem("CAMPEONATOS");
		    		  for(int i=0;i<res.length();i++) listBox.addItem(res.get(i));
		          } else {
		        	Window.alert("Couldn't retrieve JSON (" + response.getStatusText()+ ")");
		          }
		      }
		  });
	  } catch (RequestException e) {
		  Window.alert("Couldn't retrieve JSON");
	  }
  }
  
  private void requestChampionshipCircuits(String name) {

	  try{
		  String requestStr = encodeParam("function", "getChampionshipCircuits")+"&"+
			encodeParam("name", name);

		  Request request = builder.sendRequest(requestStr, new RequestCallback() {	
			  public void onError(Request request, Throwable exception) {
				  Window.alert("Couldn't retrieve JSON");
			  }
		      public void onResponseReceived(Request request, Response response) {
		    	  if (200 == response.getStatusCode()) {
		    		  JsArrayString res = asJsArrayString(response.getText());
		    		  champCircuitsList.clear();
		    		  for(int i=0;i<res.length();i++) {
		    			  CircuitInfo cInfo = new CircuitInfo();
		    			  cInfo.name = res.get(i);
		    			  int j;
		    			  boolean b = false;
		    			  for(j=0;j<circuitsList.size() && !b; j++){
		    				  if (circuitsList.get(j).name.equals(res.get(i))){
		    					  cInfo.url = circuitsList.get(j).url;
		    					  b = true;
		    				  }
		    			  }
		    			  champCircuitsList.add(cInfo);
		    		  }
		    		  indexToShow = 0;
	    			  displayCircuitsImages(champCircuitsList);
	    			  
	    			  
		          } else {
		        	Window.alert("Couldn't retrieve JSON (" + response.getStatusText()+ ")");
		          }
		      }
		  });
	  } catch (RequestException e) {
		  Window.alert("Couldn't retrieve JSON");
	  }
  }
  
  
  private void refreshDropBoxs() {
	  
	  if (circuitsDropBox.getSelectedIndex() != 0){
		  String circ = circuitsDropBox.getValue(circuitsDropBox.getSelectedIndex());
		  champsDropBox.setSelectedIndex(0);
		  teamsDropBox.setSelectedIndex(0);
		  
		  try{
			  String requestStr = encodeParam("function", "getMyChampionships")+"&"+
				encodeParam("circuit", circ);

			  Request request = builder.sendRequest(requestStr, new RequestCallback() {	
				  public void onError(Request request, Throwable exception) {
					  Window.alert("Couldn't retrieve JSON");
				  }
			      public void onResponseReceived(Request request, Response response) {
			    	  if (200 == response.getStatusCode()) {
			    		  JsArrayString res = asJsArrayString(response.getText());
			    		  champsList.clear();
			    		  champsDropBox.clear();
			    		  champsDropBox.addItem("CAMPEONATOS");
			    		  for(int i=0;i<res.length();i++) { champsList.add(res.get(i)); }
			    		  for(int i=0;i<champsList.size();i++) { champsDropBox.addItem((String)champsList.get(i)); }
			          } else {
			        	Window.alert("Couldn't retrieve JSON (" + response.getStatusText()+ ")");
			          }
			      }
			  });
		  } catch (RequestException e) {
			  Window.alert("Couldn't retrieve JSON");
		  }
	 
		  String champ = "";
		  if(champsDropBox.getSelectedIndex() != 0) champ = champsDropBox.getValue(champsDropBox.getSelectedIndex());
	  
		  try {
			  String requestStr = encodeParam("function", "getMyTeams")+"&"+
				encodeParam("circuit", circ)+"&"+
				encodeParam("championship", champ);

			  Request request = builder.sendRequest(requestStr, new RequestCallback() {	
				  public void onError(Request request, Throwable exception) {
					  Window.alert("Couldn't retrieve JSON");
				  }
			      public void onResponseReceived(Request request, Response response) {
			    	  if (200 == response.getStatusCode()) {
			    		  JsArrayString res = asJsArrayString(response.getText());
			    		  teamsList.clear();
			    		  teamsDropBox.clear();
			    		  teamsDropBox.addItem("EQUIPOS");
			    		  for(int i=0;i<res.length();i++) { teamsList.add(res.get(i)); }
			    		  for(int i=0;i<teamsList.size();i++) { teamsDropBox.addItem((String)teamsList.get(i)); }
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
  
  private void requestRanking(){
	  if(circuitsDropBox.getSelectedIndex()==0){
		  Window.alert("Debes elegir un circuito válido");
	  }
	  else {
		  int sizepage = sizePagesDropBox.getSelectedIndex();
		  int page = rankPagesDropBox.getSelectedIndex();
		  String circuit = circuitsList.get(circuitsDropBox.getSelectedIndex() - 1).name;
		  String champ;
		  if(champsDropBox.getSelectedIndex()==0) champ = "";
		  else champ = (String)champsList.get(champsDropBox.getSelectedIndex() - 1);
		  String team;
		  if(teamsDropBox.getSelectedIndex()==0) team = "";
		  else team = (String)teamsList.get(teamsDropBox.getSelectedIndex() - 1);

		  try {
			  String requestStr = encodeParam("function", "getRankings")+"&"+
				encodeParam("circuit", circuit)+"&"+
				encodeParam("team", team)+"&"+
				encodeParam("championship", champ)+"&"+
				encodeParam("page", String.valueOf(page))+"&"+
				encodeParam("sizepage", String.valueOf(sizepage));

			  Request request = builder.sendRequest(requestStr, new RequestCallback() {	
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
						  if(npag != 0) {
							  rankPagesDropBox.setSelectedIndex(pag);
							  rankPagesDropBox.setEnabled(true);
						  }
						  else rankPagesDropBox.setEnabled(false);
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
  
  
  private void requestNewChampionship() {

	  String dateString = DateTimeFormat.getFormat("dd/MM/yyyy").format(champDateBox.getValue());
	  String arrayCircs = selectedMultiBox.getItemText(0);
	  for (int i=1;i<selectedMultiBox.getItemCount();i++) arrayCircs += "+"+selectedMultiBox.getItemText(i);
	  
	  //Send request to server and catch any errors.  
	  try {
		  String requestStr = encodeParam("function", "newChampionship")+"&"+
			encodeParam("name", champNameTextBox.getText())+"&"+
			encodeParam("date_limit", dateString)+"&"+
			encodeParam("circuits", arrayCircs);

		  Request request = builder.sendRequest(requestStr, new RequestCallback() {	
			  public void onError(Request request, Throwable exception) {
				  Window.alert("Couldn't retrieve JSON");
			  }
			  public void onResponseReceived(Request request, Response response) {
				  if (200 == response.getStatusCode()) {
					  int res = asInt(response.getText());
		        	  if(res==1) Window.alert("El nombre de campeonato elegido ya existe");
		        	  else if(res==2) Window.alert("Se ha producido un error. Por favor, inténtalo de nuevo");
		        	  else if (res==0){
		        		  Window.alert("El nuevo campeonato se ha creado con éxito");
		        		  champNameTextBox.setText("");
		        		  champDateBox.setValue(null);
		        		  circuitsMultiBox.clear();
		        		  for (int i=0; i<circuitsList.size(); i++) circuitsMultiBox.addItem(circuitsList.get(i).name);
		        		  selectedMultiBox.clear();
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
  
  private void requestNewTeam() {

	  //Send request to server and catch any errors.
	  try {
		  String requestStr = encodeParam("function", "newTeam")+"&"+
			encodeParam("name", teamNameTextBox.getText());

		  Request request = builder.sendRequest(requestStr, new RequestCallback() {	
			  public void onError(Request request, Throwable exception) {
				  Window.alert("Couldn't retrieve JSON");
			  }
			  public void onResponseReceived(Request request, Response response) {
				  if (200 == response.getStatusCode()) {
					  int res = asInt(response.getText());
		        	  if(res==1) Window.alert("El nombre de equipo elegido ya existe");
		        	  else if(res==2) Window.alert("Se ha producido un error. Por favor, inténtalo de nuevo");
		        	  else if (res==0){
		        		  Window.alert("El nuevo equipo se ha creado con éxito");
		        		  teamNameTextBox.setText("");
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
  
  private void requestAllNicks() {

	  try {
		  String requestStr = encodeParam("function", "getAllNicks");

		  Request request = builder.sendRequest(requestStr, new RequestCallback() {	
			  public void onError(Request request, Throwable exception) {
				  Window.alert("Couldn't retrieve JSON");
			  }
		      public void onResponseReceived(Request request, Response response) {
		    	  if (200 == response.getStatusCode()) {
		    		  JsArrayString nicks = asJsArrayString(response.getText());
		    		  for(int i=0;i<nicks.length();i++) { oracle.add(nicks.get(i)); }
		          } else {
		        	Window.alert("Couldn't retrieve JSON (" + response.getStatusText()+ ")");
		          }
		      }
		  });
	  } catch (RequestException e) {
		  Window.alert("Couldn't retrieve JSON");
	  }
  }

  private void refreshAddPlayersDropBox(int op) {

	  //Send request to server and catch any errors.
	  try {
		  if(op == 1){
			  String requestStr = encodeParam("function", "getMyOwnChampionships");

			  Request request = builder.sendRequest(requestStr, new RequestCallback() {	
				  public void onError(Request request, Throwable exception) {
					  Window.alert("Couldn't retrieve JSON");
				  }
			      public void onResponseReceived(Request request, Response response) {
			    	  if (200 == response.getStatusCode()) {
			    		  JsArrayString champs = asJsArrayString(response.getText());
			    		  addPlayersDropBox.clear();
			    		  addPlayersDropBox.addItem("CAMPEONATOS");
			    		  for (int i=0; i<champs.length(); i++) { 
			    			  addPlayersDropBox.addItem(champs.get(i));
			    		  }
			          } else {
			        	Window.alert("Couldn't retrieve JSON (" + response.getStatusText()+ ")");
			          }
			      }
			  });
		  }
		  else if(op == 2){
			  String requestStr = encodeParam("function", "getMyOwnTeams");

			  Request request = builder.sendRequest(requestStr, new RequestCallback() {	
				  public void onError(Request request, Throwable exception) {
					  Window.alert("Couldn't retrieve JSON");
				  }
				  public void onResponseReceived(Request request, Response response) {
					  if (200 == response.getStatusCode()) {
						  JsArrayString teams = asJsArrayString(response.getText());
						  addPlayersDropBox.clear();
						  addPlayersDropBox.addItem("EQUIPOS");
						  for (int i=0; i<teams.length(); i++) { 
							  addPlayersDropBox.addItem((String)teams.get(i));
						  }
					  } else {
						  Window.alert("Couldn't retrieve JSON (" + response.getStatusText()+ ")");
					  }
				  }
			  });  
		  }
	  } catch (RequestException e) {
		  Window.alert("Couldn't retrieve JSON");
	  }
}
  
  private void requestAddPlayerToChamp() {

	  String name = addPlayersDropBox.getValue(addPlayersDropBox.getSelectedIndex());
	  String nick = suggestNickBox.getText();

	  try {
		  String requestStr = encodeParam("function", "addPlayerToChampionship")+"&"+
			encodeParam("name", name)+"&"+
			encodeParam("nick", nick);

		  Request request = builder.sendRequest(requestStr, new RequestCallback() {	
			  public void onError(Request request, Throwable exception) {
				  Window.alert("Couldn't retrieve JSON");
			  }
			  public void onResponseReceived(Request request, Response response) {
				  if (200 == response.getStatusCode()) {
					  int res = asInt(response.getText());
		    		  String name = addPlayersDropBox.getValue(addPlayersDropBox.getSelectedIndex());
		    		  String nick = suggestNickBox.getText();
		        	  if(res==1) Window.alert("No se ha enviado la invitación porque el usuario "+nick+" ya había sido invitado al campeonato "+name);
		        	  else if(res==2) Window.alert("El nick introducido no corresponde con ningún usuario existente");
		        	  else if(res==3) Window.alert("Se ha producido un error. Por favor, inténtalo de nuevo");
		        	  else if (res==0){
		        		  Window.alert("La invitación para el usuario "+nick+" al campeonato "+name+" se ha enviado con éxito");
		        		  suggestNickBox.setText("");
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

  private void requestAddPlayerToTeam() {

	  String name = addPlayersDropBox.getValue(addPlayersDropBox.getSelectedIndex());
	  String nick = suggestNickBox.getText();

	  try {
		  String requestStr = encodeParam("function", "addPlayerToTeam")+"&"+
			encodeParam("name", name)+"&"+
			encodeParam("nick", nick);

		  Request request = builder.sendRequest(requestStr, new RequestCallback() {	
			  public void onError(Request request, Throwable exception) {
				  Window.alert("Couldn't retrieve JSON");
			  }
			  public void onResponseReceived(Request request, Response response) {
				  if (200 == response.getStatusCode()) {
					  int res = asInt(response.getText());
		    		  String name = addPlayersDropBox.getValue(addPlayersDropBox.getSelectedIndex());
		    		  String nick = suggestNickBox.getText();
		        	  if(res==1) Window.alert("No se ha enviado la invitación porque el usuario "+nick+" ya había sido invitado al equipo "+name);
		        	  else if(res==2) Window.alert("El nick introducido no corresponde con ningún usuario existente");
		        	  else if(res==3) Window.alert("Se ha producido un error. Por favor, inténtalo de nuevo");
		        	  else if (res==0){
		        		  Window.alert("La invitación para el usuario "+nick+" al equipo "+name+" se ha enviado con éxito");
		        		  suggestNickBox.setText("");
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
  
  private void requestNInvitations() {

	  try {
		  String requestStr = encodeParam("function", "getNInvitations");

		  Request request = builder.sendRequest(requestStr, new RequestCallback() {	
			  public void onError(Request request, Throwable exception) {
				  Window.alert("Couldn't retrieve JSON");
			  }
			  public void onResponseReceived(Request request, Response response) {
				  if (200 == response.getStatusCode()) {
					  JSonData res = asJSonData(response.getText());
					  String nToChamps = res.get("nChamps");
					  String nToTeams = res.get("nTeams");
					  int total = Integer.parseInt(nToChamps) + Integer.parseInt(nToTeams);
					  adminStckPanel.setStackText(3, "Invitaciones ("+total+")");
					  ItemChampInvitations.setText("A campeonatos ("+nToChamps+")");
					  ItemTeamInvitations.setText("A equipos ("+nToTeams+")");

				  } else {
		        	Window.alert("Couldn't retrieve JSON (" + response.getStatusText()+ ")");
		          }
			  }
		  });
	  } catch (RequestException e) {
		  Window.alert("Couldn't retrieve JSON");
	  }
  }
  
  private void refreshInvitationsTable(String what) {

	  try {
		  if (what.equals("champs")){
			  String requestStr = encodeParam("function", "getChampionshipsInvited");

			  Request request = builder.sendRequest(requestStr, new RequestCallback() {	
				  public void onError(Request request, Throwable exception) {
					  Window.alert("Couldn't retrieve JSON");
				  }
				  public void onResponseReceived(Request request, Response response) {
					  if (200 == response.getStatusCode()) {
						  JsArray<JSonData> res =  asJsArrayJSonData(response.getText());
						  updateTable(res,"campeonato");
					  } else {
						  Window.alert("Couldn't retrieve JSON (" + response.getStatusText()+ ")");
					  }
				  }
			  });
		  }
		  else if (what.equals("teams")) {
			  String requestStr = encodeParam("function", "getTeamsInvited");

			  Request request = builder.sendRequest(requestStr, new RequestCallback() {	
				  public void onError(Request request, Throwable exception) {
					  Window.alert("Couldn't retrieve JSON");
				  }
				  public void onResponseReceived(Request request, Response response) {
					  if (200 == response.getStatusCode()) {
						  JsArray<JSonData> res =  asJsArrayJSonData(response.getText());
						  updateTable(res, "equipo");
					  } else {
						  Window.alert("Couldn't retrieve JSON (" + response.getStatusText()+ ")");
					  }
				  }
			  });
		  }
	  } catch (RequestException e) {
		  Window.alert("Couldn't retrieve JSON");
	  }  
  }
  
  private void setInvitationAnswer(String what, String name, final int answer) {

	  try {
		  if(what.equals("campeonato")){
			  String requestStr = encodeParam("function", "setChampionshipAnswer")+"&"+
				encodeParam("name", name)+"&"+
				encodeParam("answer", String.valueOf(answer));

			  Request request = builder.sendRequest(requestStr, new RequestCallback() {	
				  public void onError(Request request, Throwable exception) {
					  Window.alert("Couldn't retrieve JSON");
				  }
			      public void onResponseReceived(Request request, Response response) {
			    	  if (200 == response.getStatusCode()) {
			    		  if(answer == 1) Window.alert("La invitación al campeonato ha sido aceptada");
			    		  if(answer == 0) Window.alert("La invitación al campeonato ha sido rechazada");
			    		  requestNInvitations();
			          } else {
			        	Window.alert("Couldn't retrieve JSON (" + response.getStatusText()+ ")");
			          }
			      }
			  });
		  }
		  else if(what.equals("equipo")){
			  String requestStr = encodeParam("function", "setTeamAnswer")+"&"+
				encodeParam("name", name)+"&"+
				encodeParam("answer", String.valueOf(answer));

			  Request request = builder.sendRequest(requestStr, new RequestCallback() {	
				  public void onError(Request request, Throwable exception) {
					  Window.alert("Couldn't retrieve JSON");
				  }
			      public void onResponseReceived(Request request, Response response) {
			    	  if (200 == response.getStatusCode()) {
			    		  if(answer == 1) Window.alert("La invitación al equipo ha sido aceptada");
			    		  if(answer == 0) Window.alert("La invitación al equipo ha sido rechazada");
				          requestNInvitations();
			          } else {
			        	Window.alert("Couldn't retrieve JSON (" + response.getStatusText()+ ")");
			          }
			      }
			  }); 
		  }
	  } catch (RequestException e) {
		  Window.alert("Couldn't retrieve JSON");
	  }
  }
  
private void requestLogout() {

	  try {
		  String requestStr = encodeParam("function", "logout");

		  Request request = builder.sendRequest(requestStr, new RequestCallback() {	
			  public void onError(Request request, Throwable exception) {
				  Window.alert("Couldn't retrieve JSON");
			  }
			  public void onResponseReceived(Request request, Response response) {
				  if (200 == response.getStatusCode()) {
					  //ok
				  } else {
		        	Window.alert("Couldn't retrieve JSON (" + response.getStatusText()+ ")");
		          }
			  }
		  });
	  } catch (RequestException e) {
		  Window.alert("Couldn't retrieve JSON");
	  }
  }
  

  
  private void logout(){
	  
	  requestLogout();
	  if(modeOn == EXECUTION) engine.finishAnimation();
	  modeOn = CODE;
	  changeMode();
	  
	  USER = ""; 
	  playMode = NONE;
	  multiPanel.remove(adminHPanel);
	  
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
	  createLoginPanel();
	  multiPanel.add(loginVPanel);
	  mainPanel.insert(multiPanel,"Inicio", 0);
	  mainPanel.selectTab(0);
	  
	  inputTextArea.setText("Escribe aquí tu código");
	  consolaTextArea.setText("consola de salida");
	  
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
	  newPswdLabel.setVisible(true); confirmPswdLabel.setVisible(true);
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
	  newPswdLabel.setVisible(false); confirmPswdLabel.setVisible(false);
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
	  else if(!checkEmail(regEmailUserTextBox.getText())) {
		  Window.alert("El email de usuario debe ser una cuenta de correo válida");
	  }
	  else if(!checkEmail(regEmailSchoolTextBox.getText())){
		  Window.alert("El email de la escuela debe ser una cuenta de correo válida");
	  }
	  else if (!regNewPassword.getText().equals("") && regConfirmPassword.getText().equals("")){
		  Window.alert("Debes introducir la confirmación de la nueva contraseña");
	  }
	  else if (!regNewPassword.getText().equals("") && regPassword.getText().equals("")){
		  Window.alert("Para cambiar la contraseña debes introducir la actual");
	  }
	  else if(!regNewPassword.getText().equals(regConfirmPassword.getText())){
		  Window.alert("La nueva contraseña y su confirmación no coinciden. Por favor, introdúcelas de nuevo");
		  regPassword.setText("");
		  regNewPassword.setText("");
		  regConfirmPassword.setText("");
	  }
	  else{
		  try {
			  String requestStr = encodeParam("function", "changeUser")+"&"+
				encodeParam("name", regNameTextBox.getText())+"&"+
				encodeParam("surname1", regSurname1TextBox.getText())+"&"+
				encodeParam("surname2", regSurname2TextBox.getText())+"&"+
				encodeParam("email_user", regEmailUserTextBox.getText())+"&"+
				encodeParam("city", regCityTextBox.getText())+"&"+
				encodeParam("school", regSchoolTextBox.getText())+"&"+
				encodeParam("email_school", regEmailSchoolTextBox.getText())+"&"+
				encodeParam("oldpassword", regPassword.getText())+"&"+
				encodeParam("password", regNewPassword.getText());

			  Request request = builder.sendRequest(requestStr, new RequestCallback() {	
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
	  		inputTextArea.setEnabled(true);
			codiPanel.setSplitPosition(hPositionCode +"%");
			correPanel.setSplitPosition(vPositionCode +"%");
			break;
	  	case DEBUG:
	  		playButton.setEnabled(true);
	  		stopButton.setEnabled(false);
			codiPanel.setSplitPosition(hPositionComp +"%");
			correPanel.setSplitPosition(vPositionComp +"%");
			break;
	  	case EXECUTION:
	  		playButton.setEnabled(false);
	  		stopButton.setEnabled(true);
	  		inputTextArea.setEnabled(false);
			codiPanel.setSplitPosition(hPositionExec +"%");
			correPanel.setSplitPosition(vPositionExec +"%");
			break;
	  }
  }
  
  private void resetAll(){
	  if(modeOn == EXECUTION) engine.finishAnimation();
	  modeOn = CODE;
	  changeMode();
	  
	  inputTextArea.setText("Escribe aquí tu código");
	  consolaTextArea.setText("consola de salida");
	  
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
	  sizePagesDropBox.setSelectedIndex(0);
  }
  
  /**
   * Update the "Usuario" and "Tiempo" fields for all rows in the ranking table.
   * @param ranking data for all rows.
   */
  private void updateTable(JsArray<RankingUserData> rankInfo) {
	int rows = rankingFlexTable.getRowCount();
    for (int i=1; i<rows; i++) rankingFlexTable.removeRow(1);    
    for (int i=0; i<rankInfo.length(); i++) updateTable(rankInfo.get(i));
  }
  
  private void updateTable(JsArray<JSonData> invitationsInfo, String what) {
	  int rows = invitationsFlexTable.getRowCount();
	  for (int i=1; i<rows; i++) invitationsFlexTable.removeRow(1);
	  invitationsList.clear();
	  for (int i=0; i<invitationsInfo.length(); i++) updateTable(invitationsInfo.get(i),what);
  }
  
  /**
   * Update a single row in the ranking table.
   * @param ranking data for a single row.
  */
  private void updateTable(RankingUserData info) { 
     int row = rankingFlexTable.getRowCount();
     // Populate the "Usuario" and "Tiempo" fields with new data.
     rankingFlexTable.setText(row, 0, info.getNick());
     rankingFlexTable.setText(row, 1, formatTime(Integer.parseInt(info.getTiempo())));
     rankingFlexTable.getCellFormatter().addStyleName(row, 0, "rankingListColumn");
     rankingFlexTable.getCellFormatter().addStyleName(row, 1, "rankingListColumn");
  }
  
  private void updateTable(JSonData info, String what) { 
	  int row = invitationsFlexTable.getRowCount();
	  // Populate the "Invitacion" and "Respuesta" fields with new data.
	  String text = "Has sido invitado al "+what+" '"+info.get("name")+"' por el usuario '"+info.get("nick")+"' ";
	  invitationsFlexTable.setText(row, 0, text);
	  final String name = info.get("name");
	  final String whatTo = what;
	  invitationsList.add(name);
	  
	  // Add buttons to accept or deny this invitation from the table.
	  Button acceptInvitationButton = new Button("Aceptar");
	  acceptInvitationButton.addClickHandler(new ClickHandler() {
		  public void onClick(ClickEvent event) {
	        int removedIndex = invitationsList.indexOf(name);
	        setInvitationAnswer(whatTo,name,1);
	        invitationsList.remove(removedIndex);
	        invitationsFlexTable.removeRow(removedIndex + 1);
	      }
	    });
	  
	  Button denyInvitationButton = new Button("Rechazar");
	  denyInvitationButton.addClickHandler(new ClickHandler() {
		  public void onClick(ClickEvent event) {
	        int removedIndex = invitationsList.indexOf(name);
	        setInvitationAnswer(whatTo,name,0);
	        invitationsList.remove(removedIndex);
	        invitationsFlexTable.removeRow(removedIndex + 1);
	      }
	    });
	  
	  HorizontalPanel buttonsHPanel = new HorizontalPanel();
	  buttonsHPanel.setSpacing(5);
	  buttonsHPanel.add(acceptInvitationButton);
	  buttonsHPanel.add(denyInvitationButton);
	  invitationsFlexTable.setWidget(row, 1, buttonsHPanel);
	  invitationsFlexTable.getCellFormatter().setHorizontalAlignment(row,1, HasHorizontalAlignment.ALIGN_CENTER);
  }
  
  private String formatTime (int time){
	  int milesimas = time%1000;
	  int segs = time/1000;
	  segs = segs%60;
	  int mins = segs/60;
	  return mins+":"+segs+"."+milesimas; 
  }
  
  
  /**
   * Convert the string of JSON into JavaScript object.
   */  
  private final native RankingData asRankingData(String json) /*-{
	return eval('('+json+')');
  }-*/; 
  
  private final native UserData asUserData(String json) /*-{
	return eval('('+json+')');
  }-*/;
  
  private final native JSonData asJSonData(String json) /*-{
  	return eval('('+json+')');
  }-*/;
  
  private final native JsArray<JSonData> asJsArrayJSonData(String json) /*-{
	return eval('('+json+')');
  }-*/;
  
  private final native int asInt(String json) /*-{
  	return eval('('+json+')');
  }-*/;
  
  private final native String asString(String json) /*-{
	return eval('('+json+')');
  }-*/;
 
  private final native JsArrayString asJsArrayString(String json) /*-{
  	return eval('('+json+')');
  }-*/;
}