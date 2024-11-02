const board = [
    ['W', 'W', 'W', 'W', 'W','W','W','W','W','W','W','W','W','W','W'],
    ['W', 'P', ' ', ' ', ' ',' ',' ',' ',' ',' ',' ',' ',' ','P','W'],
    ['W', ' ', 'B', ' ', ' ','W',' ',' ',' ',' ',' ',' ',' ',' ','W'],
    ['W', ' ', ' ', ' ', ' ','W',' ','B',' ',' ',' ',' ',' ','G','W'],
    ['W', ' ', ' ', ' ', 'B',' ',' ',' ',' ',' ',' ',' ',' ','G','W'],
    ['W', ' ', 'B', ' ', ' ',' ','G',' ',' ',' ',' ',' ',' ','G','W'],
    ['W', ' ', ' ', ' ', 'B',' ','W',' ',' ',' ',' ',' ',' ','G','W'],
    ['W', ' ', ' ', ' ', ' ',' ','W',' ',' ',' ',' ',' ',' ',' ','W'],
    ['W', 'P', ' ', ' ', ' ',' ',' ',' ',' ',' ',' ',' ',' ','P','W'],
    ['W', 'W', 'W', 'W', 'W','W','W','W','W','W','W','W','W','W','W'],
];

const gameBoard = document.getElementById('game-board');

function generateBoard(board) {
    board.forEach(row => {
        row.forEach(cell => {
            const cellDiv = document.createElement('li');
            cellDiv.classList.add('cell');

            switch (cell) {
                case ' ':
                    cellDiv.classList.add('empty');
                    // cellDiv.innerText = '⬛';
                    break;
                case 'W':
                    cellDiv.classList.add('wall');
                    cellDiv.innerText = '🧱';

                    break;
                case 'B':
                    cellDiv.classList.add('box');
                    cellDiv.innerText = '📦'; 
                    break;
                case 'G':
                    cellDiv.classList.add('goal');
                    cellDiv.innerText = '⭐'; 
                    break;
                case 'P':
                    cellDiv.classList.add('player');
                    cellDiv.innerText = '😃';
                    break;
            }

            gameBoard.appendChild(cellDiv);
        });
    });
}

generateBoard(board);
