<%--
  Created by IntelliJ IDEA.
  User: yoyoa
  Date: 12/2/2025
  Time: 9:15 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Yousif Money Calculator</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-sRIl4kxILFvY47J16cr9ZwB07vP4J8+LH7qKQnuqkuIAvNWLzeN8tE5YBujZqJLB" crossorigin="anonymous">
</head>
<body>
<div class = "container">


<h1>Yousif Money Calculator</h1>
<form action = "money" method = "post">
    <div class = "row">
        <div class = "col-3">
            <label for="money1Amount" class="form-label">Money Amount</label>
            <input type="text" class="form-control" id="money1Amount" name = "money1Amount"/>
            <ul class="text-danger">
                ${money1AmountError}
            </ul>
            <label for="money1Currency" class="form-label">Money Currency</label>
            <select class="form-select" name = "money1Currency" id = "money1Currency">
                <option selected>Open this select menu</option>
                <option value="USD">US Dollar</option>
                <option value="CAD">Canadian Dollar</option>
                <option value="GPB">Great Britain Pound</option>
            </select>
            <ul class="text-danger">
                ${money1CurrencyError}
            </ul>
        </div>
        <div class = "col-3">
            <label for="operator" class="form-label">choice operation</label>
            <select class="form-select" name = "operator" id = "operator">
                <option selected>Open this select menu</option>
                <option value="add">+</option>
                <option value="subtract">-</option>
                <option value="divide">/</option>
                <option value="multiply">*</option>
            </select>
            <ul class="text-danger">
                ${operatorError}
            </ul>
        </div>
        <div class = "col-3">
            <label for="money2Amount" class="form-label">Money Amount</label>
            <input type="text" class="form-control" id="money2Amount" name = "money2Amount"/>
            <ul class="text-danger">
                ${money2AmountError}
            </ul>
            <label for="money2Currency" class="form-label">Money Currency</label>
            <select class="form-select" name = "money2Currency" id = "money2Currency">
                <option selected>Open this select menu</option>
                <option value="USD">US Dollar</option>
                <option value="CAD">Canadian Dollar</option>
                <option value="GPB">Great Britain Pound</option>
            </select>
            <ul class="text-danger">
                ${money2CurrencyError}
            </ul>
        </div>
    </div>
    <button type = "submit" value = "submit" class = "btn btn-primary">
Submit
    </button>
</form>

    <div class="text-success fs-1">${result}</div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/js/bootstrap.bundle.min.js" integrity="sha384-FKyoEForCGlyvwx9Hj09JcYn3nv7wiPVlz7YYwJrWVcXK/BmnVDxM+D2scQbITxI" crossorigin="anonymous"></script>
</body>
</html>
