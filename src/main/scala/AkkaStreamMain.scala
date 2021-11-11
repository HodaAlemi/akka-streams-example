import akka.{Done, NotUsed}
import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.scaladsl._
import org.threeten.bp.LocalDate

import scala.concurrent.{ExecutionContextExecutor, Future}


object AkkaStreamMain extends App {

  implicit val system: ActorSystem = ActorSystem("Akka-stream-test")
  implicit val executionContext: ExecutionContextExecutor = system.dispatcher
  implicit val materializer: ActorMaterializer = ActorMaterializer() //evaluation context of the stream

  val transactions: Seq[Transaction] =  Seq.tabulate(100)(_ => Transaction.generateRandom)

  // Create the transaction Source and iterating over the transaction sequence
  val transactionsSource: Source[Transaction, NotUsed] = Source.fromIterator(() => transactions.iterator)

  //Apply the filter: only passing transactions after mid month through the Flow
  val transactionsFilterFlow = Flow[Transaction].filter((t) => t.date.isAfter(LocalDate.of(2019, 5, 15)))


  //Create a Source of transactions after mid month by combining the transactionsSource with the transactions filter Flow
  val filteredTransactionsSource: Source[Transaction, NotUsed] = transactionsSource.via(transactionsFilterFlow)

  //A Sink that will write its input onto the console
  val consoleSink: Sink[Transaction, Future[Done]] = Sink.foreach[Transaction](println)

  //Connect the Source with the Sink and run it using the Materializer
  filteredTransactionsSource.runWith(consoleSink)

}
