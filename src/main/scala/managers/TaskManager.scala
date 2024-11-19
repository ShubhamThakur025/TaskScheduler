package managers

import models.Task
import repositories.TaskRepository

import java.util.UUID
import java.util.concurrent.ScheduledFuture

object TaskManager {
  def addOnceOccurringTask(taskDesc: String, delay: Long, logInfo: String): Unit = {
    val command = () => println(logInfo)
    val task: Task = new Task(task = taskDesc, command = command, delay = Some(delay))
    val scheduledFuture = Scheduler.scheduleTaskOnce(task)
    TaskRepository.addTaskToRepository(task = task, scheduledFuture = scheduledFuture)
  }
  def addRecurringTask(taskDesc: String, interval: Long, logInfo: String): Unit = {
    val command = () => println(logInfo)
    val task: Task = new Task(task = taskDesc, command = command,interval = Some(interval))
    val scheduledFuture = Scheduler.scheduleTaskRecurring(task)
    TaskRepository.addTaskToRepository(task = task, scheduledFuture = scheduledFuture)
  }
  def cancelTask(taskId: UUID): Unit = {
    TaskRepository.getScheduledFuture(taskId) match
      case Some(scheduledFuture) => {
        TaskRepository.removeTaskFromRepository(taskId = taskId, scheduledFuture = scheduledFuture)
        println("SUCCESS: Task canceled.")
      }
      case None => println("ERROR: Task not registered.")
  }
}
