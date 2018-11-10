//1
import org.apache.spark.sql.SparkSession
//2
import org.apache.log4j._
Logger.getLogger("org").setLevel(Level.ERROR)
//3
val spark = SparkSession.builder().getOrCreate()
//4
import org.apache.spark.ml.clustering.KMeans
//5
val data = spark.read.option("header","true").option("inferSchema", "true").format("csv").load("Wholesale customers data.csv")

//6
data.printSchema()
val feature_data = data.drop("Channel","Region")
//7
import org.apache.spark.ml.feature.{VectorAssembler}
import org.apache.spark.ml.linalg.Vectors

//8
val assembler = (new VectorAssembler().setInputCols(Array("Fresh", "Milk","Grocery", "Frozen", "Detergents_Paper", "Delicassen"))
.setOutputCol("features"))
//9
val results = assembler.transform(feature_data)

//10
val kmeans = new KMeans().setK(3).setSeed(1L)
val model = kmeans.fit(results)


//11
val WSSE = model.computeCost(dataset)
println(s"Within set sum of Squared Errors = $WSSE")

//12
println("Cluster Centers: ")
model.clusterCenters.foreach(println)
