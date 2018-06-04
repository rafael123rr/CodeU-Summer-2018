package codeu.model.data;

import java.time.Instant;
import java.util.UUID;

public class Activity {

  public final Type type;
  public final UUID id;
  public final Instant creationTime;


  public enum Type {
    USER_JOINED,
    CONVERSATION_CREATED,
    MESSAGE_SENT;
  }

  public Activity(Type type, UUID id, Instant creationTime) {
    this.type = type;
    this.id = id;
    this.creationTime = creationTime;
  }

  public Type getType() {
    return type;
  }

  public UUID getId() {
    return id;
  }

  public Instant getCreationTime() {
    return creationTime;
  }
}
