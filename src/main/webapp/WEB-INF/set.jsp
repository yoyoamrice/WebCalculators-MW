<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Set Calculator</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
</head>
<body>

<div class="container my-4">
    <h1>Set Calculator</h1>
    <p class="lead">Welcome! Please enter two sets and select an operation to perform a calculation.</p>
    <form method="POST" action="setcalculator">
        <div class="row">
            <div class="col-5">
                <div class="card p-3 bg-light">
                    <h5>First Set</h5>
                    <div class="form-group mb-2">
                        <label class="form-label small">Name</label>
                        <input type="text" class="form-control" name="set1Name"
                               value="${set1Name == null ? 'Set A' : set1Name}">
                    </div>
                    <div class="form-group mb-2">
                        <label class="form-label small">Elements (comma separated)</label>
                        <input type="text" class="form-control" placeholder="0 elements represents an empty set" name="set1Elements" value="${set1Elements}">
                    </div>
                </div>
            </div>

            <div class="col-2 d-flex justify-content-center align-items-center">
                <select class="form-select form-select-lg" aria-label="Set operator" name="operator">
                    <option <c:if test="${operator == 'union'}">selected</c:if> value="union">Union (∪)</option>
                    <option <c:if test="${operator == 'intersection'}">selected</c:if> value="intersection">Intersection (∩)</option>
                    <option <c:if test="${operator == 'difference'}">selected</c:if> value="difference">Difference (-)</option>
                </select>
            </div>

            <div class="col-5">
                <div class="card p-3 bg-light">
                    <h5>Second Set</h5>
                    <div class="form-group mb-2">
                        <label class="form-label small">Name</label>
                        <input type="text" class="form-control" name="set2Name"
                               value="${set2Name == null ? 'Set B' : set2Name}">
                    </div>
                    <div class="form-group mb-2">
                        <label class="form-label small">Elements (comma separated)</label>
                        <input type="text" class="form-control" name="set2Elements" value="${set2Elements}">
                    </div>
                </div>
            </div>
        </div>

        <div class="mt-3">
            <button type="submit" class="btn btn-primary">Calculate</button>
        </div>
    </form>

    <ul class="text-danger mt-3">
        ${set1NameError}
        ${set1ElementsError}
        ${operatorError}
        ${set2NameError}
        ${set2ElementsError}
    </ul>

    <div class="text-success fs-1">
        ${result}
    </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
</body>
</html>