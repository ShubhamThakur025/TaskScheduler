package task.scheduler.models

import java.util.UUID

sealed trait taskStatus
case object Completed extends taskStatus
case object Running extends taskStatus
case object Waiting extends taskStatus

class Task (
           val taskId: String = UUID.randomUUID().toString,
           val task: String,
           val command: () => Unit,
           val taskStatus: taskStatus = Waiting,
           val delay: Option[Long] = None,
           val interval: Option[Long] = None
          )