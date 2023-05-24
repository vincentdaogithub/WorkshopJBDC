<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="f" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>

<html>
    <head>
        <title>Cart</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="main.css" />
    </head>

    <body>
        <header>
            <h1>Cart</h1>
        </header>

        <main>
            <a href="/WorkshopJDBC/?p=user">Back to User</a>

            <section class="cart-container">
                <h2>Cart list</h2>

                <c:choose>
                    <c:when test="${empty requestScope.cart}">
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
                                <th>Buy Quantity</th>
                            </tr>

                            <c:forEach items="${requestScope.cart}" var="mobile" varStatus="i">
                                <tr class="mobile-item">
                                    <td>${i.count}</td>
                                    <td>${f:escapeXml(mobile.key.mobileID)}</td>
                                    <td>${f:escapeXml(mobile.key.description)}</td>
                                    <td><fmt:formatNumber type="currency" value="${mobile.key.price}" currencySymbol="$" /></td>
                                    <td>${f:escapeXml(mobile.key.mobileName)}</td>
                                    <td>${mobile.key.yearOfProduction}</td>
                                    <td>${mobile.key.quantity}</td>
                                    <td>${mobile.key.notSale ? "No" : "Yes"}</td>
                                    <td>${mobile.value}</td>
                                </tr>
                            </c:forEach>
                        </table>
                    </c:otherwise>
                </c:choose>
            </section>
        </main>
    </body>
</html>
