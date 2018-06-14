package de.htwg.se.connectfour

import akka.actor.{Actor, ActorSystem, Props}
import com.google.inject.Guice
import de.htwg.se.connectfour.mvc.controller.Controller
import de.htwg.se.connectfour.mvc.model.player.RealPlayer
import com.typesafe.scalalogging.LazyLogging
import de.htwg.se.connectfour.mvc.view.{GamingPlayers, Gui, HTTPServer, Tui}
import de.htwg.se.connectfour.mvc.persistence.PlayerDB

import scala.io.StdIn

object Main extends LazyLogging {

  object debug {
    def filter = false
  }

  class ServerActor extends Actor {
    def receive = {
      case s: String => println("String: " + s)
      case i: Int => println("Number: " + i)
    }
  }

  val system = ActorSystem("ActorSystem");
  val actor = system.actorOf(Props[ServerActor], "ServerActor")

  println()

  def main(args: Array[String]): Unit = {

    val injector = Guice.createInjector(new ConnectFourModule)
    val controller = injector.getInstance(classOf[Controller])
    controller.setActorSystem(system);
    val player1 = getPlayersName("Player 1")
    val player2 = getPlayersName("Player 2")
    val players = new GamingPlayers(player1, player2, controller, controller.actor)

    startGame(controller, players)
  }

  def getPlayersName(player: String): RealPlayer ={
    println(player + " Please insert Name\n")
    val playername = StdIn.readLine().toString
    if (playername != null) {
      val realPlayer = RealPlayer(playername)
      PlayerDB.create(realPlayer)
      realPlayer
    }
    else
      getPlayersName(player)
  }

  def startGame(controller: Controller, players: GamingPlayers): Unit = {

    logger.info("starting")

    var i = 0
    for(i <- 1 to 2) {
      logger.info(s"DB Player $i: ${PlayerDB.read(i)}")
    }

    Tui(controller, players)

    /*
      Console.print("Do you want to start gui (y/n): ")
      val input = StdIn.readLine()
      if (input.equalsIgnoreCase("y")) {
        if (Main.debug.filter) logger.info("started GUI")
        Gui(controller, players)
      } else {
        if (Main.debug.filter) logger.info("started TUI")
        Tui(controller, players)
    }
    */
  }
}
