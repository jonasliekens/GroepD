<section id="header-section">
    <div class="container">
        <div class="row-fluid">
            <div class="span12">
                <ul class="menu" id="main-menu">
                    <li>
                        <a href=""><spring:message code="menu.home"/></a>
                    </li>
                    <li>
                        <a href="trips"><spring:message code="menu.trips"/></a>
                    </li>
                    <li>
                        <c:choose>
                            <c:when test="${sessionScope.userId > 0}">
                                <a href="login/logout"><spring:message code="menu.logout"/></a>
                            </c:when>
                            <c:otherwise>
                                <a href="login"><spring:message code="menu.login"/></a>
                            </c:otherwise>
                        </c:choose>
                    </li>
                </ul>
            </div>
        </div>
    </div>
</section>