<%--
  Created by IntelliJ IDEA.
  User: gabed
  Date: 11/15/2025
  Time: 12:18 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Character Fighter Calculator</title>
</head>
<body>

<div class="container CharFight">
    <h1>Character Fighter Calculator</h1>
    <p class="lead">Enter a Move Startup, Character Status, and Move Type for both characters to
    see who would win if they did those moves at the exact same time, the result will be from player 1's perspective...</p>
    <form method="post" action="${pageContext.request.contextPath}/CharacterFighter">
        <div class="row">
            <div class="col-2">
                <div class="form-group mb-2">
                    <label for="p1MoveStartup">Player 1 Move Startup: </label>
                    <input type="number" class="form-control" placeholder="Player 1 Move Startup"
                           aria-label="MoveStartup" name="p1MoveStartup" value="${p1MoveStartup}" id="p1MoveStartup">
                </div>
            </div>
            <div class="col-1 d-flex justify-content-center align-items-center display-3">
                <label for="p1CharacterStatus">Player 1 Character Status: </label>
                <select class="form-select form-select-lg" aria-label="Player1 Character Status" name="p1CharacterStatus" id="p1CharacterStatus">
                    <option value="stand" ${p1CharacterStatus == 'stand' ? 'selected' : ''}>Stand</option>
                    <option value="crouch" ${p1CharacterStatus == 'crouch' ? 'selected' : ''}>Crouch</option>
                    <option value="downed" ${p1CharacterStatus == 'downed' ? 'selected' : ''}>Downed</option>
                </select>
                <br>
            </div>
            <div class="col-1 d-flex justify-content-center align-items-center display-3">
                <label for="p1MoveType">Player 1 Move Type: </label>
                <select class="form-select form-select-lg" aria-label="Player 1 Move Type" name="p1MoveType" id="p1MoveType">
                    <option value="high" ${p1MoveType == 'high' ? 'selected' : ''}>High</option>
                    <option value="medium" ${p1MoveType == 'medium' ? 'selected' : ''}>Medium</option>
                    <option value="low" ${p1MoveType == 'low' ? 'selected' : ''}>Low</option>
                    <option value="command grab" ${p1MoveType == 'command grab' ? 'selected' : ''}>Command Grab</option>
                    <option value="throw" ${p1MoveType == 'throw' ? 'selected' : ''}>Throw</option>
                </select>
                <br>
            </div>
        </div>
        <div class="row">
            <div class="col-2">
                <div class="form-group mb-2">
                    <label for="p2MoveStartup">Player 2 Move Startup:</label>
                    <input type="number" class="form-control" placeholder="Player 2 MoveStartup"
                           aria-label="MoveStartup" name="p2MoveStartup" value="${p2MoveStartup}" id="p2MoveStartup">
                </div>
            </div>
            <div class="col-1 d-flex justify-content-center align-items-center display-3">
                <label for="p2CharacterStatus">Player 2 Character Status: </label>
                <select class="form-select form-select-lg" aria-label="Player 2 Character Status" name="p2CharacterStatus" id="p2CharacterStatus">
                    <option value="stand" ${p2CharacterStatus == 'stand' ? 'selected' : ''}>Stand</option>
                    <option value="crouch" ${p2CharacterStatus == 'crouch' ? 'selected' : ''}>Crouch</option>
                    <option value="downed" ${p2CharacterStatus == 'downed' ? 'selected' : ''}>Downed</option>
                </select>
            </div>
            <div class="col-1 d-flex justify-content-center align-items-center display-3">
                <label for="p2MoveType">Player 2 Move Type: </label>
                <select class="form-select form-select-lg" aria-label="Player 2 Move Type" name="p2MoveType" id="p2MoveType">
                    <option value="high" ${p2MoveType == 'high' ? 'selected' : ''}>High</option>
                    <option value="medium" ${p2MoveType == 'medium' ? 'selected' : ''}>Medium</option>
                    <option value="low" ${p2MoveType == 'low' ? 'selected' : ''}>Low</option>
                    <option value="command grab" ${p2MoveType == 'command grab' ? 'selected' : ''}>Command Grab</option>
                    <option value="throw" ${p2MoveType == 'throw' ? 'selected' : ''}>Throw</option>
                </select>
            </div>
            <br>
        </div>
        <button type="submit" class="btn btn-primary">Submit</button>
    </form>
    <ul class="text-danger">
        ${p1MoveStartupError}
        ${p2MoveStartupError}
        ${player1Error}
        ${player2Error}
    </ul>
    <div class="text-success fs-1">Player 1 ${comparisonResult}</div>
</div>
</body>
</html>
