<%-- file: /src/main/webapp/WEB-INF/ingredient.jsp v3 - Subtraction uses same ingredient, positive quantities only --%>
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
        const volumeUnits = ['tsp', 'tbsp', 'fl oz', 'cup', 'pint', 'quart'];
        const weightUnits = ['oz', 'lb', 'g', 'kg'];

        function toggleFields() {
            const operator = document.querySelector('select[name="operator"]').value;
            const ingredient2Section = document.getElementById('ingredient2Section');
            const scalarSection = document.getElementById('scalarSection');
            const name2Group = document.getElementById('name2Group');
            const ingredient2Title = document.getElementById('ingredient2Title');
            const quantity2Label = document.getElementById('quantity2Label');

            if (operator === 'add') {
                ingredient2Section.style.display = 'block';
                scalarSection.style.display = 'none';
                name2Group.style.display = 'block';
                ingredient2Title.textContent = 'Second Ingredient';
                quantity2Label.textContent = 'Quantity:';
            } else if (operator === 'subtract') {
                ingredient2Section.style.display = 'block';
                scalarSection.style.display = 'none';
                name2Group.style.display = 'none';
                ingredient2Title.textContent = 'Amount Used';
                quantity2Label.textContent = 'Quantity Used:';
            } else if (operator === 'multiply' || operator === 'divide') {
                ingredient2Section.style.display = 'none';
                scalarSection.style.display = 'block';
            }
        }

        function filterUnit2Options() {
            const unit1Value = document.getElementById('unit1').value;
            const unit2Select = document.getElementById('unit2');
            const unit2VolumeGroup = document.getElementById('unit2VolumeGroup');
            const unit2WeightGroup = document.getElementById('unit2WeightGroup');

            // If no unit1 selected yet, show all options
            if (!unit1Value) {
                unit2VolumeGroup.style.display = '';
                unit2WeightGroup.style.display = '';
                setOptionsEnabled(unit2VolumeGroup, true);
                setOptionsEnabled(unit2WeightGroup, true);
                return;
            }

            // Determine if unit1 is volume or weight
            const isVolume = volumeUnits.includes(unit1Value);

            // Show/hide the appropriate option groups
            if (isVolume) {
                // Show volume, hide weight
                unit2VolumeGroup.style.display = '';
                unit2WeightGroup.style.display = 'none';

                // Enable volume options, disable weight options
                setOptionsEnabled(unit2VolumeGroup, true);
                setOptionsEnabled(unit2WeightGroup, false);

                // If current selection is weight or empty, reset to default
                if (weightUnits.includes(unit2Select.value) || !unit2Select.value) {
                    unit2Select.value = '';
                }
            } else {
                // Show weight, hide volume
                unit2VolumeGroup.style.display = 'none';
                unit2WeightGroup.style.display = '';

                // Enable weight options, disable volume options
                setOptionsEnabled(unit2VolumeGroup, false);
                setOptionsEnabled(unit2WeightGroup, true);

                // If current selection is volume or empty, reset to default
                if (volumeUnits.includes(unit2Select.value) || !unit2Select.value) {
                    unit2Select.value = '';
                }
            }
        }

        function setOptionsEnabled(optgroup, enabled) {
            const options = optgroup.getElementsByTagName('option');
            for (let i = 0; i < options.length; i++) {
                options[i].disabled = !enabled;
            }
        }

        window.onload = function() {
            toggleFields();
            filterUnit2Options();
        };
    </script>
</head>
<body>

<div class="container my-4">
    <h1>Edward's Ingredient Calculator</h1>
    <p class="lead">Perform arithmetic operations on cooking ingredients with automatic unit conversion.</p>

    <div class="alert alert-info">
        <strong>Instructions:</strong>
        <ul class="mb-2">
            <li><strong>Add (+):</strong> Combine two different ingredients with compatible units (e.g., 2 cups flour + 1 cup sugar)</li>
            <li><strong>Subtract (−):</strong> Calculate remaining quantity after using some of an ingredient (e.g., 2.5 cups flour − 1.5 cups used = 1 cup remaining)</li>
            <li><strong>Multiply/Divide (× ÷):</strong> Scale a recipe using a <strong>scalar</strong> (a multiplier, e.g., use 2 to double a recipe or 0.5 to halve it)</li>
        </ul>
        <strong>Supported Units:</strong><br>
        <strong>Volume:</strong> tsp (teaspoon), tbsp (tablespoon), fl oz (fluid ounce), cup, pint, quart<br>
        <strong>Weight:</strong> oz (ounce), lb (pound), g (gram), kg (kilogram)<br>
        <em>Note: All quantities must be positive numbers (0.001 to 999999.999). Units must be compatible (volume with volume, weight with weight). If a subtraction results in a negative value, it indicates a shortage.</em>
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
                        <div class="text-danger">${name1Error}</div>
                    </div>
                    <div class="form-group mb-2">
                        <label for="quantity1">Quantity:</label>
                        <input type="text" class="form-control" id="quantity1" name="quantity1" placeholder="e.g., 2.5" value="${quantity1}">
                        <div class="text-danger">${quantity1Error}</div>
                    </div>
                    <div class="form-group mb-2">
                        <label for="unit1">Unit:</label>
                        <select class="form-select" id="unit1" name="unit1" onchange="filterUnit2Options()">
                            <option value="" <c:if test="${empty unit1}">selected</c:if> disabled>-- Choose a Unit --</option>
                            <optgroup label="Volume">
                                <option value="tsp" <c:if test="${unit1 == 'tsp'}">selected</c:if>>tsp (teaspoon)</option>
                                <option value="tbsp" <c:if test="${unit1 == 'tbsp'}">selected</c:if>>tbsp (tablespoon)</option>
                                <option value="fl oz" <c:if test="${unit1 == 'fl oz'}">selected</c:if>>fl oz (fluid ounce)</option>
                                <option value="cup" <c:if test="${unit1 == 'cup'}">selected</c:if>>cup</option>
                                <option value="pint" <c:if test="${unit1 == 'pint'}">selected</c:if>>pint</option>
                                <option value="quart" <c:if test="${unit1 == 'quart'}">selected</c:if>>quart</option>
                            </optgroup>
                            <optgroup label="Weight">
                                <option value="oz" <c:if test="${unit1 == 'oz'}">selected</c:if>>oz (ounce)</option>
                                <option value="lb" <c:if test="${unit1 == 'lb'}">selected</c:if>>lb (pound)</option>
                                <option value="g" <c:if test="${unit1 == 'g'}">selected</c:if>>g (gram)</option>
                                <option value="kg" <c:if test="${unit1 == 'kg'}">selected</c:if>>kg (kilogram)</option>
                            </optgroup>
                        </select>
                        <div class="text-danger">${unit1Error}</div>
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
                    <div class="text-danger small">${operatorError}</div>
                </div>
            </div>

            <!-- Second Ingredient (for add) / Amount Used (for subtract) -->
            <div class="col-md-4" id="ingredient2Section">
                <div class="ingredient-section">
                    <h5 id="ingredient2Title">Second Ingredient</h5>
                    <div class="form-group mb-2" id="name2Group">
                        <label for="name2">Name:</label>
                        <input type="text" class="form-control" id="name2" name="name2" placeholder="e.g., Sugar" value="${name2}">
                        <div class="text-danger">${name2Error}</div>
                    </div>
                    <div class="form-group mb-2">
                        <label for="quantity2" id="quantity2Label">Quantity:</label>
                        <input type="text" class="form-control" id="quantity2" name="quantity2" placeholder="e.g., 1.5" value="${quantity2}">
                        <div class="text-danger">${quantity2Error}</div>
                    </div>
                    <div class="form-group mb-2">
                        <label for="unit2">Unit:</label>
                        <select class="form-select" id="unit2" name="unit2">
                            <option value="" <c:if test="${empty unit2}">selected</c:if> disabled>-- Choose a Unit --</option>
                            <optgroup label="Volume" id="unit2VolumeGroup">
                                <option value="tsp" <c:if test="${unit2 == 'tsp'}">selected</c:if>>tsp (teaspoon)</option>
                                <option value="tbsp" <c:if test="${unit2 == 'tbsp'}">selected</c:if>>tbsp (tablespoon)</option>
                                <option value="fl oz" <c:if test="${unit2 == 'fl oz'}">selected</c:if>>fl oz (fluid ounce)</option>
                                <option value="cup" <c:if test="${unit2 == 'cup'}">selected</c:if>>cup</option>
                                <option value="pint" <c:if test="${unit2 == 'pint'}">selected</c:if>>pint</option>
                                <option value="quart" <c:if test="${unit2 == 'quart'}">selected</c:if>>quart</option>
                            </optgroup>
                            <optgroup label="Weight" id="unit2WeightGroup">
                                <option value="oz" <c:if test="${unit2 == 'oz'}">selected</c:if>>oz (ounce)</option>
                                <option value="lb" <c:if test="${unit2 == 'lb'}">selected</c:if>>lb (pound)</option>
                                <option value="g" <c:if test="${unit2 == 'g'}">selected</c:if>>g (gram)</option>
                                <option value="kg" <c:if test="${unit2 == 'kg'}">selected</c:if>>kg (kilogram)</option>
                            </optgroup>
                        </select>
                        <div class="text-danger">${unit2Error}</div>
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
                        <div class="text-danger">${scalarError}</div>
                    </div>
                </div>
            </div>
        </div>

        <button type="submit" class="btn btn-primary mt-3">Calculate</button>
        <a href="${pageContext.request.contextPath}/" class="btn btn-secondary mt-3">Back to Main Menu</a>
    </form>

    <!-- Calculation Error (for errors not tied to a specific field) -->
    <c:if test="${not empty calculationError}">
        <div class="alert alert-danger mt-3">
            <ul class="mb-0">${calculationError}</ul>
        </div>
    </c:if>

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
