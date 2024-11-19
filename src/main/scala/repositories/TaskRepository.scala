package repositories

import models.Task

import java.util.UUID
import java.util.concurrent.ScheduledFuture
import scala.collection.mutable

object TaskRepository {
  private val taskMap: mutable.Map[UUID, ScheduledFuture[_]] = mutable.Map.empty
  private def refreshTaskMap: Map[UUID, ScheduledFuture[_]] = taskMap.toMap
  def addTaskToRepository(task: Task, scheduledFuture: ScheduledFuture[_]): Map[UUID, ScheduledFuture[_]] = {
    taskMap += (task.taskId -> scheduledFuture)
    refreshTaskMap
  }
  def removeTaskFromRepository(taskId: UUID, scheduledFuture: ScheduledFuture[_]): Map[UUID, ScheduledFuture[_]] = {
    taskMap -= taskId
    refreshTaskMap
  }
  def getScheduledFuture(taskId: UUID): Option[ScheduledFuture[_]] = taskMap.get(taskId)
}
