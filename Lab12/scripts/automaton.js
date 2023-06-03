var grid = [];

function initialize(size) {
    grid = new Array(size);
    for (var i = 0; i < size; i++) {
        grid[i] = new Array(size);
        for (var j = 0; j < size; j++) {
            grid[i][j] = Math.random() < 0.5;
        }
    }
}

function step() {
    var newGrid = new Array(grid.length);
    for (var i = 0; i < grid.length; i++) {
        newGrid[i] = new Array(grid[i].length);
        for (var j = 0; j < grid[i].length; j++) {
            var liveNeighbors = countLiveNeighbors(i, j);
            if (grid[i][j]) {
                newGrid[i][j] = liveNeighbors === 2 || liveNeighbors === 3;
            } else {
                newGrid[i][j] = liveNeighbors === 3;
            }
        }
    }
    grid = newGrid;
}

function countLiveNeighbors(i, j) {
    var liveNeighbors = 0;
    for (var di = -1; di <= 1; di++) {
        for (var dj = -1; dj <= 1; dj++) {
            if (di !== 0 || dj !== 0) {
                var ni = i + di;
                var nj = j + dj;
                if (ni >= 0 && ni < grid.length && nj >= 0 && nj < grid[i].length && grid[ni][nj]) {
                    liveNeighbors++;
                }
            }
        }
    }
    return liveNeighbors;
}

function getState() {
    return grid;
}

function setState(newState) {
    grid = newState;
}
