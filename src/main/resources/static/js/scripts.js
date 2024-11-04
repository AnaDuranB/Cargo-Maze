const scripts = (() => {
    const api = apiClient;

    async function initializeBoard() {
        try {
            const board = await api.getGameSessionBoard("1"); // Esperar a que la promesa se resuelva
            console.log(board);
            generateBoard(board);
        } catch (error) {
            console.error("Error al obtener el tablero de la sesión:", error);
        }
    }

    function generateBoard(board) {
        const gameBoard = document.getElementById('game-board');
        if (!gameBoard) {
            console.error("El elemento game-board no se encontró en el DOM");
            return;
        }

        // Limpiar el tablero antes de generarlo
        gameBoard.innerHTML = '';

        console.log(typeof(board));
        board.forEach(row => {
            row.forEach(cell => {
                const cellDiv = document.createElement('li');
                cellDiv.classList.add('cell');

                switch (cell) {
                    case '.':
                        cellDiv.classList.add('empty');
                        // cellDiv.innerText = '⬛';
                        break;
                    case '#':
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

    return {
        initializeBoard
    };
})();

// Hacer que initializeBoard esté disponible globalmente
window.initializeBoard = scripts.initializeBoard;