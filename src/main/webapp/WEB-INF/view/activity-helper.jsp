<%@ page import="java.util.List" %>
<%@ page import="codeu.model.data.Conversation" %>
<%@ page import="codeu.model.data.Message" %>
<%@ page import="codeu.model.store.basic.UserStore" %>

<!--The following helper methods create the layout of a specific activity.-->
<%
  /*
   * Returns a statement in the activity feed if a user joins a conversation.
   */
   public String userJoined(Activity activity){
      if (Type.USER_JOINED){
        activity.getConversationId();
        activity = new Activity(USER_JOINED);
        return "User joined the chat room.";
      }
    }

    /*
     * Returns a statement in the activity feed if a user creates a conversation.
     */
    public String conversationCreated(Activity activity) {
      activity =
      String conversation = "";
      return conversation;
    }

    /*
     * Returns a statement in the activity feed if a message is sent in a conversation.
     */
    public String messageSent(Activity activity) {
      (if (activity.Type == MESSAGE_SENT)) {
        String messageSent = "User sent a message";
      return messageSent;
      }
    }

    /*
     * Returns the creation time of a conversation (?) in the activity feed.
     */
    public String formatCreationTime(Instant time) {
      List<Activities> activities = new ArrayList();

      Query query = new Query("");
      for (Activity activity : activities) {

      }
      String creationTime = "";
      return creationTime;
    }

    /*
     * Returns the username of a User from a User ID.
     */
    public String formatUserName(UUID userID) {
      String userName = "";
      return userName;
    }

    /*
     * Returns a conversation preview (?) from a Conversation ID.
     */
    public String formatConversation(UUID conversationId) {
      String conversation = "";
      return conversation;
    }

    /*
     * Returns a message preview from a Message ID.
     */
    public String formatMessage(UUID messageId) {
      String message = "";
      return message;
    }
  %>
