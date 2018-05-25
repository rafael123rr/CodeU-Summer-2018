<%--Team 16: Winnie Wang--%>
<%--Activity Feed prototype--%>
<%@ page import="java.util.List" %>
<%@ page import="codeu.model.data.Conversation" %>
<%@ page import="codeu.model.data.Message" %>
<%@ page import="codeu.model.store.basic.UserStore" %>

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
      };
    </script>
  </head>
  <body>
    <h1>This is the activity page.</p>

    <nav>
      <%@include file="navbar.jsp"%>
    </nav>

      <div id="container">
        <!--blank for now-->
      </div>

      <div id="chat">
        <!--blank for now-->
      </div>

      <hr/>

      <%-- <% if (request.getSession().getAttribute("user") != null) { %>
      <form action="/chat/<%= conversation.getTitle() %>" method="POST">
          <input type="text" name="message">
          <br/>
          <button type="submit">Send</button>
      </form>
      <% } else { %>
        <p><a href="/login">Login</a> to send a message.</p>
      <% } %> --%>

      <hr/>

    </div>
  </body>
</html>
