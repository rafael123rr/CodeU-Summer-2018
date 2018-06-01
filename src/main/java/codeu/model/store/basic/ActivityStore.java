package codeu.model.store.basic;

import codeu.model.data.Conversation;
import codeu.model.store.persistence.PersistentStorageAgent;
import java.util.ArrayList;
import java.util.List;

public class ActivityStore {

  /* indexFrom and maxElements will help determine which activities to return*/
  private int indexFrom;
  private int maxElements;
  private static ActivityStore instance;

  // public static ActivityStore getInstance() {
  //   if (instance == null) {
  //     instance = new ActivityStore(PersistentStorageAgent.getInstance());
  //   }
  //   return instance;
  // }
}
