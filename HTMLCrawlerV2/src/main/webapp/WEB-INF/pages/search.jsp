<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Search</title>
    
    <script src="../resources/js/jquery-1.11.3.min.js"></script>
    <link rel="stylesheet" href="../resources/css/bootstrap/3.3.4/css/bootstrap.min.css">
    <link rel="stylesheet" href="../resources/css/bootstrap/3.3.4/css/bootstrap-theme.min.css">
    <script src="../resources/css/bootstrap/3.3.4/js/bootstrap.min.js"></script>
</head>

<body style="margin-top: 20px">
    <div class = "container-fluid">
        <div class = "row">
            <div class="col-md-12" style="margin-bottom: 30px">
                <form action="" method="get">
                    <input name = "q" class = "col-md-10" value="${q}" />
                    <button type="submit">Search</button>
                </form>
            </div>
            <div class="col-md-12">
                <table class = "table table-striped table-bordered">
                    <thead>
                        <tr>
                            <th width="100px"></th>
                            <th>Job Info</th>
                            <th>Category</th>
                            <th>Workplace</th>
                            <th>Salary</th>
                        </tr>
                    </thead>
                    <c:if test="${not empty results}">
                    <tbody>
	                <c:forEach items="${results}" var="result">
	                    <tr>
	                       <td style="text-align: center;"><img src="logo/${result.companyLogo}" alt="" width="50px" ></td>
	                        <td>
	                           <a href="#">${result.jobName}</a> <br>
                               ${result.companyName}
	                        </td>
                            <td>${result.categories}</td>
	                        <td>${result.workplace}</td>
	                        <td>${result.salary}</td>
	                    </tr>
	                </c:forEach>
                    </tbody>
                    </c:if>
                </table>
            </div>
        </div>
    </div>
</body>
</html>