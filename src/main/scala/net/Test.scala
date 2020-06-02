package net

import cats.effect._
import org.http4s._
import org.http4s.client.asynchttpclient.AsyncHttpClient
import org.http4s.client.blaze.BlazeClientBuilder

import scala.concurrent.ExecutionContext

object Demo extends IOApp {

  def run(args: List[String]): IO[ExitCode] = {
    import org.http4s.implicits._
    AsyncHttpClient
      BlazeClientBuilder.apply[IO](ExecutionContext.global)
      .resource
      .map(client.middleware.Logger(logHeaders = true, logBody = true, logAction = Some((msg: String) => IO(println("logged: " + msg)))))
      .use(client => client.fetchAs[Unit](Request[IO](uri = uri"https://blog.kubukoz.com/nope")))
    }.as(ExitCode.Success)
}