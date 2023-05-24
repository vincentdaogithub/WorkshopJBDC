<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="f" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>

<html>
    <head>
        <title>Staff</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="main.css" />
    </head>

    <body>
        <header>
            <h1>Staff</h1>
        </header>

        <main>
            <a href="/WorkshopJDBC/?p=create">Create new Mobile</a>

            <section class="search-container">
                <form action="/WorkshopJDBC/?a=search&s=id&p=staff" method="post">
                    <p>Search by Mobile ID</p>

                    <div class="input search-input">
                        <label for="id">ID</label>
                        <input id="id" type="text" name="txtMobileID" placeholder="mobile id..." />
                    </div>

                    <input type="submit" value="Search by Mobile ID" />
                </form>

                <form action="/WorkshopJDBC/?a=search&s=name&p=staff" method="post">
                    <p>Search by Mobile Name</p>

                    <div class="input search-input">
                        <label for="name">Mobile Name</label>
                        <input id="name" type="text" name="txtMobileName" placeholder="mobile name..." />
                    </div>

                    <input type="submit" value="Search by Mobile Name" />
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
                                <th>Delete</th>
                                <th>Update</th>
                            </tr>

                            <c:forEach items="${mobiles}" var="mobile" varStatus="i">
                                <tr>
                                    <form action="/WorkshopJDBC/?a=mobile&m=update&mid=${f:escapeXml(mobile.mobileID)}&p=staff" method="post">
                                        <td>${i.count}</td>
                                        <td>${f:escapeXml(mobile.mobileID)}</td>
                                        <td>
                                            <textarea name="txtDescription" rows="4" cols="20" required>${f:escapeXml(mobile.description)}</textarea>
                                        </td>
                                        <td>$<input type="number" name="flPrice" value="${mobile.price}" step="0.01" required /></td>
                                        <td>${f:escapeXml(mobile.mobileName)}</td>
                                        <td>${mobile.yearOfProduction}</td>
                                        <td>
                                            <input type="number" name="itQuantity" value="${mobile.quantity}" required />
                                        </td>
                                        <td>
                                            <select name="blNotSale" required>
                                                <option value="false" ${mobile.notSale ? "" : "selected"}>Yes</option>
                                                <option value="true" ${mobile.notSale ? "selected" : ""}>No</option>
                                            </select>
                                        </td>
                                        <td><a href="/WorkshopJDBC/?a=mobile&m=remove&mid=${mobile.mobileID}&p=staff">Delete</a></td>
                                        <td><input type="submit" value="Update" /></td>
                                    </form>
                                </tr>
                            </c:forEach>
                        </table>
                    </c:otherwise>
                </c:choose>
            </section>
        </main>
    </body>
</html>
