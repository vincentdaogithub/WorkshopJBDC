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
    </head>

    <body>
        <header>
            <h1>User</h1>
        </header>

        <main>
            <section class="search-container">
                <form action="/WorkshopJDBC?c=search">
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
                        <div class="mobile-item-header">
                            <p>No.</p>
                            <p>ID</p>
                            <p>Description</p>
                            <p>Price</p>
                            <p>Name</p>
                            <p>Year Of Production</p>
                            <p>Quantity</p>
                            <p>In Sale</p>
                            <p>Add To Cart</p>
                        </div>

                        <c:forEach items="${mobiles}" var="mobile" varStatus="i">
                            <div class="mobile-item">
                                <p>${i.count}</p>
                                <p>${f:escapeXml(mobile.mobileID)}</p>
                                <p>${f:escapeXml(mobileDescription)}</p>
                                <p><fmt:formatNumber type="currency" value="${mobile.price}" currencySymbol="$" /></p>
                                <p>${f:escapeXml(mobile.mobileName)}</p>
                                <p>${mobile.yearOfProduction}</p>
                                <p>${mobile.quantity}</p>
                                <p>${mobile.notSale ? "No" : "Yes"}</p>
                                <a href="/WorkshopJDBC?a=cart&ca=add&mid=${mobile.mobileID}">Add</a>
                            </div>
                        </c:forEach>
                    </c:otherwise>
                </c:choose>
            </section>
        </main>
    </body>
</html>

