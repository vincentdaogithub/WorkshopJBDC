<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
    <head>
        <title>Welcome</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
    </head>

    <body>
        <header>
            <h1>Mobile Management System</h1>
        </header>

        <main>
            <div class="loginContainer">
                <form action="/WorkshopJDBC?a=login" method="post">
                    <div class="inputContainer">
                        <label for="txtUserID">User ID</label>
                        <input id="txtUserID" type="text" name="txtUserID" placeholder="user ID..." required />
                    </div>

                    <div class="input">
                        <label for="txtPassword">Password</label>
                        <input id="txtPassword" type="password" name="txtPassword" placeholder="password..." required />
                    </div>

                    <input type="submit" value="Submit" />
                </form>
            </div>
        </main>
    </body>
</html>

