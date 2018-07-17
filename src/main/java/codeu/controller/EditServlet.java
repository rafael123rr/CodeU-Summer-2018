// Copyright 2017 Google Inc.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//    http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package codeu.controller;

import codeu.model.data.Conversation;
import codeu.model.data.Message;
import codeu.model.data.User;
import codeu.model.store.basic.ConversationStore;
import codeu.model.store.basic.MessageStore;
import codeu.model.store.basic.UserStore;
import java.io.IOException;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.kefirsf.bb.BBProcessorFactory;
import org.kefirsf.bb.TextProcessor;

/** Servlet class responsible for the chat page. */
public class EditServlet extends HttpServlet {

    /** Store class that gives access to Conversations. */
    private ConversationStore conversationStore;

    /** Store class that gives access to Messages. */
    private MessageStore messageStore;

    /** Store class that gives access to Users. */
    private UserStore userStore;

    /** Set up state for handling chat requests. */
    @Override
    public void init() throws ServletException {
        super.init();
        setConversationStore(ConversationStore.getInstance());
        setMessageStore(MessageStore.getInstance());
        setUserStore(UserStore.getInstance());
    }

    /**
     * Sets the ConversationStore used by this servlet. This function provides a common setup method
     * for use by the test framework or the servlet's init() function.
     */
    void setConversationStore(ConversationStore conversationStore) {
        this.conversationStore = conversationStore;
    }

    /**
     * Sets the MessageStore used by this servlet. This function provides a common setup method for
     * use by the test framework or the servlet's init() function.
     */
    void setMessageStore(MessageStore messageStore) {
        this.messageStore = messageStore;
    }

    /**
     * Sets the UserStore used by this servlet. This function provides a common setup method for use
     * by the test framework or the servlet's init() function.
     */
    void setUserStore(UserStore userStore) {
        this.userStore = userStore;
    }

    /**
     * Changes the message from the previous message in MessageStore to the new message
     * given by the input from the user's reply to the prompt box - called in the POST method
     */
    // TODO: figure out where to call this function; perhaps something onClick with btn or in the Post request
    void editMessage(UUID conversationID, UUID messageID, String newContent) {
        messageStore.editMessage(messageStore.getMessagesInConversation(conversationID), messageID, newContent);
    }


    /**
     * This function fires when a user navigates to the chat page. It gets the conversation title from
     * the URL, finds the corresponding Conversation, and fetches the messages in that Conversation.
     * It then forwards to chat.jsp for rendering.
     */
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
 }

    /**
     * This function fires when a user submits the form on the chat page. It gets the logged-in
     * username from the session, the conversation title from the URL, and the chat message from the
     * submitted form data. It creates a new Message from that data, adds it to the model, and then
     * redirects back to the chat page.
     */
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        System.out.println("Entered into doPost");
        String newMsg = request.getParameter("newMsg");
        System.out.println(newMsg);
        /*error on this line*/ UUID msgID = UUID.fromString(request.getParameter("msgID"));
        UUID conversationID = UUID.fromString(request.getParameter("conversationID"));
        editMessage(conversationID, msgID, newMsg);
        System.out.println("hey");
    }
}
