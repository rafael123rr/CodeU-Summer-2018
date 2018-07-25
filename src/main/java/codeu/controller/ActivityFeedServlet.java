package codeu.controller;

import codeu.model.data.Conversation;
import codeu.model.data.User;
import codeu.model.data.Activity;
import codeu.model.store.basic.ConversationStore;
import codeu.model.store.basic.UserStore;
import codeu.model.store.basic.ActivityStore;
import java.io.IOException;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ActivityFeedServlet extends HttpServlet {

  /** Store class that gives access to Users. */
  private UserStore userStore;

  /** Store class that gives access to Conversations. */
  private ConversationStore conversationStore;

  /** Store class that gives acces to Activities. */
  private ActivityStore activityStore;

  /** Sets up state for handling login-related requests.*/
  public void init() throws ServletException {
    super.init();
    setUserStore(UserStore.getInstance());
    setConversationStore(ConversationStore.getInstance());
    setActivityStore(ActivityStore.getInstance());
  }

  public void setUserStore(UserStore userStore) {
    this.userStore = userStore;
  }

  /**
   * Sets the ConversationStore used by this servlet. This function provides a common setup method
   * for use by the test framework or the servlet's init() function.
   */
  public void setConversationStore(ConversationStore conversationStore) {
    this.conversationStore = conversationStore;
  }

  /**
   * Sets the ActivityStore used by this servlet. This function provides a common setup method
   * for use by the test framework or the servlet's init() function.
   */
  public void setActivityStore(ActivityStore activityStore) {
    this.activityStore = activityStore;
  }

  /**
   * This function fires when a user navigates to the activity page. It gets all of the
   * activity from the model and forwards to activity.jsp for rendering the list.
   */
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException {
        //List<User> users= userStore.getUsers();
        //request.setAttribute("users", users);
        //request.getRequestDispatcher("/WEB-INF/view/activity.jsp").forward(request, response);
        List<Conversation> conversations = conversationStore.getAllConversations();
        request.setAttribute("conversations", conversations);
        request.getRequestDispatcher("/WEB-INF/view/activity.jsp").forward(request, response);
        // List<Activity> activities = activityStore.getActivities();
        // request.setAttribute("activities", activities);
        //request.getRequestDispatcher("/WEB-INF/view/activity").forward(request, response);
  }

  /**
   * might not need a doPost method
   */
  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException {
        String username = (String) request.getSession().getAttribute("user");
        if (username == null) {
          response.sendRedirect("/login");
          return;
        }
  }
}
