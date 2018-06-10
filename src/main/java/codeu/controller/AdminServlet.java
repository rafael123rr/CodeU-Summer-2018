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

import codeu.model.data.User;
import codeu.model.store.basic.ConversationStore;
import codeu.model.store.basic.MessageStore;
import codeu.model.store.basic.UserStore;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.repackaged.com.google.datastore.v1.Datastore;
import org.mindrot.jbcrypt.BCrypt;

/** Servlet class responsible for the login page. */
public class AdminServlet extends HttpServlet {

    /** Store class that gives access to Users. */
    private UserStore userStore;
    DatastoreService datastoreService = DatastoreServiceFactory.getDatastoreService();

    /**
     * Set up state for handling login-related requests. This method is only called when running in a
     * server, not when running in a test.
     */
    @Override
    public void init() throws ServletException {
        super.init();
        setUserStore(UserStore.getInstance());
    }

    /**
     * Sets the UserStore used by this servlet. This function provides a common setup method for use
     * by the test framework or the servlet's init() function.
     */
    void setUserStore(UserStore userStore) {
        this.userStore = userStore;
    }

    /**
     * This function fires when a user requests the /admin URL. It simply forwards the request to
     * admin.jsp.
     */
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        request.setAttribute("numUsers", userStore.numUsers());
        request.setAttribute("numConversations", ConversationStore.numConversations());
        request.setAttribute("numMessages", MessageStore.numMessages());
        request.getRequestDispatcher("/WEB-INF/view/admin.jsp").forward(request, response);

    }

    /**
     * This function fires when a user submits the admin form. It gets the username and password from
     * the submitted form data, checks for validity and if correct adds the username to the session so
     * we know the user is logged in.
     */
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        User user = userStore.getUser(username);

    }
}
