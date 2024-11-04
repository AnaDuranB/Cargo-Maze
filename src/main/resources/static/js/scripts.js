/*const board = [
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
];*/

const api = apiClient;
const board = api.getGameSessionBoard("1"); // se debera cambiar para que se obtenga el id de la sesion presionada.}


const gameBoard = document.getElementById('game-board');

function generateBoard(board) {
    console.log(typeof(board));
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
                case 'T':
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
