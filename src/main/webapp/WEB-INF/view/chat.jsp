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
<%@ page import="java.util.UUID" %>
<%@ page import="codeu.model.data.Conversation" %>
<%@ page import="codeu.model.data.Message" %>
<%@ page import="codeu.model.data.User" %>
<%@ page import="codeu.model.store.basic.UserStore" %>
<c:import url="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js" />

<%
//User currentUser = (User) request.getSession().getAttribute("user");
List<Conversation> conversations = (List<Conversation>) request.getAttribute("conversations");
Conversation conversation = (Conversation) request.getAttribute("conversation");
List<Message> messages = (List<Message>) request.getAttribute("messages");
UserStore thisUserStore = (UserStore) request.getAttribute("userStore");
%>

<!DOCTYPE html>
<html>
<head>
  <title><%= conversation.getTitle() %></title>
  <link rel="stylesheet" href="/css/main.css" type="text/css">

  <style>
    #chat {
      background-color: white;
      height: 500px;
      overflow-y: scroll
    }
  </style>

  <script>
    // scroll the chat div to the bottom
    function scrollChat() {
      var chatDiv = document.getElementById('chat');
      chatDiv.scrollTop = chatDiv.scrollHeight;
    };
  </script>
</head>

<body onload="scrollChat()">

  <nav>
    <a id="navTitle" href="/">Chatty Lambdas Chat App</a>
    <a href="/conversations">Conversations</a>
      <% if (request.getSession().getAttribute("user") != null) { %>
    <a>Hello <%= request.getSession().getAttribute("user") %>!</a>
    <% } else { %>
      <a href="/login">Login</a>
    <% } %>
    <a href="/about.jsp">About</a>
    <a href="/activity.jsp">Activity</a>
  </nav>

  <div id="container">

    <h1><%= conversation.getTitle() %>
      <a href="" style="float: right">&#8635;</a></h1>

    <hr/>

    <div id="chat">
      <ul>

      <script type="text/javascript">
         function edit(messageID, convoID, messageContent) {
           console.log("button clicked");
                var xhttp = new XMLHttpRequest();
                var newMessage = prompt("What is your new message?", messageContent);
                xhttp.open("POST", "/editchat", true);
                xhttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
                xhttp.send("msgID="+messageID+"&conversationID=" + convoID+"&newMsg="+newMessage);
                location.reload();

         }

         function deleteMessage(messageID, convoID) {
           console.log("button clicked");
                var xhttp = new XMLHttpRequest();
                xhttp.open("POST", "/deletechat", true);
                xhttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
                xhttp.send("msgID="+messageID+"&conversationID=" + convoID);
                location.reload();

         }
      </script>

      <script type="text/javascript">
              function edit(messageID, convoID) {
                 console.log("button clicked");
                      var xhttp = new XMLHttpRequest();
                      var r = prompt("please tell");
                      xhttp.open("POST", "/editchat", true);
                      xhttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
                      console.log("entered editservlet");
                      console.log(xhttp.readyState);
                      console.log(messageID);
                      xhttp.send("msgID="+messageID+"&conversationID=" + convoID+"&newMsg="+r);

               }
            </script>
    <%
      String username = (String) request.getSession().getAttribute("user");
      User user = thisUserStore.getUser(username);

      for (Message message : messages) {
        String author = UserStore.getInstance().getUser(message.getAuthorId()).getName();
        UUID authorId = message.getAuthorId();
    %>
      <li><strong><%= author %>:</strong>
       <span id="<%= message.getId()%>"> <%= message.getContent() %>
   <%
          if (user.getId().equals(authorId)) {
    %>
          <form method="post" action="/editchat">
          <input type="button" id="btn" value="Edit" onclick="edit('<%= message.getId()%>','<%= conversation.getId() %>', '<%= message.getContent() %>')"></input>

          </form>
          <form method="post" action="/deletechat">
          <input type="button" id="btn" value="Delete" onclick="deleteMessage('<%= message.getId()%>','<%= conversation.getId() %>')"></input>
          </form>

        <% }
        }
      %>
      </li>
      </ul>
    </div>
    <hr/>

    <%
        if (request.getSession().getAttribute("user") != null) { %>
          <form action="/chat/<%= conversation.getTitle() %>" method="POST">
          <input type="text" name="message">
          <br/>
          <button type="submit">Send</button>
    </form>
    <% } else { %>
          <p><a href="/login">Login</a> to send a message.</p>
    <%
    }%>

    <hr/>

  </div>

</body>
</html>
