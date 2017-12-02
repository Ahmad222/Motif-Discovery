######################################################################         power        ########################
graphF="./netscience.txt"

#numPartitions="64"
#time1="3000000000000"


#graphF="./power.txt"
auxF="./auxFiles/undir9.gt"
numPartitions="5"
time1="1"
number=2
while [ $number -lt 32 ]; do
    numPartitions=$((number))
 /home/ahmadnaser/spark2/spark-1.6.0/bin/spark-submit --class "org.apache.spark.examples.gtrie.Gtries"  --master local[$number] --total-executor-cores $number target/gtries-project-1.0.jar u 9 $graphF $numPartitions $time1 $auxF >  NNET9-$number.txt



    echo "Number = $number"
    number=$((number * 2))
done


graphF="./wikivote.txt"

#numPartitions="64"
#time1="3000000000000"


#graphF="./power.txt"
auxF="./auxFiles/undir4.gt"
numPartitions="64"
time1="5"
number=1
while [ $number -lt 128 ]; do
    numPartitions=$((number))
# /home/ahmadnaser/spark2/spark-1.6.0/bin/spark-submit --class "org.apache.spark.examples.gtrie.Gtries"  --master local[$number] --total-executor-cores $number target/gtries-project-1.0.jar u 4 $graphF $numPartitions $time1 $auxF >  FinWiki4Z-$number.txt



    echo "Number = $number"
    number=$((number * 2))
done




graphF="./neural.txt"
auxF="./auxFiles/dir5.gt"
numPartitions="64"
time1="33"
number=2
while [ $number -lt 128 ]; do
    numPartitions=$((number))
# /home/ahmadnaser/spark2/spark-1.6.0/bin/spark-submit --class "org.apache.spark.examples.gtrie.Gtries"  --master local[$number] --total-executor-cores $number target/gtries-project-1.0.jar d 5 $graphF $numPartitions $time1 $auxF >  FinDNeu5-$number.txt



    echo "Number = $number"
    number=$((number * 2))
done
