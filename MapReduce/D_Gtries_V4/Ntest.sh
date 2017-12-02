graphF="./foldoc.txt"
auxF="./auxFiles/undir5.gt"
numPartitions="64"
time1="3000000000000"

number=1

# /home/ahmadnaser/spark2/spark-1.6.0/bin/spark-submit --class "org.apache.spark.examples.gtrie.Gtries"  --master local[$number] --total-executor-cores $number target/g\
#tries-project-1.0.jar u 5 $graphF 2 $time1 $auxF >  AFol5-$number.txt


number=2
while [ $number -lt 128 ]; do
    numPartitions=$((number))
# /home/ahmadnaser/spark2/spark-1.6.0/bin/spark-submit --class "org.apache.spark.examples.gtrie.Gtries"  --master local[$number] --total-executor-cores $number target/g\

#tries-project-1.0.jar u 5 $graphF $numPartitions $time1 $auxF >   AFol5-$number.txt



    echo "Number = $number"
    number=$((number * 2))
done



######################################################################         power        ########################
graphF="./power.txt"
auxF="./auxFiles/undir7.gt"
numPartitions="64"
time1="3000000000000"

number=1

# /home/ahmadnaser/spark2/spark-1.6.0/bin/spark-submit --class "org.apache.spark.examples.gtrie.Gtries"  --master local[$number] --total-executor-cores $number target/gtries-project-1.0.jar u 7 $graphF 2 $time1 $auxF >  Apow7-$number.txt


number=2
while [ $number -lt 128 ]; do
    numPartitions=$((number))
# /home/ahmadnaser/spark2/spark-1.6.0/bin/spark-submit --class "org.apache.spark.examples.gtrie.Gtries"  --master local[$number] --total-executor-cores $number target/g\
#tries-project-1.0.jar u 7 $graphF $numPartitions $time1 $auxF >  Apow7-$number.txt



    echo "Number = $number"
    number=$((number * 2))
done



#################################################        SOCIAL
graphF="./social.txt"
numPartitions="64"
time1="800000000000000"
auxF="./auxFiles/undir6.gt"

number=1


#     /home/ahmadnaser/spark2/spark-1.6.0/bin/spark-submit --class "org.apache.spark.examples.gtrie.Gtries"  --master local[$number] --total-executor-cores $number target/gtries-project-1.0.jar u 6 $graphF 2 $time1 $auxF  >  Asoc6-$number.txt


number=2
while [ $number -lt 128 ]; do
    numPartitions=$((number))

 #    /home/ahmadnaser/spark2/spark-1.6.0/bin/spark-submit --class "org.apache.spark.examples.gtrie.Gtries"  --master local[$number] --total-executor-cores $number target/gtries-project-1.0.jar u 6 $graphF $numPartitions $time1 $auxF  >  Asoc6-$number.txt



    echo "Number = $number"
    number=$((number * 2))
done


#######################################Gnutella
graphF="./gnutella.txt"
numPartitions="64"
time1="500000000000000"
auxF="./auxFiles/undir5.gt"
number=1

 /home/ahmadnaser/spark2/spark-1.6.0/bin/spark-submit --class "org.apache.spark.examples.gtrie.Gtries"  --master local[$number] --total-executor-cores $number target/gtries-project-1.0.jar u 5 $graphF 2 $time1 $auxF >  Agnu5-$number.txt


number=2
while [ $number -lt 128 ]; do
    numPartitions=$((number))

     /home/ahmadnaser/spark2/spark-1.6.0/bin/spark-submit --class "org.apache.spark.examples.gtrie.Gtries"  --master local[$number] --total-executor-cores $number target/gtries-project-1.0.jar u 5 $graphF $numPartitions $time1 $auxF >  Agnu5-$number.txt



    echo "Number = $number"
    number=$((number * 2))
done


graphF="./gnutella.txt"
numPartitions="64"
time1="50000000000000000"
auxF="./auxFiles/undir6.gt"

#  /home/ahmadnaser/spark2/spark-1.6.0/bin/spark-submit --class "org.apache.spark.examples.gtrie.Gtries"  --master local[$number] --total-executor-cores $number target/gtries-project-1.0.jar u 6 $graphF 2 $time1 $auxF  >  NN4gnu6-1.txt


number=2
while [ $number -lt 128 ]; do
    numPartitions=$((number * 4))

#     /home/ahmadnaser/spark2/spark-1.6.0/bin/spark-submit --class "org.apache.spark.examples.gtrie.Gtries"  --master local[$number] --total-executor-cores $number target/gtries-project-1.0.jar u 6 $graphF $numPartitions $time1 $auxF  >  NN4gnu6-$number.txt



    echo "Number = $number"
    number=$((number * 2))
done
