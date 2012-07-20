package code.comet

import net.liftweb._
import common.{Logger, Empty}
import http._
import js.JsCmds
import util._
import code.snippet.Message

class Chat extends CometActor with CometListener with Logger {
  private var msgs: Vector[Message] = Vector()
  private var group: Option[String] = None

  /**
   * When the component is instantiated, register as
   * a listener with the ChatServer
   */
  def registerWith = ChatServer

  /**
   * The CometActor is an Actor, so it processes messages.
   * In this case, we're listening for Vector[String],
   * and when we get one, update our private state
   * and reRender() the component.  reRender() will
   * cause changes to be sent to the browser.
   */
  override def lowPriority = {
    case v: Vector[Message] => msgs = v; reRender()
  }

  /**
   * Put the messages in the li elements and clear
   * any elements that have the clearable class.
   */
  def render = "li *" #> msgs.filter(message => group.map(_ == message.group).getOrElse(true))
    .map(message => message.group + ":" + message.message) &
    ("#chatrooms" #> SHtml.ajaxSelect(msgs.map(_.group).map(a => (a, a)).toSet.toSeq, group,
    selected => { group = Some(selected); reRender(); JsCmds.Noop; })) &
  ClearClearable
}
