package gwtJuego.client;

import java.util.Date;

import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.client.JsArrayString;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.http.client.URL;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class ServerRequest {

	private static final String JSON_URL = "http://localhost/php/main.php?";
	
	 private void requestLogin(String user, String pswd) {
		  
		  if (user.equals("") || pswd.equals("")){
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
			      URL.encodeComponent(user)+"&"+URL.encodeComponent("password")+"="+
			      URL.encodeComponent(pswd), new RequestCallback() {
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
	  
	  private void requestTrace() {
		  
		  String url = "http://localhost/newbasic2.trc";
		  url = URL.encode(url);
		  //Send request to server and catch any errors.
		  RequestBuilder builder = new RequestBuilder(RequestBuilder.POST, url);
		  builder.setHeader("Content-Type","application/x-www-form-urlencoded");

		  try {
			  Request request = builder.sendRequest(URL.encodeComponent(""), new RequestCallback() {
				  public void onError(Request request, Throwable exception) {
					  Window.alert("Couldn't retrieve JSON");
				  }
				  public void onResponseReceived(Request request, Response response) {
					  if (200 == response.getStatusCode()) {
						  //String res = asString(response.getText());
						  engine.addAnimation(new CarAnimation(imagePanel,response.getText()));
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
