package br.com.workcarpool.repository;


import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.codecs.Codec;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.springframework.stereotype.Repository;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

import br.com.workcarpool.codec.WorkerCodec;
import br.com.workcarpool.model.Worker;

@Repository
public class WokerRepository {

	private MongoClient client;
	private MongoDatabase dataBase;

	public void save(Worker work) {
		getConnection();
		MongoCollection<Worker> workers = dataBase.getCollection("workers", Worker.class);
		workers.insertOne(work);
		client.close();
	}
	
	public List<Worker> findAll() {
		getConnection();
		MongoCollection<Worker> workers = dataBase.getCollection("workers", Worker.class);
		MongoCursor<Worker> workerInterator = workers.find().iterator();
		client.close();
		return getResultSet(workerInterator);
	}


	public List<Worker> findByName(String firstName) {
		getConnection();
		MongoCollection<Worker> workers = dataBase.getCollection("workers", Worker.class);
		MongoCursor<Worker> workersIterator = workers.find(Filters.eq("firstName", firstName), Worker.class).iterator();
		client.close();
		return getResultSet(workersIterator);
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
