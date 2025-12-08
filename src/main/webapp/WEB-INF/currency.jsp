<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: lawso
  Date: 11/23/2025
  Time: 9:10 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Math</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-sRIl4kxILFvY47J16cr9ZwB07vP4J8+LH7qKQnuqkuIAvNWLzeN8tE5YBujZqJLB" crossorigin="anonymous">
</head>
<body>
<div class="container my-4">
    <div class="row">
        <div class="col-6">
            <h1>Currency Calculator</h1>
            <p class="lead">Enter a number for currency conversion. Enter 2 numbers and an operator to add or subtract between currencies.</p>
            <form method="POST" action="currency">
                <div class="form-group mb-2">
                    <label for="num1">Number 1:</label>
                    <input type="text" class="form-control" id="num1" name="num1" value="${num1}">
                    <div class="test-danger">${num1Error}</div>
                </div>
                <div class="row mb-2">
                    <div class="col">
                        <label>Currency 1</label>
                        <select class="form-select form-select-lg" name="currency1">
                            <c:set var="currency1" value="${currency1 != null ? currency1 : 'USD'}"/>
                            <option value="USD" ${currency1 == 'USD' ? 'selected' : ''}>USD</option>
                            <option value="EUR" ${currency1 == 'EUR' ? 'selected' : ''}>EUR</option>
                            <option value="GBP" ${currency1 == 'GBP' ? 'selected' : ''}>GBP</option>
                            <option value="JPY" ${currency1 == 'JPY' ? 'selected' : ''}>JPY</option>
                        </select>

                    </div>

                    <div class="col">
                        <label>Operator</label>
                        <select class="form-select form-select-lg" name="operator">
                            <option value="convert" ${operator == 'convert' ? 'selected' : ''}>convert</option>
                            <option value="add" ${operator == 'add' ? 'selected' : ''}>+</option>
                            <option value="sub" ${operator == 'sub' ? 'selected' : ''}>-</option>
                        </select>
                    </div>

                    <div class="col">
                        <label>Currency 2</label>
                        <select class="form-select form-select-lg" name="currency2">
                            <c:set var="currency2" value="${currency2 != null ? currency2 : 'USD'}"/>
                            <option <c:if test="${currency2 == 'USD'}">selected</c:if> value="USD">USD</option>
                            <option <c:if test="${currency2 == 'EUR'}">selected</c:if> value="EUR">EUR</option>
                            <option <c:if test="${currency2 == 'GBP'}">selected</c:if> value="GBP">GBP</option>
                            <option <c:if test="${currency2 == 'JPY'}">selected</c:if> value="JPY">JPY</option>
                        </select>
                    </div>
                </div>
                <div class="form-group mb-2">
                    <label for="num2">Number 2:</label>
                    <input type="text" class="form-control" id="num2" name="num2" value="${num2}">
                    <div class="test-danger">${num2Error}</div>
                </div>
                <button type="submit" class="btn btn-primary">Submit</button>
            </form>
            <div class="text-success text-xl">${result}</div>
        </div>
    </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/js/bootstrap.bundle.min.js" integrity="sha384-FKyoEForCGlyvwx9Hj09JcYn3nv7wiPVlz7YYwJrWVcXK/BmnVDxM+D2scQbITxI" crossorigin="anonymous"></script>

</body>
</html>
