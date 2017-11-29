<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page isELIgnored="false" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">

    <link rel="stylesheet" href="/resources/css/bootstrap.css"/>
    <link rel="stylesheet" href="/resources/css/kinoviewstyles.css"/>

    <title>kinoview - ${requestScope.genreName}</title>

</head>
<body>
<div class="container">
    <div class="searchingForm" style="float: right; margin-top: 8px">
        <form action="http://localhost:8080/films/search" method="get">
            <input type="text" name="searchingParam" value="" placeholder="search film">
            <button class="btn btn-secondary">Search</button>
        </form>
    </div>
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
            <h4>Жанры:</h4>
            <form action="http://localhost:8080/films/search" method="GET">
                <p>
                    <c:forEach items="${requestScope.allGenres}" var="genre">
                        <button class="btn btn-link" value="${genre.genreName}" name="searchingParam">
                            <c:out value="${genre.genreName}"/>
                        </button>
                        <br>
                    </c:forEach>
                </p>
            </form>
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
                            <li>
                                <form action="http://localhost:8080/films/search" method="get">
                                    <em class="font-weight-bold">Год выпуска:&nbsp;</em>
                                    <button class="btn-link-describe" value="${film.releaseYear}"
                                            name="searchingParam">
                                        <c:out value="${film.releaseYear}"></c:out>
                                    </button>
                                </form>
                            <li>
                                <form action="http://localhost:8080/films/search" method="get">
                                    <em class="font-weight-bold">Качество:&nbsp;</em>
                                    <button class="btn-link-describe" value="${film.quality}"
                                            name="searchingParam">
                                        <c:out value="${film.quality}"></c:out>
                                    </button>
                                </form>
                            </li>
                            <li>
                                <form action="http://localhost:8080/films/search" method="get">
                                    <em class="font-weight-bold">Перевод:&nbsp;</em>
                                    <button class="btn-link-describe" value="${film.translation}"
                                            name="searchingParam">
                                        <c:out value="${film.translation}"></c:out>
                                    </button>
                                </form>
                            </li>
                            <li>
                                <em class="font-weight-bold">Продолжительность:&nbsp;</em>
                                <c:out value="${film.duration}"></c:out>
                            </li>
                            <li>
                                <form action="http://localhost:8080/films/search" method="get">
                                    <em class="font-weight-bold">Жанры:&nbsp;</em>
                                    <c:forEach items="${film.allGenres}" var="genre">
                                        <button class="btn-link-describe" value="${genre.genreName}"
                                                name="searchingParam">
                                            <c:out value="${genre.genreName}"/>
                                        </button>
                                    </c:forEach>
                                </form>
                            </li>
                            <li>
                                <form action="http://localhost:8080/films/search" method="get">
                                    <em class="font-weight-bold">Страны:&nbsp;</em>
                                    <c:forEach items="${film.allCountries}" var="country">
                                        <button class="btn-link-describe" value="${country.countryName}"
                                                name="searchingParam">
                                            <c:out value="${country.countryName}"/>
                                        </button>
                                    </c:forEach>
                                </form>
                            </li>
                        </ul>
                    </td>
                </tr>
                </tbody>
            </c:forEach>
        </table>

        <form class="row justify-content-center" action="http://localhost:8080/films/search" method="GET">
            <nav aria-label="Page navigation example">
                <input type="text" name="searchingParam" value="${requestScope.searchingParam}" hidden>
                <ul class="pagination" ${requestScope.pagination}>
                    <li class="page-item ${requestScope.previousPage}" ${requestScope.previousPageIsHidden}>
                        <button class="page-link" name="page" value="${requestScope.previousPageValue}">Previous
                        </button>
                    </li>
                    <li class="page-item ${requestScope.firstPage}" ${requestScope.FirstPageIsHidden}>
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
                    <li class="page-item ${requestScope.lastPage}" ${requestScope.lastPageIsHidden}>
                        <button class="page-link" name="page" value="${requestScope.lastPageValue}">Last</button>
                    </li>
                    <li class="page-item ${requestScope.nextPage}" ${requestScope.nextPageIsHidden}>
                        <button class="page-link" name="page" value="${requestScope.nextPageValue}">Next</button>
                    </li>
                </ul>
            </nav>
        </form>
    </div>
</div>
</body>
</html>
