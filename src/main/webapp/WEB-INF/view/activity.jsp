<%--Team 16: Winnie Wang--%>
<%--Activity Feed prototype--%>
<%@ page import="java.util.List" %>
<%@ page import="codeu.model.data.Conversation" %>
<%@ page import="codeu.model.data.Message" %>
<%@ page import="codeu.model.data.User" %>
<%@ page import="codeu.model.store.basic.UserStore" %>
<%@ page import="codeu.model.store.basic.ConversationStore" %>
<%@ page import="codeu.model.data.Activity" %>
<%@ page import="codeu.model.store.basic.ActivityStore" %>


<!DOCTYPE html>
<html>
  <head>
    <title>Activity Feed</title>

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
      }
    </script>
  </head>
  <body>

    <nav>
      <a id="navTitle" href="/">Chatty Lambdas Chat App</a>
      <a href="/conversations.jsp">Conversations</a>
        <% if (request.getSession().getAttribute("user") != null) { %>
      <a>Hello <%= request.getSession().getAttribute("user") %>!</a>
      <% } else { %>
        <a href="/login.jsp">Login</a> <!--testing ".jsp"-->
      <% } %>
      <a href="/about.jsp">About</a>
      <a href="/activity,jsp">Activity</a>
    </nav>

    <% if (request.getSession().getAttribute("user") == null) { %>
      <p><a href="/login.jsp">Login</a> to see your activity.</p>
    <% } %>
    <h1><strong>Activity Feed</strong></h1>
    <%
      List<Conversation> conversations = (List<Conversation>) request.getAttribute("conversations");

       if (conversations.isEmpty()) {
    %>
      <p>Start a conversation to add to your activity feed.</p>
    <% }
      else {
        for (Conversation conversation : conversations) { %>

            <ul style ="list-style: none;">
            <li><a href="/chat/<%= conversation.getTitle() %>">
              <%= conversation.getTitle() %></a></li>
            </ul>
    <% }
      }
    %>
    </div>
  </body>
</html>
