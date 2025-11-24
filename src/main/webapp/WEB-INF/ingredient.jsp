<%-- file: /src/main/webapp/WEB-INF/ingredient.jsp v1 - Initial ingredient calculator interface --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Ingredient Calculator</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <style>
        .ingredient-section {
            border: 2px solid #dee2e6;
            border-radius: 8px;
            padding: 15px;
            background-color: #f8f9fa;
        }
        .operator-section {
            display: flex;
            align-items: center;
            justify-content: center;
            min-height: 200px;
        }
        .scalar-section {
            border: 2px solid #e3f2fd;
            border-radius: 8px;
            padding: 15px;
            background-color: #e3f2fd;
        }
    </style>
    <script>
        function toggleFields() {
            const operator = document.querySelector('select[name="operator"]').value;
            const ingredient2Section = document.getElementById('ingredient2Section');
            const scalarSection = document.getElementById('scalarSection');

            if (operator === 'add' || operator === 'subtract') {
                ingredient2Section.style.display = 'block';
                scalarSection.style.display = 'none';
            } else if (operator === 'multiply' || operator === 'divide') {
                ingredient2Section.style.display = 'none';
                scalarSection.style.display = 'block';
            }
        }

        window.onload = function() {
            toggleFields();
        };
    </script>
</head>
<body>

<div class="container my-4">
    <h1>Edward's Ingredient Calculator</h1>
    <p class="lead">Perform arithmetic operations on cooking ingredients with automatic unit conversion.</p>

    <div class="alert alert-info">
        <strong>Supported Units:</strong><br>
        <strong>Volume:</strong> tsp, tbsp, fl oz, cup, pint, quart<br>
        <strong>Weight:</strong> oz, lb, g, kg<br>
        <em>Note: Operations only work between compatible unit types (volume with volume, weight with weight)</em>
    </div>

    <form method="POST" action="ingredient">
        <div class="row">
            <!-- First Ingredient -->
            <div class="col-md-4">
                <div class="ingredient-section">
                    <h5>First Ingredient</h5>
                    <div class="form-group mb-2">
                        <label for="name1">Name:</label>
                        <input type="text" class="form-control" id="name1" name="name1" placeholder="e.g., Flour" value="${name1}">
                    </div>
                    <div class="form-group mb-2">
                        <label for="quantity1">Quantity:</label>
                        <input type="text" class="form-control" id="quantity1" name="quantity1" placeholder="e.g., 2.5" value="${quantity1}">
                    </div>
                    <div class="form-group mb-2">
                        <label for="unit1">Unit:</label>
                        <input type="text" class="form-control" id="unit1" name="unit1" placeholder="e.g., cup" value="${unit1}">
                    </div>
                </div>
            </div>

            <!-- Operator -->
            <div class="col-md-1">
                <div class="operator-section">
                    <select class="form-select form-select-lg" aria-label="Mathematical operator" name="operator" onchange="toggleFields()">
                        <option <c:if test="${operator == 'add'}">selected</c:if> value="add">+</option>
                        <option <c:if test="${operator == 'subtract'}">selected</c:if> value="subtract">−</option>
                        <option <c:if test="${operator == 'multiply'}">selected</c:if> value="multiply">×</option>
                        <option <c:if test="${operator == 'divide'}">selected</c:if> value="divide">÷</option>
                    </select>
                </div>
            </div>

            <!-- Second Ingredient (for add/subtract) -->
            <div class="col-md-4" id="ingredient2Section">
                <div class="ingredient-section">
                    <h5>Second Ingredient</h5>
                    <div class="form-group mb-2">
                        <label for="name2">Name:</label>
                        <input type="text" class="form-control" id="name2" name="name2" placeholder="e.g., Sugar" value="${name2}">
                    </div>
                    <div class="form-group mb-2">
                        <label for="quantity2">Quantity:</label>
                        <input type="text" class="form-control" id="quantity2" name="quantity2" placeholder="e.g., 1.5" value="${quantity2}">
                    </div>
                    <div class="form-group mb-2">
                        <label for="unit2">Unit:</label>
                        <input type="text" class="form-control" id="unit2" name="unit2" placeholder="e.g., cup" value="${unit2}">
                    </div>
                </div>
            </div>

            <!-- Scalar (for multiply/divide) -->
            <div class="col-md-4" id="scalarSection" style="display: none;">
                <div class="scalar-section">
                    <h5>Scalar Value</h5>
                    <div class="form-group mb-2">
                        <label for="scalar">Value:</label>
                        <input type="text" class="form-control" id="scalar" name="scalar" placeholder="e.g., 2" value="${scalar}">
                        <small class="form-text text-muted">Enter the number to multiply or divide by</small>
                    </div>
                </div>
            </div>
        </div>

        <button type="submit" class="btn btn-primary mt-3">Calculate</button>
        <a href="${pageContext.request.contextPath}/" class="btn btn-secondary mt-3">Back to Main Menu</a>
    </form>

    <!-- Error Messages -->
    <ul class="text-danger mt-3">
        ${name1Error}
        ${quantity1Error}
        ${unit1Error}
        ${operatorError}
        ${name2Error}
        ${quantity2Error}
        ${unit2Error}
        ${scalarError}
        ${calculationError}
    </ul>

    <!-- Result -->
    <c:if test="${not empty result}">
        <div class="alert alert-success mt-3">
            <h4>Result:</h4>
            <p class="fs-3 mb-0">${result}</p>
        </div>
    </c:if>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
</body>
</html>
