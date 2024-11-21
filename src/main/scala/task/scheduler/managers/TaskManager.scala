package task.scheduler.managers

import task.scheduler.models.Task
import task.scheduler.repositories.TaskRepository

import java.util.Date
import java.util.concurrent.ScheduledFuture
import scala.util.{Failure, Success, Try}

object TaskManager {
  private val fm: FileManager = new FileManager()
  def addOnceOccurringTask(taskDesc: String, delay: Long, logInfo: String): Task = {
    val command = () => fm.logToFile(s"[${new Date(System.currentTimeMillis())}] RUNNING TASK: $taskDesc\n$logInfo")
    val task: Task = new Task(task = taskDesc, command = command, delay = Some(delay))
    val scheduledFuture = Scheduler.scheduleTaskOnce(task)
    TaskRepository.addTaskToRepository(task = task, scheduledFuture = scheduledFuture)
    task
  }

  def addRecurringTask(taskDesc: String, interval: Long, logInfo: String): Task = {
    val command = () => fm.logToFile(s"[${new Date(System.currentTimeMillis())}] RUNNING TASK: $taskDesc\n$logInfo")
    val task: Task = new Task(task = taskDesc, command = command,interval = Some(interval))
    val scheduledFuture = Scheduler.scheduleTaskRecurring(task)
    TaskRepository.addTaskToRepository(task = task, scheduledFuture = scheduledFuture)
    task
  }

  def cancelTask(taskId: String): Unit = {
    TaskRepository.getScheduledFuture(taskId) match
      case Some(scheduledFuture) =>
        Try {
          scheduledFuture.cancel(true)
        } match
          case Success(_) =>
            TaskRepository.removeTaskFromRepository(taskId = taskId, scheduledFuture = scheduledFuture)
            println("SUCCESS: Task canceled.")
          case Failure(exception) =>
            println(s"ERROR: Failed to cancel task $taskId: ${exception.getMessage}")
      case None => println("ERROR: Task not registered.")
  }

  def cancelAllTasks(): Unit = {
    TaskRepository.getAllScheduledFutures.foreach{
      case(taskId: String, scheduledFuture: ScheduledFuture[_]) =>
        Try {
          scheduledFuture.cancel(true)
        } match
          case Success(_) =>
            TaskRepository.removeTaskFromRepository(taskId = taskId, scheduledFuture = scheduledFuture)
          case Failure(exception) =>
            println(s"ERROR: Failed to cancel task $taskId: ${exception.getMessage}")
    }
    println("SUCCESS: All tasks canceled.")
  }
  
  def shutDownScheduler(): Unit = Scheduler.shutDown()
}
