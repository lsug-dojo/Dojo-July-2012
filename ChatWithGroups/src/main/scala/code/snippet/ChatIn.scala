package code
package snippet

import net.liftweb._
import http._
import js._
import JsCmds._
import JE._
import util.Helpers
import Helpers._

import comet.ChatServer

/**
 * A snippet transforms input to output... it transforms
 * templates to dynamic content.  Lift's templates can invoke
 * snippets and the snippets are resolved in many different
 * ways including "by convention".  The snippet package
 * has named snippets and those snippets can be classes
 * that are instantiated when invoked or they can be
 * objects, singletons.  Singletons are useful if there's
 * no explicit state managed in the snippet.
 */
object ChatIn {

  /**
   * The render method in this case returns a function
   * that transforms NodeSeq => NodeSeq.  In this case,
   * the function transforms a form input element by attaching
   * behavior to the input.  The behavior is to send a message
   * to the ChatServer and then returns JavaScript which
   * clears the input.
   */
  def render = {
    var group = ""

    "#chat_group" #> SHtml.onSubmit(s => {
      group = s
    }) &
    "#chat_in" #> SHtml.onSubmit(s => {
      ChatServer ! new Message(s, group)
      SetValById("chat_in", "")
    }
    )
  }
}


case class Message (message:String, group:String)

