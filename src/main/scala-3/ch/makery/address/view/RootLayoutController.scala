package ch.makery.address.view

import ch.makery.address.MainApp
import javafx.event.ActionEvent
import javafx.fxml.FXML

@FXML
class RootLayoutController():
  @FXML //Cannot import scalafx cz we liasing the view (scene builder), its part of the java
  def handleClose(action: ActionEvent): Unit = 
    MainApp.stage.close()
    
  @FXML
  def handleDelete(action: ActionEvent): Unit =
    MainApp.personOverviewController.map(x => x.handleDeletePerson(action)) //x is personOverviewController; which reflect reusing the action from another controller and use inside to another controller
    