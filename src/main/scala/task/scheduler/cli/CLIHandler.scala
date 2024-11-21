package task.scheduler.cli

import task.scheduler.managers.TaskManager
import task.scheduler.models.Task
import scala.io.StdIn.readLine

object CLIHandler {
  private def inputTaskDesc: String = {
    println("Enter the name for your task.")
    readLine().trim
  }
  private def inputLogInfo: String = {
    println("Enter the statement you wish to print.")
    readLine().trim
  }
  private def inputDelay: Long = {
    println("Enter the delay for the print? (Put 0 if you wish to run it immediately)")
    readLine().trim.toLong
  }
  private def inputTaskId: String = {
    println("Please enter the id of your task")
    readLine().trim
  }

  private def inputInterval: Long = {
    println("Enter the interval for the repetition of the print?")
    readLine().trim.toLong
  }
  def taskInputOccurringOnce(): Unit = {
    println("=================ENTER TASK DETAILS=============================")
    println("Please enter the data as per the instructions:")
    val taskDesc = inputTaskDesc
    val logInfo = inputLogInfo
    val delay = inputDelay
    println("=================PROCESSING YOUR REQUEST=========================")
    val newTask: Task = TaskManager.addOnceOccurringTask(taskDesc = taskDesc, delay = delay, logInfo = logInfo)
    println(s"Your task registered. Remember the Task ID: ${newTask.taskId} for future reference.")
  }

  def taskInputRecurring(): Unit = {
    println("=================ENTER TASK DETAILS=============================")
    println("Please enter the data as per the instructions:")
    val taskDesc = inputTaskDesc
    val logInfo = inputLogInfo
    val interval = inputInterval
    println("=================PROCESSING YOUR REQUEST=========================")
    val newTask: Task = TaskManager.addRecurringTask(taskDesc = taskDesc, interval = interval, logInfo = logInfo)
    println(s"Your task registered. Remember the Task ID: ${newTask.taskId} for future reference.")
  }

  def cancelTask(): Unit = {
    println("=================ENTER TASK DETAILS=============================")
    println("Please enter the data as per the instructions:")
    val taskId = inputTaskId
    println("=================PROCESSING YOUR REQUEST=========================")
    TaskManager.cancelTask(taskId)
  }

  def cancelAllTasks(): Unit = {
    println("=================PROCESSING YOUR REQUEST=========================")
    TaskManager.cancelAllTasks()
  }
  
  def shutDownScheduler(): Unit = TaskManager.shutDownScheduler()
}
