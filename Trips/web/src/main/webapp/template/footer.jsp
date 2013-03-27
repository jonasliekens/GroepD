<c:set var="page" value="${requestScope['javax.servlet.forward.request_uri']}" />

<section id="footer-section">
    <div class="container">
        <div class="row-fluid">
            <div class="span12">
                <ul class="unstyled">
                    <li>
                        <a href="${page}?lang=en">English</a>
                    </li>
                    <li>
                        <a href="${page}?lang=nl">Nederlands</a>
                    </li>
                </ul>
                <%--${page}--%>
                <%--${pageContext.response.locale}--%>
            </div>
        </div>
    </div>
</section>