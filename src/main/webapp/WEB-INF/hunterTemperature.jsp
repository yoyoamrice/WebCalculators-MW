<%--
  Created by IntelliJ IDEA.
  User: hunte
  Date: 11/21/2025
  Time: 9:14 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Hunter's Calculator</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
</head>
<body class="my-4">
<h1>Temperature Calculator</h1>
<p class="lead">Select a Temperature Type to start and end with.<br/>Then input a value.</p>
<form method="POST" action="hunterTemperature">
    <div class="row">
        <div class="col-2 m-2 justify-content-center align-items-center display-3">
            <select class="form-select form-select-lg mb-2" aria-label="Temperature Type" name="startType">
                <option <c:if test="${temperatureType == 'fahrenheit'}">selected</c:if> value="fahrenheit">Fahrenheit</option>
                <option <c:if test="${temperatureType == 'celsius'}">selected</c:if> value="celsius">Celsius</option>
                <option <c:if test="${temperatureType == 'kelvin'}">selected</c:if> value="kelvin">Kelvin</option>
            </select>
            <select class="form-select form-select-lg mb-2" aria-label="Temperature Type" name="endType">
                <option <c:if test="${temperatureType == 'fahrenheit'}">selected</c:if> value="fahrenheit">Fahrenheit</option>
                <option <c:if test="${temperatureType == 'celsius'}">selected</c:if> value="celsius">Celsius</option>
                <option <c:if test="${temperatureType == 'kelvin'}">selected</c:if> value="kelvin">Kelvin</option>
            </select>
            <div class="form-group mb-2">
                <input type="text" class="form-control" placeholder="Starting Value" aria-label="Starting Value" name="startValue" value="${startValue}">
            </div>
            <div class="text-success fs-1">${endValue}</div>
        </div>
    </div>

    <button type="submit" class="btn btn-primary">Submit</button>
</form>
<ul class="text-danger">
    ${generalError}
    ${startValueError}
    ${startTypeError}
    ${endTypeError}
</ul>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
</body>
</html>
