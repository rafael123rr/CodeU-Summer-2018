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
<!DOCTYPE html>
<html>
<head>
  <title>Chatty Lambdas Chat App</title>
  <link rel="stylesheet" href="/css/main.css">
</head>
<body>

  <nav>
    <a id="navTitle" href="/">Chatty Lambdas Chat App</a>
    <a href="/conversations">Conversations</a>
    <a href="/about.jsp">About</a>
    <a href="/admin.jsp">Admin</a>
    <a href="/activity.jsp">Activity</a>

  </nav>


  <div id="container">
    <div
      style="width:75%; margin-left:auto; margin-right:auto; margin-top: 50px;">

      <h1>Administration Page</h1>
      <h2>Welcome!</h2>

      <ul>
        <li>Number of users on the platform: ${numUsers}</li>
        <li>Number of total conversations: ${numConversations}</li>
        <li>Number of total messages: ${numMessages}</li>

      </ul>
    </div>
  </div>
</body>
</html>
