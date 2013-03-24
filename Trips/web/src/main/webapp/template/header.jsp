<section id="header-section">
    <div class="container">
        <div class="row-fluid">
            <div class="span12">
                <c:choose>
                    <c:when test="${sessionScope.userId > 0}">
                        <ul class="menu" id="main-menu">
                            <li>
                                <a href=""><spring:message code="menu.home"/></a>
                            </li>
                            <li>
                                <a href="chat"><spring:message code="menu.chat"/></a>
                            </li>
                            <li>
                                <a href="profile/edit"><spring:message code="menu.profile"/></a>
                            </li>
                            <li>
                                <a href="broadcast"><spring:message code="menu.messages"/></a>
                            </li>
                            <li>
                                <a href="logout"><spring:message code="menu.logout"/></a>
                            </li>
                        </ul>
                    </c:when>
                    <c:otherwise>
                        <ul class="menu" id="main-menu">
                            <li>
                                <a href=""><spring:message code="menu.home"/></a>
                            </li>
                            <li>
                                <a href="login"><spring:message code="menu.login"/></a>
                            </li>
                        </ul>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>
</section>