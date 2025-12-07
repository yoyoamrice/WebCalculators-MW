<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Depreciation Calculator</title>
    <style>
        .error { color: red; font-size: 0.9em; margin-left: 10px; }
        .form-group { margin-bottom: 15px; }
        label { display: inline-block; width: 150px; font-weight: bold; }
        .result-box { border: 2px solid green; padding: 15px; background-color: #f0fff0; margin-top: 20px; }
    </style>
</head>
<body>
<h1>Straight-Line Depreciation Calculator</h1>

<c:if test="${not empty errors.globalError}">
    <p class="error" style="font-size: 1.2em;">${errors.globalError}</p>
</c:if>

<form method="post" action="depreciation">

    <div class="form-group">
        <label for="assetCost">Asset Cost ($):</label>
        <input type="text" id="assetCost" name="assetCost" value="${param.assetCost}">
        <c:if test="${not empty errors.costError}">
            <span class="error">${errors.costError}</span>
        </c:if>
    </div>

    <div class="form-group">
        <label for="salvageValue">Salvage Value ($):</label>
        <input type="text" id="salvageValue" name="salvageValue" value="${param.salvageValue}">
        <c:if test="${not empty errors.salvageError}">
            <span class="error">${errors.salvageError}</span>
        </c:if>
    </div>

    <div class="form-group">
        <label for="usefulLife">Useful Life (Years):</label>
        <input type="text" id="usefulLife" name="usefulLife" value="${param.usefulLife}">
        <c:if test="${not empty errors.lifeError}">
            <span class="error">${errors.lifeError}</span>
        </c:if>
    </div>

    <div class="form-group">
        <label for="operation">Calculation:</label>
        <select name="operation" id="operation">
            <option value="annual" ${param.operation == 'annual' ? 'selected' : ''}>Annual Depreciation</option>
            <option value="accumulated" ${param.operation == 'accumulated' ? 'selected' : ''}>Accumulated Depreciation</option>
            <option value="itemValue" ${param.operation == 'itemValue' ? 'selected' : ''}>Item Value</option>
        </select>
    </div>

    <div class="form-group">
        <label for="targetYear">Target Year:</label>
        <input type="text" id="targetYear" name="targetYear" value="${param.targetYear}" placeholder="If applicable">
        <c:if test="${not empty errors.yearError}">
            <span class="error">${errors.yearError}</span>
        </c:if>
        <br><small><i>(Required only for Accumulated or Item Value calculations)</i></small>
    </div>

    <button type="submit">Calculate</button>
</form>

<c:if test="${not empty resultFraction}">
    <div class="result-box">
        <h2>Result: ${resultType}</h2>
        <p><strong>Fraction:</strong> ${resultFraction}</p>
        <p><strong>Decimal:</strong> ${resultDecimal}</p>
    </div>
</c:if>

</body>
</html>
