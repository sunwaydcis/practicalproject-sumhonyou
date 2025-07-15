package ch.makery.address.view

import ch.makery.address.MainApp
import javafx.event.ActionEvent
import javafx.fxml.FXML

@FXML
class RootLayoutController():
  @FXML //Cannot import scalafx cz we liasing the view (scene builder), its part of the java
  def handleClose(action: ActionEvent): Unit = 
    MainApp.stage.close()
