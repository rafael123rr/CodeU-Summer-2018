package codeu.model.store.basic;

import codeu.model.data.Activity;
import codeu.model.data.Conversation;
import codeu.model.data.User;
import codeu.model.store.basic.ActivityStore;
import codeu.model.store.persistence.PersistentStorageAgent;
import java.util.List;
import java.util.UUID;
import java.util.ArrayList;

public class ActivityStore {

  /* indexFrom and maxElements will help determine the range of activities to return */
  // private static int indexFrom;
  // private static int maxElements;
  private static Activity activity;
  private static ActivityStore instance;

  public static ActivityStore getInstance() {
    if (instance == null) {
      instance = new ActivityStore(PersistentStorageAgent.getInstance());
    }
    return instance;
  }

  private PersistentStorageAgent persistentStorageAgent;

  private List<Activity> activities;

  /** This class is a singleton, so its constructor is private. Call getInstance() instead. */
  private ActivityStore(PersistentStorageAgent persistentStorageAgent) {
    this.persistentStorageAgent = persistentStorageAgent;
    activities = new ArrayList<>();
  }

  /** Access the current set of conversations known to the application. */
  public List<Activity> getActivities() {
    return activities;
  }

  /** Sets the List of Messages stored by this MessageStore. */
  public void setActivities(List<Activity> activities) {
    this.activities = activities;
  }
}
