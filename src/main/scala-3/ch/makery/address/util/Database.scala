package ch.makery.address.util
import scalikejdbc.* //Using Derby Database using JDBC; Library to connect the database
import ch.makery.address.model.Person

trait Database :
  val derbyDriverClassname = "org.apache.derby.jdbc.EmbeddedDriver"
  val dbURL = "jdbc:derby:myDB;create=true;"; //myDB is the database file name
  // initialize JDBC driver & connection pool
  Class.forName(derbyDriverClassname) //For name: Load the class into JVM; Trigger to run database
  ConnectionPool.singleton(dbURL, "me", "mine") //Executing the SQL in scala way, me is username, mine is pwd
  // ad-hoc session provider on the REPL
  given AutoSession = AutoSession //given : for all autosession type, implicit parameter will have AutoSession value

object Database extends Database :
  def setupDB() =
    if (!hasDBInitialize)
      Person.initializeTable()
  def hasDBInitialize : Boolean =
    DB getTable "Person" match //DB singleton object; more details find it at scalikejdbc api document
      case Some(x) => true
      case None => false
