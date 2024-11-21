package task.scheduler

import task.scheduler.cli.CLIHandler

import scala.io.StdIn.readLine
import scala.util.control.Breaks.{break, breakable}

object TaskSchedulerCLI {
  private def start(): Unit = {
    val asciiArt =
      """
        | _____         _         ____       _              _       _
        ||_   _|_ _ ___| | __    / ___|  ___| |__   ___  __| |_   _| | ___ _ __
        |  | |/ _` / __| |/ /    \___ \ / __| '_ \ / _ \/ _` | | | | |/ _ \ '__|
        |  | | (_| \__ \   <      ___) | (__| | | |  __/ (_| | |_| | |  __/ |
        |  |_|\__,_|___/_|\_\    |____/ \___|_| |_|\___|\__,_|\__,_|_|\___|_|
        |""".stripMargin
    print("=================Welcome folks 🥳=====================================")
    println(asciiArt)
    println("====================================================================")
  }

  private def displayMenu(): Unit = {
    val menu: String =
      """
        |1. Schedule a Task.
        |2. Execute a task once after a delay (or without too 😁).
        |3. Cancel a Task.
        |4. Cancel all Tasks.
        |5. Show menu.
        |Others: Exit.
        |""".stripMargin
    println("Press the key as per your desired action")
    println(menu)
  }
  
  def main(args: Array[String]): Unit = {
    start()
    breakable {
      while (true) {
        displayMenu()
        readLine() match
          case "1" => CLIHandler.taskInputRecurring()
          case "2" => CLIHandler.taskInputOccurringOnce()
          case "3" => CLIHandler.cancelTask()
          case "4" => CLIHandler.cancelAllTasks()
          case "5" => displayMenu()
          case _ =>
            CLIHandler.cancelAllTasks()
            CLIHandler.shutDownScheduler()
            break
      }
    }
  }
}
