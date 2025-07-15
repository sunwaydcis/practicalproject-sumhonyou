package ch.makery.address

import ch.makery.address.model.Person
import ch.makery.address.view.PersonEditDialogController
import javafx.fxml.FXMLLoader
import scalafx.application.JFXApp3
import scalafx.application.JFXApp3.PrimaryStage
import scalafx.scene.Scene
import scalafx.Includes.*
import javafx.scene as jfxs
import scalafx.beans.property.StringProperty
import scalafx.collections.ObservableBuffer
import scalafx.scene.image.Image
import scalafx.stage.{Modality, Stage}

object MainApp extends JFXApp3:

  //Window Root Pane
  var roots: Option[scalafx.scene.layout.BorderPane] = None
  var cssResource = getClass.getResource("view/DarkTheme.css")
  /**
   * The data as an observable list of Persons.
   */
  val personData = new ObservableBuffer[Person]()

  /**
   * Constructor
   */
  personData += new Person("Hans", "Muster")
  personData += new Person("Ruth", "Mueller")
  personData += new Person("Heinz", "Kurz")
  personData += new Person("Cornelia", "Meier")
  personData += new Person("Werner", "Meyer")
  personData += new Person("Lydia", "Kunz")
  personData += new Person("Anna", "Best")
  personData += new Person("Stefan", "Meier")
  personData += new Person("Martin", "Mueller")


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
      icons += new Image(getClass.getResource("/images/2824439_book_note_paper_school_icon.png").toExternalForm)
      scene = new Scene():
        root = roots.get
        stylesheets = Seq(cssResource.toExternalForm)

    // call to display PersonOverview when app start
    showPersonOverview()
  // actions for display person overview window
  def showPersonOverview(): Unit =
    val resource = getClass.getResource("view/PersonOverview.fxml")
    val loader = new FXMLLoader(resource)
    loader.load()
    val roots = loader.getRoot[jfxs.layout.AnchorPane]
    this.roots.get.center = roots

    val stringA = new StringProperty("Hello") //publisher
    val stringB = new StringProperty("sunway") //subscriber
    val stringC = new StringProperty("Sunway") //subscriber

    stringA.value = "world"
    stringB <==> stringA
    stringC <== stringA
    stringB.value = "google"
    println(stringA.value)

    stringA.onChange {(_, oldValue, newValue) =>
      println(s"stringA changed from $oldValue to $newValue")
    }

    stringA.onChange { (_, _,_) =>
      println(s"stringA has change!!!!!!!")
    }
    stringA.value ="world"


  def showPersonEditDialog(person: Person): Boolean =
    val resource = getClass.getResource("view/PersonEditDialog.fxml")
    val loader = new FXMLLoader(resource)
    loader.load()
    val roots2 = loader.getRoot[jfxs.Parent] // to get ui object and assign into roots2, load the anchor pane, parent is like container like grid pane
    val control = loader.getController[PersonEditDialogController] // get the controller object

    val dialog = new Stage():
      initModality(Modality.ApplicationModal) //the dialog stay on top of the object
      initOwner(stage)
      scene = new Scene:
        root = roots2
        stylesheets = Seq(cssResource.toExternalForm)

    control.dialogStage = dialog
    control.person = person
    dialog.showAndWait()
    //when this is executed the window will pop out, can as blocking core, it will block your next line
    control.okClicked


/**
 * anymouse function
 * val func1 = (a: Int) =>
 * a + 1
 * println(func1(8))
 * OR
 *
 * val func1: Int => Int = (_) =>
 * 1
 * println(func1(8))* */
