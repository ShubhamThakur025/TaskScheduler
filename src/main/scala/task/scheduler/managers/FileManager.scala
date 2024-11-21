package task.scheduler.managers

import java.io.{BufferedWriter, File, PrintWriter}
import java.util.Date
import scala.io.Source
import scala.util.{Failure, Success, Using}

class FileManager {
  private val filePath: String = Option(System.getenv("FILE_PATH")).getOrElse(throw new IllegalStateException("Environment variable 'FILE_PATH' is not set"))

  def logToFile(logInfo: String): Unit = {
    val lines = readLinesFromFile()
    val updatedLines = lines :+ logInfo
    val result = Using(new BufferedWriter(new PrintWriter(new File(filePath)))){writer =>
      for(line <- updatedLines){
        writer.write(line + '\n')
      }
    }
    result match
      case Success(_) =>
      case Failure(exception) => println(s"[${new Date(System.currentTimeMillis())}] ERROR: Failed to write data to file. An Exception occurred: $exception")

  }
  private def readLinesFromFile(): List[String] = {
    val result = Using(Source.fromFile(filePath)) {
      source => source.getLines().toList
    }
    result match
      case Success(lines) => lines
      case Failure(exception) =>
        println(s"[${new Date(System.currentTimeMillis())}] ERROR: Failed to fetch data from file. An Exception occurred: $exception")
        List.empty
  }
}
