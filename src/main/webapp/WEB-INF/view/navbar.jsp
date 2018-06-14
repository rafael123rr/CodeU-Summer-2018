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
