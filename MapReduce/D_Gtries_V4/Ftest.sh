graphF="./gnutella.txt"

numPartitions="64"
time1="3000000000000"


number=1
# /home/ahmadnaser/spark2/spark-1.6.0/bin/spark-submit --packages net.sf.trove4j:trove4j:3.0.3 --class "org.apache.spark.examples.esu2.esu"  --master local[$number]\
# --total-executor-cores $number target/esu-project-1.0.jar u 6 $graphF 2 $time1  >  Gnu6-$number.txt

number=16

while [ $number -lt 128 ]; do
    numPartitions=$((number ))
#    /home/ahmadnaser/spark2/spark-1.6.0/bin/spark-submit --packages net.sf.trove4j:trove4j:3.0.3 --class "org.apache.spark.examples.esu2.esu"  --master local[$number] \--total-executor-cores $number target/esu-project-1.0.jar u 6 $graphF $numPartitions $time1  >  Gnu6-$number.txt



    echo "Number = $number"
    number=$((number * 2))
done



######################################################################         power        ########################
graphF="./po.txt"

numPartitions="64"
time1="3000000000000"


number=1
# /home/ahmadnaser/spark2/spark-1.6.0/bin/spark-submit --packages net.sf.trove4j:trove4j:3.0.3 --class "org.apache.spark.examples.esu2.esu"  --master local[$number]\
# --total-executor-cores $number target/esu-project-1.0.jar u 5 $graphF 2 $time1  >  Pol5-$number.txt

number=2

while [ $number -lt 16 ]; do
    numPartitions=$((number ))
#    /home/ahmadnaser/spark2/spark-1.6.0/bin/spark-submit --packages net.sf.trove4j:trove4j:3.0.3 --class "org.apache.spark.examples.esu2.esu"  --master local[$number] --total-executor-cores $number target/esu-project-1.0.jar u 5 $graphF $numPartitions $time1  >  Pol5-$number.txt



    echo "Number = $number"
    number=$((number * 2))
done


#####################################################################         polblog        ########################


graphF="./social.txt"

numPartitions="64"
time1="3000000000000"


number=1

#     /home/ahmadnaser/spark2/spark-1.6.0/bin/spark-submit --packages net.sf.trove4j:trove4j:3.0.3 --class "org.apache.spark.examples.esu2.esu"  --master local[$number] --total-executor-cores $number target/esu-project-1.0.jar u 6 $graphF 2 $time1  >  S6-$number.txt


number=2

while [ $number -lt 128 ]; do
    numPartitions=$((number))
 #    /home/ahmadnaser/spark2/spark-1.6.0/bin/spark-submit --packages net.sf.trove4j:trove4j:3.0.3 --class "org.apache.spark.examples.esu2.esu"  --master local[$number] --total-executor-cores $number target/esu-project-1.0.jar u 6 $graphF $numPartitions $time1  >  Soc6-$number.txt



    echo "Number = $number"
    number=$((number * 2))
done




#####################################################################         power        ########################


graphF="./power.txt"

numPartitions="64"
time1="3000000000000"


number=1

     /home/ahmadnaser/spark2/spark-1.6.0/bin/spark-submit --packages net.sf.trove4j:trove4j:3.0.3 --class "org.apache.spark.examples.esu2.esu"  --master local[$number\
] --total-executor-cores $number target/esu-project-1.0.jar u 7 $graphF 2 $time1  >  PW7-$number.txt


number=2

while [ $number -lt 128 ]; do
    numPartitions=$((number))
    /home/ahmadnaser/spark2/spark-1.6.0/bin/spark-submit --packages net.sf.trove4j:trove4j:3.0.3 --class "org.apache.spark.examples.esu2.esu"  --master local[$number\
] --total-executor-cores $number target/esu-project-1.0.jar u 7 $graphF $numPartitions $time1  >  PW7-$number.txt



    echo "Number = $number"
    number=$((number * 2))
done



