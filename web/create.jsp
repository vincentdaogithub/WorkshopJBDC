<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="f" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>

<html>
    <head>
        <title>Create</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="main.css" />
    </head>

    <body>
        <header>
            <h1>Create new Mobile</h1>
        </header>

        <main>
            <a href="/WorkshopJDBC/?p=staff">Back to Staff</a>

            <section class="create-container">
                <form action="/WorkshopJDBC/?a=create&p=staff" method="post">
                    <div class="input create-input">
                        <label for="name">Mobile Name</label>
                        <input id="name" type="text" name="txtMobileName" placeholder="mobile name..." required />
                    </div>

                    <div class="input create-input">
                        <label for="description">Description</label>
                        <textarea id="description" name="txtDescription" placeholder="description..." required></textarea>
                    </div>

                    <div class="input create-input">
                        <label for="yop">Year of Production</label>
                        <input id="yop" type="number" name="itYearOfProduction" placeholder="year of production..." required />
                    </div>

                    <div class="input create-input">
                        <label for="price">Price</label>
                        <input id="price" type="number" name="flPrice" step="0.01" placeholder="price..." required />
                    </div>

                    <div class="input create-input">
                        <label for="quantity">Quantity</label>
                        <input id="quantity" type="number" name="itQuantity" placeholder="quantity..." required />
                    </div>

                    <div class="input create-input">
                        <label for="notSale">Not Sale</label>
                        <select id="notSale" name="blNotSale">
                            <option value="true">Yes</option>
                            <option value="false">No</option>
                        </select>
                    </div>

                    <input type="submit" value="Create" />
                </form>
            </section>
        </main>
    </body>
</html>
