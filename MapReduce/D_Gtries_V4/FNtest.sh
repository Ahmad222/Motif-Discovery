######################################################################         power        ########################
graphF="./days.txt"
auxF="./auxFiles/undir5.gt"
numPartitions="64"
time1="5"

number=64
while [ $number -lt 128 ]; do
    numPartitions=$((number))
 /home/ahmadnaser/spark2/spark-1.6.0/bin/spark-submit --class "org.apache.spark.examples.gtrie.Gtries"  --master local[$number] --total-executor-cores $number target/\
gtries-project-1.0.jar u 5 $graphF $numPartitions $time1 $auxF >  NNDays5-$number.txt



    echo "Number = $number"
    number=$((number * 2))
done






graphF="./po.txt"
auxF="./auxFiles/undir5.gt"
numPartitions="64"
time1="22"

number=64
while [ $number -lt 128 ]; do
    numPartitions=$((number))
 /home/ahmadnaser/spark2/spark-1.6.0/bin/spark-submit --class "org.apache.spark.examples.gtrie.Gtries"  --master local[$number] --total-executor-cores $number target/gtries-project-1.0.jar u 5 $graphF $numPartitions $time1 $auxF >  NNPOL5-$number.txt



    echo "Number = $number"
    number=$((number * 2))
done


graphF="./netscience.txt"
auxF="./auxFiles/dir6.gt"
numPartitions="64"
time1="1"

number=55
while [ $number -lt 128 ]; do
    numPartitions=$((number))
# /home/ahmadnaser/spark2/spark-1.6.0/bin/spark-submit --class "org.apache.spark.examples.gtrie.Gtries"  --master local[$number] --total-executor-cores $number target/gtries-project-1.0.jar d 6 $graphF $numPartitions $time1 $auxF >  TRRNET6D-$number.txt



    echo "Number = $number"
    number=$((number * 2))
done


graphF="./dolphins.txt"
auxF="./auxFiles/dir6.gt"
numPartitions="64"
time1="1"

number=55
while [ $number -lt 128 ]; do
    numPartitions=$((number))
# /home/ahmadnaser/spark2/spark-1.6.0/bin/spark-submit --class "org.apache.spark.examples.gtrie.Gtries"  --master local[$number] --total-executor-cores $number target/g\
\
\
#tries-project-1.0.jar d 6 $graphF $numPartitions $time1 $auxF >  TRRDOL6D-$number.txt



    echo "Number = $number"
    number=$((number * 2))
done

graphF="./foldoc.txt"
auxF="./auxFiles/undir5.gt"
numPartitions="64"
time1="3"

number=64
while [ $number -lt 128 ]; do
    numPartitions=$((number))
 /home/ahmadnaser/spark2/spark-1.6.0/bin/spark-submit --class "org.apache.spark.examples.gtrie.Gtries"  --master local[$number] --total-executor-cores $number target/gtries-project-1.0.jar u 5 $graphF $numPartitions $time1 $auxF >  NNFOLD5-$number.txt



    echo "Number = $number"
    number=$((number * 2))
done
