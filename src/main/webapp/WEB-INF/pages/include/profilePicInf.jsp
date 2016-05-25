<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!-- menu prile quick info -->
<div class="profile">
    <div class="profile_pic">

        <c:set var="photo" scope="page">
            <sec:authentication property="principal.user.image"/>
        </c:set>

        <c:choose>
            <c:when test="${photo eq 'null'}">
                <c:set var="photo" scope="page" value="/static/images/anonymouse.png"/>
            </c:when>
            <c:otherwise>
                <c:set var="photo" scope="page" value="/user/photos/${photo}"/>
            </c:otherwise>
        </c:choose>

        <img data-toggle="modal" data-target="#upload-image" src="<c:url value="${photo}"/>" alt="..."
             class="img-circle profile_img">
    </div>
    <div class="profile_info">
        <span>Welcome,</span>
        <h2><sec:authentication property="principal.name"/><br><sec:authentication
                property="principal.surname"/></h2>
    </div>
</div>