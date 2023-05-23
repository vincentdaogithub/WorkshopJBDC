<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="f" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>

<html>
    <head>
        <title>User</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="main.css" />
    </head>

    <body>
        <header>
            <h1>User</h1>
        </header>

        <main>
            <a href="/WorkshopJDBC/?p=cart">To cart</a>

            <section class="search-container">
                <form action="/WorkshopJDBC/?a=search&s=price-min-max&p=user" method="post">
                    <p>Search by price</p>

                    <div class="input search-input">
                        <label for="min">Min</label>
                        <input id="min" type="number" name="numMin" step="0.01" placeholder="min price..." />
                    </div>

                    <div class="input search-input">
                        <label for="max">Max</label>
                        <input id="max" type="number" name="numMax" step="0.01" placeholder="max price..." />
                    </div>

                    <input type="submit" value="Search" />
                </form>
            </section>

            <section class="mobiles-container">
                <h2>Mobile list</h2>

                <c:choose>
                    <c:when test="${empty mobiles}">
                        <p>No mobile was found</p>
                    </c:when>

                    <c:otherwise>
                        <table>
                            <tr>
                                <th>No.</th>
                                <th>ID</th>
                                <th>Description</th>
                                <th>Price</th>
                                <th>Name</th>
                                <th>Year Of Production</th>
                                <th>Quantity</th>
                                <th>In Sale</th>
                                <th>Add To Cart</th>
                            </tr>

                            <c:forEach items="${mobiles}" var="mobile" varStatus="i">
                                <tr>
                                    <td>${i.count}</td>
                                    <td>${f:escapeXml(mobile.mobileID)}</td>
                                    <td>${f:escapeXml(mobile.description)}</td>
                                    <td><fmt:formatNumber type="currency" value="${mobile.price}" currencySymbol="$" /></td>
                                    <td>${f:escapeXml(mobile.mobileName)}</td>
                                    <td>${mobile.yearOfProduction}</td>
                                    <td>${mobile.quantity}</td>
                                    <td>${mobile.notSale ? "No" : "Yes"}</td>
                                    <td><a href="/WorkshopJDBC/?a=cart&c=add&mid=${mobile.mobileID}&p=cart">Add</a></td>
                                </tr>
                            </c:forEach>
                        </table>
                    </c:otherwise>
                </c:choose>
            </section>
        </main>
    </body>
</html>

