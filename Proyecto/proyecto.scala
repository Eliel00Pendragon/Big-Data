import org.apache.spark.ml.classification.LogisticRegression
import org.apache.spark.sql.SparkSession
import org.apache.log4j._
import org.apache.spark.ml.feature.{VectorAssembler, StringIndexer, VectorIndexer, OneHotEncoder}
import org.apache.spark.ml.linalg.Vectors

Logger.getLogger("org").setLevel(Level.ERROR)

val spark = SparkSession.builder().getOrCreate()
val data  = spark.read.option("header","true").option("inferSchema", "true").format("csv").load("Iris.csv")

data.printSchema()

data.head(1)

val colnames = data.columns
val firstrow = data.head(1)(0)

println("\n")
println("Example data row")
for(ind <- Range(1, colnames.length)){
    println(colnames(ind))
    println(firstrow(ind))
    println("\n")
}

//creación de etiquetas
val df = spark.read.option("inferSchema","true").csv("Iris.csv").toDF("SepalLength", "SepalWidth", "PetalLength", "PetalWidth","class")
//limpieza
val newcol = when($"class".contains("Iris-setosa"), 1.0).otherwise(when($"class".contains("Iris-virginica"), 3.0).otherwise(2.0))
val newdf = df.withColumn("etiqueta", newcol)
newdf.select("etiqueta","SepalLength", "SepalWidth", "PetalLength", "PetalWidth","class").show(150, false)

//Junta los datos
val assembler = new VectorAssembler()  .setInputCols(Array("SepalLength", "SepalWidth", "PetalLength", "PetalWidth","etiqueta")).setOutputCol("features")
//Transformar datos
val features = assembler.transform(newdf)
features.show(5)

/////////////////////////////////////////K-means
import org.apache.spark.ml.clustering.KMeans

//trains the k-means model
val kmeans = new KMeans().setK(10).setSeed(1L)
val model = kmeans.fit(features)

// Evaluate clustering by calculate Within Set Sum of Squared Errors.
val WSSE = model.computeCost(features)
println(s"Within set sum of Squared Errors = $WSSE")

// Show results
println("Cluster Centers: ")
model.clusterCenters.foreach(println)


////////////////////////////////////////////////////////////////////Bisegtingg k-means
import org.apache.spark.ml.clustering.BisectingKMeans

// Trains a bisecting k-means model.
val bkm = new BisectingKMeans().setK(10).setSeed(1)
val model2 = bkm.fit(features)

// Evaluate clustering.
val cost = model2.computeCost(features)
println(s"Within Set Sum of Squared Errors = $cost")

// Shows the result.
println("Cluster Centers: ")
val centers = model.clusterCenters
centers.foreach(println)
