package codeu.model.data;

import java.time.Instant;
import java.util.UUID;

public class Activity {
  public final Type type;
  public final UUID objectId;
  public final Instant creationTime;

  public enum Type {
    USER_JOINED,
    CONVERSATION_CREATED,
    MESSAGE_SENT;
  }
  public Activity(Type type, UUID objectId, Instant creationTime){
    this.type = type;
    this.objectId = objectId;
    this.creationTime = creationTime;
  }
}
