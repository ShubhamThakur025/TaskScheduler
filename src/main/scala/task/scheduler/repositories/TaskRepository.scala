package task.scheduler.repositories

import task.scheduler.models.Task

import java.util.UUID
import java.util.concurrent.ScheduledFuture
import scala.collection.mutable

object TaskRepository {
  private val taskMap: mutable.Map[String, ScheduledFuture[_]] = mutable.Map.empty
  private def refreshTaskMap: Map[String, ScheduledFuture[_]] = taskMap.toMap
  def addTaskToRepository(task: Task, scheduledFuture: ScheduledFuture[_]): Map[String, ScheduledFuture[_]] = {
    taskMap += (task.taskId -> scheduledFuture)
    refreshTaskMap
  }
  def removeTaskFromRepository(taskId: String, scheduledFuture: ScheduledFuture[_]): Map[String, ScheduledFuture[_]] = {
    taskMap -= taskId
    refreshTaskMap
  }
  def getScheduledFuture(taskId: String): Option[ScheduledFuture[_]] = taskMap.get(taskId)
  def getAllScheduledFutures: Map[String, ScheduledFuture[?]] = refreshTaskMap
}
