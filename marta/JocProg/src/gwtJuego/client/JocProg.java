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
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.PasswordTextBox;
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
	
	private static final String JSON_URL = "http://localhost/php/main.php?";
	private static final int CODE = 1;
	private static final int COMPILE = 2;
	private static final int EXECUTION = 3;

	private TabPanel mainPanel = new TabPanel();
	private HorizontalSplitPanel correPanel = new HorizontalSplitPanel();
	private AbsolutePanel imagePanel = new AbsolutePanel();
	private VerticalSplitPanel codiPanel = new VerticalSplitPanel();
	private HorizontalPanel consolaPanel = new HorizontalPanel();
	private VerticalPanel rankingVPanel = new VerticalPanel();
	private HorizontalPanel rankingHPanel = new HorizontalPanel();
	private HorizontalPanel ranking2HPanel = new HorizontalPanel();
	private HorizontalPanel multiPanel = new HorizontalPanel();
	private VerticalPanel loginVPanel = new VerticalPanel();
	private DisclosurePanel registerDisclosure = new DisclosurePanel("¿Aún no estás registrado?");
	private VerticalPanel perfilVPanel = new VerticalPanel();
	//private VerticalPanel addVPanel = new VerticalPanel();
	private HorizontalPanel adminHPanel = new HorizontalPanel();
	private DecoratedStackPanel adminStckPanel = new DecoratedStackPanel();
	private VerticalPanel newChampVPanel = new VerticalPanel();
	private VerticalPanel newTeamVPanel = new VerticalPanel();
	private VerticalPanel addPlayersVPanel = new VerticalPanel();
	private VerticalPanel invitationsVPanel = new VerticalPanel();
	
	private AnimationEngine engine = new AnimationEngine();

	
	private TextArea inputTextArea = new TextArea();
	private TextArea consolaTextArea = new TextArea();
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
	private TextBox champNameTextBox = new TextBox();
	private TextBox teamNameTextBox = new TextBox();
	private DateBox champDateBox = new DateBox();
	private Tree ChampsTree = new Tree();
	private TreeItem ItemNewChamp = new TreeItem();
	private TreeItem ItemAddChamp = new TreeItem();
	private Tree TeamsTree = new Tree();
	private TreeItem ItemNewTeam = new TreeItem();
	private TreeItem ItemAddTeam = new TreeItem();
	private Tree InvitationsTree = new Tree();
	private TreeItem ItemChampInvitations = new TreeItem();
	private TreeItem ItemTeamInvitations = new TreeItem();
	private SuggestBox suggestNickBox;
	private ListBox addPlayersDropBox = new ListBox(false);
	private Label addPlayersLabel = new Label();
	private FlexTable invitationsFlexTable = new FlexTable();
	private Image circuitImg = new Image();
	private Image carImg = new Image();

	private Button loginButton = new Button("Entrar");
	private Button logoutButton = new Button("Cerrar sesión");
	private Button regButton = new Button("Enviar");
	private Button changeButton = new Button("Modificar datos");
	private Button saveChangeButton = new Button("Guardar cambios");
	private Button playButton = new Button("¡Corre!");
	private Button stopButton = new Button("¡Para!");
	private Button createChampButton = new Button("Crear campeonato");
	private Button addCircuitsButton = new Button("Añadir circuito -->");
	private Button deleteCircuitsButton = new Button("Eliminar");
	private Button createTeamButton = new Button("Crear equipo");
	private Button addPlayersButton = new Button("Invitar");
	
	private ArrayList<String> circuitsList = new ArrayList<String>();
   	private ArrayList<String> champsList = new ArrayList<String>();
   	private ArrayList<String> teamsList = new ArrayList<String>();
   	private ArrayList<String> invitationsList = new ArrayList<String>();
   	//private String[] pageSizes = {"Tamaño","10","25","50","75","100"};
   	private String[] pageSizes = {"Tamaño","1","2","3","4","5","6","7","8","9","10"};
   	private MultiWordSuggestOracle oracle = new MultiWordSuggestOracle();
	
	private static String USER = "";
	private static int modeOn = CODE;
	private static int hPositionExec = 75;
	private static int vPositionExec = 75;
	private static int hPositionCode = 30;
	private static int vPositionCode = 95;
	private static int hPositionComp = 40;
	private static int vPositionComp = 25;
   

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
	  createAdminPanel();

	    
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
	  //mainPanel.add(prueba,"Help");
	  mainPanel.add(adminHPanel,"Administración");
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
						  codiPanel.setSplitPosition(hPositionCode +"%");
						  correPanel.setSplitPosition(vPositionCode +"%");
						  break;
				  	case COMPILE:
						  codiPanel.setSplitPosition(hPositionComp +"%");
						  correPanel.setSplitPosition(vPositionComp +"%");
						  break;
				  	case EXECUTION:
						  codiPanel.setSplitPosition(hPositionExec +"%");
						  correPanel.setSplitPosition(vPositionExec +"%");
						  break;
				  }
				  //int w = imagePanel.getOffsetWidth();
				  //int h = imagePanel.getOffsetHeight();
				  //Window.alert("width: "+String.valueOf(w)+" height: "+String.valueOf(h));
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
			  else if(tabIndex==4){ 
				  requestNInvitations();
			  }
		  }
	  });
	  
	  //Associate the Main panel with the HTML host page.
	  RootPanel.get("uiJuego").add(mainPanel);
	  //RootPanel.get("uiJuego").add(adminVPanel);
  }
  
  
  private void createLoginPanel(){
		
	  Grid loginLayout = new Grid(4, 2);
	  loginLayout.setCellSpacing(5);
	  CellFormatter loginCellFormatter = loginLayout.getCellFormatter();
	
	  // Add a title to the form
	  loginVPanel.clear();
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
	  //loginVPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
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
		    		  
		    		  perfilVPanel.clear();
		    		  perfilVPanel.add(logoutButton);

		    		  Grid perfilInfo = new Grid(8,4);
		    		  perfilInfo.setCellSpacing(6);
		    		  perfilInfo.setHTML(0, 0, "Usuario: ");
		    		  regNickTextBox.setText(res.get("nick"));
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
		    		  //CellFormatter perfilInfoCellFormatter = perfilInfo.getCellFormatter();
		    		  //perfilInfoCellFormatter.setHorizontalAlignment(8,3,HasHorizontalAlignment.ALIGN_RIGHT);
		    		  perfilVPanel.setSize("100%","100%");
		    		  perfilVPanel.add(perfilInfo);
		    		  perfilVPanel.add(changeButton);
		    		  saveChangeButton.setEnabled(false);
		    		  saveChangeButton.setVisible(false);
		    		  perfilVPanel.add(saveChangeButton);
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
	  consolaTextArea.setText("consola de salida");
	  consolaTextArea.setSize("100%","100%");
	  consolaPanel.setSize("100%","100%");
	  consolaPanel.setSpacing(5);
	  consolaPanel.add(consolaTextArea);
	  
	  //circuitImg.setUrl("http://localhost/img/newbasic2.png");
	  //circuitImg.setHeight("100%");
	  //circuitImg.setSize("100%","100%");
	  imagePanel.setSize("100%","100%");
	  //imagePanel.add(circuitImg);
	  HTMLPanel centerImagePanel = new HTMLPanel("<div align='center' style='background-color:#80FF80'><img src='http://localhost/img/newbasic2.png' height='100%'></div>");
	  imagePanel.add(centerImagePanel);
	  //carImg.setUrl("http://localhost/car.jpg");
	  
	  //Assemble Split Panels
	  correPanel.setSize("100%","100%");
	  correPanel.setSplitPosition("60%");
	  correPanel.setLeftWidget(imagePanel);
	  correPanel.setRightWidget(consolaPanel);
	  
	  HorizontalPanel buttonsPanel = new HorizontalPanel();
	  buttonsPanel.add(playButton);
	  stopButton.setEnabled(false);
	  buttonsPanel.add(stopButton);
	  
	  Button saveCodeButton = new Button("Guardar");
	  final TextBox codeNameTextBox = new TextBox();
	  buttonsPanel.add(saveCodeButton);
	  buttonsPanel.add(codeNameTextBox);
	  Button loadCodeButton = new Button("Cargar");
	  buttonsPanel.add(loadCodeButton);
	  
	  inputTextArea.setText("entrada de código");
	  inputTextArea.setSize("100%","100%");
	  VerticalPanel inputPanel = new VerticalPanel();
	  inputPanel.setSize("100%","100%");
	  inputPanel.setSpacing(5);
	  inputPanel.add(buttonsPanel);
	  inputPanel.add(inputTextArea);
		  
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
	  saveCodeButton.addClickHandler( 
			  new ClickHandler() {
				  public void onClick(ClickEvent event) {
					  requestSaveCode(inputTextArea.getText(),codeNameTextBox.getText());
				  }
			  });
	  loadCodeButton.addClickHandler( 
			  new ClickHandler() {
				  public void onClick(ClickEvent event) {
					  requestGetSavedCodes();
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
	  sizePagesDropBox.clear();
	  for(int i=0;i<pageSizes.length;i++) { sizePagesDropBox.addItem(pageSizes[i]); }
	  sizePagesDropBox.setEnabled(false);
	  rankPagesDropBox.clear();
	  rankPagesDropBox.addItem("Página");
	  rankPagesDropBox.setEnabled(false);
	  
	  Grid dropBoxes = new Grid (2,1);
	  dropBoxes.setWidget(0,0,sizePagesDropBox);
	  dropBoxes.setWidget(1,0,rankPagesDropBox);
	  
	  ranking2HPanel.setSize("100%", "70%");
	  ranking2HPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
	  ranking2HPanel.add(rankingFlexTable);
	  ranking2HPanel.add(dropBoxes);
	    
	  rankingVPanel.setSize("100%","100%");
	  rankingVPanel.add(rankingHPanel);
	  rankingVPanel.add(ranking2HPanel);
	  
	  // Listen for events on the DropBoxs.
	  circuitsDropBox.addChangeHandler(new ChangeHandler() {
		  public void onChange(ChangeEvent event) {
			  if(circuitsDropBox.getSelectedIndex()>0){
				  if(!USER.equals("")){
					  requestRanking();
					  refreshDropBoxs();
					  champsDropBox.setEnabled(true);
					  teamsDropBox.setEnabled(true);
					  sizePagesDropBox.setEnabled(true);
				  }
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

	  createNewChampPanel();
	  createNewTeamPanel();
	  createAddPlayersPanel();
	  createInvitationsPanel();
  
	  adminHPanel.setSize("100%","100%");
	  adminStckPanel.setSize("205px","50%");
	  ItemNewChamp = ChampsTree.addItem("Crear nuevo");
	  ItemAddChamp = ChampsTree.addItem("Invitar a jugadores");
	  ChampsTree.addSelectionHandler(new SelectionHandler<TreeItem>(){
		  public void onSelection(SelectionEvent<TreeItem> event){
			  TreeItem it = ChampsTree.getSelectedItem();
			  if(it.equals(ItemNewChamp)){
				  int n = adminHPanel.getWidgetCount();
				  if (n == 2) adminHPanel.remove(n-1);
				  adminHPanel.add(newChampVPanel);
			  }
			  else if(it.equals(ItemAddChamp)){
				  int n = adminHPanel.getWidgetCount();
				  if (n == 2) adminHPanel.remove(n-1);
				  addPlayersLabel.setText("¡ Invita a tus amigos a participar en tus campeonatos !");
				  refreshAddPlayersDropBox(1);
				  requestAllNicks();
				  suggestNickBox = new SuggestBox(oracle);
				  adminHPanel.add(addPlayersVPanel);
			  }
		  }
	  });
	
	  ItemNewTeam = TeamsTree.addItem("Crear nuevo");
	  ItemAddTeam = TeamsTree.addItem("Invitar a jugadores");
	  TeamsTree.addSelectionHandler(new SelectionHandler<TreeItem>(){
		  public void onSelection(SelectionEvent<TreeItem> event){
			  TreeItem it = TeamsTree.getSelectedItem();
			  if(it.equals(ItemNewTeam)){
				  int n = adminHPanel.getWidgetCount();
				  if (n == 2) adminHPanel.remove(n-1);
				  adminHPanel.add(newTeamVPanel);
			  }
			  else if(it.equals(ItemAddTeam)){
				  int n = adminHPanel.getWidgetCount();
				  if (n == 2) adminHPanel.remove(n-1);
				  addPlayersLabel.setText("¡ Invita a tus amigos a unirse a tus equipos !");
				  refreshAddPlayersDropBox(2);
				  requestAllNicks();
				  suggestNickBox = new SuggestBox(oracle);
				  adminHPanel.add(addPlayersVPanel);
			  }
		  }
	  });
	
	  ItemChampInvitations = InvitationsTree.addItem("A campeonatos");
	  ItemTeamInvitations = InvitationsTree.addItem("A equipos");
	  InvitationsTree.addSelectionHandler(new SelectionHandler<TreeItem>(){
		  public void onSelection(SelectionEvent<TreeItem> event){
			  TreeItem it = InvitationsTree.getSelectedItem();
			  if(it.equals(ItemChampInvitations)){
				  int n = adminHPanel.getWidgetCount();
				  if (n == 2) adminHPanel.remove(n-1);
				  refreshInvitationsTable("champs");
				  requestNInvitations();
				  adminHPanel.add(invitationsVPanel);
			  }
			  else if(it.equals(ItemTeamInvitations)){
				  int n = adminHPanel.getWidgetCount();
				  if (n == 2) adminHPanel.remove(n-1);
				  refreshInvitationsTable("teams");
				  requestNInvitations();
				  adminHPanel.add(invitationsVPanel);
			  }
		  }
	  });
	
	  adminStckPanel.add(ChampsTree, "Campeonatos");
	  adminStckPanel.add(TeamsTree, "Equipos");
	  adminStckPanel.add(InvitationsTree, "Invitaciones");
	  adminHPanel.add(adminStckPanel);
  }
  
  private void createNewChampPanel(){
	  
	  VerticalPanel champNameVPanel = new VerticalPanel();
	  champNameVPanel.setSize("100%","100%");
	  Grid champNameGrid = new Grid(3,2);
	  champNameGrid.setWidget(0,0,new HTML("Nombre: "));
	  champNameGrid.setWidget(0,1,champNameTextBox);
	  champNameGrid.setWidget(1,0,new HTML("Fecha límite inscripciones: "));
	  champDateBox.setFormat(new DateBox.DefaultFormat(DateTimeFormat.getFormat("dd/MM/yyyy")));
	  champNameGrid.setWidget(1,1,champDateBox);
	  //champNameGrid.setWidget(2,1,createChampButton);
	  champNameVPanel.add(champNameGrid);
	  //champNameVPanel.add(createChampButton);
	  champNameVPanel.setCellVerticalAlignment(champNameGrid,HasVerticalAlignment.ALIGN_MIDDLE);
	  //champNameVPanel.setCellVerticalAlignment(createChampButton,HasVerticalAlignment.ALIGN_BOTTOM);
	  
	  VerticalPanel champChooseVPanel = new VerticalPanel();
	  champChooseVPanel.setSize("100%","100%");
	  champChooseVPanel.add(new HTML("Circuitos disponibles:"));
	  Grid dispCircuitsGrid = new Grid(1,2);
	  circuitsMultiBox.setSize("130px","200px");
	  dispCircuitsGrid.setWidget(0,0,circuitsMultiBox);
	  dispCircuitsGrid.setWidget(0,1,addCircuitsButton);
	  CellFormatter dispCircsCellFormatter = dispCircuitsGrid.getCellFormatter();
	  dispCircsCellFormatter.setVerticalAlignment(0,1,HasVerticalAlignment.ALIGN_TOP);
	  champChooseVPanel.add(dispCircuitsGrid);
	  champChooseVPanel.add(new HTML("(para selección múltiple mantén pulsado 'Ctrl')"));
	  
	  VerticalPanel champAddVPanel = new VerticalPanel();
	  champAddVPanel.setSize("100%","100%");
	  champAddVPanel.add(new HTML("Circuitos seleccionados:"));
	  Grid selectedCircuitsGrid = new Grid(1,2);
	  selectedMultiBox.setSize("130px","200px");
	  selectedCircuitsGrid.setWidget(0,0,selectedMultiBox);
	  selectedCircuitsGrid.setWidget(0,1,deleteCircuitsButton);
	  CellFormatter selectedCircsCellFormatter = selectedCircuitsGrid.getCellFormatter();
	  selectedCircsCellFormatter.setVerticalAlignment(0,1,HasVerticalAlignment.ALIGN_TOP);
	  champAddVPanel.add(selectedCircuitsGrid);
	  champAddVPanel.add(createChampButton);
	  champAddVPanel.setCellVerticalAlignment(createChampButton,HasVerticalAlignment.ALIGN_BOTTOM);
	  champAddVPanel.setCellHorizontalAlignment(createChampButton,HasHorizontalAlignment.ALIGN_RIGHT);
	  
	  HorizontalPanel newChampHPanel = new HorizontalPanel();
	  newChampHPanel.setSize("100%","100%");
	  newChampHPanel.add(champNameVPanel);
	  newChampHPanel.add(champChooseVPanel);
	  newChampHPanel.add(champAddVPanel);
	  
	  newChampVPanel.setSize("100%","100%");
	  newChampVPanel.add(new HTML("Crear nuevo campeonato:"));
	  newChampVPanel.add(newChampHPanel);

	  
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
					  requestNewChampionship();
				  }
			  });

  }
  
  private void createNewTeamPanel(){
	  
	  Grid newTeam = new Grid(2,2);
	  newTeam.setWidget(0,0,new HTML("Nombre: "));
	  newTeam.setWidget(0,1,teamNameTextBox);
	  newTeam.setWidget(1,1,createTeamButton);
	  
	  newTeamVPanel.setSize("100%","100%");
	  newTeamVPanel.add(new HTML("¡Crea un nuevo equipo del que serás propietario!:"));
	  newTeamVPanel.add(newTeam);
	  
	  createTeamButton.addClickHandler( 
			  new ClickHandler() {
				  public void onClick(ClickEvent event) {
					  requestNewTeam();
				  }
			  });
  }
  
  private void createAddPlayersPanel(){
	  
	  requestAllNicks();
	  suggestNickBox = new SuggestBox(oracle);
	  
	  HorizontalPanel addPlayersHPanel = new HorizontalPanel();
	  addPlayersHPanel.add(addPlayersDropBox);
	  addPlayersHPanel.add(suggestNickBox);
	  addPlayersHPanel.add(addPlayersButton);
	  
	  addPlayersVPanel.setSize("100%","100%");
	  addPlayersVPanel.add(addPlayersLabel);
	  addPlayersVPanel.add(addPlayersHPanel);
	  
	  addPlayersButton.addClickHandler( 
			  new ClickHandler() {
				  public void onClick(ClickEvent event) {
					  if(addPlayersDropBox.getValue(0).equals("CAMPEONATOS")){
						  requestAddPlayerToChamp();
					  }
					  else if(addPlayersDropBox.getValue(0).equals("EQUIPOS")){
						  requestAddPlayerToTeam();
					  }
				  }
			  });  
  }
  
  private void createInvitationsPanel() {
	  
	  invitationsVPanel.setSize("100%", "100%");
	  invitationsVPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
	  
	  invitationsFlexTable.setText(0, 0, "Invitación");
	  invitationsFlexTable.setText(0, 1, "Respuesta");
	  invitationsVPanel.add(invitationsFlexTable);
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
  
  /*private void requestCircuitImage(int w, int h) {
	  
	  String url = JSON_URL;
	  url = URL.encode(url);
	  //Send request to server and catch any errors.
	  RequestBuilder builder = new RequestBuilder(RequestBuilder.POST, url);
	  builder.setHeader("Content-Type","application/x-www-form-urlencoded");

	  try {
		  Request request = builder.sendRequest(URL.encodeComponent("function")+"="+
		  URL.encodeComponent("getCircuitImage")+"&"+URL.encodeComponent("name")+"="+
				  URL.encodeComponent("Jerez")+"&"+URL.encodeComponent("width")+"="+
				  URL.encodeComponent(String.valueOf(w))+"&"+URL.encodeComponent("height")+"="+
				  URL.encodeComponent(String.valueOf(h)), new RequestCallback() {
			  public void onError(Request request, Throwable exception) {
				  Window.alert("Couldn't retrieve JSON");
			  }
		      public void onResponseReceived(Request request, Response response) {
		    	  if (200 == response.getStatusCode()) {
		    		  String res = asString(response.getText());
		    		  //coger la imagen con nombre res del directorio donde se cree
		    		  circuitImg.setUrl("http://localhost/img/"+res);
		    		  circuitImg.setSize("100%","100%");
		    		  imagePanel.add(circuitImg);
		          } else {
		        	Window.alert("Couldn't retrieve JSON (" + response.getStatusText()+ ")");
		          }
		      }
		  });
	  } catch (RequestException e) {
		  Window.alert("Couldn't retrieve JSON");
	  }
  }*/
  
  private void requestSaveCode(String code, String name) {
	  
	  String url = JSON_URL;
	  url = URL.encode(url);
	  //Send request to server and catch any errors.
	  RequestBuilder builder = new RequestBuilder(RequestBuilder.POST, url);
	  builder.setHeader("Content-Type","application/x-www-form-urlencoded");

	  try {
		  Request request = builder.sendRequest(URL.encodeComponent("function")+"="+
		  URL.encodeComponent("saveCode")+"&"+URL.encodeComponent("code")+"="+
				  URL.encodeComponent(code)+"&"+URL.encodeComponent("name")+"="+
				  URL.encodeComponent(name), new RequestCallback() {
			  public void onError(Request request, Throwable exception) {
				  Window.alert("Couldn't retrieve JSON");
			  }
		      public void onResponseReceived(Request request, Response response) {
		    	  if (200 == response.getStatusCode()) {
		    		  int res = asInt(response.getText());
		        	  if(res==1) Window.alert("El nombre de archivo ya existe");
		        	  else if(res==2) Window.alert("Se ha producido un error. Inténtalo de nuevo más tarde");
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
	  
	  String url = JSON_URL;
	  url = URL.encode(url);
	  //Send request to server and catch any errors.
	  RequestBuilder builder = new RequestBuilder(RequestBuilder.POST, url);
	  builder.setHeader("Content-Type","application/x-www-form-urlencoded");

	  try {
		  Request request = builder.sendRequest(URL.encodeComponent("function")+"="+
		  URL.encodeComponent("getSavedCodes"), new RequestCallback() {
			  public void onError(Request request, Throwable exception) {
				  Window.alert("Couldn't retrieve JSON");
			  }
		      public void onResponseReceived(Request request, Response response) {
		    	  if (200 == response.getStatusCode()) {
					  JsArray<JSonData> res =  asJsArrayJSonData(response.getText());
					  Window.alert(response.getText());
					  
					  //final DialogBox dialogBox = createDialogBox();
					  final DialogBox dialogBox = new DialogBox();
					  dialogBox.setAnimationEnabled(true);
					  
					  // Create a table to layout the content
					  VerticalPanel dialogContents = new VerticalPanel();
					  dialogContents.setSize("100%","100%");
					  dialogContents.setSpacing(4);
					  dialogBox.setWidget(dialogContents);

					  // Add some text to the top of the dialog
					  HTML details = new HTML("Elige el archivo que quieres cargar: ");
					  dialogContents.add(details);
					  dialogContents.setCellHorizontalAlignment(details, HasHorizontalAlignment.ALIGN_CENTER);
					  
					  // Add an image to the dialog
					  final ListBox savedCodesMultiBox = new ListBox(true);
					  savedCodesMultiBox.setSize("170px","200px");
					  
					  for (int i=0; i<res.length(); i++){
						  JSonData info = res.get(i);
						  String name = info.get("name");
						  String date = info.get("date");
						  //savedCodesMultiBox.addItem(name +"  ("+date+")");
						  savedCodesMultiBox.addItem(name);
					  }
					  
					  dialogContents.add(savedCodesMultiBox);
					  dialogContents.setCellHorizontalAlignment(savedCodesMultiBox, HasHorizontalAlignment.ALIGN_CENTER);
					  
					  // Add a load button at the bottom of the dialog
					  Button loadButton = new Button("Cargar",
							  new ClickHandler() {
						  		public void onClick(ClickEvent event) {
						  			requestLoadCode(savedCodesMultiBox.getItemText(savedCodesMultiBox.getSelectedIndex()));
						  			dialogBox.hide();
						  		}
					  });
					  dialogContents.add(loadButton);

					  // Add a cancel button at the bottom of the dialog
					  Button closeButton = new Button("Cancelar",
							  new ClickHandler() {
						  		public void onClick(ClickEvent event) {
						  			dialogBox.hide();
						  		}
					  });
					  dialogContents.add(closeButton);
					  dialogContents.setCellHorizontalAlignment(closeButton, HasHorizontalAlignment.ALIGN_RIGHT);
					  dialogContents.setCellHorizontalAlignment(loadButton, HasHorizontalAlignment.ALIGN_RIGHT);
					  
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
	  
	  String url = JSON_URL;
	  url = URL.encode(url);
	  //Send request to server and catch any errors.
	  RequestBuilder builder = new RequestBuilder(RequestBuilder.POST, url);
	  builder.setHeader("Content-Type","application/x-www-form-urlencoded");

	  try {
		  Request request = builder.sendRequest(URL.encodeComponent("function")+"="+
		  URL.encodeComponent("loadCode")+"&"+URL.encodeComponent("name")+"="+
				  URL.encodeComponent(name), new RequestCallback() {
			  public void onError(Request request, Throwable exception) {
				  Window.alert("Couldn't retrieve JSON");
			  }
		      public void onResponseReceived(Request request, Response response) {
		    	  if (200 == response.getStatusCode()) {
		    		  String res = asString(response.getText());
		    		  Window.alert(response.getText());
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
  
  private void requestCircuits() {    //al iniciar la aplicacion una sola vez
	  
	  String url = JSON_URL;
	  url = URL.encode(url);
	  //Send request to server and catch any errors.
	  RequestBuilder builder = new RequestBuilder(RequestBuilder.POST, url);
	  builder.setHeader("Content-Type","application/x-www-form-urlencoded");

	  try {
		  Request request = builder.sendRequest(URL.encodeComponent("function")+"="+
		  URL.encodeComponent("getCircuits"), new RequestCallback() {
			  public void onError(Request request, Throwable exception) {
				  Window.alert("Couldn't retrieve JSON");
			  }
		      public void onResponseReceived(Request request, Response response) {
		    	  if (200 == response.getStatusCode()) {
		    		  JsArrayString circs = asJsArrayString(response.getText());
		    		  circuitsList.clear();
		    		  circuitsDropBox.clear();
		    		  circuitsDropBox.addItem("CIRCUITOS");
		    		  for(int i=0;i<circs.length();i++) { circuitsList.add(circs.get(i)); }
		    		  for (int i=0; i<circuitsList.size(); i++) { 
		    			  circuitsDropBox.addItem((String)circuitsList.get(i));
		    			  circuitsMultiBox.addItem((String)circuitsList.get(i));
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
  

  private void refreshDropBoxs() {   //cuando se elija la pestaña de ranking
	  
	  if (circuitsDropBox.getSelectedIndex() != 0){
		  String circ = circuitsDropBox.getValue(circuitsDropBox.getSelectedIndex());
		  String url = JSON_URL;
		  url = URL.encode(url);
		  //Send request to server and catch any errors.
		  RequestBuilder builder = new RequestBuilder(RequestBuilder.POST, url);
		  builder.setHeader("Content-Type","application/x-www-form-urlencoded");
		
		  try{
			  Request request = builder.sendRequest(URL.encodeComponent("function")+"="+
			  URL.encodeComponent("getMyChampionships")+"&"+URL.encodeComponent("circuit")+"="+
			  URL.encodeComponent(circ), new RequestCallback() {
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
			  Request request = builder.sendRequest(URL.encodeComponent("function")+"="+
			  URL.encodeComponent("getMyTeams")+"&"+URL.encodeComponent("circuit")+"="+
			  URL.encodeComponent(circ)+"&"+URL.encodeComponent("championship")+"="+
			  URL.encodeComponent(champ), new RequestCallback() {
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
		  String circuit = (String)circuitsList.get(circuitsDropBox.getSelectedIndex() - 1);
		  String champ;
		  if(champsDropBox.getSelectedIndex()==0) champ = "";
		  else champ = (String)champsList.get(champsDropBox.getSelectedIndex() - 1);
		  String team;
		  if(teamsDropBox.getSelectedIndex()==0) team = "";
		  else team = (String)teamsList.get(teamsDropBox.getSelectedIndex() - 1);

		  String url = JSON_URL;
		  url = URL.encode(url);
		  // Send request to server and catch any errors.
		  RequestBuilder builder = new RequestBuilder(RequestBuilder.POST, url);
		  builder.setHeader("Content-Type","application/x-www-form-urlencoded");

		  try {
			  Request request = builder.sendRequest(URL.encodeComponent("function")+"="+
					  URL.encodeComponent("getRankings")+"&"+URL.encodeComponent("circuit")+"="+
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

	  Date today = new Date(); 
	  if (champNameTextBox.getText().equals("")){
		  Window.alert("Debes indicar un nombre para el circuito");
	  } 
	 else if (champDateBox.getValue() == null){
		  Window.alert("Debes indicar una fecha correcta");
	  }
	  else if (champDateBox.getValue().before(today)){
		  Window.alert("Debes seleccionar una fecha límite para las inscripciones posterior al día de creación");
	  }
	  else if (selectedMultiBox.getItemCount()==0){
		  Window.alert("El nuevo campeonato debe constar de al menos un circuito");
		  
	  }
	  else{
		  String url = JSON_URL;
		  url = URL.encode(url);
		  String dateString = DateTimeFormat.getFormat("dd/MM/yyyy").format(champDateBox.getValue());
		  String arrayCircs = selectedMultiBox.getItemText(0);
		  for (int i=1;i<selectedMultiBox.getItemCount();i++) { arrayCircs += "+"+selectedMultiBox.getItemText(i); }
		  //Send request to server and catch any errors.
		  RequestBuilder builder = new RequestBuilder(RequestBuilder.POST, url);
		  builder.setHeader("Content-Type","application/x-www-form-urlencoded");
		  Window.alert(arrayCircs);
		  
		  try {
			  Request request = builder.sendRequest(URL.encodeComponent("function")+"="+
		      URL.encodeComponent("newChampionship")+"&"+URL.encodeComponent("name")+"="+
		      URL.encodeComponent(champNameTextBox.getText())+"&"+URL.encodeComponent("date_limit")+"="+
		      URL.encodeComponent(dateString)+"&"+URL.encodeComponent("circuits")+"="+
		      URL.encodeComponent(arrayCircs), new RequestCallback() {
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
		        		  for (int i=0; i<circuitsList.size(); i++) { circuitsMultiBox.addItem((String)circuitsList.get(i)); }
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
  }
  
  private void requestNewTeam() {
	  if (teamNameTextBox.getText().equals("")){
		  Window.alert("Debes indicar un nombre para el equipo");
	  }
	  else{
		  String url = JSON_URL;
		  url = URL.encode(url);
		  //Send request to server and catch any errors.
		  RequestBuilder builder = new RequestBuilder(RequestBuilder.POST, url);
		  builder.setHeader("Content-Type","application/x-www-form-urlencoded");

		  try {
			  Request request = builder.sendRequest(URL.encodeComponent("function")+"="+
		      URL.encodeComponent("newTeam")+"&"+URL.encodeComponent("name")+"="+
		      URL.encodeComponent(teamNameTextBox.getText()), new RequestCallback() {
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
  }
  
  
  private void requestAllNicks() {
	  
	  String url = JSON_URL;
	  url = URL.encode(url);
	  //Send request to server and catch any errors.
	  RequestBuilder builder = new RequestBuilder(RequestBuilder.POST, url);
	  builder.setHeader("Content-Type","application/x-www-form-urlencoded");

	  try {
		  Request request = builder.sendRequest(URL.encodeComponent("function")+"="+
		  URL.encodeComponent("getAllNicks"), new RequestCallback() {
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
	  
	  String url = JSON_URL;
	  url = URL.encode(url);
	  //Send request to server and catch any errors.
	  RequestBuilder builder = new RequestBuilder(RequestBuilder.POST, url);
	  builder.setHeader("Content-Type","application/x-www-form-urlencoded");

	  try {
		  if(op == 1){
			  Request request = builder.sendRequest(URL.encodeComponent("function")+"="+
					  URL.encodeComponent("getMyOwnChampionships"), new RequestCallback() {
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
			  Request request = builder.sendRequest(URL.encodeComponent("function")+"="+
					  URL.encodeComponent("getMyOwnTeams"), new RequestCallback() {
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
	  if (addPlayersDropBox.getSelectedIndex() == 0){
		  Window.alert("Debes elegir un campeonato");
	  }
	  else if (suggestNickBox.getText().equals("")){
		  Window.alert("Debes introducir el nombre del usuario al que quieres invitar");
	  }
	  else{
		  String url = JSON_URL;
		  url = URL.encode(url);
		  //Send request to server and catch any errors.
		  RequestBuilder builder = new RequestBuilder(RequestBuilder.POST, url);
		  builder.setHeader("Content-Type","application/x-www-form-urlencoded");
		  String name = addPlayersDropBox.getValue(addPlayersDropBox.getSelectedIndex());
		  String nick = suggestNickBox.getText();

		  try {
			  Request request = builder.sendRequest(URL.encodeComponent("function")+"="+
		      URL.encodeComponent("addPlayerToChampionship")+"&"+URL.encodeComponent("name")+"="+
		      URL.encodeComponent(name)+"&"+URL.encodeComponent("nick")+"="+
		      URL.encodeComponent(nick), new RequestCallback() {
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
}

  private void requestAddPlayerToTeam() {
	  if (addPlayersDropBox.getSelectedIndex() == 0){
		  Window.alert("Debes elegir un equipo");
	  }
	  else if (suggestNickBox.getText().equals("")){
		  Window.alert("Debes introducir el nombre del usuario al que quieres invitar");
	  }
	  else{
		  String url = JSON_URL;
		  url = URL.encode(url);
		  //Send request to server and catch any errors.
		  RequestBuilder builder = new RequestBuilder(RequestBuilder.POST, url);
		  builder.setHeader("Content-Type","application/x-www-form-urlencoded");
		  String name = addPlayersDropBox.getValue(addPlayersDropBox.getSelectedIndex());
		  String nick = suggestNickBox.getText();

		  try {
			  Request request = builder.sendRequest(URL.encodeComponent("function")+"="+
		      URL.encodeComponent("addPlayerToTeam")+"&"+URL.encodeComponent("name")+"="+
		      URL.encodeComponent(name)+"&"+URL.encodeComponent("nick")+"="+
		      URL.encodeComponent(nick), new RequestCallback() {
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
}
  
  private void requestNInvitations() {
	  
	  String url = JSON_URL;
	  url = URL.encode(url);
	  //Send request to server and catch any errors.
	  RequestBuilder builder = new RequestBuilder(RequestBuilder.POST, url);
	  builder.setHeader("Content-Type","application/x-www-form-urlencoded");

	  try {
		  Request request = builder.sendRequest(URL.encodeComponent("function")+"="+
				  URL.encodeComponent("getNInvitations"), new RequestCallback() {
			  public void onError(Request request, Throwable exception) {
				  Window.alert("Couldn't retrieve JSON");
			  }
			  public void onResponseReceived(Request request, Response response) {
				  if (200 == response.getStatusCode()) {
					  //JSonArrayData res = asJSonArrayData(response.getText());
					  JSonData res = asJSonData(response.getText());
					  //int nToChamps = res.getInt("nChamps");
					  //int nToTeams = res.getInt("nTeams");
					  String nToChamps = res.get("nChamps");
					  String nToTeams = res.get("nTeams");
					  int total = Integer.parseInt(nToChamps) + Integer.parseInt(nToTeams);
					  adminStckPanel.setStackText(2, "Invitaciones ("+total+")");
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

	  String url = JSON_URL;
	  url = URL.encode(url);
	  // Send request to server and catch any errors.
	  RequestBuilder builder = new RequestBuilder(RequestBuilder.POST, url);
	  builder.setHeader("Content-Type","application/x-www-form-urlencoded");

	  try {
		  if (what.equals("champs")){
			  Request request = builder.sendRequest(URL.encodeComponent("function")+"="+
					  URL.encodeComponent("getChampionshipsInvited"), new RequestCallback() {
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
			  Request request = builder.sendRequest(URL.encodeComponent("function")+"="+
					  URL.encodeComponent("getTeamsInvited"), new RequestCallback() {
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
  
  private void setInvitationAnswer(String what, String name, int answer) {
	  
	  String url = JSON_URL;
	  url = URL.encode(url);
	  //Send request to server and catch any errors.
	  RequestBuilder builder = new RequestBuilder(RequestBuilder.POST, url);
	  builder.setHeader("Content-Type","application/x-www-form-urlencoded");
	  
	  try {
		  if(what.equals("campeonato")){
			  Request request = builder.sendRequest(URL.encodeComponent("function")+"="+
					  URL.encodeComponent("setChampionshipAnswer")+"&"+URL.encodeComponent("name")+"="+
					  URL.encodeComponent(name)+"&"+URL.encodeComponent("answer")+"="+
					  URL.encodeComponent(String.valueOf(answer)), new RequestCallback() {
				  public void onError(Request request, Throwable exception) {
					  Window.alert("Couldn't retrieve JSON");
				  }
			      public void onResponseReceived(Request request, Response response) {
			    	  if (200 == response.getStatusCode()) {
			    		  //retornara algo??
			    		  requestNInvitations();
			          } else {
			        	Window.alert("Couldn't retrieve JSON (" + response.getStatusText()+ ")");
			          }
			      }
			  });
		  }
		  else if(what.equals("equipo")){
			  Request request = builder.sendRequest(URL.encodeComponent("function")+"="+
					  URL.encodeComponent("setTeamAnswer")+"&"+URL.encodeComponent("name")+"="+
					  URL.encodeComponent(name)+"&"+URL.encodeComponent("answer")+"="+
					  URL.encodeComponent(String.valueOf(answer)), new RequestCallback() {
				  public void onError(Request request, Throwable exception) {
					  Window.alert("Couldn't retrieve JSON");
				  }
			      public void onResponseReceived(Request request, Response response) {
			    	  if (200 == response.getStatusCode()) {
			    		  //retornara algo??
				          requestNInvitations();  //hacerlo sin llamada, modificando string!!
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
  
  
  private void logout(){
	  USER = ""; 
	  
	  multiPanel.remove(perfilVPanel);
	  
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
			codiPanel.setSplitPosition(hPositionCode +"%");
			correPanel.setSplitPosition(vPositionCode +"%");
			imagePanel.remove(carImg);
			break;
	  	case COMPILE:
	  		playButton.setEnabled(true);
	  		stopButton.setEnabled(false);
			codiPanel.setSplitPosition(hPositionComp +"%");
			correPanel.setSplitPosition(vPositionComp +"%");
			break;
	  	case EXECUTION:
	  		playButton.setEnabled(false);
	  		stopButton.setEnabled(true);
			codiPanel.setSplitPosition(hPositionExec +"%");
			correPanel.setSplitPosition(vPositionExec +"%");
			String trace = "290 142 0 290.021 142 0.000497709 290.094 142 0.00252046 290.219 142.001 0.00610232 "+
							"290.397 142.002 0.0112484 290.625 142.006 0.0179655 290.906 142.012 0.0262623 291.237 142.023 0.0361479 "+
							"291.618 142.039 0.0476322 292.05 142.063 0.0607244 292.531 142.097 0.0754323 293.06 142.142 0.0917614 "+
							"293.638 142.202 0.109713 294.263 142.278 0.129284 294.934 142.374 0.150464 295.65 142.492 0.173235 "+
							"296.411 142.637 0.197569 297.215 142.811 0.223426 298.061 143.018 0.250753 298.949 143.262 0.279484 299.876 143.546 0.309536 "+
							"300.842 143.875 0.340812 301.846 144.253 0.373197 302.885 144.685 0.406562 303.96 145.174 0.440763 305.067 145.725 0.475645 "+
							"306.207 146.343 0.51104 307.377 147.033 0.546777 308.536 147.772 0.581811 309.644 148.532 0.614287 310.778 149.365 0.646732 "+
							"311.935 150.275 0.679012 313.115 151.266 0.710998 314.317 152.341 0.74257 315.538 153.506 0.773616 316.779 154.763 0.804037 "+
							"318.038 156.117 0.833745 319.313 157.573 0.862663 320.49 158.992 0.888704 321.612 160.414 0.912199 322.747 161.924 0.935075 "+
							"323.896 163.525 0.957299 325.056 165.22 0.978845 326.228 167.011 0.999689 327.41 168.901 1.01982 328.601 170.892 1.03922 "+
							"329.802 172.988 1.05788 330.899 174.983 1.0744 331.976 177.015 1.08972 333.06 179.139 1.10449 334.152 181.355 1.11872 "+
							"335.25 183.665 1.1324 336.355 186.071 1.14554 337.423 188.478 1.15777 338.447 190.858 1.16887 339.477 193.325 1.17953 "+
							"340.512 195.879 1.18976 341.552 198.52 1.19955 342.597 201.249 1.20891 343.625 204.011 1.21772 344.61 206.724 1.22568 "+
							"345.6 209.517 1.23328 346.594 212.389 1.24052 347.592 215.341 1.24739 348.595 218.373 1.25389 349.586 221.432 1.25997 "+
							"350.548 224.463 1.26549 351.515 227.565 1.27068 352.487 230.738 1.27555 353.464 233.981 1.28009 354.445 237.292 1.2843 "+
							"355.414 240.608 1.28813 356.373 243.939 1.29158 357.339 247.332 1.29472 358.311 250.783 1.29754 359.288 254.293 1.30004 "+
							"360.272 257.856 1.30222 361.248 261.417 1.30404 362.231 265.028 1.30554 363.222 268.686 1.30671 364.222 272.39 1.30754 "+
							"365.226 276.121 1.30802 366.24 279.892 1.30815 367.261 283.688 1.30792 368.26 287.396 1.30734 369.234 290.998 1.30643 "+
							"370.178 294.478 1.30524 371.093 297.832 1.30378 371.98 301.062 1.30208 372.851 304.21 1.30015 373.695 307.238 1.29801 "+
							"374.511 310.137 1.2957 375.297 312.909 1.29326 376.055 315.552 1.29069 376.783 318.069 1.28802 377.482 320.459 1.28528 "+
							"378.173 322.799 1.28241 378.834 325.014 1.27948 379.464 327.101 1.27655 380.062 329.061 1.27362 380.628 330.897 1.27073 "+
							"381.161 332.609 1.26789 381.66 334.2 1.26513";
			engine.addAnimation(new CarAnimation(imagePanel,trace));
			break;
	  }
	  //int w = imagePanel.getOffsetWidth();
	  //int h = imagePanel.getOffsetHeight();
	  //Window.alert("width: "+String.valueOf(w)+" height: "+String.valueOf(h));
	  //requestCircuitImage(w,h);  //faltara name del circuito
  }
  
  /**
   * Update the "Usuario" and "Tiempo" fields for all rows in the ranking table.
   * @param ranking data for all rows.
   */
  private void updateTable(JsArray<RankingUserData> rankInfo) {
	int rows = rankingFlexTable.getRowCount();
    for (int i=1; i<rows; i++) rankingFlexTable.removeRow(1);    
	//rankingFlexTable.setText(0, 0, "Usuario");
	//rankingFlexTable.setText(0, 1, "Tiempo");
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
     rankingFlexTable.setText(row, 1, info.getTiempo());
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
	  buttonsHPanel.add(acceptInvitationButton);
	  buttonsHPanel.add(denyInvitationButton);
	  invitationsFlexTable.setWidget(row, 1, buttonsHPanel);
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
  
 //private final native JSonArrayData asJSonArrayData(String json) /*-{
 //	return eval('('+json+')');
 // }-*/;
  
  private final native JsArray<JSonData> asJsArrayJSonData(String json) /*-{
  	return eval('('+json+')');
  }-*/;
  
  private final native JSonData asJSonData(String json) /*-{
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