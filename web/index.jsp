<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="controller.Users" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="f" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>

<html>
    <head>
        <title>Welcome</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="main.css" />
    </head>

    <body>
        <header>
            <h1>Mobile Management System</h1>
        </header>

        <main>
            <c:choose>
                <c:when test="${user != null && user.role == Users.USER.roleID}">
                    <a href="/WorkshopJDBC/?p=user">Go to User page</a>
                </c:when>

                <c:otherwise>
                    <div class="loginContainer">
                        <form action="/WorkshopJDBC/?a=login" method="post">
                            <div class="inputContainer">
                                <label for="txtUserID">User ID</label>
                                <input id="txtUserID" type="text" name="txtUserID" value="${f:escapeXml(prevTxtUserID)}" placeholder="user ID..." required />
                            </div>

                            <div class="input">
                                <label for="txtPassword">Password</label>
                                <input id="txtPassword" type="password" name="txtPassword" value="${f:escapeXml(prevTxtPassword)}" placeholder="password..." required />
                            </div>

                            <c:if test="${isInvalidLogin}">
                                <p>Invalid User ID or Password</p>
                            </c:if>

                            <input type="submit" value="Submit" />
                        </form>
                    </div>
                </c:otherwise>
            </c:choose>
        </main>
    </body>
</html>

