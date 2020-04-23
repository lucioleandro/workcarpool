package br.com.workcarpool.repository;


import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.codecs.Codec;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Indexes;
import com.mongodb.client.model.geojson.Point;
import com.mongodb.client.model.geojson.Position;

import br.com.workcarpool.codec.WorkerCodec;
import br.com.workcarpool.model.Worker;

@Repository
public class WorkerRepository {

	private MongoClient client;
	private MongoDatabase dataBase;

	public void save(Worker work) {
		getConnection();
		MongoCollection<Worker> collection = dataBase.getCollection("workers", Worker.class);
		collection.insertOne(work);
		client.close();
	}
	
	public List<Worker> findAll() {
		getConnection();
		MongoCollection<Worker> collection = dataBase.getCollection("workers", Worker.class);
		MongoCursor<Worker> workerInterator = collection.find().iterator();
		client.close();
		return getResultSet(workerInterator);
	}


	public List<Worker> findByName(String firstName) {
		getConnection();
		MongoCollection<Worker> collection = dataBase.getCollection("workers", Worker.class);
		MongoCursor<Worker> workersIterator = collection.find(Filters.eq("firstName", firstName), Worker.class).iterator();
		client.close();
		return getResultSet(workersIterator);
	}
	
	public Worker findById(String workId) {
		getConnection();
		MongoCollection<Worker> collection = dataBase.getCollection("workers", Worker.class);
		Worker worker = collection.find(Filters.eq("_id", new ObjectId(workId)), Worker.class).first();
		client.close();
		return worker;
	}

	public List<Worker> searchByGeoLocation(Worker worker) {
		getConnection();
		MongoCollection<Worker> collection = dataBase.getCollection("workers", Worker.class);
		collection.createIndex(Indexes.geo2dsphere("homeAddress"));
		List<Double> coordinates = worker.getHomeAddress().getCoordinates();
		Point point = new Point(new Position(coordinates));
		
		MongoCursor<Worker> resultSet = collection.find(Filters.nearSphere("homeAddress", point, 2000.0, 0.0)).limit(2).skip(1).iterator();
		client.close();
		return getResultSet(resultSet);
	}
	
	private List<Worker> getResultSet(MongoCursor<Worker> workerInterator) {
		List<Worker> workersResult = new ArrayList<>();
		
		while(workerInterator.hasNext()) {
			workersResult.add(workerInterator.next());
		}
		return workersResult;
	}
	
	private void getConnection() {
		Codec<Document> codec = MongoClient.getDefaultCodecRegistry().get(Document.class);
		WorkerCodec workerCodec = new WorkerCodec(codec);
		CodecRegistry register = CodecRegistries.fromRegistries(
			      MongoClient.getDefaultCodecRegistry(), 
			      CodecRegistries.fromCodecs(workerCodec));
		MongoClientOptions options = MongoClientOptions.builder().codecRegistry(register).build();
		
		client = new MongoClient("localhost:27017", options);
		dataBase = client.getDatabase("test");
	}


}
