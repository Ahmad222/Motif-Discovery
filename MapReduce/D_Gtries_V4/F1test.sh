######################################################################         power        ########################
graphF="./gnutella.txt"
auxF="./auxFiles/dir6.gt"
numPartitions="64"
time1="22"

number=1
while [ $number -lt 128 ]; do
    numPartitions=$((number))
 /home/ahmadnaser/spark2/spark-1.6.0/bin/spark-submit --class "org.apache.spark.examples.gtrie.Gtries"  --master local[$number] --total-executor-cores $number target/\gtries-project-1.0.jar d 6 $graphF $numPartitions $time1 $auxF >  DirAGNU6-$number.txt



    echo "Number = $number"
    number=$((number * 2))
done






graphF="./po.txt"
auxF="./auxFiles/undir5.gt"
numPartitions="64"
time1="1"

number=644
while [ $number -lt 128 ]; do
    numPartitions=$((number))
 /home/ahmadnaser/spark2/spark-1.6.0/bin/spark-submit --class "org.apache.spark.examples.gtrie.Gtries"  --master local[$number] --total-executor-cores $number target/gtries-project-1.0.jar d 5 $graphF $numPartitions $time1 $auxF >  IterPOL5-$number.txt

    echo "Number = $number"
    number=$((number * 2))
done


graphF="./foldoc.txt"
auxF="./auxFiles/undir5.gt"
numPartitions="64"
time1="5"

number=64
while [ $number -lt 128 ]; do
    numPartitions=$((number))
# /home/ahmadnaser/spark2/spark-1.6.0/bin/spark-submit --class "org.apache.spark.examples.gtrie.Gtries"  --master local[$number] --total-executor-cores $number target/gtries-project-1.0.jar u 5 $graphF $numPartitions $time1 $auxF >  NNJFOLD5-$number.txt



    echo "Number = $number"
    number=$((number * 2))
done


graphF="./dolphins.txt"
auxF="./auxFiles/dir6.gt"
numPartitions="64"
time1="1"

number=1
while [ $number -lt 128 ]; do
    numPartitions=$((number))
# /home/ahmadnaser/spark2/spark-1.6.0/bin/spark-submit --class "org.apache.spark.examples.gtrie.Gtries"  --master local[$number] --total-executor-cores $number target/g\
\
\
#tries-project-1.0.jar d 6 $graphF $numPartitions $time1 $auxF >  TRRDOL6D-$number.txt



    echo "Number = $number"
    number=$((number * 2))
done

graphF="./gnutella.txt"
auxF="./auxFiles/undir6.gt"
numPartitions="64"
time1="1"

number=16
while [ $number -lt 128 ]; do
    numPartitions=$((number))
# /home/ahmadnaser/spark2/spark-1.6.0/bin/spark-submit --class "org.apache.spark.examples.gtrie.Gtries"  --master local[$number] --total-executor-cores $number target/g\
#tries-project-1.0.jar u 6 $graphF $numPartitions $time1 $auxF >  TRRGNU6-$number.txt



    echo "Number = $number"
    number=$((number * 2))
done


graphF="./metabolic.txt"
auxF="./auxFiles/undir3.gt"
numPartitions="1"
time1="55"

number=3
while [ $number -lt 2 ]; do
    #numPartitions=$((number))
# /home/ahmadnaser/spark2/spark-1.6.0/bin/spark-submit --class "org.apache.spark.examples.gtrie.Gtries"  --master local[$number] --total-executor-cores $number target/g\
#tries-project-1.0.jar u $number $graphF $numPartitions $time1 $auxF >  GRNEU-$number.txt



    echo "Number = $number"
    number=$((number * 2))
done
