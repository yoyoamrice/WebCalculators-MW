<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
    <title>Money Calculator</title>
</head>
<body>
<h1>Server-Side Money Calculator</h1>

<form action="moneyCalculator" method="post">

    <h3>First Amount</h3>
    <c:forEach var="i" begin="0" end="5">
        <label>${coinNames[i]}:</label>
        <input type="text" name="coins1_${i}"
               value="${coins1[i] != null ? coins1[i] : ''}"/>
        <c:if test="${not empty errorMsg1}">
            <span style="color:red">${errorMsg1}</span>
        </c:if>
        <br/>
    </c:forEach>

    <h3>Second Amount</h3>
    <c:forEach var="i" begin="0" end="5">
        <label>${coinNames[i]}:</label>
        <input type="text" name="coins2_${i}"
               value="${coins2[i] != null ? coins2[i] : ''}"/>
        <c:if test="${not empty errorMsg2}">
            <span style="color:red">${errorMsg2}</span>
        </c:if>
        <br/>
    </c:forEach>

    <h3>Operation:</h3>
    <select name="operation">
        <option value="add" <c:if test="${operation == 'add'}">selected</c:if>>Add</option>
        <option value="subtract" <c:if test="${operation == 'subtract'}">selected</c:if>>Subtract</option>
    </select>
    <br/><br/>

    <input type="submit" value="Calculate"/>
</form>

<c:if test="${not empty resultCents}">
    <h2>Result in cents: ${resultCents}</h2>
</c:if>

</body>
</html>
