package es.zed.repository;

import com.mongodb.MongoCommandException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.IndexOptions;
import com.mongodb.client.model.Indexes;
import es.zed.repository.model.EventId;
import jakarta.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

/**
 * Repository for managing EventId, used to register processed events and avoid duplicate execution.
 */
@Repository
public class EventIdRepository {

  /**
   * collection name.
   */
  @Value("${amqp.event.idStorage.collection}")
  private String collectionName;

  /**
   * ttl.
   */
  @Value("${amqp.event.idStorage.ttl}")
  private long ttl;

  /**
   * ttlUnit.
   */
  @Value("${amqp.event.idStorage.ttl.unit}")
  private String ttlUnit;

  /**
   * index name.
   */
  @Value("${amqp.event.idStorage.ttl.indexName}")
  private String indexName;

  /**
   * Mongo template.
   */
  private final MongoTemplate mongoTemplate;

  /**
   * Constructor.
   *
   * @param mongoTemplate Mongo template.
   */
  public EventIdRepository(MongoTemplate mongoTemplate) {
    this.mongoTemplate = mongoTemplate;
  }

  /**
   * Init mongodb.
   */
  @PostConstruct
  public void init() {
    MongoCollection<Document> collection = mongoTemplate.getCollection(collectionName);
    try {
      collection.createIndex(Indexes.ascending("createdDate"), new IndexOptions().expireAfter(ttl, TimeUnit.valueOf(ttlUnit)).name(indexName));
    } catch (MongoCommandException e) {
      if (e.getErrorMessage().contains("IndexOptionsConflict")) {
        collection.dropIndex(indexName);
        collection.createIndex(Indexes.ascending("createdDate"), new IndexOptions().expireAfter(ttl, TimeUnit.valueOf(ttlUnit)).name(indexName));
      } else {
        throw e;
      }
    }
  }

  /**
   * Save event.
   *
   * @param eventId event id.
   * @return event id.
   */
  public EventId save(EventId eventId) {
    Document document = new Document("_id", eventId.getId());
    document.append("_context", eventId.getContext());
    document.append("_eventType", eventId.getEventType());
    mongoTemplate.getCollection(collectionName).insertOne(document);
    return eventId;
  }

  /**
   * Delete all event.
   */
  public void deleteAll() {
    mongoTemplate.getCollection(collectionName).deleteMany(new Document());
  }

  /**
   * Delete an event.
   *
   * @param eventId event id.
   */
  public void deleteOne(EventId eventId) {
    mongoTemplate.getCollection(collectionName).deleteOne(new Document("_id", eventId.getId()));
  }
}