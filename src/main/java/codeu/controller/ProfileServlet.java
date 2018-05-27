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
import codeu.model.data.User;
import codeu.model.store.basic.ConversationStore;
import codeu.model.store.basic.UserStore;
import java.io.IOException;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** Servlet class responsible for the profile page for the user. */ 
public class ProfileServlet extends HttpServlet {

	/** Store class that gives aceces to Users. */
	private UserStore userStore; 

	/** 
	* Setts the UserStore used by this servlet. This function provides a common setup for use
	* by the test framework or the servlet's init() function.
	*/ 
	void setUserStore(UserStore userStore) {
		this.userStore = userStore; 
	}

	@Override
  	public void doGet(HttpServletRequest request, HttpServletResponse response)
  		throws IOException, ServletException {
  			request.getRequestDispatcher("/WEB-INF/view/profile.jsp").forward(request, response);
  	}

  	@Override
  	public void doPost(HttpServletRequest request, HttpServletResponse response)
  		throws IOException, ServletException {
  			
  			String username = (String) request.getSession().getAttribute("user");


  			if (username == null){
  				// if userr is not logeed in, don't have access to their profile 
  				response.sendRedirect("/profile");
  				return; 
  			}

  			User user = userStore.getUser(username);

  			if(user == null){
  				// if user was not found, don't have access to their profile
  				System.out.println("User not found: " + username);
  				response.sendRedirect("/login"); 
  				return;
  			}

  			String aboutMe = request.getParameter("aboutme");

  			response.sendRedirect("/profile");
  	}
}