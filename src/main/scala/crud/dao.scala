package crud

import org.mongodb.scala.model.Filters
import org.mongodb.scala.result.UpdateResult
import org.mongodb.scala._



  object dao extends App {
    val client: MongoClient = MongoClient("mongodb://localhost")
    val database: MongoDatabase = client.getDatabase("mydb")
    val collection: MongoCollection[Document] = database.getCollection("mycoll")

    // insert a document
    val doc1: Document = Document("_id" -> 1 , "favourite number" -> 333)
    val doc2: Document = Document("_id" -> 3, "favourite number" -> 700)

    val insertion: Observable[Completed] = collection.insertOne(doc2)


      insertion.subscribe(new Observer[Completed] {
        override def onNext(result: Completed): Unit = println(s"onNext: $result")

        override def onError(e: Throwable): Unit = println(s"onError: $e")

        override def onComplete(): Unit = println("onComplete")
      })
      //---------------

      val replacementDoc: Document = Document("_id" -> 1, "favourite number" -> 420)

      // replace a document
      collection.replaceOne(Filters.eq("_id", 1), replacementDoc).subscribe((updateResult: UpdateResult) => println("updating " + updateResult))

      //---------------------------------------------

      // find documents
      collection.find().collect().subscribe((results: Seq[Document]) => println(s"Found: #${results.size} and ${results}"))


//    collection.updateOne(equal("_id", 2), set("_id", 20)).printHeadResult("Update Result: ")

    Thread.sleep(3000)

}



