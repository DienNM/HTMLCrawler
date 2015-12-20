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
    
    <style type="text/css">
        .pageItem {
            padding: 4px 5px 4px 5px;
            border: 1px #aaa solid;
            background: #eef;
            margin-right: 5px;
        }
        
        .pageItem.active {
            background: #ccc;
        }
        
        .pageItem a{
            text-decoration: none;
        }
    </style>
    
</head>

<body style="margin-top: 20px">
    <div class = "container-fluid">
        <div class = "row">
            <div class="col-md-12">
                <form action="" method="get">
                    <input name = "q" class = "col-md-10" value="${q}" />
                    <button type="submit">Search</button>
                </form>
            </div>
            
            <div class="col-md-12" style="margin-top: 10px; margin-bottom: 10px">
                <c:set var="firstPage" value="0" />
                <c:set var="lastPage" value="${pageable.totalPages}" />
                <c:if test="${pageable.currentPage - 3 > 0}">
                    <c:set var="firstPage" value="${pageable.currentPage - 3}"></c:set>
                </c:if>
                <c:if test="${pageable.currentPage + 3 < pageable.totalPages}">
                    <c:set var="lastPage" value="${pageable.currentPage + 3}"></c:set>
                </c:if>
                
                <c:if test="${firstPage > 0}">
                    <c:if test="${pageable.currentPage > 4}">
                        <span class="pageItem">
	                        <a href = "search?q=${q}">0</a>
	                    </span>
                    </c:if>
                    <span class="pageItem">
                        ...
                    </span>
                </c:if>
                <c:forEach begin="${firstPage}" end="${lastPage}" var="index">
                    <c:if test="${index == pageable.currentPage}">
                        <span class="pageItem acive">
	                        ${index}
	                    </span>
                    </c:if>
                    <c:if test="${index != pageable.currentPage}">
                        <span class="pageItem">
	                        <a href = "search?q=${q}&currentPage=${index}">${index}</a>
	                    </span>
                    </c:if>
                </c:forEach>
                <c:if test="${lastPage < pageable.totalPages}">
                    <span class="pageItem">
                        ...
                    </span>
                    <span class="pageItem">
                        <a href = "search?q=${q}&currentPage=${pageable.totalPages}">${pageable.totalPages}</a>
                    </span>
                </c:if>
            </div>
            
            <div class="col-md-12">
                <table class = "table table-striped table-bordered">
                    <thead>
                        <tr>
                            <th width="60px"></th>
                            <th>Job Info</th>
                            <th>Category</th>
                            <th>Workplace</th>
                            <th>Salary</th>
                            <th></th>
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
                            <td><a target="_blank" href="${result._url}">Link</a></td>
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