package ch.makery.address.view

import ch.makery.address.model.Person
import ch.makery.address.MainApp
import javafx.scene.control.{ Label, TableColumn, TextField}
import scalafx.scene.control.Alert
import scalafx.stage.Stage
import scalafx.Includes.*
import ch.makery.address.util.DateUtil.*
import javafx.fxml.FXML
import javafx.event.ActionEvent

@FXML
class PersonEditDialogController ():
  @FXML
  private var firstNameField: TextField = null
  @FXML
  private var lastNameField: TextField = null
  @FXML
  private var streetField: TextField = null
  @FXML
  private var postalCodeField: TextField = null
  @FXML
  private var cityField: TextField = null
  @FXML
  private var birthdayField: TextField = null

  //important properties
  var         dialogStage  : Stage  = null //stage is window; window need ui to display
  private var __person     : Person = null //null means haven't initialise yet; Why person properties got mentioned here because this person edit dialog got store a person type of data, so the controller need to store this also
  var         okClicked             = false

  def person = __person
  def person_(x : Person): Unit =
    __person = x //update the data field with the parsing value

    //Once the person have value, then all text field will fill with value and assign back to the variable that defined inside the person object
    firstNameField.text = __person.firstName.value
    lastNameField.text  = __person.lastName.value
    streetField.text    = __person.street.value
    postalCodeField.text= __person.postalCode.value.toString
    cityField.text      = __person.city.value
    birthdayField.text  = __person.date.value.asString
    birthdayField.setPromptText("dd.mm.yyyy")

  @FXML //it mention this action collaborate/link fxml; Only the function that need to bind to FXML
  def handleOk(action :ActionEvent): Unit =

    if (isInputValid()) //the if no need else can also
      __person.firstName <== firstNameField.text //Binding is not necessary
      __person.lastName  <== lastNameField.text
      __person.street    <== streetField.text
      __person.city      <== cityField.text
      __person.postalCode.value = postalCodeField.getText().toInt
      __person.date.value       = birthdayField.text.value.parseLocalDate.getOrElse(null)
      //birthdayField.text.value will be string value,
      // so parseLocalDate will change to Local Date, but local date is not one of the member of Option,
      // so need use getOrElse, if true will come to get means return local date object

      okClicked = true
      dialogStage.close() //close the window

  @FXML
  def handleCancel(action :ActionEvent): Unit =
    dialogStage.close()

  def nullChecking (x : String) = x == null || x.length == 0

  def isInputValid() : Boolean =
    var errorMessage = ""

    if (nullChecking(firstNameField.text.value)) then
      errorMessage += "No valid first name!\n"
    if (nullChecking(lastNameField.text.value)) then
      errorMessage += "No valid last name!\n"
    if (nullChecking(streetField.text.value)) then
      errorMessage += "No valid street!\n"
    if (nullChecking(postalCodeField.text.value)) then
      errorMessage += "No valid postal code!\n"
    else
      try
        postalCodeField.getText().toInt
        //postalCodeField is now Java fx object, so need call getText,
        // to retrieve the value inside the postalCodeField and change to String type
      catch
        case e : NumberFormatException =>
          errorMessage += "No valid postal code (must be an integer)!\n"

    if (nullChecking(cityField.text.value)) then
      errorMessage += "No valid city!\n"
    if (nullChecking(birthdayField.text.value)) then
      errorMessage += "No valid birtday!\n"
    else
      if (!birthdayField.text.value.isValid) then
        errorMessage += "No valid birthday. Use the format dd.mm.yyyy!\n";

    if (errorMessage.length() == 0) then
      true
    else
      // Show the error message.
      val alert = new Alert(Alert.AlertType.Error):
        initOwner(dialogStage)
        title = "Invalid Fields"
        headerText = "Please correct invalid fields"
        contentText = errorMessage
      alert.showAndWait()
//        Two-way to fix the ShowAndWait: either call the alert object again and indent to left means outside the alert properties.
        // 2nd way:
      false
    end if //end if is like mention is end of the if..else statement in scala 3 version
