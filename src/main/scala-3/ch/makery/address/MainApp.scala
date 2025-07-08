package ch.makery.address

import ch.makery.address.view.PersonEditDialogController
import javafx.fxml.FXMLLoader
import scalafx.application.JFXApp3
import scalafx.application.JFXApp3.PrimaryStage
import scalafx.scene.Scene
import scalafx.Includes.*
import javafx.scene as jfxs
import javafx.stage.Stage
import scalafx.stage.Modality 

object MainApp extends JFXApp3:

  //Window Root Pane
  var roots: Option[scalafx.scene.layout.BorderPane] = None

  override def start(): Unit =
    // transform path of RootLayout.fxml to URI for resource location.
    val rootResource = getClass.getResource("view/RootLayout.fxml")
    // initialize the loader object.
    val loader = new FXMLLoader(rootResource)
    // Load root layout from fxml file.
    loader.load()

    // retrieve the root component BorderPane from the FXML
    roots = Option(loader.getRoot[jfxs.layout.BorderPane])

    stage = new PrimaryStage():
      title = "AddressApp"
      scene = new Scene():
        root = roots.get

    // call to display PersonOverview when app start
    showPersonOverview()
  // actions for display person overview window
  def showPersonOverview(): Unit =
    val resource = getClass.getResource("view/PersonOverview.fxml")
    val loader = new FXMLLoader(resource)
    loader.load()
    val roots = loader.getRoot[jfxs.layout.AnchorPane]
    this.roots.get.center = roots

  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  def showPersonEditDialog(person: Person): Boolean =
    val resource = getClass.getResource("view/PersonEditDialog.fxml")
    val loader = new FXMLLoader(resource)
    loader.load();
    val roots2 = loader.getRoot[jfxs.Parent] //To get Ui object and assign into roots2, load the anchor pane, parent is like container like grid pane, anchor pane or use anchor pane can also
    val control = loader.getController[PersonEditDialogController] //Get controller object

    val dialog = new Stage():
      initModality(Modality.ApplicationModal) //The dialog stay on top of the application
      initOwner(stage) //if the window is close, it will fall back to main stage (window that mention earlier here as primary stage)
      scene = new Scene:
        root = roots2

    control.dialogStage = dialog //Initialise the dialog window that we created. Controller need dialog object because the behaviour it have close or ok behaviour so link to dialog 
    control.person = person
    dialog.showAndWait()
    //When the specific code block under the object will start execution the code and Show the window
    //Pause the program and wait for user to interact with it (e.g., click "OK" or "Cancel")
    //After the user is done, it continues running the rest of the code

    control.okClicked

