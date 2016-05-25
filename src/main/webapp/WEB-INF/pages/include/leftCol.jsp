<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<div class="col-md-3 left_col">
    <div class="left_col scroll-view">

        <div class="navbar nav_title" style="border: 0;">
            <a href="<c:url value="/"/>" class="site_title"><i class="fa fa-paw"></i>
                <span>Net<strong>Cracker</strong></span></a>
        </div>
        <div class="clearfix"></div>

        <!-- menu prile quick info -->
        <%@ include file="profilePicInf.jsp" %>
        <!-- /menu prile quick info -->

        <br/>
        <sec:authorize access='hasAnyRole("ROLE_HR")'>
            <c:url value="hr" var="roleAddres"/>
        </sec:authorize>
        <sec:authorize access='hasAnyRole("ROLE_BA")'>
            <c:url value="ba" var="roleAddres"/>
        </sec:authorize>
        <sec:authorize access='hasAnyRole("ROLE_DEV")'>
            <c:url value="dev" var="roleAddres"/>
        </sec:authorize>
        <!-- sidebar menu -->
        <div id="sidebar-menu" class="main_menu_side hidden-print main_menu">
            <div class="menu_section">
                <ul class="nav side-menu">
                    <sec:authorize access="hasAnyRole('ROLE_STUDENT')">
                        <li><a data-toggle="modal" data-target="#upload-image"><i class="fa fa-upload"></i> Load
                            Image</a></li>
                    </sec:authorize>
                    <sec:authorize access="hasAnyRole('ROLE_STUDENT')">
                        <li><a href="<c:url value="/service/getPDF"/>"><i class="fa fa-file-pdf-o"></i> Print PDF</a>
                        </li>
                    </sec:authorize>
                    <sec:authorize access="hasAnyRole('ROLE_HR', 'ROLE_BA', 'ROLE_DEV')">
                        <li><a href="<c:url value="/${roleAddres}/interview_page"/>"><i class="fa fa-comments-o"></i>Interview
                            Page</a></li>
                    </sec:authorize>
                    <li><a href="<c:url value="/logout"/>"><i class="fa fa-sign-out"></i> Logout </a></li>
                </ul>
            </div>
        </div>
    </div>

</div>