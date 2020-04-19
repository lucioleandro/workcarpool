package br.com.workcarpool.codec;

import java.util.Objects;

import org.bson.BsonReader;
import org.bson.BsonString;
import org.bson.BsonValue;
import org.bson.BsonWriter;
import org.bson.Document;
import org.bson.codecs.Codec;
import org.bson.codecs.CollectibleCodec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;

import br.com.workcarpool.model.HomeAddress;
import br.com.workcarpool.model.Worker;

public class WorkerCodec implements CollectibleCodec<Worker> {
	
	private Codec<Document> codec;
	
	public WorkerCodec(Codec<Document> codec) {
		this.codec = codec;
	}

	@Override
	public void encode(BsonWriter writer, Worker worker, EncoderContext encoder) {
		Document document = new Document();
		document.put("_id", worker.getId());
		document.put("firstName", worker.getFirstName());
		document.put("lastName", worker.getLastName());
		document.put("register", worker.getRegister());
		document.put("homeAddress", new Document()
				.append("address", worker.getHomeAddress().getAddress())
				.append("coordinates", worker.getHomeAddress().getCoordinates())
				.append("type", worker.getHomeAddress().getType()));
		
		codec.encode(writer, document, encoder);
	}

	@Override
	public Class<Worker> getEncoderClass() {
		return Worker.class;
	}

	@Override
	public Worker decode(BsonReader reader, DecoderContext decoder) {
		Document document = codec.decode(reader, decoder);
		return  new Worker(document.getObjectId("_id"),
				   document.getString("firstName"),
				   document.getString("lastName"),
				   document.getString("register"),
				   (HomeAddress) document.get("homeAddress")
				);
	}

	@Override
	public Worker generateIdIfAbsentFromDocument(Worker worker) {
		return documentHasId(worker) ? worker : worker.generateId();
	}

	@Override
	public boolean documentHasId(Worker worker) {
		return !Objects.isNull(worker.getId());
	}

	@Override
	public BsonValue getDocumentId(Worker worker) {
		if(!documentHasId(worker)) {
			throw new IllegalStateException("This worker don't has id");
		}
		return new BsonString(worker.getId().toHexString());
	}

}
