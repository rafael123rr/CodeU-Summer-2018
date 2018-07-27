<%--
  Copyright 2017 Google Inc.

  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
--%>
<%@ page import="java.util.List" %>
<%@ page import="codeu.model.store.basic.UserStore" %>

<!DOCTYPE html>
<html>
<head>
  <title>About Me</title>
  <link rel="stylesheet" href="/css/main.css" type="text/css">
</head>
<body>

  <nav>
    <a id="navTitle" href="/">CodeU Chat App</a>
    <a href="/conversations.jsp">Conversations</a>
    <% if(request.getSession().getAttribute("user") != null){ %>
      <a>Hello <%= request.getSession().getAttribute("user") %>!</a>
      <a href ="/profile.jsp">About Me</a>
    <% } else{ %>
      <a href="/login.jsp">Login</a>
    <% } %>
    <a href="/about.jsp">About</a>
  </nav>

  <div id="container">

    <% if(request.getAttribute("error") != null){ %>
        <h2 style="color:red"><%= request.getAttribute("error") %></h2>
    <% } %>

    <% if(request.getSession().getAttribute("user") != null){ %>
      <h1>About Me</h1>
      <form action="/profile" method="POST">
          <div class="form-group">
            <label class="form-control-label">About Me(edit)</label>
          <input type="text" name="AboutMe">
        </div>

        <button type="submit">Submit</button>
      </form>

      <hr/>
    <% } %>

    <h1>About Me</h1>

  </div>

</body>
</html>
