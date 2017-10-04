<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page isELIgnored="false" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/css/bootstrap.min.css"
          integrity="sha384-/Y6pD6FV/Vv2HJnA6t+vslU6fwYXjCFtcEpHbNJ0lyAFsXTsjBbfaDjzALeQsN6M" crossorigin="anonymous">
    <link rel="stylesheet" href="/resources/css/kinoviewstyles.css"/>

    <%--todo: paginaton--%>
    <link rel="stylesheet" href="/resources/css/simplePagination.css"/>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <script type="text/javascript" src="/resources/js/jquery.simplePagination.js"></script>

    <title>kinoview - Main</title>

</head>
<body>
<div class="container">
    <a href="http://localhost:8080/main"> <img class="logo" src="/resources/thumbnails/logo.png"></a>
    <c:choose>
        <c:when test="${sessionScope.login == null}">
            <div class="user-panel">
                <form action="http://localhost:8080/users/login" method="GET">
                    <button class="btn btn-secondary">Login</button>
                </form>

                <form action="http://localhost:8080/users/signUp" method="GET">
                    <button class="btn btn-secondary">Sign up</button>
                </form>
            </div>
        </c:when>
        <c:otherwise>
            <h5> Привет, ${sessionScope.login}</h5>
            <form action="http://localhost:8080/users/logout" method="POST">
                <button class="btn btn-secondary">Logout</button>
            </form>
        </c:otherwise>
    </c:choose>


    <div class="thumbnails-container">
        <c:forEach items="${requestScope.thumbnails}" var="film">
            <img class="image-thumbnail" src="${film.imgLink}"/>
        </c:forEach>
    </div>
    <div class="container-data">
        <div class="leftbar">
            <p>Здесь будут жанры</p>
            <p>
                Комедии <br/>Ужасы <br/>Фантастика
            </p>
        </div>

        <table class="mb-4">
            <c:forEach items="${requestScope.films}" var="film">
                <tbody>
                <th colspan="2">
                    <div>
                        <c:out value="${film.name}"></c:out>
                    </div>
                </th>
                <tr>
                    <td width="23%">
                        <img src="${film.imgLink}" class="float-left"/>
                    </td>

                    <td width="77%">
                        <ul class="list-unstyled">
                            <li><c:out value="${film.shortStory}"></c:out></li>
                            <li>&nbsp;</li>
                            <li><em class="font-weight-bold">Год выпуска:&nbsp;</em>
                                    <c:out value="${film.releaseYear}"></c:out>
                            <li>
                                    <%--<c:out value="${film.genre}"></c:out>--%>
                                    <%--<c:out value="${film.country}"></c:out>--%>
                            <li><em class="font-weight-bold">Качество:&nbsp;</em><c:out value="${film.quality}"></c:out>
                            </li>
                            <li><em class="font-weight-bold">Перевод:&nbsp;</em><c:out
                                    value="${film.translation}"></c:out>
                            </li>
                            <li><em class="font-weight-bold">Продолжительность:&nbsp;</em><c:out
                                    value="${film.duration}"></c:out></li>
                        </ul>
                    </td>
                </tr>
                </tbody>
            </c:forEach>
        </table>

        <form class="row justify-content-center" action="http://localhost:8080/main" method="GET">
            <nav aria-label="Page navigation example">
                <ul class="pagination">
                    <li class="page-item ${requestScope.previousPage}">
                        <button class="page-link" name="page" value="${requestScope.previousPageValue}">Previous
                        </button>
                    </li>
                    <li class="page-item ${requestScope.firstPage}">
                        <button class="page-link" name="page" value="${requestScope.firstPageValue}">First</button>
                    </li>
                    <li class="page-item" ${requestScope.pageOneisHidden}>
                        <button class="page-link" name="page"
                                value="${requestScope.pageOneValue}">${requestScope.pageOneValue}</button>
                    </li>
                    <li class="page-item" ${requestScope.pageTwoIsHidden}>
                        <button class="page-link" name="page"
                                value="${requestScope.pageTwoValue}">${requestScope.pageTwoValue}</button>
                    </li>
                    <li class="page-item" ${requestScope.pageThreeIsHidden}>
                        <button class="page-link" name="page"
                                value="${requestScope.pageThreeValue}">${requestScope.pageThreeValue}</button>
                    </li>
                    <li class="page-item disabled">
                        <button class="page-link" type="submit" name="page"
                                value="${requestScope.currentPage}">${requestScope.currentPage}</button>
                    </li>
                    <li class="page-item" ${requestScope.pageFourIsHidden}>
                        <button class="page-link" name="page"
                                value="${requestScope.pageFourValue}">${requestScope.pageFourValue}</button>
                    </li>
                    <li class="page-item" ${requestScope.pageFiveIsHidden}>
                        <button class="page-link" name="page"
                                value="${requestScope.pageFiveValue}">${requestScope.pageFiveValue}</button>
                    </li>
                    <li class="page-item" ${requestScope.pageSixIsHidden}>
                        <button class="page-link" name="page"
                                value="${requestScope.pageSixValue}">${requestScope.pageSixValue}</button>
                    </li>
                    <li class="page-item ${requestScope.lastPage}">
                        <button class="page-link" name="page" value="${requestScope.lastPageValue}">Last</button>
                    </li>
                    <li class="page-item ${requestScope.nextPage}">
                        <button class="page-link" name="page" value="${requestScope.nextPageValue}">Next</button>
                    </li>
                </ul>
            </nav>
        </form>
    </div>
</div>
</body>
</html>
