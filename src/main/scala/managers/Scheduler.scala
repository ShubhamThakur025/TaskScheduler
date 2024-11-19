package managers

import models.Task
import java.util.concurrent.{Executors, ScheduledExecutorService, ScheduledFuture}

object Scheduler {
  private val scheduledExecutor: ScheduledExecutorService = Executors.newScheduledThreadPool(4)

  def scheduleTaskOnce(task: Task): ScheduledFuture[_] = {
    val runnable: Runnable = () => task.command()
    task.delay match
      case Some(delay) =>
        scheduledExecutor.schedule(runnable, delay, java.util.concurrent.TimeUnit.MILLISECONDS)
      case None =>
        println(s"ERROR: Delay missing for task with Task ID: ${task.taskId}: ${task.task}. Running with a delay of 0 ms.")
        scheduledExecutor.schedule(runnable, 0, java.util.concurrent.TimeUnit.MILLISECONDS)
  }

  def scheduleTaskRecurring(task: Task): ScheduledFuture[_] = {
    val runnable: Runnable = () => task.command()
    task.interval match
      case Some(interval) =>
        scheduledExecutor.scheduleAtFixedRate(runnable, 0, interval, java.util.concurrent.TimeUnit.MILLISECONDS)
      case None =>
        println(s"ERROR: Interval missing for task with Task ID: ${task.taskId}: ${task.task}. Running only once.")
        scheduledExecutor.schedule(runnable, 0, java.util.concurrent.TimeUnit.MILLISECONDS)
  }

  def shutDown(): Unit = {
    scheduledExecutor.shutdown()
    println("Shutting down...")
  }
  
}