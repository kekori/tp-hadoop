#Projet Hadoop Tao Mellac

Comment lancer les trois jobs Hadoop pour le tp

## 1 Construire l'image Docker
On va dans deploy pour accéder au dockerfile

	cd deploy/
	docker buildx build -t hadoop-tp1 .
	cd ../


##2 On le lance

	docker run --rm  -p 8088:8088  -p 9870:9870  -p 9864:9864 -v C:\Users\taome\Desktop\Taff\MIN\obd\rendu\hadoop-tp3-main\data  -v C:\Users\taome\Desktop\Taff\MIN\obd\rendu\hadoop-tp3-main\jars  --name hadoop-container  hadoop-tp1


##3 on l'execute

	docker exec -it container /bin/bash


##4 On clean et compile le projet maven

   	mvn clean package


##5 On crée le répertoire hdfs

 	hadoop fs -mkdir -p /data/relationships


##6 On copie les données de data vers le répertoire

	hadoop fs -put /tmp/data/relationships/data.txt /data/relationships/


##7 On exécute les jobs
Pour le job 1 :


	hadoop jar /tmp/jars/tpfinal-tao_mellac_job1.jar /data/relationships/data.txt /data/output/job1

On peut vérifier les résultats :

	hadoop fs -cat /data/output/job1/part-r-00000


Pour le job 2 :
	Même commande, mais cette fois ci en entrée on met la sortie du job1

	hadoop jar /tmp/jars/tpfinal-tao_mellac_job2.jar /output/job1/part-r-00000 /data/output/job2


On peut vérifier les résultats

	hadoop fs -cat /data/output/job2/part-r-00000
